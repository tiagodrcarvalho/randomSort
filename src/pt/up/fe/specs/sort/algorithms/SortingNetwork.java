package pt.up.fe.specs.sort.algorithms;


public class SortingNetwork {
    public static void __sort(int[] valuesArray) {
        // foldedSortNet
        int length = valuesArray.length;
        int halfLength = length / 2;
        for (int i = 0; i < halfLength; i++) {
            /* odd */
            SortingNetwork.compareLevel(valuesArray, 0);
            /* even */
            SortingNetwork.compareLevel(valuesArray, 1);
        }
        if ((length % 2) != 0) {
            SortingNetwork.compareLevel(valuesArray, 0);
        }
    }

    private static void swapMax(int[] valuesArray, int index) {
        if ((valuesArray[index]) > (valuesArray[(index + 1)])) {
            int temp = valuesArray[index];
            valuesArray[index] = valuesArray[(index + 1)];
            valuesArray[(index + 1)] = temp;
        }
    }

    private static void compareLevel(int[] values, int start) {
        int i;
        int length = values.length;
        for (i = start; (i + 1) < length; i += 2) {
            SortingNetwork.swapMax(values, i);
        }
    }
}

