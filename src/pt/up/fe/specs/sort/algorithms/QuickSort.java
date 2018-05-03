package pt.up.fe.specs.sort.algorithms;


public class QuickSort {
    public static void sort(int[] values) {
        int size = values.length;
        QuickSort.quicksort(values, 0, (size - 1));
    }

    private static void quicksort(int[] values, int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        // Calculate pivot number, I am taking pivot as middle index number.
        int pivot = values[(lowerIndex + ((higherIndex - lowerIndex) / 2))];
        // Divide into two arrays.
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which is greater then the pivot value, and
also we will identify a number from right side which is less then the pivot value. Once the search is
done, then we exchange both numbers.
             */
            while ((values[i]) < pivot) {
                i++;
            } 
            while ((values[j]) > pivot) {
                j--;
            } 
            if (i <= j) {
                int temp = values[i];
                values[i] = values[j];
                values[j] = temp;
                // Move index to next position on both sides.
                i++;
                j--;
            }
        } 
        // Call quicksort recursively.
        if (lowerIndex < j) {
            QuickSort.quicksort(values, lowerIndex, j);
        }
        if (i < higherIndex) {
            QuickSort.quicksort(values, i, higherIndex);
        }
    }
}

