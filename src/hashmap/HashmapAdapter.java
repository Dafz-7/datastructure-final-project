package hashmap;

import interfaces.AutocompleteStructure;

import java.util.List;
import java.util.Map;

public class HashmapAdapter
implements AutocompleteStructure {

    private HashmapSystem hashmap;

    public HashmapAdapter() {

        hashmap = new HashmapSystem(
                new String[]{},
                new int[]{}
        );
    }

    @Override
    public void insert(String word) {

        hashmap.insert(word, 1);
    }

    @Override
    public void delete(String word) {

        hashmap.remove(word);
    }

    @Override
    public boolean search(String word) {

        Map<String, Integer> result =
                hashmap.lookup(word);

        return result.containsKey(word);
    }

    @Override
    public List<String> getSuggestions(String prefix) {

        return hashmap.search(prefix);
    }
}