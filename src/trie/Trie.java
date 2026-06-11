// this class is for trie structure (rofi).

package trie;

import interfaces.AutocompleteStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie implements AutocompleteStructure {

    // a trie is built from many TriNodes.
    // each node represents 1 character.
    // hence, TrieNode class follows as shown below.
    // each node contains:
    // 1. children
    // 2. boolean isEndOfWord that can be true/false
    private static class TrieNode {

        // character -> next TrieNode
        // node a can continue to
        // p -> node
        // n -> node
        // meaning, a can continue to ap or an.
        HashMap<Character, TrieNode> children;
        boolean isEndOfWord;

        // TrieNode constructor.
        // initially,
        // children = {}
        // isEndOfWord = false
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    // root node
    private TrieNode root;

    // Trie constructor.
    // creates an empty trie structure.
    public Trie() {
        root = new TrieNode();
    }

    // insert word.
    // for example, insert("cat"),
    // root -> c -> a -> t*
    // (*) is end of word set to true.
    @Override
    public void insert(String word) {

        // always traverse from the root first.
        TrieNode current = root;

        // word.toCharArray() converts the String word to individual letter inside a char[] array, then loop through it.
        // in short, traverse into every character in the word.
        for (char c : word.toCharArray()) {

            // create node if one does not exist.
            current.children.putIfAbsent(c, new TrieNode());

            // traverse to the next node.
            current = current.children.get(c);
        }

        // mark the final nord as the complete word / the last letter.
        current.isEndOfWord = true;
    }

    // search a word (exact look up)
    // will return true if the word exist, false if otherwise.
    @Override
    public boolean search(String word) {

        // start from root.
        TrieNode current = root;

        // traverse through every character.
        for (char c : word.toCharArray()) {

            // if one of the searched character does not exist, return false immediately.
            if (!current.children.containsKey(c)) {
                return false;
            }

            // traverse the next character.
            current = current.children.get(c);
        }

        // checks whether the word is a complete word or not.
        // returns true if the word is complete word.
        // returns false if the word is not a complete word.
        return current.isEndOfWord;
    }

    // delete a word.
    // this process is recursive.
    @Override
    public void delete(String word) {
        // mentioned a delete helper with 3 parameters:
        // the root, the word, and the current index its traversing.
        deleteHelper(root, word, 0);
    }

    // the deleteHelper method (recursive method).
    // how it works:
    // 1. it unmarks the last character/letter is end of word to false.
    // 2. remove nodes that does not have children.
    private boolean deleteHelper(
            TrieNode current,
            String word,
            int index) {

        // this is the base case.
        // check until the end of the word / the last letter.
        // this condition statement will be skipped until the last letter is curently traversed.
        if (index == word.length()) {

            // return false if the last letter of the word is not there, thus the word does not exist.
            if (!current.isEndOfWord) {
                return false;
            }

            // if the last letter exists, that the end of the word marker is now false.
            current.isEndOfWord = false;

            // if the current node does not have children, it will send a signal (return true) that it can be deleted.
            return current.children.isEmpty();
        }

        // get the first character from the word.
        char c = word.charAt(index);

        // move downward, selecting the children node from the current node that have the current character.
        TrieNode node = current.children.get(c);

        // if node does not exist, meaning the word does not exist as well, then return false immediately in that case.
        if (node == null) {
            return false;
        }

        // this is the recursive case.
        // with the index incremented by 1, all characters will eventually be traversed.
        // if (index == word.length()) conditional statement will then be executed, returning the value inside shouldDeleteChild variable.
        boolean shouldDeleteChild = deleteHelper(node, word, index + 1);

        // if the child node is no longer needed which means it has no child and isEndOfWord is false, it can finally be deleted.
        if (shouldDeleteChild) {

            // delete the child node.
            current.children.remove(c);

            // if current node has no children AND it is not the end of any word, then it is also safe to be deleted.
            return current.children.isEmpty() && !current.isEndOfWord;
        }

        return false;
    }

    // get suggestions.
    @Override
    public List<String> getSuggestions(String prefix) {

        // generate a list.
        List<String> suggestions = new ArrayList<>();

        // start from root.
        TrieNode current = root;

        // traverse to prefix nodes.
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

    // depth-first search.
    // recursively searches all descendants of a prefix node.
    private void collectWords(
            TrieNode node,
            String currentWord,
            List<String> suggestions) {

        // check whether the word found is a complete word
        // if true, then add to the suggestions.
        if (node.isEndOfWord) {
            suggestions.add(currentWord);
        }

        // visit every child node
        for (char c : node.children.keySet()) {

            collectWords(node.children.get(c), currentWord + c, suggestions);

        }
    }
}