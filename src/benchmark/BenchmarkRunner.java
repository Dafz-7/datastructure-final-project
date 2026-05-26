package benchmark;

import hashmap.HashmapAdapter;
import interfaces.AutocompleteStructure;
import sortedarray.SortedArray;
import trie.Trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenchmarkRunner {

    // =========================================
    // MAIN
    // =========================================
    public static void main(String[] args) {

        String[] datasets = {
                "dataset/words_100.txt",
                "dataset/words_1000.txt",
                "dataset/words_10000.txt",
                "dataset/words_20000.txt",
                "dataset/words_50000.txt",
                "dataset/words_75000.txt",
                "dataset/words_100000.txt",
                "dataset/words_200000.txt",
                "dataset/words_300000.txt"
        };

        for (String datasetPath : datasets) {

            List<String> words = loadWords(datasetPath);

            int repetitions = getRepetitions(words.size());

            System.out.println(
                    "\n==================================================");

            System.out.println(
                    "DATASET: " + datasetPath);

            System.out.println(
                    "WORDS: " + words.size());

            System.out.println(
                    "REPETITIONS: " + repetitions);

            System.out.println(
                    "==================================================");

            // =========================================
            // INSERT BENCHMARK
            // =========================================

            System.out.println("\nINSERT BENCHMARK");

            benchmarkInsert(
                    "Trie",
                    new Trie(),
                    words,
                    repetitions);

            benchmarkInsert(
                    "HashMap",
                    new HashmapAdapter(),
                    words,
                    repetitions);

            benchmarkInsert(
                    "SortedArray",
                    new SortedArray(),
                    words,
                    repetitions);

            // =========================================
            // SEARCH BENCHMARK
            // =========================================

            System.out.println("\nSEARCH BENCHMARK");

            benchmarkSearch(
                    "Trie",
                    new Trie(),
                    words,
                    repetitions);

            benchmarkSearch(
                    "HashMap",
                    new HashmapAdapter(),
                    words,
                    repetitions);

            benchmarkSearch(
                    "SortedArray",
                    new SortedArray(),
                    words,
                    repetitions);

            // =========================================
            // DELETE BENCHMARK
            // =========================================

            System.out.println("\nDELETE BENCHMARK");

            benchmarkDelete(
                    "Trie",
                    new Trie(),
                    words,
                    repetitions);

            benchmarkDelete(
                    "HashMap",
                    new HashmapAdapter(),
                    words,
                    repetitions);

            benchmarkDelete(
                    "SortedArray",
                    new SortedArray(),
                    words,
                    repetitions);

            // =========================================
            // SUGGESTION BENCHMARK
            // =========================================

            System.out.println("\nSUGGESTION BENCHMARK");

            benchmarkSuggestions(
                    "Trie",
                    new Trie(),
                    words,
                    repetitions);

            benchmarkSuggestions(
                    "HashMap",
                    new HashmapAdapter(),
                    words,
                    repetitions);

            benchmarkSuggestions(
                    "SortedArray",
                    new SortedArray(),
                    words,
                    repetitions);
        }
    }

    // =========================================
    // LOAD WORDS
    // =========================================
    private static List<String> loadWords(
            String path) {

        List<String> words = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(
                    new FileReader(path));

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {
                    words.add(line);
                }
            }

            br.close();

        } catch (IOException e) {

            System.out.println(
                    "Error loading dataset: "
                            + path);

            e.printStackTrace();
        }

        return words;
    }

    // =========================================
    // INSERT
    // =========================================
    private static void benchmarkInsert(
            String structureName,
            AutocompleteStructure structure,
            List<String> words,
            int repetitions) {

        long totalTime = 0;

        for (int i = 0; i < repetitions; i++) {

            // Create fresh structure every repetition
            structure = createStructure(structureName);

            long start = System.nanoTime();

            for (String word : words) {
                structure.insert(word);
            }

            long end = System.nanoTime();

            totalTime += (end - start);
        }

        double average = totalTime / (double) repetitions;

        System.out.printf(
                "%-15s : %.2f ms%n",
                structureName,
                average / 1_000_000.0);
    }

    // =========================================
    // SEARCH
    // =========================================
    private static void benchmarkSearch(
            String structureName,
            AutocompleteStructure structure,
            List<String> words,
            int repetitions) {

        long totalTime = 0;

        Random random = new Random();

        for (int i = 0; i < repetitions; i++) {

            structure = createStructure(structureName);

            // preload
            for (String word : words) {
                structure.insert(word);
            }

            long start = System.nanoTime();

            for (int j = 0; j < words.size(); j++) {

                String word = words.get(
                        random.nextInt(
                                words.size()));

                structure.search(word);
            }

            long end = System.nanoTime();

            totalTime += (end - start);
        }

        double average = totalTime / (double) repetitions;

        System.out.printf(
                "%-15s : %.2f ms%n",
                structureName,
                average / 1_000_000.0);
    }

    // =========================================
    // DELETE
    // =========================================
    private static void benchmarkDelete(
            String structureName,
            AutocompleteStructure structure,
            List<String> words,
            int repetitions) {

        long totalTime = 0;

        for (int i = 0; i < repetitions; i++) {

            structure = createStructure(structureName);

            // preload
            for (String word : words) {
                structure.insert(word);
            }

            long start = System.nanoTime();

            for (String word : words) {
                structure.delete(word);
            }

            long end = System.nanoTime();

            totalTime += (end - start);
        }

        double average = totalTime / (double) repetitions;

        System.out.printf(
                "%-15s : %.2f ms%n",
                structureName,
                average / 1_000_000.0);
    }

    // =========================================
    // SUGGESTIONS
    // =========================================
    private static void benchmarkSuggestions(
            String structureName,
            AutocompleteStructure structure,
            List<String> words,
            int repetitions) {

        long totalTime = 0;

        Random random = new Random();

        for (int i = 0; i < repetitions; i++) {

            structure = createStructure(structureName);

            // preload
            for (String word : words) {
                structure.insert(word);
            }

            long start = System.nanoTime();

            for (int j = 0; j < words.size(); j++) {

                String word = words.get(
                        random.nextInt(
                                words.size()));

                // prevent substring crash
                if (word.length() >= 2) {

                    String prefix = word.substring(0, 2);

                    structure.getSuggestions(prefix);
                }
            }

            long end = System.nanoTime();

            totalTime += (end - start);
        }

        double average = totalTime / (double) repetitions;

        System.out.printf(
                "%-15s : %.2f ms%n",
                structureName,
                average / 1_000_000.0);
    }

    // =========================================
    // CREATE STRUCTURE
    // =========================================
    private static AutocompleteStructure createStructure(String name) {

        switch (name) {

            case "Trie":
                return new Trie();

            case "HashMap":
                return new HashmapAdapter();

            case "SortedArray":
                return new SortedArray();

            default:
                return null;
        }
    }

    // =========================================
    // REPETITION RULES
    // =========================================
    private static int getRepetitions(
            int size) {

        if (size <= 1000) {
            return 100;
        }

        if (size <= 10000) {
            return 50;
        }

        if (size <= 50000) {
            return 20;
        }

        if (size <= 100000) {
            return 10;
        }

        return 5;
    }
}