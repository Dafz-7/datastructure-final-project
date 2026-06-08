# Performance Comparison of Trie, HashMap, and Sorted ArrayList in an Autocomplete Program

<p align="center">
  <img src="assets/ui_design.png" width="800">
</p>

![Java](https://img.shields.io/badge/Java-8+-orange)
![Course](https://img.shields.io/badge/Course-Data%20Structures-blue)
![Status](https://img.shields.io/badge/Status-Completed-green)

---

## Project Overview

This repository contains the source code, datasets, benchmarking utilities, and reports for a Data Structures final project that compares the performance of three autocomplete implementations:

- Trie
- HashMap
- Sorted ArrayList

The project evaluates:

- Dataset loading time
- Prefix search performance
- Insertion performance
- Deletion performance
- Exact lookup speed
- Memory usage
- Scalability
- Theoretical time complexity

---

## Quick Links

- 📄 Data Structures Report: [Insert DS Report Link]
- 📄 OOP Report: [Insert OOP Report Link]
- 📊 Raw Benchmark Results: https://docs.google.com/spreadsheets/d/1js1JTCgj572451xxZB2vJC9xbKhmUcKjTqukPu13jJQ/edit?gid=0#gid=0
- 📁 Dataset Source: https://github.com/dwyl/english-words

---

## Team Members

| Name | Student ID | Responsibility |
|--------|--------|--------|
| Muhammad Dafi Arib Asyrofi | 2902718944 | Trie Implementation, UI Development, GitHub Repository Initialization |
| Febrian Haliesius | 2902724745 | HashMap Implementation, UI Development |
| Renjiro Kunio Handoko | 2902740591 | Sorted ArrayList Implementation, UI Development, Benchmark Testing |

---

## System Architecture

### System Architecture Flowchart

<p align="center">
  <img src="assets/system_architecture_flowchart.png" width="850">
</p>

The user interacts with the application through MainUI. MainUI communicates with the AutocompleteStructure interface, which defines insertion, deletion, exact lookup, and prefix search operations. Trie, HashMap, and Sorted ArrayList implement the same interface, allowing all structures to be benchmarked under identical conditions.

### Unified Class Diagram

<p align="center">
  <img src="assets/unified_classdiagram.png" width="950">
</p>

The class diagram illustrates the relationships among the user interface, data structures, dataset loader, benchmarking utilities, and supporting classes.

---

## User Interface

<p align="center">
  <img src="assets/ui_design.png" width="850">
</p>

The application was developed using Java Swing.

Users can:

- Select a data structure
- Select a dataset
- Load datasets
- Insert words
- Delete words
- Search words
- Retrieve autocomplete suggestions
- Run benchmarks

---

## Data Structures

### Trie

- Character-based tree structure.
- Uses TrieNode objects connected through parent-child relationships.
- Supports insertion, deletion, exact lookup, and prefix search.
- Prefix suggestions are generated using Depth-First Search (DFS).
- Time complexity depends primarily on word length.

### HashMap

- Uses a prefix-indexing strategy.
- Stores prefixes as keys and matching words as values.
- Provides very fast exact lookup performance.
- Suggestions are sorted using a custom comparator.
- Trades memory efficiency for speed.

### Sorted ArrayList

- Stores all words in a sorted ArrayList.
- Uses binary search for exact lookup.
- Uses modified binary search for prefix searching.
- Maintains sorted order during insertions and deletions.
- Achieved the fastest prefix-search performance in benchmarking.

---

## Final Time Complexity

| Operation | Trie | HashMap | Sorted ArrayList |
|------------|------------|------------|------------|
| Insert | O(L) | O(L) | O(n) |
| Delete | O(L) | O(L) | O(n) |
| Search (Exact Lookup) | O(L) | O(1) | O(log n) |
| Prefix Search | O(L + K) | O(K log K) | O(log n + K) |

Where:

- L = Word Length
- K = Number of Suggestions Returned
- n = Total Number of Stored Words

---

## Key Findings

- HashMap achieved the fastest exact lookup.
- Sorted ArrayList achieved the fastest prefix search.
- Sorted ArrayList used the least memory.
- Trie provided the most balanced overall performance.
- Experimental results generally matched theoretical analysis.

---

## Running the Program

Clone the repository:

```bash
git clone https://github.com/Dafz-7/datastructure-final-project
```

Run:

```text
src/ui/MainUI.java
```

---

## Running Benchmarks

Execute one of the following files:

```text
src/hashmap/HashMapBenchmark.java
src/sortedarraylist/SortedArrayListBenchmark.java
src/trie/TrieBenchmark.java
```

---

## Dataset Sizes

The project benchmarks the following dataset sizes:

- 100 words
- 1,000 words
- 10,000 words
- 20,000 words
- 50,000 words
- 75,000 words
- 100,000 words
- 200,000 words
- 300,000 words

---

## Raw Benchmark Results

https://docs.google.com/spreadsheets/d/1js1JTCgj572451xxZB2vJC9xbKhmUcKjTqukPu13jJQ/edit?gid=0#gid=0

---

## References

See the complete report for full APA references.