package trie;

import java.util.HashMap;

public class TrieNode {

    HashMap<Character, TrieNode> children;

    boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}