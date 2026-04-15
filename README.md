# PG4200 – Data Structures and Algorithms
### Final Exam 2025/2026 · Kristiania University College

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge)
![Course](https://img.shields.io/badge/Course-PG4200-blue?style=for-the-badge)
![Grade](https://img.shields.io/badge/Exam-100%25_Weight-critical?style=for-the-badge)
![Dataset](https://img.shields.io/badge/Dataset-Wine_Quality_UCI-purple?style=for-the-badge)

---

## Overview

Implementation of four classic sorting algorithms applied to the **Wine Quality Dataset** from the UCI Machine Learning Repository. The dataset contains alcohol content values (%) from combined red and white wine samples. All algorithms sort the **112 unique alcohol content values** (range: 8.0% – 14.9%) extracted from the dataset.

---

## Problems Solved

| # | Algorithm | Key Feature | Time Complexity |
|---|-----------|-------------|-----------------|
| 1 | **BubbleSort** | Non-optimised + Optimised (early-exit) | O(n²) / O(n) best |
| 2 | **InsertionSort** | In-place insertion with shift | O(n) best / O(n²) worst |
| 3 | **MergeSort** | Divide-and-conquer, merge count tracked | O(n log n) always |
| 4 | **QuickSort** | 4 pivot strategies compared | O(n log n) avg / O(n²) worst |

---

## Project Structure

```
EXAM_PG4200/
├── src/
│   ├── res/
│   │   ├── winequality-red.csv       # 1,599 red wine entries
│   │   └── winequality-white.csv     # 4,898 white wine entries
│   ├── DataLoader.java               # Loads & deduplicates alcohol values via TreeSet
│   ├── BubbleSort.java               # Problem 1 – optimised & non-optimised
│   ├── InsertionSort.java            # Problem 2
│   ├── MergeSort.java                # Problem 3
│   ├── QuickSort.java                # Problem 4 – 4 pivot strategies
│   └── Main.java                     # Runs all 4 problems and prints results
├── PG4200_Exam_Report.docx           # Full algorithmic report
└── Data Structures and Algorithms_Exam_2026.pdf
```

---

## How to Run

### Compile
```bash
javac -d out src/*.java
```

### Run
```bash
java -cp out Main
```

> Make sure the CSV files are present at `src/res/winequality-red.csv` and `src/res/winequality-white.csv` before running.

---

## Dataset

- **Source:** [UCI Machine Learning Repository – Wine Quality](https://archive.ics.uci.edu/ml/datasets/wine+quality)
- **Files:** `winequality-red.csv` + `winequality-white.csv`
- **Attribute used:** Alcohol content (column index 10, semicolon-separated)
- **Unique values:** 112 (duplicates removed via `TreeSet<Double>`)
- **Range:** 8.0% – 14.9%

---

## Algorithm Results (n = 112)

### Problem 1 – BubbleSort

| Input | Non-Optimised | Optimised |
|-------|--------------|-----------|
| Already sorted | 6,216 comparisons | **111 comparisons** |
| Shuffled | 6,216 comparisons | ~6,000+ comparisons |

- Non-optimised always performs `n*(n-1)/2 = 6,216` comparisons — input order has no effect.
- Optimised exits after the first pass on sorted input → **O(n)** best case.

### Problem 2 – InsertionSort

| Input | Comparisons | Complexity |
|-------|------------|------------|
| Already sorted | 111 | O(n) |
| Shuffled | ~3,000–4,000 | O(n²) |
| Reverse sorted | 6,216 | O(n²) |

### Problem 3 – MergeSort

| Input | Merge Operations |
|-------|-----------------|
| Already sorted | **111** |
| Shuffled | **111** |

Merge count is always exactly `n-1` — input order never affects it.

### Problem 4 – QuickSort

| Pivot Strategy | Sorted Input | Shuffled Input | Notes |
|----------------|-------------|----------------|-------|
| `FIRST` | ~6,216 | ~700–900 | O(n²) on sorted data |
| `LAST` | ~6,216 | ~700–900 | O(n²) on sorted data |
| `RANDOM` | ~700–900 | ~700–900 | Good avg, has variance |
| `MEDIAN_OF_THREE` | **~700–900** | **~700–900** | Best overall |

**Best strategy for this dataset: `MEDIAN_OF_THREE`** — deterministic, balanced partitions, and avoids the O(n²) worst case that FIRST/LAST hit on the pre-sorted input.

---

## Key Concepts

- **`TreeSet<Double>`** – used in `DataLoader` to deduplicate and sort values in a single pass (O(log n) insertion, O(n) traversal)
- **Fisher-Yates shuffle** – used to generate reproducible random permutations (fixed seed 42)
- **Lomuto partition scheme** – used in QuickSort for clean pivot placement
- **In-place sorting** – all algorithms sort the array directly; `DataLoader` returns a fresh array each call to prevent cross-contamination between tests

---

## Authors

| Name           | GitHub                           |
|----------------|----------------------------------|
| Group member 1 | [@MafiaBV1](https://github.com/MafiaBV1) |
| Group member 2 | [@Danlis1](https://github.com/Danlis1)  |

---

## Course Info

| Field | Detail |
|-------|--------|
| Course | PG4200-H – Data Structures and Algorithms |
| Institution | Kristiania University College, Oslo |
| Professor | Prof. Dr. Rashmi Gupta |
| Exam weight | 100% |
| Duration | 4 weeks |
