import java.util.Random;
//import static java.util.Collections.sort;
//import static java.util.Collections.swap;

/**
 * Task 4: QuickSort (25 Marks)
 * Implements Quick Sort with four pivot-selectionstrategies on the
 * unique alcohol content values from the Wine Quality Dataset,
 * counting comparisons per strategy.
 *───────────────────────────────────────────────────────────────────────
 * PIVOT TRATEGIES IMPLEMENTED
 *      (a) FIRST   - always pick arr[low] as pivot
 *      (b) LAST    - always pick arr[high] as pivot
 *      (c) RANDOM  - pick a random in [low, high] as pivot
 *      (d) MEDIAN_OF_THREE - median of arr[low], arr[mid], arr[high]
 *
 * TIME COMPLEXITY (general)
 *      Best / Average case: O(n log n)
 *      Worst case:          O(n²) - happens when pivot are alway the
 *                                    minimum or maximum element (e.g. FIRST or
 *                                    LAST on a sorted/neatly-sorted array).
 *
 * DOES THE NUMBER OF COMPARISONS CHANGE WITH PIVOT STRATEGY?
 *      YES - significantly on real datasets
 *      - FIRST/LAST on an already-sorted array -> worstcase O(n²) partition
 *
 *      - RANDOM avoids consistent worst-case with high probability
 *      - MEDIAN_OF_THREE reduces the chance of unableanced partitions and
 *        typicaly gives the fewest comparions on real-world data
 *
 * WICH STRATEGY WORKS BEST FOR THIS DATASET?
 *      MEDIAN_OF_THREE generally oerfirms best because:
 *      1. Our dataset is nearly sorted (unique values loaded in order)
 *      2. FITST and LAST degrade to O(n²) on sorted data
 *      3. Median-of-three reliably selects a pivot near the true median,
 *         producing balanced partitions close to O(n log n)
 *      4. RANDOM is also good on average but has variance; Median-of-three
 *         is more predivtable and consistent
 */

public class QuickSort {

    public enum PivotStrategy { FIRST, LAST, RANDOM, MEDIAN_OF_THREE }

    private static long comparisons;
    private static final Random rng = new Random(42);

    /**
     * Sorts the array in ascenting order using the specified pivot strategy
     *
     * @param arr      array to sort in-place
     * @param strategy pivot-selection strategy
     * @return number of comparisons performed
     */
    public static long quickSort(double[] arr, PivotStrategy strategy) {
        comparisons = 0;
        sort(arr, 0, arr.length - 1, strategy);
        return comparisons;
    }

    // Recursiv sort
    private static void sort(double[] arr, int low, int high,
                             PivotStrategy strategy) {
        if (low >= high) return;

        int pivotIndex = partition(arr, low, high, strategy);
        sort(arr, low, pivotIndex - 1, strategy);
        sort(arr, pivotIndex + 1, high, strategy);
    }

    // Partition
    /**
     * Lomuto partition scheme
     * Places the pivot in its final sorted position and returns that index
     */
    private static int partition(double[] arr, int low, int high,
                                 PivotStrategy strategy) {
        // Move chosen pivot the the end so Lomuto logic work unifrly
        int pivotPos = choosenPivotIndex(arr, low, high, strategy);
        swap(arr, pivotPos, high);
        double pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if(arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Pivot selection
    private static int choosenPivotIndex(double[] arr, int low, int high, PivotStrategy strategy) {
        switch (strategy) {
            case FIRST:
                return low;
            case LAST:
                return high;
            case RANDOM:
                return low + rng.nextInt(high - low + 1);
            case MEDIAN_OF_THREE: {
                int mid = low + (high - low) / 2;
                // Sort the three candidates to find the median
                if (arr[low] > arr[mid]) swap(arr, low, mid);
                if (arr[low] > arr[high]) swap(arr, low, high);
                if (arr[mid] > arr[high]) swap(arr, mid, high);
                // Now arr[mid] is the median
                return mid;
            }
            default:
                return high;
        }
    }

    // Helpers
    private static void swap(double[] arr, int i, int j) {
        double tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    private static boolean isSorted(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1]) return false;
        return true;
    }

    // Main
    public  static void main(String[] args) {
        System.out.println("Problem 4: QuickSort");
        System.out.println("Dataset: 112 unique alcohol content values (combind red + white wine)\n");

        int n = DataLoader.getUniqueAlcoholValues().length;
        System.out.printf("n = %d%n%n", n);
        System.out.printf("%-25s %15s %15s%n", "Pivot Strategy", "Comparisons", "Sorted?");

        for (PivotStrategy strategy : PivotStrategy.values()) {
            double[] data = DataLoader.getUniqueAlcoholValues();
            // The dataset is loaded already sorted - good stress test for FIRST/LAST
            long cmp = quickSort(data, strategy);
            System.out.printf("%-25s %25d %15s%n",
                    strategy, cmp, isSorted(data));
        }

        System.out.println("\n After shuffle (average-case behaviour)");
        System.out.printf("%-25s %25s %25s%n", "Pivot Strategy", "Comparisons", "Sorted?");

        double[] shuffledBase = DataLoader.getUniqueAlcoholValues();
        BubbleSort.shuffleArray(shuffledBase);

        for (PivotStrategy strategy : PivotStrategy.values()) {
            double[]data = shuffledBase.clone();
            long cmp = quickSort(data, strategy);
            System.out.printf("%-20s %15d %10s\n",
                    strategy, cmp, isSorted(data));
        }
        System.out.println("\n--- Analysis ---");
        System.out.println(". FIRST AND LAST degrade on sorted inpur (worst-case O(n²))");
        System.out.println(". RANDOM avoids worst-case on average");
        System.out.println(". MEDIAN_OF_THREEE typically uses the fewest comparisons,");
        System.out.println(". especially on nearly-sorted data like theis dataset");
        System.out.println(". Best overall strategy for this dataset: MEDIAN_OF_THREE");


    }
}

