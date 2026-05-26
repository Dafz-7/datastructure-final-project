package datasetutils;

import interfaces.AutocompleteStructure;

import java.io.File;
import java.util.Scanner;

public class DatasetLoader {

    public static int loadWords(
            String filename,
            AutocompleteStructure structure) {

        int wordCount = 0;

        try {

            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNextLine()) {

                String word = sc.nextLine().trim();

                if (!word.isEmpty()) {

                    structure.insert(word);
                    wordCount++;
                }
            }

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wordCount;
    }
}