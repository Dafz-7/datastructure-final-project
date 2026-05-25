package trie;

import datasetutils.DatasetLoader;

public class benchmarkrofi {

    public static void main(String[] args) {

        // =====================================
        // DATASET SIZES
        // =====================================
        int[] datasetSizes = {
                100,
                1000,
                10000,
                20000,
                50000,
                75000,
                100000,
                200000,
                300000
        };

        // =====================================
        // TEST WORDS / PREFIXES
        // =====================================
        String searchWord = "apple";

        String deleteWord = "apple";

        String insertWord = "rofiword";

        String prefix = "ap";

        // =====================================
        // LOOP THROUGH DATASETS
        // =====================================
        for (int size : datasetSizes) {

            System.out.println(
                    "\n====================================="
            );

            System.out.println(
                    "DATASET SIZE: " + size
            );

            System.out.println(
                    "====================================="
            );

            // =====================================
            // CREATE TRIE
            // =====================================
            Trie trie = new Trie();

            // =====================================
            // LOAD DATASET
            // =====================================
            String filename =
                    "dataset/words_" + size + ".txt";

            long loadStart = System.nanoTime();

            DatasetLoader.loadWords(
                    filename,
                    trie
            );

            long loadEnd = System.nanoTime();

            long loadTime =
                    loadEnd - loadStart;

            // =====================================
            // INSERTION TEST
            // =====================================
            long insertStart = System.nanoTime();

            trie.insert(insertWord);

            long insertEnd = System.nanoTime();

            long insertTime =
                    insertEnd - insertStart;

            // =====================================
            // SEARCH TEST
            // =====================================
            long searchStart = System.nanoTime();

            boolean found =
                    trie.search(searchWord);

            long searchEnd = System.nanoTime();

            long searchTime =
                    searchEnd - searchStart;

            // =====================================
            // PREFIX SUGGESTION TEST
            // =====================================
            long suggestionStart =
                    System.nanoTime();

            trie.getSuggestions(prefix);

            long suggestionEnd =
                    System.nanoTime();

            long suggestionTime =
                    suggestionEnd - suggestionStart;

            // =====================================
            // DELETE TEST
            // =====================================
            long deleteStart = System.nanoTime();

            trie.delete(deleteWord);

            long deleteEnd = System.nanoTime();

            long deleteTime =
                    deleteEnd - deleteStart;

            // =====================================
            // MEMORY USAGE
            // =====================================
            Runtime runtime =
                    Runtime.getRuntime();

            runtime.gc();

            long memoryUsed =
                    runtime.totalMemory()
                            - runtime.freeMemory();

            // Convert to MB
            double memoryMB =
                    memoryUsed
                            / (1024.0 * 1024.0);

            // =====================================
            // PRINT RESULTS
            // =====================================
            System.out.println(
                    "Dataset Load Time: "
                            + loadTime
                            + " ns"
            );

            System.out.println(
                    "Insertion Time: "
                            + insertTime
                            + " ns"
            );

            System.out.println(
                    "Search Time: "
                            + searchTime
                            + " ns"
            );

            System.out.println(
                    "Search Result: "
                            + found
            );

            System.out.println(
                    "Suggestion Time: "
                            + suggestionTime
                            + " ns"
            );

            System.out.println(
                    "Delete Time: "
                            + deleteTime
                            + " ns"
            );

            System.out.println(
                    "Memory Usage: "
                            + memoryMB
                            + " MB"
            );
        }
    }
}