package algo;

public class MergeSort {

    private static final int CUTOFF = 15; // switch to insertion sort below this size

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        int[] buffer = new int[a.length]; // single buffer, allocated once
        metrics.startTimer();
        sortRecursive(a, buffer, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sortRecursive(int[] a, int[] buffer, int lo, int hi, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (hi - lo <= CUTOFF) {
                insertionSort(a, lo, hi, metrics);
                return;
            }
            int mid = lo + (hi - lo) / 2;
            sortRecursive(a, buffer, lo, mid, metrics);
            sortRecursive(a, buffer, mid + 1, hi, metrics);
            merge(a, buffer, lo, mid, hi, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static void merge(int[] a, int[] buffer, int lo, int mid, int hi, Metrics metrics) {
        System.arraycopy(a, lo, buffer, lo, hi - lo + 1); // copy once into buffer

        int i = lo, j = mid + 1, k = lo;

        while (i <= mid && j <= hi) {
            metrics.incComparisons();
            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }
        while (i <= mid) a[k++] = buffer[i++]; // copy remaining left
        while (j <= hi) a[k++] = buffer[j++];  // copy remaining right
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                metrics.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}