/**
 * Main runner - executes all four sorting problems and prints results.
 *
 * Compile: javac -d out src/*.java
 * Run:     java -cp out Main
 */
public class Main {

    private static void header(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    private static void section(String label) {
        System.out.println("\n  -- " + label + " --");
    }

    private static void result(String label, long comparisons, boolean sorted) {
        System.out.printf("  %-40s comparisons: %6d   sorted: %s%n",
                label, comparisons, sorted ? "yes" : "NO");
    }

    private static boolean isSorted(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1]) return false;
        return true;
    }

    public static void main(String[] args) {

        double[] base     = DataLoader.getUniqueAlcoholValues();
        double[] shuffled = base.clone();
        BubbleSort.shuffleArray(shuffled);

        int n = base.length;
        System.out.println("Wine Quality Dataset — " + n + " unique alcohol content values (range: 8.0% – 14.9%)");

        // ── Problem 1: BubbleSort ─────────────────────────────────────────────
        header("Problem 1: BubbleSort");

        section("Already-sorted input");
        double[] d = base.clone();
        result("Non-optimised", BubbleSort.sort(d), isSorted(d));

        d = base.clone();
        result("Optimised (early-exit)", BubbleSort.optimizedSort(d), isSorted(d));

        section("Shuffled input");
        d = shuffled.clone();
        result("Non-optimised", BubbleSort.sort(d), isSorted(d));

        d = shuffled.clone();
        result("Optimised (early-exit)", BubbleSort.optimizedSort(d), isSorted(d));

        System.out.println("\n  Non-optimised always does n*(n-1)/2 = " + (n * (n - 1) / 2) + " comparisons (O(n²)).");
        System.out.println("  Optimised drops to O(n) on sorted input; shuffle pushes it back towards O(n²).");

        // ── Problem 2: InsertionSort ──────────────────────────────────────────
        header("Problem 2: InsertionSort");

        section("Already-sorted input (best case)");
        d = base.clone();
        result("InsertionSort", InsertionSort.insertionSort(d), isSorted(d));
        System.out.println("  Expected n-1 = " + (n - 1) + " comparisons -> O(n)");

        section("Shuffled input (average case)");
        d = shuffled.clone();
        result("InsertionSort", InsertionSort.insertionSort(d), isSorted(d));

        System.out.println("\n  Shuffling increases comparisons significantly.");
        System.out.println("  Best case O(n) on sorted input; average/worst case O(n²) on random input.");

        // ── Problem 3: MergeSort ──────────────────────────────────────────────
        header("Problem 3: MergeSort");

        section("Already-sorted input");
        d = base.clone();
        long mergeOps1 = MergeSort.mergeSort(d);
        System.out.printf("  %-40s merge ops:    %6d   sorted: %s%n", "MergeSort", mergeOps1, isSorted(d) ? "yes" : "NO");

        section("Shuffled input");
        d = shuffled.clone();
        long mergeOps2 = MergeSort.mergeSort(d);
        System.out.printf("  %-40s merge ops:    %6d   sorted: %s%n", "MergeSort", mergeOps2, isSorted(d) ? "yes" : "NO");

        System.out.println("\n  Merge operations are always n-1 = " + (n - 1) + " regardless of input order.");
        System.out.println("  Shuffling does NOT change the merge count. Time complexity: O(n log n) in all cases.");

        // ── Problem 4: QuickSort ──────────────────────────────────────────────
        header("Problem 4: QuickSort");

        System.out.printf("%n  %-20s  %15s  %15s  %s%n", "Pivot Strategy", "Sorted input", "Shuffled input", "Correct?");
        System.out.println("  " + "-".repeat(60));

        for (QuickSort.PivotStrategy strategy : QuickSort.PivotStrategy.values()) {
            double[] sortedRun   = base.clone();
            double[] shuffledRun = shuffled.clone();

            long cmpSorted   = QuickSort.quickSort(sortedRun,   strategy);
            long cmpShuffled = QuickSort.quickSort(shuffledRun, strategy);

            System.out.printf("  %-20s  %15d  %15d  %s%n",
                    strategy, cmpSorted, cmpShuffled,
                    isSorted(sortedRun) && isSorted(shuffledRun) ? "yes" : "NO");
        }

        System.out.println("\n  FIRST/LAST degrade to O(n²) on sorted input.");
        System.out.println("  RANDOM avoids worst-case on average.");
        System.out.println("  MEDIAN_OF_THREE gives the fewest comparisons on this nearly-sorted dataset.");
    }
}
