package trie;

import datasetutils.DatasetLoader;

public class testingrofi {

    public static void main(String[] args) {

        Trie trie = new Trie();

        // =====================================
        // Load dataset
        // =====================================
        DatasetLoader.loadWords(
                "dataset/words_100.txt",
                trie
        );

        System.out.println("Dataset loaded.\n");

        // =====================================
        // INSERT TEST
        // =====================================
        trie.insert("apple");
        trie.insert("application");
        trie.insert("approve");

        System.out.println("Inserted custom words.\n");

        // =====================================
        // SEARCH TEST
        // =====================================
        System.out.println("SEARCH TEST");

        System.out.println(
                "apple -> " +
                trie.search("apple")
        );

        System.out.println(
                "app -> " +
                trie.search("app")
        );

        System.out.println(
                "banana -> " +
                trie.search("banana")
        );

        System.out.println();

        // =====================================
        // SUGGESTION TEST
        // =====================================
        System.out.println("SUGGESTION TEST");

        System.out.println(
                "Suggestions for 'ap': "
                + trie.getSuggestions("ap")
        );

        System.out.println();

        // =====================================
        // DELETE TEST
        // =====================================
        System.out.println("DELETE TEST");

        System.out.println(
                "Before delete apple: "
                + trie.search("apple")
        );

        trie.delete("apple");

        System.out.println(
                "After delete apple: "
                + trie.search("apple")
        );

        System.out.println();

        // =====================================
        // SUGGESTION AFTER DELETE
        // =====================================
        System.out.println("SUGGESTIONS AFTER DELETE");

        System.out.println(
                trie.getSuggestions("ap")
        );
    }
}