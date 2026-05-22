package sortedarray;
import interfaces.AutocompleteStructure;
import java.util.*;

public class SortedArray implements AutocompleteStructure{
    
    private final ArrayList<String> words;

    public SortedArray () {
        this.words = new ArrayList<>();
    }

    @Override
    public void insert(String word) {
        if (word == null || word.isEmpty()) return;

        word = word.trim();

        int index = Collections.binarySearch(words, word);

        if (index < 0) {
            words.add(-index - 1, word);
        }
    }
}
