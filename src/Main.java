import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Main runner - executes all four sorting problems and prints results
 *
 * Compile: javac -d out src/mnai/java/exam/*.java
 * Run:     java -cp out exam.Main
 */
public class Main {

    public static void main(String[] args) {

        BubbleSort.main(args);
        InsertionSort.main(args);
        MergeSort.main(args);
        QuickSort.main(args);
    }
}