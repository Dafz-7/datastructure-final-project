// this class is the main UI, responsible for comparison demo.
// UI was made using Java Swing.

package ui;

import javax.swing.*;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

import interfaces.AutocompleteStructure;
import sortedarraylist.SortedArrayList;
import trie.Trie;
import hashmap.HashMapSystem;
import datasetutils.DatasetLoader;
import benchmark.BenchmarkResult;
import benchmark.BenchmarkRunner;

public class MainUI extends JFrame {

        // ui components
        private JComboBox<String> structureBox;
        private JComboBox<String> datasetBox;
        private JButton loadButton;
        private AutocompleteStructure currentStructure;
        private JLabel structureInfoLabel;
        private JLabel runtimeLabel;
        private JTextField wordField;
        private JButton searchButton;
        private JTextArea resultsArea;
        private JTextField prefixField;
        private JButton suggestionsButton;
        private JButton insertButton;
        private JButton deleteButton;
        private JButton clearButton;
        private JButton benchmarkButton;

   public MainUI() {

        // window setup
        setTitle("Autocomplete Program - Trie, HashMap, and Sorted ArrayList");

        setSize(600, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // ui components
        structureBox = new JComboBox<>(
                new String[] {
                        "Sorted Array",
                        "HashMap",
                        "Trie"
                });

        datasetBox = new JComboBox<>(
                new String[] {
                        "words_100.txt",
                        "words_1000.txt",
                        "words_10000.txt",
                        "words_20000.txt",
                        "words_50000.txt",
                        "words_75000.txt",
                        "words_100000.txt",
                        "words_200000.txt",
                        "words_300000.txt",
                });

        loadButton = new JButton("Load Dataset");

        structureInfoLabel = new JLabel("No dataset loaded");

        runtimeLabel = new JLabel("Load Time: N/A");

        wordField = new JTextField(15);

        searchButton = new JButton("Search");

        resultsArea = new JTextArea(12, 50);

        prefixField = new JTextField(15);

        suggestionsButton = new JButton("Get Suggestions");

        clearButton = new JButton("Clear Results");

        insertButton = new JButton("Insert");

        deleteButton = new JButton("Delete");

        benchmarkButton = new JButton("Run Benchmark");

        resultsArea.setEditable(false);

        // main panel
        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS));

        // dataset panel
        JPanel datasetPanel = new JPanel();

        datasetPanel.add(new JLabel("Structure:"));

        datasetPanel.add(structureBox);

        datasetPanel.add(new JLabel("Dataset:"));

        datasetPanel.add(datasetBox);

        datasetPanel.add(loadButton);

        panel.add(datasetPanel);

        // status information panel
        JPanel statusPanel = new JPanel();

        statusPanel.setLayout(
                new BoxLayout(statusPanel, BoxLayout.Y_AXIS));

        structureInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        runtimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusPanel.add(structureInfoLabel);

        statusPanel.add(runtimeLabel);

        panel.add(statusPanel);

        // word operations panel
        JPanel wordPanel = new JPanel();

        wordPanel.add(new JLabel("Word:"));

        wordPanel.add(wordField);

        wordPanel.add(searchButton);

        wordPanel.add(insertButton);

        wordPanel.add(deleteButton);

        panel.add(wordPanel);

        // autocomplete panel
        JPanel autocompletePanel = new JPanel();

        autocompletePanel.add(new JLabel("Prefix:"));

        autocompletePanel.add(prefixField);

        autocompletePanel.add(suggestionsButton);

        panel.add(autocompletePanel);

        // benchmark panel
        JPanel benchmarkPanel = new JPanel();

        benchmarkPanel.add(benchmarkButton);

        panel.add(benchmarkPanel);

        // results panel
        JPanel resultsControlPanel = new JPanel();

        resultsControlPanel.add(clearButton);

        panel.add(resultsControlPanel);

        panel.add(new JScrollPane(resultsArea));

        // load dataset
        loadButton.addActionListener(e -> {

        String selectedStructure = (String) structureBox.getSelectedItem();

            // create selected data structure
        switch (selectedStructure) {

                case "Sorted Array":

                        currentStructure = new SortedArrayList();
                        break;

                case "HashMap":
                        currentStructure = new HashMapSystem();

                        break;

                case "Trie":
                        currentStructure = new Trie();

                        break;
                }

                String selectedDataset = (String) datasetBox.getSelectedItem();

            // selected dataset path
                String path = "dataset/" + selectedDataset;

            // start load timer
                long start = System.nanoTime();
                int wordsLoaded = 0;

            // bulk load for sortedarraylist
                if (currentStructure instanceof SortedArrayList) {

                SortedArrayList sa = (SortedArrayList) currentStructure;

                List<String> datasetWords = new ArrayList<>();

                // read dataset file
                try {

                        Scanner sc = new Scanner(new File(path));
                        while (sc.hasNextLine()) {
                                datasetWords.add(sc.nextLine());
                        }

                        sc.close();

                } catch (Exception ex) {

                        ex.printStackTrace();
                }

                // load words into sortedarraylist
                sa.loadDataset(datasetWords);
                wordsLoaded = datasetWords.size();

                }

                else {
                // load words into trie and hashmap
                wordsLoaded = DatasetLoader.loadWords(path, currentStructure);
                }
                long end = System.nanoTime();

                double loadTime = (end - start) / 1_000_000.0;

                structureInfoLabel.setText(
                        "Structure: "
                                + selectedStructure
                                + " | Dataset: "
                                + selectedDataset
                                + " | Words Loaded: "
                                + wordsLoaded);

                runtimeLabel.setText(
                        String.format(
                                "Load Time: %.3f ms",
                                loadTime));
                });

