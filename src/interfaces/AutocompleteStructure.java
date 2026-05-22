package interfaces;

import java.util.List;

public interface AutocompleteStructure {

    void insert(String word);

    void delete(String word);

    boolean search(String word);

    List<String> getSuggestions(String prefix);
}