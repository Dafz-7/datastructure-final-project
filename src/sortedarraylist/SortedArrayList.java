package sortedarraylist;

import java.util.*;

import interfaces.*;

public class SortedArrayList implements AutocompleteStructure {

    private ArrayList<String> words;

    public SortedArrayList() {
        words = new ArrayList<>();
    }

    // =========================
    // BULK LOAD METHOD
    // =========================
    public void loadDataset(List<String> dataset) {

        // Add all words unsorted
        for (String word : dataset) {

            if (word != null && !word.isEmpty()) {
                words.add(word);
            }
        }

        // Sort entire dataset once
        Collections.sort(words);

    }

    // =========================
    // NORMAL INSERT
    // =========================
    @Override
    public void insert(String word) {

        if (word == null || word.isEmpty()) {
            return;
        }

        int index = Collections.binarySearch(words, word);

        if (index >= 0) {
            return;
        }

        words.add(-(index + 1), word);
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public void delete(String word) {

        int index = Collections.binarySearch(words, word);

        if (index >= 0) {
            words.remove(index);
        }
    }

    // =========================
    // SEARCH
    // =========================
    @Override
    public boolean search(String word) {

        return Collections.binarySearch(words, word) >= 0;
    }

    // =========================
    // GET SUGGESTIONS
    // =========================
    @Override
    public List<String> getSuggestions(String prefix) {

        List<String> results = new ArrayList<>();

        int startIndex = findInitialPrefix(prefix);

        if (startIndex == -1) {
            return results;
        }

        for (int i = startIndex; i < words.size(); i++) {

            String word = words.get(i);

            if (word.startsWith(prefix)) {
                results.add(word);
            } else {
                break;
            }
        }

        return results;
    }

    // =========================
    // FIND FIRST PREFIX
    // =========================
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