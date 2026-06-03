// this class is to generate multiple benchmark datasets from one large master dataset (words_full.txt) found online from a github website (read the report we made for full explanation).

package datasetutils;

import java.io.*;
import java.util.*;

public class DatasetGenerator {

    public static void main(String[] args) {

        try {

            // master dataset is words_full.txt .
            // read all words from master dataset.
            List<String> words = new ArrayList<>();

            Scanner sc = new Scanner(new File("dataset/words_full.txt"));

            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }

            sc.close();

            // pick words randomly from the master dataset when splitting
            // Exception for the word "antineutrino", since it is for testing purposes that
            // is available on all datasets.
            Collections.shuffle(words, new Random(42));

            // dataset sizes
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

            // generate splitting files
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

            // java method that will print exception name, error message, a line-by-line
            // breakdown of the active methods, class names, file sources, and exact line
            // numbers where the problem is located.
            e.printStackTrace();

        }
    }
}