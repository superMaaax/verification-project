#!/usr/bin/env python3
"""
Stage 4 — JPF verification over OpenJML RAC bytecode (SpecGenBench few-shot JML).

Generates one JPF config + one harness per benchmark, compiles harnesses,
runs JPF with a wall-clock timeout per benchmark, and writes:

  results/jpf_verification.csv
  results/traces/<Method>.txt   (full JPF stdout/stderr on failures)

CSV columns (exact):
  method, jpf_pass, violation_type, counterexample, instructions, states_new,
  states_end, depth, timeout, notes

Pass semantics in `notes` (prefix):
  pass_kind=A   genuine exploration (enough states / instructions / symbolic choices)
  pass_kind=B   trivial / shallow (very small state space)
  pass_kind=C   suspicious / silent-risk (heuristic; see notes text)

Prerequisites:
  - RAC classes under benchmark/SpecGenBench/common_few_shot_jml_rac/classes
  - jmlruntime.jar next to that tree (common_few_shot_jml_rac/jmlruntime.jar)
  - Built JPF under .tools/jpf-core (see PIPELINE.md; ClassFile MAX_SUPPORTED_VERSION 60)
  - JDK 11 on PATH or JAVA_HOME for javac / java running RunJPF.jar
"""

from __future__ import annotations

import argparse
import csv
import os
import re
import shutil
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable


try:
    _SELF = Path(__file__).resolve()
except NameError:  # pragma: no cover - interactive exec
    _SELF = Path.cwd()
REPO_ROOT = _SELF.parents[3]
DEFAULT_SRC = REPO_ROOT / "benchmark" / "SpecGenBench" / "common_few_shot_jml"
DEFAULT_RAC = REPO_ROOT / "benchmark" / "SpecGenBench" / "common_few_shot_jml_rac" / "classes"
DEFAULT_JMLRT = REPO_ROOT / "benchmark" / "SpecGenBench" / "common_few_shot_jml_rac" / "jmlruntime.jar"
DEFAULT_JPF = REPO_ROOT / ".tools" / "jpf-core"
DEFAULT_OUT = REPO_ROOT / "benchmark" / "SpecGenBench" / "stage4_common_few_shot_jpf"
# OpenJML bundles JML helper classes (e.g. org.jmlspecs.lang.internal.bigint) under the
# patched JDK module tree — RAC bytecode may reference them at runtime.
DEFAULT_OPENJML_JAVA_BASE = REPO_ROOT / ".tools" / "openjml" / "jdk" / "modules" / "java.base"


@dataclass(frozen=True)
class Bench:
    name: str
    java_path: Path


def discover_benchmarks(bench_root: Path) -> list[Bench]:
    out: list[Bench] = []
    for child in sorted(bench_root.iterdir()):
        if not child.is_dir():
            continue
        primary = child / f"{child.name}.java"
        if primary.is_file():
            out.append(Bench(child.name, primary))
            continue
        java_files = sorted(
            p for p in child.glob("*.java") if not p.name.endswith("Driver.java")
        )
        if len(java_files) == 1:
            out.append(Bench(child.name, java_files[0]))
    return out


def strip_jml(text: str) -> str:
    text = re.sub(r"/\*@[\s\S]*?@\*/", " ", text)
    text = re.sub(r"//@.*", " ", text)
    return text


def normalize_ws(text: str) -> str:
    parts: list[str] = []
    for line in text.splitlines():
        t = line.strip()
        if not t:
            continue
        parts.append(t)
    return " ".join(parts)


@dataclass
class ParsedMethod:
    is_static: bool
    return_type: str
    name: str
    params: list[tuple[str, str]]  # (type, name)
    raw_visibility: str  # public, package, private


def _split_params(args: str) -> list[tuple[str, str]]:
    args = args.strip()
    if not args:
        return []
    chunks: list[str] = []
    depth = 0
    cur: list[str] = []
    for ch in args:
        if ch == "<":
            depth += 1
            cur.append(ch)
        elif ch == ">":
            depth = max(0, depth - 1)
            cur.append(ch)
        elif ch == "," and depth == 0:
            chunks.append("".join(cur).strip())
            cur = []
        else:
            cur.append(ch)
    if cur:
        chunks.append("".join(cur).strip())
    out: list[tuple[str, str]] = []
    for chunk in chunks:
        chunk = chunk.strip()
        if not chunk:
            continue
        if " " not in chunk:
            continue
        pname = chunk.rsplit(None, 1)[-1]
        ptype = chunk[: -len(pname)].strip()
        if pname.endswith("[]"):
            pname = pname[:-2].strip()
            ptype = f"{ptype}[]"
        out.append((ptype, pname))
    return out


