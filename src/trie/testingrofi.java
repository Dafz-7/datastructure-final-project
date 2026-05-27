package trie;

import datasetutils.DatasetLoader;

public class testingrofi {

    private static final int ITERATIONS = 10000;

    public static void main(String[] args) {

        String[] datasets = {
                "words_100.txt",
                "words_1000.txt",
                "words_10000.txt",
                "words_20000.txt",
                "words_50000.txt",
                "words_75000.txt",
                "words_100000.txt",
                "words_200000.txt",
                "words_300000.txt"
        };

        // ==========================================
        // REPEAT WHOLE BENCHMARK 2 TIMES
        // ==========================================

        for (int run = 1; run <= 3; run++) {

            System.out.println(
                    "\n========================================================");

            System.out.println(
                    "BENCHMARK RUN #" + run);

            System.out.println(
                    "========================================================");

            System.out.printf(
                    "%-15s %-12s %-12s %-12s %-12s %-12s %-12s%n",
                    "Dataset",
                    "Load(ms)",
                    "Search(ns)",
                    "Prefix(ns)",
                    "Insert(ns)",
                    "Delete(ns)",
                    "Memory(MB)");

            System.out.println(
                    "---------------------------------------------------------------------------------------------");

            // ==========================================
            // LOOP THROUGH DATASETS
            // ==========================================

            for (String dataset : datasets) {

                Trie trie = new Trie();

                // ==========================
                // LOAD DATASET
                // ==========================

                long startLoad = System.nanoTime();

                DatasetLoader.loadWords(
                        "dataset/" + dataset,
                        trie);

                long endLoad = System.nanoTime();

                double loadTime =
                        (endLoad - startLoad)
                                / 1_000_000.0;

                // ==========================
                // MEMORY USAGE
                // ==========================

                Runtime runtime =
                        Runtime.getRuntime();

                runtime.gc();

                long usedMemory =
                        runtime.totalMemory()
                                - runtime.freeMemory();

                double memoryMB =
                        usedMemory
                                / (1024.0 * 1024.0);

                // ==========================
                // SEARCH BENCHMARK
                // ==========================

                long startSearch =
                        System.nanoTime();

                for (int i = 0;
                     i < ITERATIONS;
                     i++) {

                    trie.search("antineutrino");
                }

                long endSearch =
                        System.nanoTime();

                double avgSearch =
                        (endSearch - startSearch)
                                / (double) ITERATIONS;

                // ==========================
                // PREFIX SEARCH BENCHMARK
                // ==========================

                long startPrefix =
                        System.nanoTime();

                for (int i = 0;
                     i < ITERATIONS;
                     i++) {

                    trie.getSuggestions("sh");
                }

                long endPrefix =
                        System.nanoTime();

                double avgPrefix =
                        (endPrefix - startPrefix)
                                / (double) ITERATIONS;

                // ==========================
                // INSERT BENCHMARK
                // ==========================

                long startInsert =
                        System.nanoTime();

                for (int i = 0;
                     i < ITERATIONS;
                     i++) {

                    trie.insert(
                            "benchmarkInsert" + i);
                }

                long endInsert =
                        System.nanoTime();

                double avgInsert =
                        (endInsert - startInsert)
                                / (double) ITERATIONS;

                // ==========================
                // DELETE BENCHMARK
                // ==========================

                long startDelete =
                        System.nanoTime();

                for (int i = 0;
                     i < ITERATIONS;
                     i++) {

                    trie.delete(
                            "benchmarkInsert" + i);
                }

                long endDelete =
                        System.nanoTime();

                double avgDelete =
                        (endDelete - startDelete)
                                / (double) ITERATIONS;

                // ==========================
                // PRINT RESULTS
                // ==========================

                System.out.printf(
                        "%-15s %-12.3f %-12.2f %-12.2f %-12.2f %-12.2f %-12.2f%n",
                        dataset,
                        loadTime,
                        avgSearch,
                        avgPrefix,
                        avgInsert,
                        avgDelete,
                        memoryMB);
            }
        }
    }
}