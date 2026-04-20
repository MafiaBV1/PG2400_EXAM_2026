/**
 * Problem 2: InsertionSort
 *
 * Implements Inertion Sort on the unique alcohole content values
 * from the Wine Quality Dataset
 *
 * ─────────────────────────────────────────────────────────────
 * AlGORIHM DESCRIPTION
 *      Inertion Sort builds a orted sub-array from left to right
 *      For each element it index i, it is compared backwards through the
 *      already-sorted portion (indicaes 0..i-1) and "inserted" into its
 *      correct position by shifting larger elements one step to the right
 *
 * TIME CCOMPLEXITY
 *      Best case:      O(n)    - input already sorted; inner loop never executes
 *      Average case:   O(n²)   - random input
 *      Wort case:      O(n²)   - reverse-sorted input; each element travels
 *                                the full length of the sorted sub-array
 *
 * DOSE SHUFFLING CHANGE THE COMPLEXITY?
 *      YES - for the best case
 *      When the input is already sorted (as our dataset is when loaded), only
 *      n-1 comparisons are needed -> O(n)
 *      After a random shuffle, the expected number of comparisons is
 *      approximatley n*(n-1)/4, with is O(n²)
 *      The wort-case bound (O(n²)) is the same either way, but the actual
 *      comparison count can increase dramatically after shuffling
 */

public class InsertionSort {
    /**
     * Sorts te giving array in ascending order using Isertion Sort
     *
     * @param arr array to sort in-place
     * @return number of comparisons prefomed
     */

    public static long insertionSort(double[] arr) {
        int n = arr.length;
        long comparisons = 0;

        for (int i = 1; i < n; i++) {
            double key = arr[i];
            int j = i - 1;

            // Shift elements that are greater than key one position to the right
            while (j >= 0) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
        return comparisons;
    }

    // ─── main ─────────────────────────────────────────────
    public static void main (String[] args) {
        System.out.println("\nProblem 2: InsertionSort ");
        System.out.println("Dataset: 112 unique alcohol content vlaues (combuned red + whitewine\n");

        // Already sorted input (best case)
        double[] data1 = DataLoader.getUniqueAlcoholValues();
        long cmp1 = insertionSort(data1);
        System.out.println("InsertionSort (original/already-sorted order):");
        System.out.println("    Comparisons: " + cmp1
                + " <- best-case O(n), n-1 = " + (data1.length - 1));
        System.out.println("    Sorted correctly: " + isSorted(data1));

        // Shuffled input (average case)
        double[] data2 = DataLoader.getUniqueAlcoholValues();
        BubbleSort.shuffleArray(data2);
        long cmp2 = insertionSort(data2);
        System.out.println("InsertionSort (shuffled input -> average case):");
        System.out.println("    Comparisons: " + cmp2);
        System.out.println("    Sorted correctly: " + isSorted(data2));

        // Reverse sorted (worst case)
        double[] data3 = DataLoader.getUniqueAlcoholValues();
        reverseArray(data3);
        long cmp3 = insertionSort(data3);
        System.out.println("InsertionSort (reverse sorted -> worst case):");
        System.out.println("    Comparisons: " + cmp3);
        long n = data3.length;
        System.out.println(" Expected n*(n-1)/2 = " + (n * (n-1) / 2));
        System.out.println(" Sorted correctly: " + isSorted(data3));
    }

    // Helpers
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
}
