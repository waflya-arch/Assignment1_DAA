# DAA Assignment 1 — Fast Sorting & Selection Engine

Implementation of MergeSort, QuickSort and QuickSelect in Java, with metrics collection
(comparisons, recursion depth, time) and a benchmark that exports results to CSV.

## Project structure

```
src/main/java/algo/
  Metrics.java       - counts comparisons, max recursion depth, elapsed time
  MergeSort.java      - merge sort with a single reusable buffer and insertion-sort cutoff
  QuickSort.java       - quicksort with random pivot, 3-way partition, bounded recursion depth
  QuickSelect.java     - k-th smallest element, reuses QuickSort's partition
  Benchmark.java       - runs all algorithms on sizes 1e3..1e6 and 3 input types, writes results.csv
src/test/java/algo/
  SortTest.java         - JUnit 5 tests: correctness vs Arrays.sort, edge cases, depth bound, QuickSelect
```

## Requirements

- JDK 17+
- Maven 3.8+

## Build

```bash
mvn clean compile
```

## Run tests

```bash
mvn test
```

This runs correctness tests (100 repeated random arrays vs `Arrays.sort`), edge cases
(empty array, single element, all-equal elements, already sorted), the QuickSort recursion
depth bound check, and QuickSelect correctness tests.

## Run the benchmark

```bash
mvn compile exec:java
```

This runs MergeSort, QuickSort and QuickSelect on n = 1 000 / 10 000 / 100 000 / 1 000 000,
on random / sorted / duplicates input, 5 repetitions each (median is saved), and writes
`results.csv` in the project root with columns:

```
algorithm,input,n,time_ms,comparisons,max_depth
```

## Generate plots

A `results.csv` file is produced by the benchmark above. Plots (time vs n, max depth vs n,
ratio vs n) are generated from it separately and saved as PNG files in the repository root —
see `REPORT.md` for how each plot is used to check the Θ bound.

## Git workflow

- `main` — working code only, tagged `v1.0`
- `feature/mergesort`, `feature/quicksort`, `feature/select`, `feature/metrics` — one branch
  per component, merged into `main` via Pull Request