        // search button
        searchButton.addActionListener(e -> {

            // check whether dataset is loaded.
                if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first.");

                return;
                }

            // get word from input.
                String word = wordField.getText().trim();

            // measure search time
                long start = System.nanoTime();

            // perform search
                boolean found = currentStructure.search(word);

                long end = System.nanoTime();

                long runtime = end - start;

                runtimeLabel.setText(
                        String.format(
                                "Search Time: %d ns",
                                runtime));

                // display results
                if (found) {

                resultsArea.setText(
                        "\"" + word + "\" found.");

                } else {

                resultsArea.setText(
                        "\"" + word + "\" not found.");
                }
        });

        // insert button
        insertButton.addActionListener(e -> {

                if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first.");

                return;
                }

                String word = wordField.getText().trim();

                long start = System.nanoTime();

            // insert word
                currentStructure.insert(word);

                long end = System.nanoTime();

                long runtime = end - start;

                runtimeLabel.setText(
                        String.format(
                                "Insert Time: %d ns",
                                runtime));

                resultsArea.setText(
                        "\"" + word + "\" inserted.");
                });

                // delete button
                deleteButton.addActionListener(e -> {

                if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first.");

                return;
                }

                // let the user input the word to delete.
                String word = wordField.getText().trim();

                long start = System.nanoTime();

                currentStructure.delete(word);

                long end = System.nanoTime();

                long runtime = end - start;

                runtimeLabel.setText(
                        String.format(
                                "Delete Time: %d ns",
                                runtime));

                resultsArea.setText(
                        "\"" + word + "\" deleted.");
                });

        // search prefix button
        suggestionsButton.addActionListener(e -> {

                if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first.");

                return;
                }

            // get prefix input from the user.
                String prefix = prefixField.getText().trim();

                long start = System.nanoTime();

            // generate suggestions
                List<String> suggestions = currentStructure.getSuggestions(
                        prefix);

            long end = System.nanoTime();

            long runtime = end - start;

            runtimeLabel.setText(
                    String.format(
                            "Suggestion Time: %d ns",
                            runtime));

            resultsArea.setText("");

            if (suggestions.isEmpty()) {

                resultsArea.setText(
                        "No suggestions found.");

            } else {

                // display suggestions
                for (String word : suggestions) {

                    resultsArea.append(
                            word + "\n");
                }

                resultsArea.setCaretPosition(0);
            }
        });

        // benchmark button
        benchmarkButton.addActionListener(e -> {

            String selectedStructure = (String) structureBox.getSelectedItem();

            String selectedDataset = (String) datasetBox.getSelectedItem();

            String path = "dataset/" + selectedDataset;

            AutocompleteStructure benchmarkStructure = null;

            // create structure for benchmarking
            switch (selectedStructure) {

                case "Sorted Array":

                    benchmarkStructure = new SortedArrayList();

                    break;

                case "HashMap":

                    benchmarkStructure = new HashMapSystem();

                    break;

                case "Trie":

                    benchmarkStructure = new Trie();

                    break;
            }

            // run benchmark
            BenchmarkResult result = BenchmarkRunner.runBenchmark(
                    benchmarkStructure,
                    path,
                    selectedDataset);

            resultsArea.setText("");

            // display benchmark results
            resultsArea.append(
                    "========== BENCHMARK ==========\n\n");

            resultsArea.append(
                    "Structure: "
                            + selectedStructure
                            + "\n");

            resultsArea.append(
                    "Dataset: "
                            + result.datasetName
                            + "\n\n");

            resultsArea.append(
                    String.format(
                            "Load Time: %.3f ms\n",
                            result.loadTime));

            resultsArea.append(
                    String.format(
                            "Average Search: %.2f ns\n",
                            result.avgSearch));

            resultsArea.append(
                    String.format(
                            "Average Prefix: %.2f ns\n",
                            result.avgPrefix));

            resultsArea.append(
                    String.format(
                            "Average Insert: %.2f ns\n",
                            result.avgInsert));

            resultsArea.append(
                    String.format(
                            "Average Delete: %.2f ns\n",
                            result.avgDelete));

            resultsArea.append(
                    String.format(
                            "Memory Usage: %.2f MB\n",
                            result.memoryMB));

            resultsArea.setCaretPosition(0);
        });

        // clear button
        clearButton.addActionListener(e -> {
            // clear output area
            resultsArea.setText("");
        });

        add(panel);

        setVisible(true);
    }

    // start application
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainUI();
        });
    }
}