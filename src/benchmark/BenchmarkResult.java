package benchmark;

public class BenchmarkResult {

    // =========================
    // Dataset Info
    // =========================
    public String datasetName;

    public String searchWord;

    public String prefixWord;

    public String insertBase;

    // =========================
    // Benchmark Results
    // =========================
    public double loadTime;

    public double avgSearch;

    public double avgPrefix;

    public double avgInsert;

    public double avgDelete;

    public double memoryMB;
}