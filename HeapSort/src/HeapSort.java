import java.io.*;
import java.util.*;

public class HeapSort {

		// keeps track of heapSize as it is not equivalent to the array length
		private int heapSize;

		/**
		 * Parent method returns index i's parent node
		 * 
		 * @param index
		 *              int -> index who's parent trying to find
		 * @return index int -> parent index number
		 */
		public int parent(int index) {
			return (int) Math.floor(index / 2);		// Parent located at floor of index/2
		}

		/**
		 * Left method returns index of i's left child node
		 * 
		 * @param index
		 *              int -> index who's left child trying to find
		 * @return index int -> left child index number
		 */
		public int left(int index) {
			return 2 * index;		// Left child located at index*2
		}

		/**
		 * Right method returns index of i's right node
		 * 
		 * @param index
		 *              int - > index who's right child trying to find
		 * @return index int - right child index number
		 */
		public int right(int index) {
			return 2 * index + 1;		// Right child located at index*2 + 1
		}

		/**
		 * Max Heapify reorders heap to maintain max-heap property by comparing parent, left, and right nodes
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes
		 * @return void
		 */
		public void maxHeapify(ArrayList<Rank> A, int currentIndex) {
			int largestIndex;		// Stores the index of the largest node

			int leftChild = left(currentIndex);		// Store index of left child
			int rightChild = right(currentIndex);		// Store index of right child

			// Check if left child index is within heap & if left child value is greater than current index
			if (leftChild <= heapSize && A.get(leftChild).getRank() > A.get(currentIndex).getRank()) {
					largestIndex = leftChild;		// If so, set largest to index of left child
			} else {
					largestIndex = currentIndex;		// If not, set largest index to current index
			}

			// Check if right child index is within heap & if right child value is greater than current index
			if (rightChild <= heapSize && A.get(rightChild).getRank() > A.get(largestIndex).getRank()) {
					largestIndex = rightChild;		// Set largest index to index of right child
			}

			if (largestIndex != currentIndex) {		// If largest index is not the current index (Does not satisfy max-heap property)
					Collections.swap(A, currentIndex, largestIndex);		// exchange A[currentIndex] with A[largestIndex]
					maxHeapify(A, largestIndex);			// Call maxHeapify on index of where largest used to be
			}

		}

		/**
		 * Build Max Heap using ArrayList and ensure max heap property is maintained Since A[((n/2) +1)] to A[n] are leaf nodes, ensure nodes A[0] to A[n/2]
		 * satisfy max heap property
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes
		 * @return void
		 */
		public void buildMaxHeap(ArrayList<Rank> A) {
			heapSize = A.size() - 1;		// Set heap size to Array of size (size returns # of elements but heap numbering starts at 0; subtract 1)

			// Goes through all nodes with children and calls Max heapify on them
			for (int index = (int) Math.floor(A.size() / 2); index >= 0; index--) {
					maxHeapify(A, index);			// Max heapify every parent node of tree
			}
		}

		/**
		 * Sort heap by first building the heap, swapping the first and last values, and call max heapify The ArrayList will be sorted in ascending order
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes
		 * @return void -> ArrayList is now sorted in ascending order
		 */
		public void heapSort(ArrayList<Rank> A) {
			buildMaxHeap(A);		// Call build max heap to build max heap

			// Start at end of array and insert node at root of tree, then decrement to go through entire array
			for (int index = A.size() - 1; index > 0; index--) {
					Collections.swap(A, 0, index);		// exchange root node with index
					heapSize = heapSize - 1;		// Decrease heap size
					maxHeapify(A, 0);		// Call max heapify on new root node
			}
		}

		/**
		 * Returns node of largest element in the heap. Peek function so does not remove node
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes
		 * @return Rank -> max value of heap tree as Rank object
		 */
		public Rank heapMaximum(ArrayList<Rank> A) {
			return A.get(0);		// By the max heap property, the largest element is stored at the root of the heap = A[0]
		}

		/**
		 * Extracts root node with maximum value of heap if heap size is greater than 0
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes
		 * @return Rank -> max value of heap tree as Rank object
		 */
		public Rank heapExtractMax(ArrayList<Rank> A) {
			try {		// Try used for catching exception thrown in if statement
					if (heapSize < 0) {			// Check if heap has any elements
						throw new ArrayIndexOutOfBoundsException("Heap underflow");		// If has no elements, throw an exception for heap underflow
					}
			} catch (ArrayIndexOutOfBoundsException ex) {		// Catch the exception thrown
					System.out.println(ex.getMessage());		// Print exception error message
			}

			Rank max = A.get(0);		// Store max value that is at root node of heap
			A.set(0, A.get(heapSize));		// Set heap root node to last element in heap
			A.remove(heapSize);
			heapSize--;		// Decrease heap size because extracting max element
			maxHeapify(A, 0);		// Call max heapify on new heap
			return max;			// Return value of max element
		}

		/**
		 * Increases key value of given index and reorders tree
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes 
		 *              index int -> index of node want to change key of key int -> new key value of node
		 * @return void -> newly sorted max heap
		 */
		public void heapIncreaseKey(ArrayList<Rank> A, int index, int key) {
			try {		// Try used for catching exception thrown in if statement
					if (key < A.get(index).getRank()) {		// Check if value of new key is less than current key value
						throw new IOException("New key is smaller than current key");		// If less than current value, throw exception
					}
			} catch (IOException ex) {		// Catch the exception thrown
					System.out.println(ex.getMessage());		// Print exception error message
			}

			if (A.get(index) instanceof WebPageURL) {
					A.get(index).increase(key);
			} else {
					A.get(index).increase(1);
			}

			while (index > 0 && A.get(parent(index)).getRank() < A.get(index).getRank()) {		// Heapify elements to check if parent's index is less
					// than child's index
					Collections.swap(A, index, parent(index));			// Swap values of parent and child
					index = parent(index);		// Set new index to be parent's index
			}
		}

		/**
		 * Insert new element into the max heap
		 * 
		 * @param A
		 *              ArrayList<Rank> -> ArrayList of Rank objects that hold nodes 
		 *              key int -> key value of node you are inserting
		 * @return void -> newly sorted max heap
		 */
		public void maxHeapInsert(ArrayList<Rank> A, int key) {
			heapSize += 1;		// Increases the heap size
			A.add(heapSize, new WebPageURL(key));		// Creates a new leaf node in the heap
			heapIncreaseKey(A, heapSize, key);		// Sets the new leaf node to its correct key value
		}

}
