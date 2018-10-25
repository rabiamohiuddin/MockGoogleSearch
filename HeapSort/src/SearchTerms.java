
public class SearchTerms implements Rank {
		private String searchTerm;		// String of search term
		private int numTimesSearched;		// Occurrences search term has been searched

		/**
		 * Constructor with search term sets occurrence to 1
		 * 
		 * @param searchTerm
		 *              String -> What the user has entered
		 * @return none
		 */
		public SearchTerms(String searchTerm) {
			this.searchTerm = searchTerm;
			numTimesSearched = 1;
		}

		/**
		 * Get method for name of search term
		 * 
		 * @param none
		 * @return searchTerm String
		 */
		public String getName() {
			return searchTerm;
		}

		/**
		 * Get method for ranking (number of times term has been searched)
		 * 
		 * @param none
		 * @return numTimesSearched int
		 */
		public int getRank() {
			return numTimesSearched;
		}

		/**
		 * Set method for numTimesSearched
		 * 
		 * @param value
		 *              int
		 * @return none
		 */
		public void increase(int value) {
			numTimesSearched += value;
		}

		/**
		 * Override equals method that says two objects are equal is their search terms are the same
		 * 
		 * @param o
		 *              Object
		 * @return boolean -> true if have same search term. false if different search term
		 */
		@Override
		public boolean equals(Object o) {
			if (o instanceof SearchTerms) {
					SearchTerms p = (SearchTerms) o;
					return this.getName().equalsIgnoreCase(p.getName());
			} else if (o instanceof String) {
				return this.getName().equalsIgnoreCase((String) o);
			}
			return false;
		}

		/**
		 * Print attributes (search term and number of occurrences)
		 * 
		 * @param none
		 * @return none
		 */
		public void printAttributes() {
			System.out.println(searchTerm + " : " + numTimesSearched + " times");
		}

}
