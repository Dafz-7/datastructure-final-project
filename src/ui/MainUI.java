package ui;

import javax.swing.*;
import java.awt.*;
import interfaces.AutocompleteStructure;
import sortedarray.SortedArray;
import trie.Trie;
import hashmap.HashmapAdapter;
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

        // Create components
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

        resultsArea.setEditable(false);

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel datasetPanel = new JPanel();

        datasetPanel.add(new JLabel("Structure:"));
        datasetPanel.add(structureBox);

        datasetPanel.add(new JLabel("Dataset:"));
        datasetPanel.add(datasetBox);

        datasetPanel.add(loadButton);

        panel.add(datasetPanel);

        JPanel statusPanel = new JPanel();

        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));

        structureInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        runtimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusPanel.add(structureInfoLabel);

        statusPanel.add(runtimeLabel);

        panel.add(statusPanel);

/* 
        panel.add(new JLabel("Structure:"));
        panel.add(structureBox);

        panel.add(new JLabel("Dataset:"));
        panel.add(datasetBox);

        panel.add(loadButton);
 */


/*
        panel.add(structureInfoLabel);

        panel.add(runtimeLabel); 
*/
/* 
        panel.add(new JLabel("Word:"));

        panel.add(wordField);

        panel.add(searchButton);

        panel.add(insertButton);

        panel.add(deleteButton);
 */

        panel.add(statusPanel);

        JPanel wordPanel = new JPanel();

        wordPanel.add(new JLabel("Word:"));

        wordPanel.add(wordField);

        wordPanel.add(searchButton);

        wordPanel.add(insertButton);

        wordPanel.add(deleteButton);

        panel.add(wordPanel);

/* 
        panel.add(new JLabel("Prefix:"));

        panel.add(prefixField);

        panel.add(suggestionsButton);
 */

        panel.add(wordPanel);

        JPanel autocompletePanel = new JPanel();

        autocompletePanel.add(new JLabel("Prefix:"));

        autocompletePanel.add(prefixField);

        autocompletePanel.add(suggestionsButton);

        panel.add(autocompletePanel);

        JPanel resultsControlPanel = new JPanel();

        resultsControlPanel.add(clearButton);

        panel.add(resultsControlPanel);

        panel.add(new JScrollPane(resultsArea));

// Load Button
        loadButton.addActionListener(e -> {

    String selectedStructure =
            (String) structureBox.getSelectedItem();

    switch (selectedStructure) {

        case "Sorted Array":
            currentStructure =
                    new SortedArray();
            break;

        case "HashMap":
            currentStructure =
                    new HashmapAdapter();
            break;

        case "Trie":
            currentStructure =
                    new Trie();
            break;
    }

    String selectedDataset =
            (String) datasetBox.getSelectedItem();

    String path =
        "dataset/" + selectedDataset;

long start = System.nanoTime();

int wordsLoaded =
        DatasetLoader.loadWords(
                path,
                currentStructure);

long end = System.nanoTime();

double loadTime =
        (end - start) / 1_000_000.0;

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
// Search Button

        searchButton.addActionListener(e -> {

        if (currentStructure == null) {

            resultsArea.setText(
                    "Please load a dataset first."
            );

            return;
        }

        String word =
                wordField.getText().trim();

        long start = System.nanoTime();

        boolean found =
                currentStructure.search(word);

        long end = System.nanoTime();

        double runtime =
                (end - start) / 1_000_000.0;

        runtimeLabel.setText(
                String.format(
                        "Search Time: %.6f ms",
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

// insert button

insertButton.addActionListener(e -> {

    if (currentStructure == null) {

        resultsArea.setText(
                "Please load a dataset first."
        );

        return;
    }

    String word =
            wordField.getText().trim();

    long start = System.nanoTime();

    currentStructure.insert(word);

    long end = System.nanoTime();

    double runtime =
            (end - start) / 1_000_000.0;

    runtimeLabel.setText(
            String.format(
                    "Insert Time: %.6f ms",
                    runtime
            )
    );

    resultsArea.setText(
            "\"" + word + "\" inserted."
    );
});

// delete button

deleteButton.addActionListener(e -> {

    if (currentStructure == null) {

        resultsArea.setText(
                "Please load a dataset first."
        );

        return;
    }

    String word =
            wordField.getText().trim();

    long start = System.nanoTime();

    currentStructure.delete(word);

    long end = System.nanoTime();

    double runtime =
            (end - start) / 1_000_000.0;

    runtimeLabel.setText(
            String.format(
                    "Delete Time: %.6f ms",
                    runtime
            )
    );

    resultsArea.setText(
            "\"" + word + "\" deleted."
    );
});

suggestionsButton.addActionListener(e -> {

    if (currentStructure == null) {

        resultsArea.setText(
                "Please load a dataset first."
        );

        return;
    }

    String prefix =
            prefixField.getText().trim();

    long start = System.nanoTime();

    java.util.List<String> suggestions =
            currentStructure.getSuggestions(
                    prefix
            );

    long end = System.nanoTime();

    double runtime =
            (end - start) / 1_000_000.0;

    runtimeLabel.setText(
            String.format(
                    "Suggestion Time: %.6f ms",
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