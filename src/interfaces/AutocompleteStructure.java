// this interface is for providing operations that every data structure will perform in this autocomplete program.
// therefore, this interface should be implemented and used by trie, hashmap, and sortedarraylist.

package interfaces;

import java.util.List;

public interface AutocompleteStructure {

    // interface method for inserting a word.
    void insert(String word);

    // interface method for deleting a word.
    void delete(String word);

    // interface method for searching a word (for exact look up).
    boolean search(String word);

    // interface method for getting suggestions (prefix search).
    List<String> getSuggestions(String prefix);
}