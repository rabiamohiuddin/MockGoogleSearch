# MockGoogleSearch
Mock Google Search simulator using heap sort and web crawlers

<b>How to Run Application</b>

1. Unzip application
2. Open a terminal or console window
3. Change directories to the bin folder of the unzipped project
4. Execute java -jar BuildApp.jar
5. Try all options to view complete functionality of application

<b>Implementation Overview</b>

The Google Search Engine Simulator allows a user to conduct a search of a term they
desire with four main sub functions. Once the search is submitted, a list of the top 10 results are
displayed to the user along with options on what to do next. 
These options include:
1. View the complete search list of 30 URLs
2. Pay to raise the rankings of a site (only available for top 10 URLs)
3. View the top 10 searches of the day
4. Run another search

The Simulator consists of five java classes and one interface: HeapSort, FunnyCrawler,
BuildApp, WebPageURL, SearchTerms, and Rank Interface.

The BuildApp class uses all classes (HeapSort, FunnyCrawler, WebPageURL, and
SearchTerms).

HeapSort uses Rank Interface for its ArrayList.
