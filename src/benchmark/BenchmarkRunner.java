// this class is to measure the performance of data structures.

// therefore, this class will generate a BenchmarkResult object containing all measured metrics.

package benchmark;

import sortedarraylist.SortedArrayList;
import java.io.File;
import java.util.*;
import datasetutils.DatasetLoader;
import interfaces.AutocompleteStructure;

public class BenchmarkRunner {

    // number of iterations
    private static final int ITERATIONS = 10000;

    // returns BenchmarkResult containing all measurement metrics.
    public static BenchmarkResult runBenchmark(
            AutocompleteStructure structure,
            String datasetPath,
            String datasetName) {

        // BenchmarkResult object creation
        BenchmarkResult result = new BenchmarkResult();

        // the dataset name
        result.datasetName = datasetName;

        // dataset loading process
        // start the timer
        // System.nanoTime() is used because it provides high-resolution timing, based
        // on Oracle's java documentation website (read the full report we made for full explanation)
        long startLoad = System.nanoTime();

        // sortedarraylist has different method of inserting the words from the dataset
        // to its structure, because this new dataset loading method is faster.
        // trie and hashmap are using the same dataset loading method.
        if (structure instanceof SortedArrayList) {

            // since structure is declared as interface in the AutocompleteStructure, we
            // cast it back to sortedarraylist so we can access loadDataset() as provided in
            // SortedArrayList.java .
            SortedArrayList sa = (SortedArrayList) structure;

            // temporary list that stores dataset words before loading
            List<String> datasetWords = new ArrayList<>();

            try {

                Scanner sc = new Scanner(new File(datasetPath));

                while (sc.hasNextLine()) {
                    datasetWords.add(sc.nextLine());
                }

                sc.close();

            } catch (Exception e) {

                // java method that will print exception name, error message, a line-by-line
                // breakdown of the active methods, class names, file sources, and exact line
                // numbers where the problem is located.
                e.printStackTrace();

            }

            // bulk-load the dataset, optimized for sortedarraylist.
            sa.loadDataset(datasetWords);

        } else {

            // standard dataset loading method for trie and hashmap.
            DatasetLoader.loadWords(datasetPath, structure);

        }

        // end the timer.
        long endLoad = System.nanoTime();

        // result is in milliseconds, hence the division of the nanosecond with 1
        // million.
        result.loadTime = (endLoad - startLoad) / 1_000_000.0;

        // memory usage benchmark.
        // this measures how much memory each data structures occupies after loading the
        // dataset.
        Runtime runtime = Runtime.getRuntime();

        // garbage collection before measuring memory to free up memory.
        runtime.gc();

        long usedMemory = runtime.totalMemory() - runtime.freeMemory();

        // converts bytes to megabytes.
        result.memoryMB = usedMemory / (1024.0 * 1024.0);

        // search benchmark.
        // timer started.
        long startSearch = System.nanoTime();

        // iterate for 10k times for this search operation.
        for (int i = 0; i < ITERATIONS; i++) {

            // the word "antineutrino" is selected to perform the search.
            // the word "antineutrino" is made available throughout all datasets.
            structure.search("antineutrino");

        }

        // timer ended.
        long endSearch = System.nanoTime();

        // result is in nanosecond.
        result.avgSearch = (endSearch - startSearch) / (double) ITERATIONS;

        // prefix search benchmark.
        // timer started.
        long startPrefix = System.nanoTime();

        // iterate for 10k times.
        for (int i = 0; i < ITERATIONS; i++) {

            // the prefix used is "sh".
            structure.getSuggestions("sh");

        }

        // timer ended.
        long endPrefix = System.nanoTime();

        // result is in nanoseconds.
        result.avgPrefix = (endPrefix - startPrefix) / (double) ITERATIONS;

        // insert benchmark
        long startInsert = System.nanoTime();

        // iterate for 10k times.
        for (int i = 0; i < ITERATIONS; i++) {

            // insert the word "benchmarkInsert".
            structure.insert("benchmarkInsert" + i);

        }

        // timer ended.
        long endInsert = System.nanoTime();

        // result is in nanoseconds.
        result.avgInsert = (endInsert - startInsert) / (double) ITERATIONS;

        // delete benchmark.
        // start timer.
        long startDelete = System.nanoTime();

        // iterate for 10k times.
        for (int i = 0; i < ITERATIONS; i++) {

            // delete the word "benchmarkInsert" as it was inserted before.
            structure.delete("benchmarkInsert" + i);

        }

        // timer ended.
        long endDelete = System.nanoTime();

        // result is in nanoseconds.
        result.avgDelete = (endDelete - startDelete) / (double) ITERATIONS;

        // returns all the resulting metrics.
        return result;
    }
}