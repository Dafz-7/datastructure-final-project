package hashmap;

import interfaces.AutocompleteStructure;

import java.util.*;



public class HashmapSystem implements AutocompleteStructure {

    // Main Data Structure
    private HashMap<String, HashMap<String, Integer>> prefixHashMap;


    // Constructor
    public HashmapSystem() {

        prefixHashMap = new HashMap<>();
    }


    // Insert Word
    @Override
    public void insert(String word) {

        insert(word, 1);
    }


     // Insert with frequency
    public void insert(String word, int times) {

        StringBuilder sb = new StringBuilder();

        // Build prefixes
        for (int i = 0; i < word.length(); i++) {

            sb.append(word.charAt(i));

            String prefix = sb.toString();

            // Create prefix map if missing
            prefixHashMap.putIfAbsent(prefix, new HashMap<>());

            HashMap<String, Integer> map = prefixHashMap.get(prefix);

            // Add or update frequency
            map.put(word, map.getOrDefault(word, 0) + times);
        }
    }


    // Search Exact Word
    @Override
    public boolean search(String word) {

        Map<String, Integer> result = lookup(word);

        return result.containsKey(word);
    }


    // Delete Word
    @Override
    public void delete(String word) {

        StringBuilder sb = new StringBuilder();

        // Remove word from all prefixes
        for (int i = 0; i < word.length(); i++) {

            sb.append(word.charAt(i));

            String prefix = sb.toString();

            if (prefixHashMap.containsKey(prefix)) {

                prefixHashMap.get(prefix).remove(word);

                // Remove empty prefix maps
                if (prefixHashMap.get(prefix).isEmpty()) {

                    prefixHashMap.remove(prefix);
                }
            }
        }
    }


    // Lookup Prefix
    public Map<String, Integer> lookup(String prefix) {

        if (prefixHashMap.containsKey(prefix)) {

            return prefixHashMap.get(prefix);
        }

        return new HashMap<>();
    }


    // Get Suggestions
    @Override
    public List<String> getSuggestions(String prefix) {

        List<Map.Entry<String, Integer>> list = new ArrayList<>(lookup(prefix).entrySet());

        Collections.sort(list, new ValueThenKeyComparator<>());

        List<String> suggestions = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : list) {

            suggestions.add(entry.getKey());
        }

        return suggestions;
    }


    // Custom Comparator
    public class ValueThenKeyComparator <K extends Comparable<? super K>, V extends Comparable<? super V>> implements Comparator<Map.Entry<K, V>> {

        @Override
        public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {

            // Higher frequency first
            int cmp = b.getValue().compareTo(a.getValue());

            if (cmp != 0) {
                return cmp;
            }

            // Alphabetical if tied
            return a.getKey().compareTo(b.getKey());
        }
    }
}