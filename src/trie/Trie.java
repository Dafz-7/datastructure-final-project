package trie;

import interfaces.AutocompleteStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie implements AutocompleteStructure {

    // =========================
    // Trie Node
    // =========================
    private static class TrieNode {

        HashMap<Character, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    // =========================
    // Root Node
    // =========================
    private TrieNode root;

    // =========================
    // Constructor
    // =========================
    public Trie() {
        root = new TrieNode();
    }

    // =========================
    // Insert Word
    // =========================
    @Override
    public void insert(String word) {

        TrieNode current = root;

        for (char c : word.toCharArray()) {

            // Create node if missing
            current.children.putIfAbsent(c, new TrieNode());

            // Move downward
            current = current.children.get(c);
        }

        // Mark complete word
        current.isEndOfWord = true;
    }

    // =========================
    // Search Exact Word
    // =========================
    @Override
    public boolean search(String word) {

        TrieNode current = root;

        for (char c : word.toCharArray()) {

            // Missing path
            if (!current.children.containsKey(c)) {
                return false;
            }

            current = current.children.get(c);
        }

        return current.isEndOfWord;
    }

    // =========================
    // Delete Word
    // =========================
    @Override
    public void delete(String word) {
        deleteHelper(root, word, 0);
    }

    private boolean deleteHelper(
            TrieNode current,
            String word,
            int index) {

        // Reached end of word
        if (index == word.length()) {

            // Word doesn't exist
            if (!current.isEndOfWord) {
                return false;
            }

            current.isEndOfWord = false;

            // If no children, node can be deleted
            return current.children.isEmpty();
        }

        char c = word.charAt(index);

        TrieNode node = current.children.get(c);

        // Path doesn't exist
        if (node == null) {
            return false;
        }

        boolean shouldDeleteChild = deleteHelper(node, word, index + 1);

        // Remove child node
        if (shouldDeleteChild) {

            current.children.remove(c);

            // Decide whether current node should also be removed
            return current.children.isEmpty()
                    && !current.isEndOfWord;
        }

        return false;
    }

    // =========================
    // Get Suggestions
    // =========================
    @Override
    public List<String> getSuggestions(String prefix) {

        List<String> suggestions = new ArrayList<>();

        TrieNode current = root;

        // Traverse to prefix node
        for (char c : prefix.toCharArray()) {

            if (!current.children.containsKey(c)) {
                return suggestions;
            }

            current = current.children.get(c);
        }

        // Collect all words from prefix node
        collectWords(current, prefix, suggestions);

        return suggestions;
    }

    // =========================
    // DFS Collection Helper
    // =========================
    private void collectWords(
            TrieNode node,
            String currentWord,
            List<String> suggestions) {

        // Found complete word
        if (node.isEndOfWord) {
            suggestions.add(currentWord);
        }

        // Traverse children
        for (char c : node.children.keySet()) {

            collectWords(
                    node.children.get(c),
                    currentWord + c,
                    suggestions);
        }
    }
}