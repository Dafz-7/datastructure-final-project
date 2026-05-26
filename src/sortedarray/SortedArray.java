package sortedarray;
import java.util.*;

import interfaces.AutocompleteStructure;

public class SortedArray implements AutocompleteStructure{
    
    private ArrayList<String> words;

    public SortedArray() {
        words = new ArrayList<>();
    }

    @Override
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        int position = 0;

        while (position < words.size() && words.get(position).compareTo(word) < 0) {
            position++;
        }

        if (position < words.size() && words.get(position).equals(word)) {
            return;
        }

        words.add(position,word);
    }

    @Override
    public void delete(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        int index = Collections.binarySearch(words, word);

        if (index >= 0) {
            words.remove(index);
        }
    }



    @Override
    public boolean search(String word) {

        if (word == null || word.isEmpty()) {
        return false;
        }

        return Collections.binarySearch(words, word) >= 0;
    }

    @Override
    public List<String> getSuggestions(String prefix) {

        List<String> results = new ArrayList<>();

        if (prefix == null || prefix.isEmpty()) {
            return results;
        }

        int startIndex = findInitialPrefix(prefix);

        if (startIndex == -1) {
            return results;
        }

        for (int i = startIndex; i < words.size(); i++) {

            String word = words.get(i);

            if (word.startsWith(prefix)) {
                results.add(word);
            }
            else {
                break;
            }
        }

        return results;
    }

    public int size() {
        return words.size();
    }

    private int findInitialPrefix(String prefix) {

    int low = 0;
    int high = words.size() - 1;
    int result = -1;

    while (low <= high) {

        int mid = (low + high) / 2;
        String word = words.get(mid);

        if (word.startsWith(prefix)) {

            result = mid;
            high = mid - 1;

        } else if (word.compareTo(prefix) < 0) {

            low = mid + 1;

        } else {

            high = mid - 1;
        }
    }

    return result;
}
}
