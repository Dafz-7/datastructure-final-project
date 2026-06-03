// this class is a container used to store benchmark results for one autocomplete structure (trie, hashmap, or sortedarraylist) on one dataset.

// instead of storing these values in many separate variables, they are grouped together inside one object.

/**
 * This will make it easier to
 * - return benchmark results from methods.
 * - print benchmark results in a table format.
 * - compare different data structures.
 */

package benchmark;

public class BenchmarkResult {

    // dataset informations
    public String datasetName; // name of the dataset being used

    public String searchWord; // word used

    public String prefixWord; // prefix used

    public String insertBase; // word used when doing insertion

    // benchmark result informations
    public double loadTime; // measured in milliseconds (ms)

    public double avgSearch; // measured in nanoseconds (ns)

    public double avgPrefix; // (ns)

    public double avgInsert; // (ns)

    public double avgDelete; // (ns)

    public double memoryMB; // measured in megabytes (MB)
}