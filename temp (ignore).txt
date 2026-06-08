# Performance Comparison of Trie, HashMap, and Sorted ArrayList in an Autocomplete Program

## Quick Links
Link to report (for OOP class): https://docs.google.com/document/d/1Gv1QPSBOIifG12q4DnK7RZ5Ra1F5EuOMN59h4Pf3g8c/edit?tab=t.0

Link to report (for DS class): https://docs.google.com/document/d/1Gv1QPSBOIifG12q4DnK7RZ5Ra1F5EuOMN59h4Pf3g8c/edit?tab=t.bp0si8anjy3e

## Objectives
The objectives of this project are:

To design and implement an autocomplete system using Trie, HashMap, and Sorted ArrayList data structures.
To compare the execution time of Trie, HashMap, and Sorted ArrayList in operations such as: prefix search, insertion, deletion, exact word lookup, and memory usage, with different sizes of datasets.
To predict the scalability of each data structure as the dataset becomes larger.
To determine which data structure performs the best in an autocomplete system based on theoretical and experimental analysis.

## Team Members and Division
Each member has their own responsibilities contributing to the project, as follows:

MUHAMMAD DAFI ARIB ASYROFI (2902718944) - Trie
He is responsible for:
Implementing the Trie data structure and working on the Trie report.
Contributes implementing the UI.
Initialized GitHub repository.

FEBRIAN HALIESIUS (2902724745) - HashMap
He is responsible for: 
Implementing the HashMap data structure and working on the HashMap report.
Contributes implementing the UI.

RENJIRO KUNIO HANDOKO (2902740591) - Sorted ArrayList
He is responsible for:
Implementing the Sorted ArrayList data structure and working on the Sorted ArrayList report.
Contributes implementing the UI.
Testing all data structures in his personal computer (PC) and sharing the results among members.

## Dataset Preparation

The datasets used in this project is from this link: https://github.com/dwyl/english-words, and selected the words_alpha.txt file. The file contains approximately 370,105 words, which we think is enough, as it has the highest word count with small size (4.4MB) to save storage space.

For testing purposes, we splitted up the full text file using DatasetGenerator.java, into separate text files that each contains:
**Note that the splitting process takes up random words from the words_alpha.txt into each every text files.
100 words (smallest)
1,000 words
10,000 words
20,000 words
50,000 words
75,000 words
100,000 words
200,000 words
300,000 words (largest)
The splitted datasets can be found in this link:
 https://github.com/Dafz-7/datastructure-final-project/tree/main/dataset 

These text files serves as the input source for the autocomplete program implemented by Trie, HashMap, and Sorted ArrayList. Each datasets are loaded into each data structure before the testing scenario begins. All words in each datasets are in lowercase to ensure consistency throughout all operations. The same datasets are used across all data structure to ensure fair and consistent performance comparisons.

## Performance Metrics

### Dataset Load Time (ms)
Dataset load time measures how quickly words from datasets are inserted to each data structure measured in millisecond (ms). Dataset load time will be the only one with millisecond time measure since it takes a long time to load that it will take millions or billions or even trillions of nanoseconds (1.xxx.xxx - 1.xxx.xxx.xxx.xxx ns) to load with higher datasets. Therefore, to avoid bad readability, we used milliseconds instead to increase readability.

For example, the time taken in millisecond (ms) to insert all of the words from 300,000 words dataset to Trie/HashMap/Sorted ArrayList.

### Prefix Search Time (ns)
Prefix seach time measures how quickly the autocomplete program can retrieve words that begin with a given prefix designed by the code measured in nanosecond (ns).

### Insertion Time (ns)
Insertion time measures how quickly the autocomplete program can insert a new word into the data structure measured in nanosecond (ns).

### Deletion Time (ns)
Deletion time measures how quickly the autocomplete program checks for an existing word and delete that word from the data structure measured in nanosecond (ns).

### Exact Lookup Speed (ns)
Exact lookup speed measures how quickly the autocomplete program finds the exact word in the dataset measured in nanosecond (ns).

### Memory Usage (MB)
Memory usage measures the amount of memory consumed specifically by each data structure during the dataset loading, measured in megabytes (MB). Each data structure stores data differently, thus the total memory used when loading the dataset will also be different depending on their internal implementation (Weiss, 2013).

## System Architecture & User Interface Design

### System Architecture Flowchart

![System Architecture Flowchart](assets/system_architecture_flowchart.png)

The flowchart above shows the entirety of the system architecture. First, the user communicates with the application through the MainUI component. Then, MainUI sends signal through AutocompleteStructure interface, which provides common set of operations such as insertion, deletion, exact lookup, and prefix search. Trie, HashMap, and Sorted ArrayList implements AutocompleteStructure interface the same way. For datasets, these are loaded through DatasetLoader component and inserted into each data structure. For benchmarking, the BenchmarkRunner component run testings on the selected data structure and print out the results inside BenchmarkResults.

### Unified Class Diagram

![Unified Class Diagram](assets/unified_classdiagram.png)

