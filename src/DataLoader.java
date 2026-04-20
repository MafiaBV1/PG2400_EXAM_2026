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