def parse_methods(class_name: str, text: str) -> list[ParsedMethod]:
    text = strip_jml(text)
    text = re.sub(r"\bstatic\s+(public|protected|private)\b", r"\1 static", text)
    text = normalize_ws(text)
    methods: list[ParsedMethod] = []
    # Method declarations ending with `{` (single-line normalized body start)
    pat = re.compile(
        r"(public|private|protected)?\s*(static)?\s*([\w\[\].]+)\s+(\w+)\s*\(([^)]*)\)\s*(?:throws\s+[\w.,\s]+)?\s*\{"
    )
    for m in pat.finditer(text):
        vis, st, rt, nm, args = m.group(1) or "", m.group(2) or "", m.group(3), m.group(4), m.group(5)
        # Constructors look like `public ClassName(...)` — same token twice.
        if nm == class_name and rt == class_name:
            continue
        is_static = st == "static"
        vis_label = vis or "package"
        params = _split_params(args)
        methods.append(
            ParsedMethod(
                is_static=is_static,
                return_type=rt,
                name=nm,
                params=params,
                raw_visibility=vis_label,
            )
        )
    return methods


def pick_method(class_name: str, methods: list[ParsedMethod]) -> ParsedMethod | None:
    def ok_vis(pm: ParsedMethod) -> bool:
        return pm.raw_visibility != "private"

    cands = [m for m in methods if ok_vis(m)]
    if not cands:
        return None

    def score(pm: ParsedMethod) -> tuple[int, int, int]:
        name_match = 1 if pm.name.lower() == class_name.lower() else 0
        static_pref = 1 if pm.is_static else 0
        arity = len(pm.params)
        return (name_match, static_pref, -arity)

    cands.sort(key=score, reverse=True)
    return cands[0]


def java_init_for_param(ptype: str, pname: str, idx: int) -> list[str]:
    t = ptype.strip()
    lines: list[str] = []
    if t == "int":
        lines.append(f"int {pname} = gov.nasa.jpf.vm.Verify.getInt(-4, 4);")
    elif t in ("boolean",):
        lines.append(f"boolean {pname} = gov.nasa.jpf.vm.Verify.getInt(0, 1) != 0;")
    elif t in ("byte",):
        lines.append(f"byte {pname} = (byte) gov.nasa.jpf.vm.Verify.getInt(-2, 3);")
    elif t in ("short",):
        lines.append(f"short {pname} = (short) gov.nasa.jpf.vm.Verify.getInt(0, 8);")
    elif t in ("long",):
        lines.append(f"long {pname} = (long) gov.nasa.jpf.vm.Verify.getInt(-3, 5);")
    elif t in ("char",):
        lines.append(f"char {pname} = (char) gov.nasa.jpf.vm.Verify.getInt(32, 126);")
    elif t in ("double",):
        lines.append(f"double {pname} = (double) gov.nasa.jpf.vm.Verify.getInt(-2, 2);")
    elif t in ("float",):
        lines.append(f"float {pname} = (float) gov.nasa.jpf.vm.Verify.getInt(-1, 1);")
    elif t.endswith("[]") and t[:-2].strip() == "int":
        lines.append(f"int len{idx} = gov.nasa.jpf.vm.Verify.getInt(0, 4);")
        lines.append(f"int[] {pname} = new int[len{idx}];")
        lines.append(f"for (int __i{idx} = 0; __i{idx} < len{idx}; __i{idx}++) {{")
        lines.append(f"  {pname}[__i{idx}] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);")
        lines.append("}")
    elif t == "String":
        lines.append(f"int slen{idx} = gov.nasa.jpf.vm.Verify.getInt(0, 2);")
        lines.append(f"char[] __cb{idx} = new char[slen{idx}];")
        lines.append(f"for (int __j{idx} = 0; __j{idx} < slen{idx}; __j{idx}++) {{")
        lines.append(
            f"  __cb{idx}[__j{idx}] = (char) gov.nasa.jpf.vm.Verify.getInt(97, 122);"
        )
        lines.append("}")
        lines.append(f"String {pname} = new String(__cb{idx});")
    else:
        raise ValueError(f"unsupported param type: {ptype}")
    return lines