The unified class diagram above shows the unified diagram of the system. In addition to figure 2, the Trie class contains TrieNode objects through a composition relationship, while BenchmarkRunner and DatasetLoader provide benchmarking and dataset management functionalities and other classes doing their functions.

## User Interface Design

![User Interface Design](assets/ui_design.png)

The UI above shows the graphical user interface for the autocomplete program made using Java Swing (Runestone Academy, n.d.). As shown in figure 4, users can select a data structure (Trie, HashMap, or Sorted ArrayList) and select dataset (100, 1k, 10k, 20k, 50k, 75k, 100k, 200k, and 300k) to load into the selected data structure. After dataset has been loaded, user can type a word and perform the selected operations (search/exact lookup, insert, and delete). Users can also type letters in the prefix placeholder (for example, “ap”) and click the “Get Suggestions” button to get the words that start with the typed letter “ap” in the result area. Users can also click the “Clear Results” button to clear whatever printed out in the result area for clarity (Bro Code, 2020). All of this are learnt from the youtube video which can be accessed here: https://www.youtube.com/watch?v=Kmgo00avvEw and with the little help of some internet searching, same applies with the implementation process of the 3 data structures here in the report.

## Trie Implementation

The Trie implementation is made up of 2 classes: Trie and TrieNode. The TrieNode class represents each node in the trie and it contains: 
A HashMap<Character, TrieNode> designated “children” that stores references to child nodes
A boolean variable isEndOfWord that designates whether a node represents the final character of a complete word.

The Trie class itself contains a root node which is the starting point for all operations. The root node does not represent any character in the word, it simply created when the Trie object gets initialized (GeeksforGeeks, n.d.-c). 

The Trie starts with a root node. During the dataset loading, each word front the file is inserted into the Trie using an insert() method. The insert() method works by reading a word character by character. Starting from the root node, the method checks if the characters is in the current node, if it is not then a new node created with that character. This continues until all the character have been created into a node. After the last character is reached the final node is marked as the end of the word.
For searching, the implementation uses the search() method. The method goes through the Trie following the characters of the input word. If one of the characters cannot be found, the method will return false. If all the characters are found and the final node is marked as the end of a word, the method will return true.

The delete() method is used to remove a word from the Trie. The implementation uses a recursive helper method named deleteHelper(). After the target word is found, the end of word variable is removed. During the recursive return process, the nodes that are no longer in use are deleted. However, nodes that are still being used by other words who share the same prefix will be kept.

For example, if Trie stores the words “apple” and “application” and the user were to delete the word “apple”, none of the nodes created by the word apple will be removed because they all are still needed by the “application” which has “apple” as a prefix.

The autocomplete suggestions are implemented with a getSuggestions() method. The method goes through each Trie node that matches with the entered prefix. After that, a collectWords() method performs a depth-first search (DFS) to collect all the words that start with the entered prefix. These words are then returned as a suggestion to the user.

Since Trie stores words based on its characters, most operations depend on the word’s length rather than the amount of words stored in that dataset. This makes Trie exceptional for autocomplete applications where a prefix searching system is needed.

## HashMap Implementation

The main data structure is HashMap<String, HashMap<String, Integer>> named prefixHashMap. The outer HashMap part stores prefixes as keys, while the inner part stores words related to that prefixes together with the frequencies (GeeksforGeeks, n.d.-b).

The system starts with an empty prefixHashMap when a HashMapSystem object is first created. The prefix hashmap works by loading the dataset from a .txt file. This is done by reading words in each line in the text file. The word is then inserted to the prefix hashmap using the insert() method.

For example, when the word “apple” is inserted, the following prefixes are generated:
“a”
“ap”
“app”
“appl”
“apple”

For each word that is inserted the prefix will be created and the word will be stored in each prefix. If the inserted word does not exist, a new HashMap is created instead. This allows every prefix to directly store all words that begin with that prefix.

The search() method is used for exact lookup. When this method is called, the system checks whether the word matches the stored prefixes inside the prefix map. If it matches as a key, the method will return true, otherwise false.

The delete() method removes a word from the structure. Similar to insertion, the system first checks the match prefixes to the typed word. For each prefix, the word is removed from the prefix map. 

For example, delete “apple” removes the word from:
“a”
“ap”
“app”
“appl”
“apple”

After removal, if a prefix map becomes empty, the system deletes that prefix entirely from the structure as this prevents empty prefixes that builds up in the memory.

For getting a suggestions for the autocomplete system, the getSuggestions() method will be used this will first get all matching words using the lookup() method, this will then be copied into a list and the list will be sorted using the rule of the custom comparator ValueThenKeyComparator before it is returned as a list.

Since every inserted word has their prefixes stored, exact lookups can be pretty much instant. However, this structure requires additional memory usage because the same word may be stored across different prefix maps.

## Sorted ArrayList Implementation

Sorted ArrayList stores all words inside ArrayList<String> word. Working differently from Trie and HashMap, this structure maintains alphabetical orders at all times.

The Sorted ArrayList implementation works by first loading the dataset from the text file into ArrayList<String> word, using the loadDataset() method.

