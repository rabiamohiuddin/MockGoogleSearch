/**
 * Interface for WebPageURL and SearchTerms so HeapSort can use ArrayList with both of these objects
 */
public interface Rank {
		public int getRank();		// Total rank for WebPageURL or occurrence for SearchTerms

		public void printAttributes();		// Print attributes function

		public String getName();			// Retrieve URL name or search term name

		public void increase(int value);		// Increase value of object (either total rank or search occurrence)
}
