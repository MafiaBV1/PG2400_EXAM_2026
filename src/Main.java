/**
 * Main runner - executes all four sorting problems and prints results.
 *
 * Compile: javac -d out src/*.java
 * Run:     java -cp out Main
 */
public class Main {

    private static final int RUNS = 100;
    private static final Runtime RT = Runtime.getRuntime();

    private static void header(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    private static void section(String label) {
        System.out.println("\n  -- " + label + " --");
    }

    private static void result(String label, long comparisons, boolean sorted, String complexity) {
        System.out.printf("  %-40s comparisons: %6d   %-20s sorted: %s%n",
                label, comparisons, complexity, sorted ? "yes" : "NO");
    }

    private static boolean isSorted(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1]) return false;
        return true;
    }

    private static void reverseArray(double[] arr) {
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            double tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        }
    }

    // ── Benchmark helpers ─────────────────────────────────────────────────────

    interface SortFn { void sort(double[] arr); }

    private static double avgTimeMs(SortFn fn, double[] base) {
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            double[] copy = base.clone();
            long start = System.nanoTime();
            fn.sort(copy);
            total += System.nanoTime() - start;
        }
        return total / (double) RUNS / 1_000_000.0;
    }

    private static long avgMemBytes(SortFn fn, double[] base) {
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            double[] copy = base.clone();
            RT.gc();
            long before = RT.totalMemory() - RT.freeMemory();
            fn.sort(copy);
            long after = RT.totalMemory() - RT.freeMemory();
            total += Math.max(0, after - before);
        }
        return total / RUNS;
    }

    private static void benchRow(String label, String complexity, SortFn fn, double[] sorted, double[] shuffled) {
        double tSorted   = avgTimeMs(fn, sorted);
        long   mSorted   = avgMemBytes(fn, sorted);
        double tShuffled = avgTimeMs(fn, shuffled);
        long   mShuffled = avgMemBytes(fn, shuffled);
        System.out.printf("  %-32s %-16s %8.3f ms %7d B    %8.3f ms %7d B%n",
                label, complexity, tSorted, mSorted, tShuffled, mShuffled);
    }

    private static void benchmark(double[] base, double[] shuffled) {
        header("Benchmark (" + RUNS + " runs average)");
        System.out.printf("%n  %-32s %-16s %12s %8s   %12s %8s%n",
                "Algorithm", "Complexity", "Sorted avg", "Mem", "Shuffled avg", "Mem");
        System.out.println("  " + "-".repeat(92));
        benchRow("BubbleSort (non-optimised)", "O(n²)",     arr -> BubbleSort.sort(arr),            base, shuffled);
        benchRow("BubbleSort (optimised)",     "O(n)-O(n²))",arr -> BubbleSort.optimizedSort(arr),   base, shuffled);
        benchRow("InsertionSort",              "O(n)-O(n²)",arr -> InsertionSort.insertionSort(arr), base, shuffled);
        benchRow("MergeSort",                  "O(n log n)", arr -> MergeSort.mergeSort(arr),         base, shuffled);
        System.out.println();
        benchRow("QuickSort (FIRST)",          "O(n²)*",    arr -> QuickSort.quickSort(arr, QuickSort.PivotStrategy.FIRST),           base, shuffled);
        benchRow("QuickSort (LAST)",           "O(n²)*",    arr -> QuickSort.quickSort(arr, QuickSort.PivotStrategy.LAST),            base, shuffled);
        benchRow("QuickSort (RANDOM)",         "O(n log n)", arr -> QuickSort.quickSort(arr, QuickSort.PivotStrategy.RANDOM),          base, shuffled);
        benchRow("QuickSort (MEDIAN_OF_THREE)","O(n log n)", arr -> QuickSort.quickSort(arr, QuickSort.PivotStrategy.MEDIAN_OF_THREE), base, shuffled);
        System.out.println("\n  * FIRST/LAST are O(n²) on sorted input, O(n log n) on average.");
        System.out.println("  In-place sorts (Bubble, Insertion, Quick) allocate near-zero extra memory.");
        System.out.println("  MergeSort allocates O(n) extra bytes for temporary arrays during each merge.");
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        double[] base     = DataLoader.getUniqueAlcoholValues();
        double[] shuffled = base.clone();
        double[] reversed = base.clone();
        BubbleSort.shuffleArray(shuffled);
        reverseArray(reversed);
        int n = base.length;
        long worstCase = (long) n * (n - 1) / 2;
        System.out.println("Wine Quality Dataset - " + n + " unique alcohol content values (range: 8.0% - 14.9%)");
        System.out.println("Worst case comparisons n*(n-1)/2 = " + worstCase);

        // ── Problem 1: BubbleSort ─────────────────────────────────────────────
        header("Problem 1: BubbleSort");
        section("Already-sorted input (best case for optimised)");
        double[] d = base.clone();
        result("Non-optimised", BubbleSort.sort(d), isSorted(d), "O(n²)");
        d = base.clone();
        result("Optimised (early-exit)", BubbleSort.optimizedSort(d), isSorted(d), "O(n) best case");
        section("Shuffled input (average case)");
        d = shuffled.clone();
        result("Non-optimised", BubbleSort.sort(d), isSorted(d), "O(n²)");
        d = shuffled.clone();
        result("Optimised (early-exit)", BubbleSort.optimizedSort(d), isSorted(d), "O(n²) avg case");
        section("Reverse-sorted input (worst case)");
        d = reversed.clone();
        result("Non-optimised", BubbleSort.sort(d), isSorted(d), "O(n²) worst case");
        d = reversed.clone();
        result("Optimised (early-exit)", BubbleSort.optimizedSort(d), isSorted(d), "O(n²) worst case");
        System.out.println("\n  Non-optimised always does n*(n-1)/2 = " + worstCase + " comparisons regardless of order.");
        System.out.println("  Optimised exits early on sorted input (O(n)), degrades to O(n²) otherwise.");

        // ── Problem 2: InsertionSort ──────────────────────────────────────────
        header("Problem 2: InsertionSort");
        section("Already-sorted input (best case)");
        d = base.clone();
        result("InsertionSort", InsertionSort.insertionSort(d), isSorted(d), "O(n) best case");
        System.out.println("  Expected n-1 = " + (n - 1) + " comparisons");
        section("Shuffled input (average case)");
        d = shuffled.clone();
        result("InsertionSort", InsertionSort.insertionSort(d), isSorted(d), "O(n²) avg case");
        section("Reverse-sorted input (worst case)");
        d = reversed.clone();
        result("InsertionSort", InsertionSort.insertionSort(d), isSorted(d), "O(n²) worst case");
        System.out.println("  Expected n*(n-1)/2 = " + worstCase + " comparisons");
        System.out.println("\n  Shuffling changes complexity from O(n) best case to O(n²) average/worst case.");

        // ── Problem 3: MergeSort ──────────────────────────────────────────────
        header("Problem 3: MergeSort");
        section("Already-sorted input");
        d = base.clone();
        long mergeOps1 = MergeSort.mergeSort(d);
        long mergeCmp1 = MergeSort.getLastComparisonCount();
        System.out.printf("  %-40s merge ops: %4d   comparisons: %6d   O(n log n)   sorted: %s%n",
                "MergeSort", mergeOps1, mergeCmp1, isSorted(d) ? "yes" : "NO");
        section("Shuffled input");
        d = shuffled.clone();
        long mergeOps2 = MergeSort.mergeSort(d);
        long mergeCmp2 = MergeSort.getLastComparisonCount();
        System.out.printf("  %-40s merge ops: %4d   comparisons: %6d   O(n log n)   sorted: %s%n",
                "MergeSort", mergeOps2, mergeCmp2, isSorted(d) ? "yes" : "NO");
        section("Reverse-sorted input");
        d = reversed.clone();
        long mergeOps3 = MergeSort.mergeSort(d);
        long mergeCmp3 = MergeSort.getLastComparisonCount();
        System.out.printf("  %-40s merge ops: %4d   comparisons: %6d   O(n log n)   sorted: %s%n",
                "MergeSort", mergeOps3, mergeCmp3, isSorted(d) ? "yes" : "NO");
        System.out.println("\n  Merge operations are always n-1 = " + (n - 1) + " regardless of input order.");
        System.out.println("  Comparisons inside merge can vary slightly but complexity stays O(n log n).");

        // ── Problem 4: QuickSort ──────────────────────────────────────────────
        header("Problem 4: QuickSort");
        System.out.printf("%n  %-20s  %15s  %15s  %-20s  %s%n",
                "Pivot Strategy", "Sorted input", "Shuffled input", "Complexity (sorted)", "Correct?");
        System.out.println("  " + "-".repeat(78));
        for (QuickSort.PivotStrategy strategy : QuickSort.PivotStrategy.values()) {
            double[] sortedRun   = base.clone();
            double[] shuffledRun = shuffled.clone();
            long cmpSorted   = QuickSort.quickSort(sortedRun,   strategy);
            long cmpShuffled = QuickSort.quickSort(shuffledRun, strategy);
            String complexityNote = (cmpSorted == worstCase)
                    ? "O(n²) worst case"
                    : "O(n log n)";
            System.out.printf("  %-20s  %15d  %15d  %-20s  %s%n",
                    strategy, cmpSorted, cmpShuffled, complexityNote,
                    isSorted(sortedRun) && isSorted(shuffledRun) ? "yes" : "NO");
        }
        System.out.println("\n  FIRST/LAST hit O(n²) on sorted input because the pivot is always min/max.");
        System.out.println("  RANDOM avoids worst-case on average.");
        System.out.println("  MEDIAN_OF_THREE gives the fewest comparisons on this nearly-sorted dataset.");

        // ── Benchmark ─────────────────────────────────────────────────────────
        benchmark(base, shuffled);
    }
}
