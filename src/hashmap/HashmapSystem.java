package hashmap;

import java.util.*;

/*
 * AUTOCOMPLETE SYSTEM
 *
 * This program stores words and suggests matching words
 * based on prefixes typed by the user.
 *
 * Example:
 * Typing "ap" may suggest:
 * apple
 * app
 * application
 */

public class HashmapSystem {

    /*
     * MAIN DATA STRUCTURE
     *
     * Stores:
     * prefix -> (word -> frequency)
     *
     * Example:
     *
     * "ap" ->
     * {
     * "apple" : 5,
     * "app" : 3,
     * "application" : 2
     * }
     */
    private HashMap<String, HashMap<String, Integer>> prefixHashMap;

    /*
     * INSERT METHOD
     *
     * Adds a word into the autocomplete system.
     *
     * Parameters:
     * s -> the word
     * times -> how many times the word appears
     *
     * Example:
     * insert("apple", 5)
     *
     * This creates prefixes:
     * a
     * ap
     * app
     * appl
     * apple
     */
    public void insert(String s, int times) {

        // Used to build prefixes character by character
        String str = "";

        // Loop through every character in the word
        for (int i = 0; i < s.length(); i++) {

            // Current character
            char c = s.charAt(i);

            // Add character to current prefix
            str += c;

            /*
             * If prefix does not exist yet,
             * create a new hashmap for it.
             */
            if (!prefixHashMap.containsKey(str)) {

                prefixHashMap.put(
                        str,
                        new HashMap<String, Integer>());
            }

            /*
             * If word does not exist under this prefix,
             * add it with its frequency.
             */
            if (!prefixHashMap.get(str).containsKey(s)) {

                prefixHashMap.get(str).put(s, times);

            } else {

                /*
                 * If word already exists,
                 * increase frequency.
                 *
                 * Example:
                 * apple : 5 -> 6
                 */
                prefixHashMap.get(str).put(
                        s,
                        prefixHashMap.get(str).get(s) + times);
            }
        }
    }

    public void remove(String s) {

        String str = "";

        /*
         * Go through every prefix
         */
        for (int i = 0; i < s.length(); i++) {

            str += s.charAt(i);

            /*
             * If prefix exists
             */
            if (prefixHashMap.containsKey(str)) {

                /*
                 * Remove word
                 */
                prefixHashMap
                        .get(str)
                        .remove(s);

                /*
                 * Remove empty prefix maps
                 */
                if (prefixHashMap
                        .get(str)
                        .isEmpty()) {

                    prefixHashMap.remove(str);
                }
            }
        }
    }

    /*
     * LOOKUP METHOD
     *
     * Finds all words matching a prefix.
     *
     * Example:
     * lookup("ap")
     *
     * Returns:
     * {
     * apple : 5,
     * app : 3,
     * application : 2
     * }
     */
    public Map<String, Integer> lookup(String s) {

        // If prefix exists, return matching words
        if (prefixHashMap.containsKey(s)) {
            return prefixHashMap.get(s);
        }

        // Otherwise return empty hashmap
        return new HashMap<String, Integer>();
    }

    /*
     * SEARCH METHOD
     *
     * Finds autocomplete suggestions
     * based on a typed prefix.
     *
     * Example:
     *
     * search("ap")
     *
     * Possible matching words:
     * apple
     * app
     * application
     *
     * The method:
     *
     * 1. Gets all matching words
     * from the hashmap
     *
     * 2. Sorts them using:
     * - highest frequency first
     * - alphabetical order if tied
     *
     * 3. Returns only the top 3 results
     */
    public java.util.List<String> search(String prefix) {

        java.util.List<String> res = new ArrayList<>();

        java.util.List<Map.Entry<String, Integer>> list =

                new ArrayList<>(
                        lookup(prefix).entrySet());

        Collections.sort(
                list,
                new ValueThenKeyComparator<String, Integer>());

        for (int i = 0; i < Math.min(5, list.size()); i++) {

            res.add(
                    list.get(i).getKey());
        }

        return res;
    }

    /*
     * CUSTOM COMPARATOR
     *
     * Used for sorting autocomplete suggestions.
     *
     * Sorting Rules:
     *
     * 1. Higher frequency first
     * 2. Alphabetical order if frequencies tie
     *
     * Example:
     *
     * apple : 5
     * app : 3
     * application : 2
     */
    public class ValueThenKeyComparator<K extends Comparable<? super K>, V extends Comparable<? super V>>
            implements Comparator<Map.Entry<K, V>> {

        public int compare(
                Map.Entry<K, V> a,
                Map.Entry<K, V> b) {

            /*
             * Compare frequencies first.
             *
             * Higher frequency comes first.
             */
            int cmp1 = b.getValue().compareTo(a.getValue());

            // If frequencies are different
            if (cmp1 != 0) {
                return cmp1;
            }

            /*
             * If frequencies are equal,
             * sort alphabetically.
             */
            return a.getKey().compareTo(b.getKey());
        }
    }

    /*
     * CONSTRUCTOR
     *
     * Initializes the autocomplete system.
     *
     * Parameters:
     * words -> array of words
     * times -> array of frequencies
     */
    public HashmapSystem(
            String[] words,
            int[] times) {

        // Create the main hashmap
        prefixHashMap = new HashMap<>();

        // Insert all words into system
        for (int i = 0; i < words.length; i++) {

            insert(words[i], times[i]);
        }
    }

    /*
     * Stores what the user is currently typing.
     *
     * Example:
     *
     * Typing:
     * a -> "a"
     * p -> "ap"
     * p -> "app"
     */
    String current = "";

    /*
     * INPUT METHOD
     *
     * Called every time the user types a character.
     *
     * Example:
     * input('a')
     * input('p')
     * input('p')
     */
    public List<String> input(char c) {

        // Stores autocomplete results
        List<String> res = new ArrayList<>();

        /*
         * If user types '#'
         *
         * This means:
         * finish word and save it.
         */
        if (c == '#') {

            // Insert typed word into system
            insert(current, 1);

            // Reset current word
            current = "";

        } else {

            /*
             * Add typed character
             * to current word.
             */
            current += c;

            /*
             * Get all matching words
             * for the current prefix.
             */
            List<Map.Entry<String, Integer>> list = new ArrayList<>(
                    lookup(current).entrySet());

            /*
             * Sort words using custom comparator.
             */
            Collections.sort(
                    list,
                    new ValueThenKeyComparator<String, Integer>());

            /*
             * Only return top 3 results.
             */
            for (int i = 0; i < Math.min(3, list.size()); i++) {

                res.add(list.get(i).getKey());
            }
        }

        // Return autocomplete suggestions
        return res;
    }
}
