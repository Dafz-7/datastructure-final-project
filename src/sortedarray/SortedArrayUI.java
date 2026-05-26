package sortedarray;

import datasetutils.DatasetLoader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SortedArrayUI extends JFrame {

    private SortedArray sortedArray;

    private JTextField inputField;

    private DefaultListModel<String> listModel;

    private JList<String> suggestionList;

    private JLabel statusLabel;

    public SortedArrayUI() {

        // =====================================
        // Initialize Sorted Array
        // =====================================

        sortedArray = new SortedArray();

        DatasetLoader.loadWords(
                "datastructure-final-project\\dataset\\words_1000.txt",
                sortedArray
        );

        // =====================================
        // Window Settings
        // =====================================

        setTitle("Sorted Array Autocomplete System");

        setSize(600, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // =====================================
        // Input Field
        // =====================================

        inputField = new JTextField(25);

        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Enter Word:"));

        topPanel.add(inputField);

        add(topPanel, BorderLayout.NORTH);

        // =====================================
        // Suggestion List
        // =====================================

        listModel = new DefaultListModel<>();

        suggestionList = new JList<>(listModel);

        JScrollPane scrollPane =
                new JScrollPane(suggestionList);

        add(scrollPane, BorderLayout.CENTER);

        // =====================================
        // Status Label
        // =====================================

        statusLabel = new JLabel("Ready.");

        add(statusLabel, BorderLayout.SOUTH);

        // =====================================
        // Buttons
        // =====================================

        JButton insertButton =
                new JButton("Insert");

        JButton deleteButton =
                new JButton("Delete");

        JButton searchButton =
                new JButton("Search");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(insertButton);

        buttonPanel.add(deleteButton);

        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.WEST);

        // =====================================
        // LIVE AUTOCOMPLETE
        // =====================================

        inputField.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateSuggestions();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateSuggestions();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateSuggestions();
                    }
                }
        );

        // =====================================
        // ARROW KEY SUPPORT
        // =====================================

        inputField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_DOWN) {

                    int index =
                            suggestionList.getSelectedIndex();

                    if (index <
                            listModel.size() - 1) {

                        suggestionList.setSelectedIndex(
                                index + 1
                        );

                        suggestionList.ensureIndexIsVisible(
                                index + 1
                        );
                    }
                }

                else if (key == KeyEvent.VK_UP) {

                    int index =
                            suggestionList.getSelectedIndex();

                    if (index > 0) {

                        suggestionList.setSelectedIndex(
                                index - 1
                        );

                        suggestionList.ensureIndexIsVisible(
                                index - 1
                        );
                    }
                }

                else if (key == KeyEvent.VK_ENTER) {

                    String selected =
                            suggestionList.getSelectedValue();

                    if (selected != null) {

                        inputField.setText(selected);

                        statusLabel.setText(
                                "Selected: " + selected
                        );
                    }
                }
            }
        });

        // =====================================
        // INSERT BUTTON
        // =====================================

        insertButton.addActionListener(e -> {

            String word =
                    inputField.getText()
                            .trim()
                            .toLowerCase();

            if (word.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            if (sortedArray.search(word)) {

                statusLabel.setText(
                        "'" + word
                                + "' already exists."
                );

                return;
            }

            sortedArray.insert(word);

            statusLabel.setText(
                    "Inserted: " + word
            );

            updateSuggestions();
        });

        // =====================================
        // DELETE BUTTON
        // =====================================

        deleteButton.addActionListener(e -> {

            String word =
                    inputField.getText()
                            .trim()
                            .toLowerCase();

            if (word.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            if (!sortedArray.search(word)) {

                statusLabel.setText(
                        "'" + word
                                + "' does not exist."
                );

                return;
            }

            sortedArray.delete(word);

            statusLabel.setText(
                    "Deleted: " + word
            );

            updateSuggestions();
        });

        // =====================================
        // SEARCH BUTTON
        // =====================================

        searchButton.addActionListener(e -> {

            String word =
                    inputField.getText()
                            .trim()
                            .toLowerCase();

            if (word.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            boolean found =
                    sortedArray.search(word);

            if (found) {

                statusLabel.setText(
                        "'" + word
                                + "' FOUND."
                );

            } else {

                statusLabel.setText(
                        "'" + word
                                + "' NOT FOUND."
                );
            }
        });
    }

    // =====================================
    // UPDATE SUGGESTIONS
    // =====================================

    private void updateSuggestions() {

        String prefix =
                inputField.getText()
                        .trim()
                        .toLowerCase();

        listModel.clear();

        if (prefix.isEmpty()) {
            return;
        }

        List<String> suggestions =
                sortedArray.getSuggestions(prefix);

        if (suggestions.isEmpty()) {

            statusLabel.setText(
                    "No suggestions found."
            );

            return;
        }

        for (String word : suggestions) {

            listModel.addElement(word);
        }

        statusLabel.setText(
                suggestions.size()
                        + " suggestion(s) found."
        );

        suggestionList.setSelectedIndex(0);
    }

    // =====================================
    // MAIN METHOD
    // =====================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            SortedArrayUI ui =
                    new SortedArrayUI();

            ui.setVisible(true);
        });
    }
}