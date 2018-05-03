package pt.up.fe.specs.sort.algorithms;


public class MergerSort {
    public static void sort(int[] a) {
        int[] tmp = new int[a.length];
        MergerSort.mergeSort(a, tmp, 0, ((a.length) - 1));
    }

    private static void mergeSort(int[] a, int[] tmp, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            MergerSort.mergeSort(a, tmp, left, center);
            MergerSort.mergeSort(a, tmp, (center + 1), right);
            MergerSort.merge(a, tmp, left, (center + 1), right);
        }
    }

    private static void merge(int[] a, int[] tmp, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = (rightEnd - left) + 1;
        while ((left <= leftEnd) && (right <= rightEnd)) {
            if ((a[left]) <= (a[right])) {
                tmp[(k++)] = a[(left++)];
            }else {
                tmp[(k++)] = a[(right++)];
            }
        } 
        while (left <= leftEnd) {
            tmp[(k++)] = a[(left++)];
        } 
        while (right <= rightEnd) {
            tmp[(k++)] = a[(right++)];
        } 
        // Copy tmp back
        for (int i = 0; i < num; i++ , rightEnd--) {
            a[rightEnd] = tmp[rightEnd];
        }
    }
}

