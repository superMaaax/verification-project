# 失败案例分析报告 — OpenRouter GPT-5.4-mini Pipeline

*UT Austin · Spring 2026 · EE382C-3 · 配套数据：`experiment/openrouter-run/`*

## Pipeline 总览

| 策略 | LLM 生成 | RAC 通过 | JPF 通过 (A+B) | 语义验证通过 (A) |
|:---:|:---:|:---:|:---:|:---:|
| Zero-shot | 120 | **90 (75.0%)** | **67 (55.8%)** | **66 (55.0%)** |
| **Few-shot** | 120 | **110 (91.7%)** | **79 (65.8%)** | **77 (64.2%)** |
| 差值 (FS − ZS) | — | +20 pp | +10 pp | +9 pp |

参考基线（Composer 2 同口径数据）：RAC 117/120 (97.5%)、JPF 83 (69.2%)。

---

# 一、Stage 3：OpenJML RAC 编译失败分析

## 1.1 Zero-shot（30 个失败）

| 类别 | 数量 | 典型表现 |
|:---|:---:|:---|
| **JML loop 注解语法错** | **13** | 用了 `@ loop invariant`（两个单词、缺关键字 `loop_invariant`），或 `@ loop_variant`（拼成 variant 而非 decreases），导致 `';' expected / Expected a declaration or a JML construct` |
| 多行 `ensures` 跨行括号失败 | 6 | `ComputeArea/Branch`、`ComputeOverlap/Branch` 这类涉及 `\max/\min` 嵌套的几何公式，被换行/`@` 续行符断开后括号失衡 |
| **缺 `import java.util.*`** | 4 | `ContainsDuplicate, LargestPerimeter, RepeatedChar, SortLibrary` — 用了 `Set/HashSet/Arrays` 但忘记 import |
| 非纯方法在 JML 中被调用 | 1 | `StrPalindrome`：`@ ensures` 调用了非 `\@pure` 方法 |
| visibility 不匹配 | 2 | `LinearSearch / PrimeNumbers`：`@ assignable / ensures` 引用 private 字段，但 spec case 是 public |
| 关键字误用 / spec 放错位置 | 2 | `NextGreaterElem`：循环里用 `assignable`（不是循环 spec 关键字）；`SetZero`：spec 跟在 `static` 关键字后 |
| OpenJML 内部 `AssertionError` | 1 | `RotateArray`：触发 javac 的 `Items$LocalItem` 内部 assert（**工具 bug**，不是 LLM 问题） |
| 其他局部错误 | 1 | `Return100Nested`：循环 spec 错位置 + 局部变量声明位置不合法 |

**核心模式**：zero-shot 完全没掌握 JML loop 注解的精确语法。13/30 = 43% 的失败源于 `loop_invariant`/`loop_variant`/`maintaining`/`decreases` 这几个关键字的误用。

## 1.2 Few-shot（10 个失败）

| 类别 | 数量 | 方法 |
|:---|:---:|:---|
| **缺 `import java.util.*`** | **4** | `ContainsDuplicate, LargestPerimeter, RepeatedChar, SortLibrary`（与 zero-shot 完全相同的 4 个） |
| 多行 `ensures` 跨行失败 | 2 | `ComputeArea, ComputeAreaBranch` |
| OpenJML 内部 `AssertionError` | 2 | `ReverseString, RotateArray`（工具 bug） |
| 非纯方法在 JML 中被调用 | 1 | `Fibonacci`：`@ ensures n>0 ==> \result == fibCompute(n)`，`fibCompute` 不是 pure |
| JML 表达式类型错 | 1 | `JewelsInStones`：把 `char ==` 当 boolean 用却用在 `\sum` 算术里 |

**核心模式**：few-shot 把 JML 语法学会了（loop 注解错误清零），剩下的失败是 **3 类与 prompt 无关的源码层缺陷**：(1) 非 JML 的 Java import 缺失、(2) JPF/OpenJML 工具 bug、(3) 复杂数学表达式跨行格式。

## 1.3 跨策略对比关键发现

- **Few-shot 在 +20pp 编译率提升上几乎全部来自"消除 JML 语法错误"**（13 → 0）。其余失败类别（import 缺失、工具 bug、跨行表达式）在两种策略下数量基本一致 —— 这些是 prompt 调整无法修复的。
- 4 个 import 缺失方法在 zero/few-shot 下**完全相同**，且原始 SpecGenBench 题目中本身就缺 import —— 这是数据集级别的问题，不是 LLM 偏向。

---

# 二、Stage 4：JPF 动态验证失败分析

## 2.1 Zero-shot（53 个失败 / 120）

| 类别 | 数量 | 占比 |
|:---|:---:|:---:|
| 无 RAC class（Stage 3 已挂） | 30 | 25.0% |
| **JML 前置条件违反**（`NoUncaughtExceptionsProperty`） | 13 | 10.8% |
| 纯超时（`exit_code_-9`） | 9 | 7.5% |
| Harness 不支持 `int[][]` | 1 | 0.8% |

## 2.2 Few-shot（41 个失败 / 120）

| 类别 | 数量 | 占比 |
|:---|:---:|:---:|
| 无 RAC class | 10 | 8.3% |
| **JML 前置条件违反**（不超时） | 15 | 12.5% |
| **JML 违反 + 超时** | 3 | 2.5% |
| 纯超时 | 7 | 5.8% |
| Java 运行时异常 | 2 | 1.7% |
| Harness 不支持 `int[][]` | 4 | 3.3% |