Initially, the Collections.sort() was called at every word insertion from the dataset but turned out that this approach was super slow, making sorted arraylist the slowest structure in all operations (dataset load, insert, deletion, etc). Hence, to make the dataset loading and all operations quicker, all the words are initially stored without sorting because word insertion into an arraylist is very fast. Then, after the dataset have been fully loaded, the structure uses Collections.sort() to sort the entire ArrayList alphabetically. This approach helped the structure being the fastest in dataset load time because Collections.sort() uses Java’s TimSort implementation which is a fast and efficient sorting technique (GeeksforGeeks, n.d.-a). Once it has been sorted, the structure will maintain its sorted nature through program execution.

The insert() method uses Collections.binarySearch() to determine the correct insertion position. If the word already exists, no insertion is performed, otherwise the returned insertion index is used to place the inserted word into the corrected position in the arraylist while maintaining sorted order.

The search() method works by performing an exact lookup using binary search. Because it is already sorted, the proces of finding the searched word will be very fast. If the word exists, it will return true, otherwise false.

Similar to the search() method, the delete() method also uses binary search to find the targeted word. If the word is found, it will be removed from the ArrayList.

Autocomplete suggestions in this structure was made possible through the getSuggestions() method. It is helped by a method helper findInitialPrefix() which performs a modified binary search to locate the first occurrence of a matching perfix. After the first matching position is found, the structure checks through the ArrayList and collects all the word that start with the types prefix. The check stops when no more matched word that matches the typed prefix exists. The findInitialPrefix() helper method is efficient because the starting search is not from the first word, but since it uses binary search, it quickly narrows down the search scale inside the ArrayList.

Since the words are always maintained in sorted order, exact lookups and prefix searches can be fast in this structure. However, insertion and deletion might take longer time because every new word inserted or deleted needs to involve shifting every elements to preserve the sorted order.

## Raw Benchmark Results

Can be accessed in this link: https://docs.google.com/spreadsheets/d/1js1JTCgj572451xxZB2vJC9xbKhmUcKjTqukPu13jJQ/edit?gid=0#gid=0

## Program Manual

1. Clone this GitHub repo by typing in the terminal:
```git clone https://github.com/Dafz-7/datastructure-final-project```

2. Navigate to src/ui/MainUI.java and run the file.

3. To benchmark individual data structure, navigate to either three of these files:
    - src/hashmap/HashMapBenchmark.java
    - src/sortedarraylist/SortedArrayListBenchmark.java
    - src/trie/TrieBenchmark.java

## References (taken from the report)

Bro Code. (2020, October 16). Java GUI tutorial #1 - JFrame [Video]. YouTube. https://www.youtube.com/watch?v=Kmgo00avvEw 

Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2022). Introduction to algorithms (4th ed.). MIT Press. https://github.com/Maria4lexzy/LeetCodeTraining/blob/main/Introduction.to.Algorithms.4th.Leiserson.Stein.Rivest.Cormen.MIT.Press.9780262046305.EBooksWorld.ir.pdf 

Fredkin, E. (1960). Trie memory. Communications of the ACM, 3(9), 490–499. https://doi.org/10.1145/367390.367400 

GeeksforGeeks. (n.d.-a). Collections.sort() in Java with examples. GeeksforGeeks. Retrieved June 8, 2026, from https://www.geeksforgeeks.org/java/collections-sort-java-examples/ 

GeeksforGeeks. (n.d.-b). HashMap in Java with examples. GeeksforGeeks. Retrieved June 8, 2026, from https://www.geeksforgeeks.org/java/java-util-hashmap-in-java-with-examples/ 

GeeksforGeeks. (n.d.-c). Trie | (Insert and search). GeeksforGeeks. Retrieved June 8, 2026, from https://www.geeksforgeeks.org/dsa/trie-insert-and-search/ 

Goodrich, M. T., Tamassia, R., & Goldwasser, M. H. (2014). Data structures and algorithms in Java (6th ed.). Wiley. https://ku.edu.af/sites/default/files/2023-11/Data%20Structures%20and%20Algorithms%20in%20Java%2C%206th%20Edition%2C%202014.pdf 

Oracle Corporation. (2025). Class System. Java Platform, Standard Edition 8 API Specification. https://docs.oracle.com/javase/8/docs/api/java/lang/System.html 

Runestone Academy. (n.d.). Graphical user interfaces and Java Swing. Runestone Academy. Retrieved June 8, 2026, from https://runestone.academy/ns/books/published/javajavajava/swing.html 

Sedgewick, R., & Wayne, K. (2011). Algorithms (4th ed.). Addison-Wesley. https://github.com/ShraavaniTople/DataStructureBooks/blob/main/Algorithms-4th-Edition-By-Robert%20Sedgewick%20and%20Kevin%20Wayne.pdf 

Weiss, M. A. (2013). Data structures and algorithm analysis in Java (3rd ed.). Pearson. https://thuvienso.dau.edu.vn:88/bitstream/DHKTDN/6913/1/6238.Data%20structures%20and%20algorithm%20analysis%20in%20Java%20%283rd%20ed%29.pdf