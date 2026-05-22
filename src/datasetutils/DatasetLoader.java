package datasetutils;

import interfaces.AutocompleteStructure;

import java.io.File;
import java.util.Scanner;

public class DatasetLoader {

    public static void loadWords(
            String filename,
            AutocompleteStructure structure) {

        try {

            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNextLine()) {

                String word = sc.nextLine();

                structure.insert(word);
            }

            sc.close();

            System.out.println("Dataset loaded successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}