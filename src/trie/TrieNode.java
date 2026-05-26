package trie;

import java.util.TreeMap;

public class TrieNode {

    TreeMap<Character, TrieNode> children;

    boolean isEndOfWord;

    public TrieNode() {
        children = new TreeMap<>();
        isEndOfWord = false;
    }
}