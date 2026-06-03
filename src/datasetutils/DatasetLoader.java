// this class is for reading the words from the dataset adn inserting them to each data structures.

package datasetutils;

import interfaces.AutocompleteStructure;

import java.io.File;
import java.util.Scanner;

public class DatasetLoader {

    public static int loadWords(
            String filename,
            AutocompleteStructure structure) {

        // count how many valid words were inserted into the structure.
        int wordCount = 0;

        try {

            // open the dataset file
            Scanner sc = new Scanner(new File(filename));

            // read file line by line.
            while (sc.hasNextLine()) {

                // read current line and removes front and back spaces.
                String word = sc.nextLine().trim();

                // empty line should NOT BE inserted to the structure.
                if (!word.isEmpty()) {

                    structure.insert(word);
                    wordCount++;

                }
            }

            sc.close();

        } catch (Exception e) {

            // print detailed error information.
            e.printStackTrace();

        }

        // return total number of words loaded/counted.
        return wordCount;
    }
}