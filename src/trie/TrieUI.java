package trie;

import datasetutils.DatasetLoader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class TrieUI extends JFrame {

    private Trie trie;

    private JTextField inputField;

    private DefaultListModel<String> listModel;

    private JList<String> suggestionList;

    private JLabel statusLabel;

    public TrieUI() {

        // =====================================
        // Initialize Trie
        // =====================================
        trie = new Trie();

        DatasetLoader.loadWords(
                "dataset/words_1000.txt",
                trie
        );a

        // =====================================
        // Window Settings
        // =====================================
        setTitle("Trie Autocomplete System");

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

                // DOWN ARROW
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

                // UP ARROW
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

                // ENTER
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

            // Empty input
            if (word.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            // Duplicate word
            if (trie.search(word)) {

                statusLabel.setText(
                        "'" + word
                                + "' already exists."
                );

                return;
            }

            trie.insert(word);

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

            // Empty input
            if (word.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            // Word not found
            if (!trie.search(word)) {

                statusLabel.setText(
                        "'" + word
                                + "' does not exist."
                );

                return;
            }

            trie.delete(word);

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

            // Empty input
            if (word.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            boolean found = trie.search(word);

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

        // Empty input
        if (prefix.isEmpty()) {
            return;
        }

        List<String> suggestions =
                trie.getSuggestions(prefix);

        // No suggestions
        if (suggestions.isEmpty()) {

            statusLabel.setText(
                    "No suggestions found."
            );

            return;
        }

        // Add suggestions to list
        for (String word : suggestions) {

            listModel.addElement(word);
        }

        statusLabel.setText(
                suggestions.size()
                        + " suggestion(s) found."
        );

        // Auto-select first suggestion
        suggestionList.setSelectedIndex(0);
    }

    // =====================================
    // MAIN METHOD
    // =====================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TrieUI ui = new TrieUI();

            ui.setVisible(true);
        });
    }
}