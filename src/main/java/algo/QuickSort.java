package algo;

import java.util.Random;

public class QuickSort {

    private static final Random RANDOM = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        metrics.startTimer();
        sortRecursive(a, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sortRecursive(int[] a, int lo, int hi, Metrics metrics) {
        metrics.enterRecursion();
        try {
            while (lo < hi) {
                int[] bounds = partition3way(a, lo, hi, metrics); // {lt, gt}
                int lt = bounds[0];
                int gt = bounds[1];

                int leftSize = lt - lo;
                int rightSize = hi - gt;

                if (leftSize < rightSize) {
                    sortRecursive(a, lo, lt - 1, metrics); // recurse smaller side
                    lo = gt + 1;                            // loop over larger side
                } else {
                    sortRecursive(a, gt + 1, hi, metrics);
                    hi = lt - 1;
                }
            }
        } finally {
            metrics.exitRecursion();
        }
    }

    // 3-way partition: a[lo..lt-1] < pivot, a[lt..gt] == pivot, a[gt+1..hi] > pivot
    static int[] partition3way(int[] a, int lo, int hi, Metrics metrics) {
        int pivotIndex = lo + RANDOM.nextInt(hi - lo + 1); // random pivot
        int pivot = a[pivotIndex];

        int lt = lo, i = lo, gt = hi;

        while (i <= gt) {
            metrics.incComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else {
                metrics.incComparisons();
                if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}