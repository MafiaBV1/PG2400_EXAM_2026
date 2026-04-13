import jdk.jfr.DataAmount;

import static java.io.ObjectInputFilter.merge;
import static java.util.Arrays.sort;

/**
 * Problem 3: MergeSort
 *  Implements Merge Sort on the unique alcohol content values
 *  from the Wine Quality Dataser, counting merge operations
 *
 * ─────────────────────────────────────────────────────────────
 * Description
 * Merge Sort is a divide-and-conquer algorithm:
 *      1. Divide - recurively split the array into halves until
 *                  each sub-array has one element (base case)
 *      2. Cinquer - merge adjacent sorted sub-array back together
 *  Each call to merge() is counted as one "merge operation"
 *
 *  TIME COMPLEXITY
 *      Best case:      O(n log n)
 *      Average case:   O(n log n)
 *      Wort case:      O(n log n)
 *      Space:          O(n) auxiliaey (temporary arrays during merge)
 *
 * Dose shuffling change the number of merges?
 *      NO. The number of merge operations is determined solely by the
 *      size of the input, not by the initial order of elements.
 *      Regardless of whether the array is sorted, reverse-sorted, or
 *      randomly shuffled, the recursion always splits into the same
 *      number of sub-problems, resulting in exactly n-1 merge calls
 *      (The number of element-levle comparisons inside each merge
 *      can vary sligtly, but the merge count itself dose not)
 */

public class MergeSort {

    // Shared conter (rest before each sort)
    private static long mergeOperations = 0;

    /**
     * Public entry point: sort ar in ascending order
     *
     * @param arr array to sort in-place
     * @return number of merge operations performed
     */
    public static long mergeSort(double[] arr) {
        mergeOperations = 0;
        sort(arr, 0, arr.length - 1);
        return mergeOperations;
    }

    // ─── Recursive sort ───────────────────────────────────────────
    private static void sort(double[] arr, int left, int right) {
        if (left >= right) return;       // base case: single element
        int mid = left + (right - left) / 2;

        sort(arr, left, mid);               // sort left half
        sort(arr, mid + 1, right);     // sort right half
        merge(arr, left, mid, right);      // merge the two sorted halves
    }

    // ─── Merge two sorted sub-arrays ─────────────────────────────
    /**
     * Merges arr[left..mid] and arr[mid+1..right] into sorted order
     * Increments mergeOperations by 1
     */
    private static void merge(double[] arr, int left, int mid, int right) {
        mergeOperations++;      // count this merge operation

        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        // Temporary arrays
        double[] leftArr = new  double[leftSize];
        double[] rightArr = new  double[rightSize];

        System.arraycopy(arr, left,             leftArr,    0, leftSize);
        System.arraycopy(arr, mid + 1,  rightArr,    0, rightSize);

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < leftSize) arr[k++] = leftArr[i++];
        while (j < rightSize) arr[k++] = rightArr[j++];
    }

    // ─── main ─────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Problem 3: MergeSort ===");
        System.out.println("Dataser: 112 unique alcohol content vlaues (combined red + white wine)\n");

        int n = DataLoader.getUniqueAlcoholValues().length;
        System.out.println("n = " + n + " -> ecpevted merge operations = n-1 = " + (n - 1));

        // Already sorted input
        double[] data1 = DataLoader.getUniqueAlcoholValues();
        long ops1 = mergeSort(data1);
        System.out.println("\nMergeSort (original/already-sorted order):");
        System.out.println(" Merge operations : " + ops1);
        System.out.println(" Sorted correctly : " + isSorted(data1));

        // Shuffled input
        double[] data2 = DataLoader.getUniqueAlcoholValues();
        BubbleSort.shuffleArray(data2);
        long ops2 = mergeSort(data2);
        System.out.println("\nMergeSort (after shuffle):");
        System.out.println(" Merge operations : " + ops2);
        System.out.println(" Sorted correctly : " + isSorted(data2));


        System.out.println("\n--- Analysis ---");
        System.out.println("Merge operation count is ALWAYS n-1 = " + (n - 1));
        System.out.println("Shuffling dose NOT change the number of merge operations");
        System.out.println("Time complexity: =(n log n) in all cases");


        // Show first and last 5 sorted vlaues
        System.out.printf("\nFirst 5 sorted vlaues: ");
        for (int i = 0; i < 5; i++) System.out.printf("%.4f   ", data2[i]);
        System.out.printf("\nLast 5 sorted vlaues: ");
        for (int i = n - 5; i < n; i++) System.out.printf("%.4f   ", data2[i]);
        System.out.println();
    }


    // ─── Helpers ──────────────────────────────────────────────────
    private static boolean isSorted(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1]) return  false;
        return true;
    }
}
