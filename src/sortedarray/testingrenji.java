package sortedarray;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class testingrenji {
    public static void main(String[] args) throws Exception {

        SortedArray sa = new SortedArray();

        // Load dataset
        Scanner sc = new Scanner(
                new File("src/dataset/words_100.txt"));

        while (sc.hasNextLine()) {
            sa.insert(sc.nextLine());
        }

        sc.close();

        System.out.println("=== SORTED ARRAY TESTING ===\n");

        // --------------------------------------------------
        // DATASET LOAD TEST
        // --------------------------------------------------

        System.out.println("Dataset Size: " + sa.size());

        // --------------------------------------------------
        // SEARCH TESTS
        // --------------------------------------------------

        System.out.println("\n=== SEARCH TESTS ===");

        System.out.println(
                "acceptilating -> "
                        + sa.search("acceptilating"));

        System.out.println(
                "antineutrino -> "
                        + sa.search("antineutrino"));

        System.out.println(
                "apple -> "
                        + sa.search("apple"));

        // --------------------------------------------------
        // INSERT TESTS
        // --------------------------------------------------

        System.out.println("\n=== INSERT TESTS ===");

        System.out.println(
                "Before insert apple: "
                        + sa.search("apple"));

        sa.insert("apple");

        System.out.println(
                "After insert apple: "
                        + sa.search("apple"));

        System.out.println(
                "Size after insert: "
                        + sa.size());

        // --------------------------------------------------
        // DUPLICATE TEST
        // --------------------------------------------------

        System.out.println("\n=== DUPLICATE TEST ===");

        int beforeDuplicate = sa.size();

        sa.insert("apple");

        int afterDuplicate = sa.size();

        System.out.println(
                "Size before duplicate insert: "
                        + beforeDuplicate);

        System.out.println(
                "Size after duplicate insert: "
                        + afterDuplicate);

        // --------------------------------------------------
        // DELETE TESTS
        // --------------------------------------------------

        System.out.println("\n=== DELETE TESTS ===");

        System.out.println(
                "Before delete antineutrino: "
                        + sa.search("antineutrino"));

        sa.delete("antineutrino");

        System.out.println(
                "After delete antineutrino: "
                        + sa.search("antineutrino"));

        System.out.println(
                "Size after delete: "
                        + sa.size());

        // --------------------------------------------------
        // SUGGESTION TESTS
        // --------------------------------------------------

        System.out.println("\n=== SUGGESTION TESTS ===");

        List<String> suggestions;

        suggestions = sa.getSuggestions("ac");

        System.out.println("\nSuggestions for 'ac':");

        for (String word : suggestions) {
            System.out.println(word);
        }

        suggestions = sa.getSuggestions("ant");

        System.out.println("\nSuggestions for 'ant':");

        for (String word : suggestions) {
            System.out.println(word);
        }

        suggestions = sa.getSuggestions("xyz");

        System.out.println("\nSuggestions for 'xyz':");

        if (suggestions.isEmpty()) {
            System.out.println("No suggestions found.");
        }

        System.out.println("\n=== TESTING COMPLETE ===");
    }
}
