import argparse
from pathlib import Path


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--strategy", choices=["zero_shot", "few_shot", "cot"], required=True)
    parser.add_argument("--method", required=True)
    args = parser.parse_args()

    root = Path(__file__).resolve().parent
    template_path = root / "prompts" / f"{args.strategy}.txt"
    source_path = root / "benchmarks" / f"{args.method}.java"

    template = template_path.read_text()
    source = source_path.read_text()

    print(template.replace("{{JAVA_SOURCE}}", source.rstrip()))


if __name__ == "__main__":
    main()
