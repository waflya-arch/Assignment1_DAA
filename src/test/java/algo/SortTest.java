package algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    private static final Random RANDOM = new Random();

    @RepeatedTest(100)
    void mergeSortMatchesArraysSort() {
        int[] a = randomArray(500);
        int[] expected = a.clone();
        Arrays.sort(expected);
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(expected, a);
    }

    @RepeatedTest(100)
    void quickSortMatchesArraysSort() {
        int[] a = randomArray(500);
        int[] expected = a.clone();
        Arrays.sort(expected);
        QuickSort.sort(a, new Metrics());
        assertArrayEquals(expected, a);
    }

    @Test
    void mergeSortHandlesEmptyArray() {
        int[] a = {};
        assertDoesNotThrow(() -> MergeSort.sort(a, new Metrics()));
    }

    @Test
    void mergeSortHandlesSingleElement() {
        int[] a = {42};
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void quickSortHandlesAllEqualElements() {
        int[] a = new int[1000];
        Arrays.fill(a, 7);
        QuickSort.sort(a, new Metrics());
        for (int v : a) assertEquals(7, v);
    }

    @Test
    void mergeSortHandlesAlreadySorted() {
        int[] a = {1, 2, 3, 4, 5};
        int[] expected = a.clone();
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(expected, a);
    }

    @Test
    void quickSortDepthIsBoundedOnSortedInput() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(a, metrics);

        int limit = (int) (2 * (Math.log(n) / Math.log(2)));
        assertTrue(metrics.getMaxDepth() <= limit);
    }

    @RepeatedTest(100)
    void quickSelectMatchesSortedArray() {
        int[] a = randomArray(300);
        int k = RANDOM.nextInt(a.length);

        int[] sorted = a.clone();
        Arrays.sort(sorted);

        int result = QuickSelect.select(a, k, new Metrics());
        assertEquals(sorted[k], result);
    }

    @Test
    void quickSelectThrowsOnEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{}, 0, new Metrics()));
    }

    @Test
    void quickSelectThrowsOnInvalidK() {
        int[] a = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, 5, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, -1, new Metrics()));
    }

    private int[] randomArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = RANDOM.nextInt(10_000);
        return a;
    }
}