def emit_harness(class_name: str, pm: ParsedMethod) -> str:
    harness_name = f"Harness_{class_name}"
    inits: list[str] = []
    for i, (pt, pn) in enumerate(pm.params):
        try:
            inits.extend(java_init_for_param(pt, pn, i))
        except ValueError as exc:
            raise ValueError(str(exc)) from exc
    args = ", ".join(pn for _, pn in pm.params)
    call: str
    if pm.is_static:
        if pm.return_type == "void":
            call = f"{class_name}.{pm.name}({args});"
        else:
            call = f"{pm.return_type} __r = {class_name}.{pm.name}({args});"
    else:
        if pm.return_type == "void":
            call = f"new {class_name}().{pm.name}({args});"
        else:
            call = f"{pm.return_type} __r = new {class_name}().{pm.name}({args});"
    body = "\n    ".join(inits + [call])
    return f"""import gov.nasa.jpf.vm.Verify;

public class {harness_name} {{
  public static void main(String[] args) throws Exception {{
    {body}
  }}
}}
"""


def write_jpf(
    path: Path,
    harness_name: str,
    classpath_entries: list[Path],
) -> None:
    cp = os.pathsep.join(str(p.resolve()) for p in classpath_entries)
    content = "\n".join(
        [
            f"target={harness_name}",
            f"classpath={cp}",
            "vm.enable_assertions=*",
            "report.console.finished=statistics",
            "report.console.property_violation=error,trace",
            "search.multiple_errors=true",
            "search.depth_limit=40",
            "search.state_limit=50000",
            "",
        ]
    )
    path.write_text(content, encoding="utf-8")


def parse_jpf_stats(text: str) -> dict[str, str]:
    stats: dict[str, str] = {}
    m = re.search(r"states:\s+new=(\d+),visited=(\d+),backtracked=(\d+),end=(\d+)", text)
    if m:
        stats["states_new"] = m.group(1)
        stats["states_visited"] = m.group(2)
        stats["states_end"] = m.group(4)
    m = re.search(r"maxDepth=(\d+)", text)
    if m:
        stats["depth"] = m.group(1)
    m = re.search(r"instructions:\s+(\d+)", text)
    if m:
        stats["instructions"] = m.group(1)
    m = re.search(r"data=(\d+)", text)
    if m:
        stats["data_choice_generators"] = m.group(1)
    return stats


def refine_violation_type(output: str, base: str) -> str:
    m = re.search(r"(java\.lang\.[\w]+)", output)
    if m and base:
        return f"{base}:{m.group(1)}"
    return base


def jpf_detect_failure(output: str) -> tuple[bool, str]:
    """
    Returns (failed, violation_type).
    failed=True means we treat the run as NOT a successful verification pass.
    """
    if re.search(r"======================================================\s*error", output):
        if "NoUncaughtExceptionsProperty" in output:
            return True, "NoUncaughtExceptionsProperty"
        if "JML" in output or "org.jmlspecs" in output:
            return True, "JMLRuntimeAssertion"
        if "AssertionError" in output:
            return True, "AssertionError"
        return True, "JPFPropertyViolation"
    if "java.lang." in output and "Exception" in output and "search finished" not in output:
        return True, "JavaException"
    return False, ""


def classify_pass_kind(output: str, stats: dict[str, str]) -> str:
    if jpf_detect_failure(output)[0]:
        return "n/a"
    try:
        nnew = int(stats.get("states_new", "0"))
        end = int(stats.get("states_end", "0"))
        data_cg = int(stats.get("data_choice_generators", "0"))
    except ValueError:
        return "C"
    if "search finished" not in output:
        return "C"
    # Ignore raw instruction counts (JPF bootstrap dominates). Prefer branching.
    if data_cg >= 3 or nnew >= 10 or end >= 8:
        return "A"
    if data_cg <= 1 and nnew <= 6 and end <= 4:
        return "B"
    return "A"


def short_counterexample(output: str, max_len: int = 600) -> str:
    tail = "\n".join(output.splitlines()[-80:])
    tail = tail.replace("\r\n", "\n")
    if len(tail) > max_len:
        tail = tail[: max_len - 3] + "..."
    return tail.replace("\n", "\\n")


