package algo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

            for (int n : SIZES) {
                for (String inputType : INPUT_TYPES) {
                    runCase("MergeSort", inputType, n, writer);
                    runCase("QuickSort", inputType, n, writer);
                    runCase("QuickSelect", inputType, n, writer);
                }
            }
        }
        System.out.println("Done. Results saved to results.csv");
    }

    private static void runCase(String algorithm, String inputType, int n, FileWriter writer) throws IOException {
        double[] times = new double[RUNS];
        long[] comparisons = new long[RUNS];
        int[] depths = new int[RUNS];

        for (int r = 0; r < RUNS; r++) {
            int[] data = generateInput(inputType, n);
            Metrics metrics = new Metrics();

            switch (algorithm) {
                case "MergeSort" -> MergeSort.sort(data, metrics);
                case "QuickSort" -> QuickSort.sort(data, metrics);
                case "QuickSelect" -> QuickSelect.select(data, n / 2, metrics); // find median
            }

            times[r] = metrics.getElapsedMillis();
            comparisons[r] = metrics.getComparisons();
            depths[r] = metrics.getMaxDepth();
        }

        double medianTime = median(times);
        long medianComparisons = medianLong(comparisons);
        int medianDepth = medianInt(depths);

        writer.write(String.format("%s,%s,%d,%.4f,%d,%d%n",
                algorithm, inputType, n, medianTime, medianComparisons, medianDepth));
        System.out.printf("%s | %s | n=%d | time=%.2fms | comparisons=%d | depth=%d%n",
                algorithm, inputType, n, medianTime, medianComparisons, medianDepth);
    }

    private static int[] generateInput(String type, int n) {
        Random rnd = new Random();
        int[] a = new int[n];
        switch (type) {
            case "random" -> { for (int i = 0; i < n; i++) a[i] = rnd.nextInt(1_000_000); }
            case "sorted" -> { for (int i = 0; i < n; i++) a[i] = i; }
            case "duplicates" -> { for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10); }
        }
        return a;
    }

    private static double median(double[] values) {
        double[] copy = values.clone();
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }

    private static long medianLong(long[] values) {
        long[] copy = values.clone();
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }

    private static int medianInt(int[] values) {
        int[] copy = values.clone();
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }
}