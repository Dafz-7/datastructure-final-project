package datasetutils;

import java.io.*;
import java.util.*;

public class DatasetGenerator {

    public static void main(String[] args) {

        try {

            // Read all words from master dataset
            List<String> words = new ArrayList<>();

            Scanner sc = new Scanner(new File("dataset/words_full.txt"));

            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }

            sc.close();

            // Shuffle words randomly
            Collections.shuffle(words, new Random(42));

            // Dataset sizes
            int[] sizes = {
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

            // Generate subset files
            for (int size : sizes) {

                PrintWriter writer = new PrintWriter("dataset/words_" + size + ".txt");

                for (int i = 0; i < size; i++) {
                    writer.println(words.get(i));
                }

                writer.close();

                System.out.println("Generated words_" + size + ".txt");
            }

            System.out.println("All datasets generated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}