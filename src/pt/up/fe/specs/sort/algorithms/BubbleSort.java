package pt.up.fe.specs.sort.algorithms;


/**
 *
 *
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public class BubbleSort {
    /**
     * This method implements the Generic Bubble Sort
     *
     * @param array
     * 		The array to be sorted
     * @param last
     * 		The count of total number of elements in array Sorts the array in increasing order
     */
    public static void __sort(int[] array) {
        // Sorting
        boolean swap;
        int last = array.length;
        do {
            swap = false;
            for (int count = 0; count < (last - 1); count++) {
                if ((array[count]) > (array[(count + 1)])) {
                    int temp = array[count];
                    array[count] = array[(count + 1)];
                    array[(count + 1)] = temp;
                    swap = true;
                }
            }
            last--;
        } while (swap );
    }
}