def main() -> int:
    ap = argparse.ArgumentParser(description="Stage 4 JPF over RAC bytecode.")
    ap.add_argument("--src-root", type=Path, default=DEFAULT_SRC)
    ap.add_argument("--rac-classes", type=Path, default=DEFAULT_RAC)
    ap.add_argument("--jml-runtime", type=Path, default=DEFAULT_JMLRT)
    ap.add_argument("--jpf-root", type=Path, default=DEFAULT_JPF)
    ap.add_argument(
        "--openjml-java-base",
        type=Path,
        default=DEFAULT_OPENJML_JAVA_BASE,
        help="OpenJML jdk/modules/java.base (for org.jmlspecs.lang.internal.* at runtime).",
    )
    ap.add_argument("--out", type=Path, default=DEFAULT_OUT)
    ap.add_argument("--java11-home", type=Path, default=None, help="JDK 11 home (for javac/java).")
    ap.add_argument("--wall-timeout", type=float, default=25.0, help="Seconds per JPF process (hard kill).")
    ap.add_argument("--max-methods", type=int, default=None)
    args = ap.parse_args()

    java11 = args.java11_home
    if java11 is None:
        try:
            out = subprocess.run(
                ["/usr/libexec/java_home", "-v", "11"],
                check=True,
                capture_output=True,
                text=True,
            )
            java11 = Path(out.stdout.strip())
        except (subprocess.CalledProcessError, FileNotFoundError):
            java11 = Path(os.environ.get("JAVA_HOME", ""))

    if not java11 or not (java11 / "bin" / "java").is_file():
        print("error: need JDK 11 (set --java11-home or install /usr/libexec/java_home -v 11)", file=sys.stderr)
        return 1

    javac = java11 / "bin" / "javac"
    java = java11 / "bin" / "java"
    jpf_root = args.jpf_root
    run_jpf = jpf_root / "build" / "RunJPF.jar"
    jpf_classes = jpf_root / "build" / "jpf-classes.jar"
    jpf_jar = jpf_root / "build" / "jpf.jar"
    for p in (run_jpf, jpf_classes, jpf_jar, args.jml_runtime, args.rac_classes):
        if not p.exists():
            print(f"error: missing path: {p}", file=sys.stderr)
            return 1
    extra_java_base = args.openjml_java_base
    if not extra_java_base.is_dir():
        print(f"warning: OpenJML java.base module path missing (JPF may fail on some RAC): {extra_java_base}", file=sys.stderr)

    out_root: Path = args.out
    gen = out_root / "generated" / "harness_src"
    hbuild = out_root / "generated" / "harness_build"
    jpf_dir = out_root / "jpf"
    res = out_root / "results"
    traces = res / "traces"
    shutil.rmtree(out_root, ignore_errors=True)
    gen.mkdir(parents=True, exist_ok=True)
    hbuild.mkdir(parents=True, exist_ok=True)
    jpf_dir.mkdir(parents=True, exist_ok=True)
    res.mkdir(parents=True, exist_ok=True)
    traces.mkdir(parents=True, exist_ok=True)

    site = Path.home() / ".jpf" / "site.properties"
    site.parent.mkdir(parents=True, exist_ok=True)
    site.write_text(
        f"jpf-core={jpf_root}\nextensions=${{jpf-core}}\n",
        encoding="utf-8",
    )

    benches = discover_benchmarks(args.src_root)
    if args.max_methods is not None:
        benches = benches[: args.max_methods]

    harness_files: list[Path] = []
    rows_meta: list[tuple[Bench, ParsedMethod, str]] = []

    for bench in benches:
        cls = bench.name
        rac_cls = args.rac_classes / f"{cls}.class"
        if not rac_cls.is_file():
            rows_meta.append(
                (
                    bench,
                    None,  # type: ignore[arg-type]
                    "no_rac_class",
                )
            )
            continue
        text = bench.java_path.read_text(encoding="utf-8", errors="replace")
        methods = parse_methods(cls, text)
        pm = pick_method(cls, methods)
        if pm is None:
            rows_meta.append((bench, None, "no_harnessable_method"))  # type: ignore[arg-type]
            continue
        try:
            src = emit_harness(cls, pm)
        except ValueError as e:
            rows_meta.append((bench, None, f"harness_unsupported:{e}"))  # type: ignore[arg-type]
            continue
        hpath = gen / f"Harness_{cls}.java"
        hpath.write_text(src, encoding="utf-8")
        harness_files.append(hpath)
        rows_meta.append((bench, pm, "ok"))

    if harness_files:
        cp_parts = [
            str(args.rac_classes.resolve()),
            str(hbuild.resolve()),
            str(args.jml_runtime.resolve()),
            str(extra_java_base.resolve()) if extra_java_base.is_dir() else "",
            str(jpf_classes.resolve()),
            str(jpf_jar.resolve()),
        ]
        cp_parts = [p for p in cp_parts if p]
        cp = os.pathsep.join(cp_parts)
        cmd = [str(javac), "--release", "11", "-cp", cp, "-d", str(hbuild)] + [str(p) for p in harness_files]
        r = subprocess.run(cmd, capture_output=True, text=True)
        if r.returncode != 0:
            print("javac failed:", r.stderr or r.stdout, file=sys.stderr)
            return 1
    elif not rows_meta:
        print("error: no benchmarks discovered", file=sys.stderr)
        return 1

    csv_path = res / "jpf_verification.csv"
    fieldnames = [
        "method",
        "jpf_pass",
        "violation_type",
        "counterexample",
        "instructions",
        "states_new",
        "states_end",
        "depth",
        "timeout",
        "notes",
    ]

    with csv_path.open("w", newline="", encoding="utf-8") as fcsv:
        w = csv.DictWriter(fcsv, fieldnames=fieldnames)
        w.writeheader()
        for bench, pm, status in rows_meta:
            method_id = f"{bench.name}.{pm.name}" if pm else bench.name
            if status != "ok":
                w.writerow(
                    {
                        "method": method_id,
                        "jpf_pass": "false",
                        "violation_type": "skipped",
                        "counterexample": "",
                        "instructions": "",
                        "states_new": "",
                        "states_end": "",
                        "depth": "",
                        "timeout": "false",
                        "notes": status,
                    }
                )
                continue

            harness_name = f"Harness_{bench.name}"
            jpf_path = jpf_dir / f"{bench.name}.jpf"
            jpf_cp = [
                args.rac_classes,
                hbuild,
                args.jml_runtime,
            ]
            if extra_java_base.is_dir():
                jpf_cp.append(extra_java_base)
            jpf_cp.extend([jpf_classes, jpf_jar])
            write_jpf(
                jpf_path,
                harness_name,
                jpf_cp,
            )
            cmd_jpf = [
                str(java),
                "-Xmx1024m",
                "-jar",
                str(run_jpf),
                "+report.console.finished=statistics",
                "+report.console.property_violation=error,trace",
                str(jpf_path.name),
            ]
            timed_out = False
            try:
                proc = subprocess.run(
                    cmd_jpf,
                    cwd=str(jpf_dir),
                    capture_output=True,
                    text=True,
                    timeout=args.wall_timeout,
                )
                out = (proc.stdout or "") + "\n" + (proc.stderr or "")
                code = proc.returncode
            except subprocess.TimeoutExpired as exc:
                timed_out = True

                def _txt(blob: object) -> str:
                    if blob is None:
                        return ""
                    if isinstance(blob, bytes):
                        return blob.decode(errors="replace")
                    return str(blob)

                out = _txt(exc.stdout) + "\n" + _txt(exc.stderr)
                code = -9

            stats = parse_jpf_stats(out)
            failed, vtype = jpf_detect_failure(out)
            if code != 0:
                failed = True
                vtype = vtype or f"exit_code_{code}"
            if timed_out:
                failed = True
                vtype = "wall_clock_timeout" if not vtype else vtype
            if failed and vtype and "error parsing class" in out:
                vtype = refine_violation_type(out, vtype)
                if "NoClassDefFoundError" in out:
                    vtype = f"{vtype};jpf_class_parse"

            jpf_ok = not failed
            pass_kind = classify_pass_kind(out, stats) if jpf_ok else "n/a"
            notes_parts = [f"pass_kind={pass_kind}", f"entry={pm.name}"]
            if timed_out:
                notes_parts.append(f"wall_timeout_s={args.wall_timeout}")
            notes = "; ".join(notes_parts)

            trace_path = traces / f"{bench.name}.txt"
            if failed or timed_out:
                trace_path.write_text(out, encoding="utf-8")
                cx = short_counterexample(out)
            else:
                cx = ""

            w.writerow(
                {
                    "method": method_id,
                    "jpf_pass": "true" if jpf_ok else "false",
                    "violation_type": "" if jpf_ok else (vtype or "unknown"),
                    "counterexample": cx,
                    "instructions": stats.get("instructions", ""),
                    "states_new": stats.get("states_new", ""),
                    "states_end": stats.get("states_end", ""),
                    "depth": stats.get("depth", ""),
                    "timeout": "true" if timed_out else "false",
                    "notes": notes,
                }
            )

    print(f"Wrote {csv_path}")
    print(f"Traces (failures only): {traces}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
