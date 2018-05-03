package pt.up.fe.specs.sort.algorithms;


/**
 * Heap Sort Algorithm Implements MinHeap
 *
 * @author Unknown
 */
public class HeapSort {
    /**
     * * Array to store heap
     */
    private int[] heap;

    /**
     * * The size of the heap
     */
    private int size;

    public static void sort(int[] values) {
        new HeapSort(values).sort();
    }

    /**
     * Constructor
     *
     * @param heap
     * 		array of unordered integers
     */
    public HeapSort(int[] heap) {
        this.setHeap(heap);
        this.setSize(heap.length);
    }

    /**
     * Setter for variable size
     *
     * @param length
     * 		new size
     */
    private void setSize(int length) {
        this.size = length;
    }

    /**
     * Setter for variable heap
     *
     * @param heap
     * 		array of unordered elements
     */
    private void setHeap(int[] heap) {
        this.heap = heap;
    }

    /**
     * Swaps index of first with second
     *
     * @param first
     * 		First index to switch
     * @param second
     * 		Second index to switch
     */
    private void swap(int first, int second) {
        int temp = this.heap[first];
        this.heap[first] = this.heap[second];
        this.heap[second] = temp;
    }

    /**
     * Heapifies subtree from top as root to last as last child
     *
     * @param rootIndex
     * 		index of root
     * @param lastChild
     * 		index of last child
     */
    private void heapSubtree(int rootIndex, int lastChild) {
        int leftIndex = (rootIndex * 2) + 1;
        int rightIndex = (rootIndex * 2) + 2;
        int root = this.heap[rootIndex];
        // if no right child, but has left child
        if (rightIndex <= lastChild) {
            // if has right and left children
            int left = this.heap[leftIndex];
            int right = this.heap[rightIndex];
            if ((left < right) && (left < root)) {
                this.swap(leftIndex, rootIndex);
                this.heapSubtree(leftIndex, lastChild);
            }else
                if (right < root) {
                    this.swap(rightIndex, rootIndex);
                    this.heapSubtree(rightIndex, lastChild);
                }
            
        }// if no right child, but has left child
        else
            if (leftIndex <= lastChild) {
                int left = this.heap[leftIndex];
                if (left < root) {
                    this.swap(leftIndex, rootIndex);
                    this.heapSubtree(leftIndex, lastChild);
                }
            }
        
    }

    /**
     * Makes heap with root as root
     *
     * @param root
     * 		index of root of heap
     */
    private void makeMinHeap(int root) {
        int leftIndex = (root * 2) + 1;
        int rightIndex = (root * 2) + 2;
        boolean hasLeftChild = leftIndex < (this.heap.length);
        boolean hasRightChild = rightIndex < (this.heap.length);
        if (hasRightChild) {
            // if has left and right
            this.makeMinHeap(leftIndex);
            this.makeMinHeap(rightIndex);
            this.heapSubtree(root, ((this.heap.length) - 1));
        }else
            if (hasLeftChild) {
                this.heapSubtree(root, ((this.heap.length) - 1));
            }
        
    }

    /**
     * Gets the root of heap
     *
     * @return root of heap
     */
    private int getRoot() {
        this.swap(0, ((this.size) - 1));
        (this.size)--;
        this.heapSubtree(0, ((this.size) - 1));
        return this.heap[this.size];// return old root
        
    }

    /**
     * Sorts heap with heap sort; displays ordered elements to console.
     *
     * @return sorted array of sorted elements
     */
    public final int[] sort() {
        this.makeMinHeap(0);// make min heap using index 0 as root.
        
        int[] sorted = new int[this.size];
        int index = 0;
        while ((this.size) > 0) {
            int min = this.getRoot();
            sorted[index] = min;
            index++;
        } 
        return sorted;
    }
}

