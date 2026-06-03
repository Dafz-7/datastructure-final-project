package benchmark;

import sortedarraylist.SortedArrayList;
import java.io.File;
import java.util.*;
import datasetutils.DatasetLoader;
import interfaces.AutocompleteStructure;

public class BenchmarkRunner {

    private static final int ITERATIONS = 10000;

    public static BenchmarkResult runBenchmark(

            AutocompleteStructure structure,
            String datasetPath,
            String datasetName) {

        BenchmarkResult result =
                new BenchmarkResult();

        result.datasetName = datasetName;

        // ==========================
        // LOAD DATASET
        // ==========================

        long startLoad = System.nanoTime();
/* 
        DatasetLoader.loadWords(
                datasetPath,
                structure
        );
 */

        if (structure instanceof SortedArrayList) {

        SortedArrayList sa =
                (SortedArrayList) structure;

        List<String> datasetWords =
                new ArrayList<>();

        try {

        Scanner sc = new Scanner(new File(datasetPath));

        while (sc.hasNextLine()) {

                datasetWords.add(
                        sc.nextLine());
        }

        sc.close();

        } catch (Exception e) {
                e.printStackTrace();
        }
        
        sa.loadDataset(datasetWords);

        } else {

        DatasetLoader.loadWords(
                datasetPath,
                structure
        );
        }
        long endLoad = System.nanoTime();

        result.loadTime =
                (endLoad - startLoad)
                        / 1_000_000.0;

        // ==========================
        // MEMORY
        // ==========================

        Runtime runtime = Runtime.getRuntime();

        runtime.gc();

        long usedMemory =
                runtime.totalMemory()
                        - runtime.freeMemory();

        result.memoryMB =
                usedMemory
                        / (1024.0 * 1024.0);

        // ==========================
        // SEARCH
        // ==========================

        long startSearch = System.nanoTime();

        for (int i = 0; i < ITERATIONS; i++) {

            structure.search("antineutrino");
        }

        long endSearch = System.nanoTime();

        result.avgSearch =
                (endSearch - startSearch)
                        / (double) ITERATIONS;

        // ==========================
        // PREFIX SEARCH
        // ==========================

        long startPrefix = System.nanoTime();

        for (int i = 0; i < ITERATIONS; i++) {

            structure.getSuggestions("sh");
        }

        long endPrefix = System.nanoTime();

        result.avgPrefix =
                (endPrefix - startPrefix)
                        / (double) ITERATIONS;

        // ==========================
        // INSERT
        // ==========================

        long startInsert = System.nanoTime();

        for (int i = 0; i < ITERATIONS; i++) {

            structure.insert(
                    "benchmarkInsert" + i
            );
        }

        long endInsert = System.nanoTime();

        result.avgInsert =
                (endInsert - startInsert)
                        / (double) ITERATIONS;

        // ==========================
        // DELETE
        // ==========================

        long startDelete = System.nanoTime();

        for (int i = 0; i < ITERATIONS; i++) {

            structure.delete(
                    "benchmarkInsert" + i
            );
        }

        long endDelete = System.nanoTime();

        result.avgDelete =
                (endDelete - startDelete)
                        / (double) ITERATIONS;

        return result;
    }
}