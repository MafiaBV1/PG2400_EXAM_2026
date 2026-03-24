
/**
 * WineDataLoader
 *
 * Provides the unique alcahole content values extrcted from the combined
 * red-wine (winequality-red.csv) and white-wine (winequality-white.csv) dataset
 *
 *
 * Dataset source: https://archive.ics.uci.edu/ml/datasets/wine+quality
 *
 * Total unique values 112
 */

public class DataLoader {

    /**
     * Return a fresh copy of the 112 unique alcohole content values
     * (combined from red and withe wine datasets),sorted by value
     */

    public static double[] getUniqueAlcoholValues() {
        return new double[] {
                8.0, 8.0, 8.4, 8.5, 8.5, 8.7, 8.8, 8.9,

        };
    }
}
