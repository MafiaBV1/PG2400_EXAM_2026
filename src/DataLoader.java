import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/**
 * WineDataLoader:
 * Provides the unique alcahole content values extrcted from the combined
 * red-wine (winequality-red.csv) and white-wine (winequality-white.csv) dataset
 * Dataset source: https://archive.ics.uci.edu/ml/datasets/wine+quality
 * Total unique values 112
 */

public class DataLoader {

    /**
     * Return a fresh copy of the 112 unique alcohole content values
     * (combined from red and withe wine datasets),sorted by value.
     * All sorting algorithms in this project sort the array IN-PLACE,
     * meaning they modify the original array directly
     * By returning a new array every time this method is called,
     * we ensure that each sorting algorithem starts with the same
     * unmodified dataset - preventing one sort from affecting another.
     *
     * Value range: 8.0% to 14.9% alcohole content
     *
     * @return double array of 112 unique alcohole content values
     */

//    public static double[] getUniqueAlcoholValues() {
//        // Each row represents a range of alcohole % values from the dataset
//        // Values are floating point (double) to preserve decimal precision
//        // from the original CSV data
//        return new double[] {
//                // 8.x range - lowest alcohole content in dataset
//                8.0, 8.0, 8.4, 8.5, 8.5, 8.7, 8.8, 8.9,
//
//                // 9.x range
//                9.0, 9.05, 9.1, 9.2, 9.23333333333333, 9.25,
//                9.3, 9.4, 9.5, 9.53333333333333, 9.55, 9.56666666666667,
//                9.6, 9.63333333333333, 9.7, 9.73333333333333, 9.75,
//                9.8, 9.9, 9.95,
//
//                // 10.x range
//                10.0, 10.0333333333333, 10.1, 10.1333333333333, 10.15, 10.2,
//                10.3, 10.4, 10.4666666666667, 10.5, 10.5333333333333, 10.55,
//                10.5666666666667, 10.6, 10.65, 10.7, 10.75, 10.8,
//                10.9, 10.9333333333333, 10.9666666666667, 10.98,
//
//                // 11.x range - most dense cluster of values
//                11.0, 11.05, 11.0666666666667, 11.1, 11.2, 11.2666666666667,
//                11.3, 11.3333333333333, 11.35, 11.3666666666667, 11.4,
//                11.4333333333333, 11.45, 11.4666666666667, 11.5, 11.55,
//                11.6, 11.6333333333333, 11.65, 11.7, 11.7333333333333,
//                11.75, 11.8, 11.85, 11.9, 11.94, 11.95,
//
//                // 12.x range
//                12.0, 12.05, 12.0666666666667, 12.1, 12.15, 12.2,
//                12.25, 12.3, 12.3333333333333, 12.4, 12.5, 12.6,
//                12.7, 12.75, 12.8, 12.8933333333333, 12.9,
//
//                // 13.x range
//                13.0, 13.05, 13.1, 13.1333333333333, 13.2, 13.3,
//                13.4, 13.5, 13.55, 13.5666666666667, 13.6, 13.7,
//                13.8, 13.9,
//
//                // 14.x range - highest alcohol content in dataset
//                14.0, 14.05, 14.2, 14.9
//        };
//    }
//
//    /**
//     * Utility method to print any double array to the consol.
//     * Used for debugging and verifying that sorting algorithms
//     * produce the correct output.
//     * Example output: [8.0000, 8.4000, 8.5000, ...]
//     *
//     * @param arr the array to print
//     */
//
//
//    public static void printArray(double[] arr) {
//        System.out.print("[");
//        for (int i = 0; i < arr.length; i++) {
//            // %.4f formats each value to 4 decimal places for consistent output
//            System.out.printf("%.4f", arr[i]);
//            // Print comma separator after every element except the last
//            if (i < arr.length - 1) System.out.print(", ");
//        }
//        System.out.println("]"); // closing bracket + newline
//    }

    public static double[] getUniqueAlcoholValues() {
        // TreeSet removes duplicates
        TreeSet<Double> uniqueValues = new TreeSet<>();

        String[] files = {
                "src/res/winequality-red.csv", // <- pass på at filen ligger i riktig fil bane
                "src/res/winequality-white.csv" // <- pass på at filen ligger i riktig fil bane
        };

        for (String filename : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                boolean firstLine = true;

                while ((line = br.readLine()) != null) {
                    // skip header's
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    // CSV uses smeicolon ; as separator
                    String[] columns = line.split(";");

                    // Alcohol is in column 11 (index 10)
                    String alcholStr = columns[10].replace("\"", "").trim();
                    double alcohole = Double.parseDouble(alcholStr);

                    uniqueValues.add(alcohole);
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + filename);
            }
        }

        // Convert TreeSet to double[]
        double[] result = new double[uniqueValues.size()];
        int i = 0;
        for (double val : uniqueValues) {
            result[i++] = val;
        }
        return result;
    }
}
