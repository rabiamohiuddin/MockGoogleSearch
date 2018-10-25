import java.util.*;

public class BuildApp {
		ArrayList<Rank> URLObjects = new ArrayList<Rank>();
		HeapSort top10heap = new HeapSort();
		ArrayList<Rank> top10urls = new ArrayList<Rank>();

		ArrayList<Rank> searchOccurrences = new ArrayList<Rank>();
		HeapSort top10searchesHeap = new HeapSort();
		ArrayList<Rank> top10searches = new ArrayList<Rank>();

		/**
		 * private search method takes search string as input and returns ArrayList of WebPageURL objects
		 * 
		 * @param search
		 *              String -> search term
		 * @return ArrayList<Rank> -> ArrayList of type WebPageURL (implementation of Rank)
		 */
		private ArrayList<Rank> search(String search) {
			FunnyCrawler obj = new FunnyCrawler();			// Create web crawler object
			Set<String> result = obj.getDataFromGoogle(search);		// Set of unique URLs from search results
			result.remove("");		// Remove any empty URLs or strings

			ArrayList<Rank> URLobjects = new ArrayList<Rank>();		// Create ArrayList of WebPageURL objects
			URLobjects.clear();		// Clear the local ArrayList to make sure nothing is inside
			int index = 1;
			for (String temp : result) {			// Iterate through unique URL results, create WebPageURL objects and add to ArrayList
					URLobjects.add(new WebPageURL(temp, index));			// Create WebPageURL object and add to URLobjects ArrayList
					index++;
			}

			return URLobjects;		// Return ArrayList of URLobjects
		}

		/**
		 * public search method that retrieves search string from user, calls search and retrieves results, then builds heap, extracts top 10 results, builds a
		 * top 10 heap, and prints top 10 results
		 * 
		 * @param searchTerm
		 *              String -> search term
		 * @return none
		 */
		public void enterSearch(String searchTerm) {
			addSearchTerm(searchTerm);			// Given searh term user entered, call method to add term to the search term heap
			
			URLObjects.clear();			// Clear the public URLObjects ArrayList to ensure no old results left behind

			URLObjects = search(searchTerm);		// Get new search results and store them in public ArrayList

			HeapSort hSort = new HeapSort();		// Create HeapSort object to sort all search results
			hSort.buildMaxHeap(URLObjects);		// Build max heap using the ArrayList of URLObjects retrieved from search results
			System.out.println("Your term  '" + searchTerm + "' found " + URLObjects.size() + " results");		// Print # of results to user

			top10urls.clear();		// Clear the public top10urls ArrayList to ensure no old results left behind
			for (int i = 0; i < 10 && i < URLObjects.size(); i++) {		// Extract top 10 highest ranked URLs
					top10urls.add(hSort.heapExtractMax(URLObjects));			// Add each max node from results to top10urls ArrayList
			}

			top10heap.buildMaxHeap(top10urls);			// Build max heap using top10urls ArrayList
			print(top10urls);			// Print top 10 urls and their attributes to user
	
		}

		/**
		 * print attributes for given ArrayList in numbered fashion
		 * 
		 * @param arrlist
		 *              ArrayList<Rank> -> ArrayList of type Rank (WebPageURL, SearchTerms)
		 * @return none
		 */
		public void print(ArrayList<Rank> arrlist) {
			int number = 1;			// Start numbering at 1
			for (Rank obj : arrlist) {			// For each Rank object in the ArrayList, print
					System.out.println(number);		// Print item number
					obj.printAttributes();		// Call printAttributes of the object
					System.out.println();		// Print empty line between items for clarity
					number++;			// Increment the number for the next iteration
			}
		}

		/**
		 * Retreive index of search term in the search occurrences ArrayList
		 * 
		 * @param searchTerm
		 *              String -> Search term user had initially entered
		 * @return index of search term in sesrchOccurences ArrayList
		 */
		public int getIndexByname(String searchTerm) {
			for (Rank item : searchOccurrences) {		// For every Rank object in searchOccurrences
					if (item.getName().equals(searchTerm)) 		// Check if Rank objects name is equal to the search term
						return searchOccurrences.indexOf(item);		// If so return index of Rank object
			}
			return -1;		// If can't find search term in arrayList return -1 to show not found
		}

		/**
		 * Add search term to searchOccurrences ArrayList whenever search method is called or increment value of searchTerm
		 * 
		 * @param searchTerm
		 *              String -> Search term user had initially entered
		 * @return none
		 */
		private void addSearchTerm(String searchTerm) {
						
			int index = getIndexByname(searchTerm);		// Call getIndexByName function to retrieve index in ArrayList, return -1 if not
			if (index != -1) {					// If search term is in ArrayList
					searchOccurrences.get(index).increase(1);		// Retrieve object from searchOccurrences and increase value by 1
			} else {
					searchOccurrences.add(new SearchTerms(searchTerm));		// If not in searchOccurrences then add it
			}
		}