## 2.3 失败聚类分析（few_shot 为主）

### 聚类 A — Common Factor / Multiple 家族（7 个，确凿系统性偏差）

`IsCommonFactor / IsCommonFactorBranch / IsCommonMultiple / IsCommonMultipleBranch / NotCommonFactor / NotCommonFactorBranch / NotCommonMultiple`

LLM 在所有除/取模相关方法上都加了 `requires b != 0` 类的防御式前提，而 Harness 的非确定性输入域 `[-5, 5]` 包含 0。**与 Composer 2 基线完全一致** —— 这是跨 prompt、跨 LLM、跨人都重现的系统性现象。

### 聚类 B — Calculator 变体（2 个）

`Calculator.calculate`、`CalculatorShuffled.calculate`：两者都生成了同形态的 `requires (op=='/' || op=='%') ==> num2!=0` 等前提，被 Harness 的全字符域触发。

### 聚类 C — 升降序判断（2 个）

`IsAscending.isAscending`、`IsDescending.isDescending`：两者 instructions / states 数完全相同 —— 同一类规约错误的镜像。

### 聚类 D — 几何重叠面积（2 个超时 + 2 个 Stage 3 fail）

`ComputeOverlap`、`ComputeOverlapBranch` 在 Stage 4 超时；同家族 `ComputeArea / ComputeAreaBranch` 因多行 `\max/\min` 表达式在 Stage 3 已挂。**几何重叠这一族在 zero-shot/few-shot/Composer 2 三套 run 里均失败**，是 Pipeline 的稳定瓶颈。

### 聚类 E — 字符串 / 序列枚举超时（4 个）

`IsPrefix, IsSubsequence, NextGreaterElem, CompareArray.arrcmp`：字符级非确定性输入触发 JPF 状态空间爆炸。`Swap` 也属此类（数组对换，状态指数）。

### 聚类 F — JML 违反 + 超时三元组（3 个）

`CopyArray, IsOneBitCharacter, SetZero`：在 30s 内既触发了规约违反又没探完，属于既有规约错误又有状态爆炸的双重失败。

### 聚类 G — Java 异常（2 个，仅 few_shot 出现）

`Inverse.Inverse, IsSuffix.isSuffix`：抛出非 JML 的 Java 运行时异常（如 `ArrayIndexOutOfBounds`）。zero-shot 下 `Inverse` 是单纯超时，few-shot 里因为多了一层规约约束反而触发了异常路径 —— 说明 few-shot 规约更"积极"，副作用也更多。

---

# 三、跨策略 / 跨基线对比

| 维度 | Zero-shot | Few-shot | Composer 2（基线） |
|:---|:---:|:---:|:---:|
| Stage 3 通过 | 90/120 | 110/120 | 117/120 |
| Stage 4 pass_kind=A | 66 | 77 | 81 |
| JML 前置条件违反 | 13 | 18 | 19 |
| 纯超时 | 9 | 7 | 12 |
| Harness 不支持 `int[][]` | 1 | 4 | 4 |
| 无 RAC class | 30 | 10 | 3 |

**判读**：
1. Few-shot 把 zero-shot 的 -20 个 RAC 损失补回来 12 个有效验证（剩下的 8 个补回的 method 进了 Stage 4 后又失败）。
2. JML 违反类失败在三套 run 里都是 13–19 个区间，**这是 Common Factor/Multiple/Calculator/Ascending 这套结构性现象的固定收敛点**。
3. 纯超时数（7–12 个）也几乎不随 prompt 变化 —— 这是 JPF 工具瓶颈，不是 LLM 能改的。
4. 与 Composer 2 基线的差距（few-shot 77 vs 81）只有 4 个 method，**主要差异在 Stage 3** —— 我们多挂了 7 个编译（其中 4 个 import 缺失 + 2 个跨行 ensures + 1 个非 pure 调用）。

---

# 四、Pipeline 三类瓶颈（按修复成本排序）

| 类别 | zero+few 损失 | 性质 | 修复难度 |
|:---|:---:|:---|:---:|
| LLM 生成的 JML 语义/前提偏差（Common Factor 等） | ~20 | LLM 行为 | 高（需 prompt eng. 或 RAG） |
| Java import 缺失 + 跨行表达式 + 工具 bug | 12 | 源码/工具 | 低（写后处理脚本可修） |
| JPF 状态空间爆炸（几何/字符串） | 10–15 | 工具固有 | 中（缩小输入域 / 用 Symbolic JPF） |
| Harness 不支持 `int[][]` | 4 | 工程实现 | 低（改 harness 生成器） |
| OpenJML 自身崩溃 | 1–2 | 工具 bug | 不可控 |

---

## 数据文件位置

| 文件 | 说明 |
|:---|:---|
| `experiment/openrouter-run/stage3-rac/<strategy>/compile_logs/summary.tsv` | Stage 3 编译结果 |
| `experiment/openrouter-run/stage3-rac/<strategy>/compile_logs/<Method>.log` | 每个失败方法的 OpenJML 完整输出 |
| `experiment/openrouter-run/stage4-jpf/<strategy>/results/jpf_verification.csv` | Stage 4 JPF 主结果 |
| `experiment/openrouter-run/stage4-jpf/<strategy>/results/traces/*.txt` | 失败用例 trace |
