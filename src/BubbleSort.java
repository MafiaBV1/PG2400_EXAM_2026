import java.util.Random;

public class BubbleSort {

    private static final Random rng = new Random(42);

    public static int sort(double[] arr) {
        int n = arr.length;
        int comparisons = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;

                if (arr[j] > arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return comparisons;
    }

    public static int optimizedSort(double[] arr) {
        int n = arr.length;
        int comparisons = 0;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;

                if (arr[j] > arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
        return comparisons;
    }

    /**
     * Fisher-Yates shuffle - randomises the array in-place.
     * Uses a fixed seed (42) for reproducibility across all sorting classes.
     */
    public static void shuffleArray(double[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            double tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    private static boolean isSorted(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1]) return false;
        return true;
    }


    // ─── main ─────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("Problem 1: BubbleSort");
        System.out.println("Dataset: 112 unique alcohol content values (combined red + white wine)");
        int n = DataLoader.getUniqueAlcoholValues().length;
        System.out.printf("n = %d%n%n", n);

        // --- Already sorted input ---
        double[] data1 = DataLoader.getUniqueAlcoholValues();
        int cmp1 = sort(data1);
        System.out.println("Non-optimised BubbleSort (already sorted input):");
        System.out.println("  Comparisons : " + cmp1);
        System.out.printf("  Expected n*(n-1)/2 = %d%n", (n * (n - 1) / 2));
        System.out.println("  Sorted correctly  : " + isSorted(data1));

        double[] data2 = DataLoader.getUniqueAlcoholValues();
        int cmp2 = optimizedSort(data2);
        System.out.println("\nOptimised BubbleSort (already sorted input - best case):");
        System.out.println("  Comparisons : " + cmp2);
        System.out.printf("  Expected n-1 = %d  (exits after first pass, no swaps)%n", n - 1);
        System.out.println("  Sorted correctly  : " + isSorted(data2));

        // --- Shuffled input ---
        double[] shuffledBase = DataLoader.getUniqueAlcoholValues();
        shuffleArray(shuffledBase);

        double[] data3 = shuffledBase.clone();
        int cmp3 = sort(data3);
        System.out.println("\nNon-optimised BubbleSort (shuffled input):");
        System.out.println("  Comparisons : " + cmp3);
        System.out.println("  Sorted correctly  : " + isSorted(data3));

        double[] data4 = shuffledBase.clone();
        int cmp4 = optimizedSort(data4);
        System.out.println("\nOptimised BubbleSort (shuffled input):");
        System.out.println("  Comparisons : " + cmp4);
        System.out.println("  Sorted correctly  : " + isSorted(data4));

        System.out.println("\n--- Analysis (Part b) ---");
        System.out.println("Non-optimised: always performs n*(n-1)/2 comparisons regardless of input order -> O(n²)");
        System.out.println("Shuffling does NOT change its complexity or comparison count.");
        System.out.println("Optimised: O(n) best case on already-sorted input (exits after 1 pass with no swaps).");
        System.out.println("Shuffling DOES increase the optimised version's comparison count towards O(n²),");
        System.out.println("because random order prevents early exit.");
    }
}