		/**
		 * Retreive top 10 searches and print to the user
		 * 
		 * @param none
		 * @return none
		 */
		public void getTop10searches() {
			System.out.println("Top 10 search terms today: ");		// Header to print to the user
			
			HeapSort heapSort = new HeapSort();			// Create heapsort object to sort searchOccurrences
			heapSort.buildMaxHeap(searchOccurrences);		// Build max heap using searchOccurrences

			top10searches.clear();
			ArrayList<Rank> searchOccCOPY = new ArrayList<Rank>();
			for (Rank obj: searchOccurrences) {
				searchOccCOPY.add(obj);
			}
			
			for (int i = 0; i < 10 && i < searchOccurrences.size(); i++) {		// Iterate 10 times or size of searchOccurrences to extract top 10
					top10searches.add(heapSort.heapExtractMax(searchOccCOPY));		// Extract max nodes from searchOccurrences
																									// And add to top10searches ArrayList
			}

			top10searchesHeap.heapSort(top10searches);		// Use public top10searchesHeap to sort top10searches
			Collections.reverse(top10searches);		// Heapsort sorts in ascending order so reverse to sort in descending order
			print(top10searches);			// Print top 10 searches and their attributes to the user

		}

		/**
		 * Creates user interface that displays options to user, reads in their choice and calls respective functions and loops back to options
		 * 
		 * @param none
		 * @return none
		 */
		public void userInterface() {
			Scanner reader = new Scanner(System.in);  // Reading from System.in

			System.out.print("Enter a search term: ");			// Ask user for a search term to start application
			enterSearch(reader.nextLine());			// Read the search term in and call search function

			String option = null;			// Set option to null so can enter while loop
			while (!"0".equals(option)) {		// Option 0 represents Quit - As long as not quit, loop through options
					// Ask user what they want to do - display 6 options
					System.out.println("What would you like to do next?");
					System.out.println("1. View top 10 URLs from search");
					System.out.println("2. View the complete search list of 30 URLs");
					System.out.println("3. Pay to raise the rankings of a site (only available for top 10 URLs)");
					System.out.println("4. View the top 10 searches of the day");
					System.out.println("5. Run another search");
					System.out.println("0. Quit");

					System.out.print("\nOption: ");		// Ask user to select one of the above options

					option = reader.nextLine().trim();		// Read in their option choice

					if (!"0".equals(option)) {		// If option is not quit, go through switch statement for respective options
						switch (option) {
						case "1":			// View top 10 URLs from search
								print(top10urls);		// Call print function on the top 10 URLs
								break;

						case "2":			// View complete search results of about 30 URLs
								print(URLObjects);		// Call print function on results ArrayList
								break;

						case "3":		// Pay to raise the rankings of a site (only available for top 10 URLs)
								System.out.print("Which number URL do you want to pay for? ");
								try {
									int URLnumber = Integer.parseInt(reader.nextLine().trim());		// Convert URL number to int

									if (URLnumber < 1 || URLnumber > 10) {		// Ensure number is between 1 and 10
											// throw exception is not between 1 and 10
											throw new ArrayIndexOutOfBoundsException("URLnumber does not exist\n");
									}

									System.out.print("Enter dollars you will pay: $ ");		// Ask user how much they want to pay

									try {
											int dollarsPaying = Integer.parseInt(reader.nextLine().trim());		// Ensure dollar amount is int
											// Increase key of URL user picked with dollar amount they included
											top10heap.heapIncreaseKey(top10urls, URLnumber - 1, dollarsPaying);

									} catch (NumberFormatException ex) {		// If could not convert to int, throw exception
											System.out.println("Invalid dollar amount\n");
											System.out.println(ex.getMessage());		// Print exception error message
									}

								} catch (NumberFormatException ex) {		// If could not convert URL number to int, throw exception
									System.out.println("Invalid URL number\n");
									System.out.println(ex.getMessage());		// Print exception error message

								} catch (ArrayIndexOutOfBoundsException ex) {		// If URL number is not between 1 and 10, throw exception
									System.out.println(ex.getMessage());		// Print exception error message
								}

								break;

						case "4":		// View the top 10 searches of the day
								getTop10searches();		// Call top10searches function to display to the user
								System.out.println("");
								break;

						case "5":		// Run another search
								System.out.print("Enter a search term: ");		// Ask for new search term
								enterSearch(reader.nextLine());			// Call search function with the search term
								break;

						default:		// If not options 0-5, ask user to reselect an option
								System.out.println("Please select one of the above options. Input must be a digit between 0 and 4\n");
						}
					} else {		// User has selected 0 meaning quit, say Goodbye
						System.out.println("Goodbye!");
					}

			}

			reader.close();		// Close scanner object

		}

		/**
		 * Welcomes user and calls the userInterface method
		 * 
		 * @param none
		 * @return none
		 */
		public static void main(String[] args) {
			BuildApp app = new BuildApp();			// Create BuildApp object to call userInterface
			System.out.println("Welcome to my mock Google search simulator!");
			System.out.println("Author: Rabia Mohiuddin\n");
			app.userInterface();		// Call userInterface to start application
		}
}
