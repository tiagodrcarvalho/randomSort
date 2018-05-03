package pt.up.fe.specs.sort.algorithms;


/**
 *
 *
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public class SelectionSort {
    /**
     * This method implements the Generic Selection Sort
     *
     * @param arr
     * 		The array to be sorted
     * @param n
     * 		The count of total number of elements in array Sorts the array in increasing order
     */
    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < (length - 1); i++) {
            // Initial index of min
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if ((arr[j]) < (arr[min])) {
                    min = j;
                }
            }
            // Swapping if index of min is changed
            if (min != i) {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }
}

