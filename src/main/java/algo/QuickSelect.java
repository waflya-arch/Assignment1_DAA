package algo;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k=" + k + " is out of range [0, " + (a.length - 1) + "]");
        }

        int[] copy = a.clone(); // don't mutate caller's array
        metrics.startTimer();
        int result = selectRecursive(copy, 0, copy.length - 1, k, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int selectRecursive(int[] a, int lo, int hi, int k, Metrics metrics) {
        metrics.enterRecursion();
        try {
            while (true) {
                if (lo == hi) return a[lo];

                int[] bounds = QuickSort.partition3way(a, lo, hi, metrics);
                int lt = bounds[0];
                int gt = bounds[1];

                if (k < lt) {
                    hi = lt - 1; // target is in the left part
                } else if (k > gt) {
                    lo = gt + 1; // target is in the right part
                } else {
                    return a[k]; // k falls in the "== pivot" zone
                }
            }
        } finally {
            metrics.exitRecursion();
        }
    }
}