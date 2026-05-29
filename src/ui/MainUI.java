package ui;

import javax.swing.*;
import java.awt.*;

import interfaces.AutocompleteStructure;
import sortedarraylist.SortedArrayList;
import trie.Trie;
import hashmap.HashmapSystem;
import datasetutils.DatasetLoader;

public class MainUI extends JFrame {

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

    public MainUI() {

        setTitle("Autocomplete Data Structure Comparison");

        setSize(600, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // ==========================================
        // COMPONENTS
        // ==========================================

        structureBox = new JComboBox<>(
                new String[]{
                        "Sorted Array",
                        "HashMap",
                        "Trie"
                });

        datasetBox = new JComboBox<>(
                new String[]{
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

        structureInfoLabel =
                new JLabel("No dataset loaded");

        runtimeLabel =
                new JLabel("Load Time: N/A");

        wordField =
                new JTextField(15);

        searchButton =
                new JButton("Search");

        resultsArea =
                new JTextArea(12, 50);

        prefixField =
                new JTextField(15);

        suggestionsButton =
                new JButton("Get Suggestions");

        clearButton =
                new JButton("Clear Results");

        insertButton =
                new JButton("Insert");

        deleteButton =
                new JButton("Delete");

        resultsArea.setEditable(false);

        // ==========================================
        // MAIN PANEL
        // ==========================================

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS));

        // ==========================================
        // DATASET PANEL
        // ==========================================

        JPanel datasetPanel = new JPanel();

        datasetPanel.add(
                new JLabel("Structure:"));

        datasetPanel.add(structureBox);

        datasetPanel.add(
                new JLabel("Dataset:"));

        datasetPanel.add(datasetBox);

        datasetPanel.add(loadButton);

        panel.add(datasetPanel);

        // ==========================================
        // STATUS PANEL
        // ==========================================

        JPanel statusPanel = new JPanel();

        statusPanel.setLayout(
                new BoxLayout(
                        statusPanel,
                        BoxLayout.Y_AXIS));

        structureInfoLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        runtimeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        statusPanel.add(
                structureInfoLabel);

        statusPanel.add(runtimeLabel);

        panel.add(statusPanel);

        // ==========================================
        // WORD PANEL
        // ==========================================

        JPanel wordPanel = new JPanel();

        wordPanel.add(new JLabel("Word:"));

        wordPanel.add(wordField);

        wordPanel.add(searchButton);

        wordPanel.add(insertButton);

        wordPanel.add(deleteButton);

        panel.add(wordPanel);

        // ==========================================
        // PREFIX PANEL
        // ==========================================

        JPanel autocompletePanel =
                new JPanel();

        autocompletePanel.add(
                new JLabel("Prefix:"));

        autocompletePanel.add(prefixField);

        autocompletePanel.add(
                suggestionsButton);

        panel.add(autocompletePanel);

        // ==========================================
        // CLEAR PANEL
        // ==========================================

        JPanel resultsControlPanel =
                new JPanel();

        resultsControlPanel.add(clearButton);

        panel.add(resultsControlPanel);

        panel.add(
                new JScrollPane(resultsArea));

        // ==========================================
        // LOAD BUTTON
        // ==========================================

        loadButton.addActionListener(e -> {

            String selectedStructure =
                    (String)
                            structureBox.getSelectedItem();

            switch (selectedStructure) {

                case "Sorted Array":

                    currentStructure =
                            new SortedArrayList();

                    break;

                case "HashMap":

                    currentStructure =
                            new HashmapSystem();

                    break;

                case "Trie":

                    currentStructure =
                            new Trie();

                    break;
            }

            String selectedDataset =
                    (String)
                            datasetBox.getSelectedItem();

            String path =
                    "dataset/" + selectedDataset;

            long start =
                    System.nanoTime();

            int wordsLoaded =
                    DatasetLoader.loadWords(
                            path,
                            currentStructure);

            long end =
                    System.nanoTime();

            double loadTime =
                    (end - start)
                            / 1_000_000.0;

            structureInfoLabel.setText(
                    "Structure: "
                            + selectedStructure
                            + " | Dataset: "
                            + selectedDataset
                            + " | Words Loaded: "
                            + wordsLoaded
            );

            runtimeLabel.setText(
                    String.format(
                            "Load Time: %.3f ms",
                            loadTime
                    )
            );
        });

        // ==========================================
        // SEARCH BUTTON
        // ==========================================

        searchButton.addActionListener(e -> {

            if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first."
                );

                return;
            }

            String word =
                    wordField.getText().trim();

            long start =
                    System.nanoTime();

            boolean found =
                    currentStructure.search(word);

            long end =
                    System.nanoTime();

            long runtime =
                    end - start;

            runtimeLabel.setText(
                    String.format(
                            "Search Time: %d ns",
                            runtime
                    )
            );

            if (found) {

                resultsArea.setText(
                        "\"" + word + "\" found."
                );

            } else {

                resultsArea.setText(
                        "\"" + word + "\" not found."
                );
            }
        });

        // ==========================================
        // INSERT BUTTON
        // ==========================================

        insertButton.addActionListener(e -> {

            if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first."
                );

                return;
            }

            String word =
                    wordField.getText().trim();

            long start =
                    System.nanoTime();

            currentStructure.insert(word);

            long end =
                    System.nanoTime();

            long runtime =
                    end - start;

            runtimeLabel.setText(
                    String.format(
                            "Insert Time: %d ns",
                            runtime
                    )
            );

            resultsArea.setText(
                    "\"" + word + "\" inserted."
            );
        });

        // ==========================================
        // DELETE BUTTON
        // ==========================================

        deleteButton.addActionListener(e -> {

            if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first."
                );

                return;
            }

            String word =
                    wordField.getText().trim();

            long start =
                    System.nanoTime();

            currentStructure.delete(word);

            long end =
                    System.nanoTime();

            long runtime =
                    end - start;

            runtimeLabel.setText(
                    String.format(
                            "Delete Time: %d ns",
                            runtime
                    )
            );

            resultsArea.setText(
                    "\"" + word + "\" deleted."
            );
        });

        // ==========================================
        // SUGGESTION BUTTON
        // ==========================================

        suggestionsButton.addActionListener(e -> {

            if (currentStructure == null) {

                resultsArea.setText(
                        "Please load a dataset first."
                );

                return;
            }

            String prefix =
                    prefixField.getText().trim();

            long start =
                    System.nanoTime();

            java.util.List<String> suggestions =
                    currentStructure.getSuggestions(
                            prefix
                    );

            long end =
                    System.nanoTime();

            long runtime =
                    end - start;

            runtimeLabel.setText(
                    String.format(
                            "Suggestion Time: %d ns",
                            runtime
                    )
            );

            resultsArea.setText("");

            if (suggestions.isEmpty()) {

                resultsArea.setText(
                        "No suggestions found."
                );

            } else {

                for (String word : suggestions) {

                    resultsArea.append(
                            word + "\n"
                    );
                }

                resultsArea.setCaretPosition(0);
            }
        });

        // ==========================================
        // CLEAR BUTTON
        // ==========================================

        clearButton.addActionListener(e -> {
            resultsArea.setText("");
        });

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainUI();
        });
    }
}