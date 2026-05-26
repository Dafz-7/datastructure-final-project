package hashmap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.util.List;

/*
 * UI FOR HASHMAP AUTOCOMPLETE SYSTEM
 */

public class HashUI {

    public static void main(String[] args) {

        /*
         * CREATE AUTOCOMPLETE SYSTEM
         */
        HashmapSystem ac =
                new HashmapSystem(
                        new String[]{},
                        new int[]{}
                );

        /*
         * LOAD WORDS FROM FILE
         */
        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "dataset\\words_20000.txt"
                            )
                    );

            String line;

            /*
             * READ EACH WORD
             */
            while ((line = br.readLine()) != null) {

                line = line.trim()
                        .toLowerCase();

                /*
                 * IGNORE EMPTY LINES
                 */
                if (!line.isEmpty()) {

                    ac.insert(line, 1);
                }
            }

            br.close();

            System.out.println(
                    "Dataset loaded successfully."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error reading file."
            );

            e.printStackTrace();
        }

        /*
         * CREATE WINDOW
         */
        JFrame frame =
                new JFrame(
                        "HashMap Autocomplete"
                );

        frame.setSize(600, 450);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);

        frame.setLayout(
                new BorderLayout()
        );

        /*
         * INPUT FIELD
         */
        JTextField textField =
                new JTextField();

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        JLabel label =
                new JLabel(
                        "Enter Word: "
                );

        topPanel.add(
                label,
                BorderLayout.WEST
        );

        topPanel.add(
                textField,
                BorderLayout.CENTER
        );

        frame.add(
                topPanel,
                BorderLayout.NORTH
        );

        /*
         * SUGGESTION LIST
         */
        DefaultListModel<String>
                listModel =
                new DefaultListModel<>();

        JList<String>
                suggestionList =
                new JList<>(listModel);

        JScrollPane scrollPane =
                new JScrollPane(
                        suggestionList
                );

        frame.add(
                scrollPane,
                BorderLayout.CENTER
        );

        /*
         * STATUS LABEL
         */
        JLabel statusLabel =
                new JLabel("Ready.");

        frame.add(
                statusLabel,
                BorderLayout.SOUTH
        );

        /*
         * BUTTONS
         */
        JButton insertButton =
                new JButton("Insert");

        JButton deleteButton =
                new JButton("Delete");

        JButton searchButton =
                new JButton("Search");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(insertButton);

        buttonPanel.add(deleteButton);

        buttonPanel.add(searchButton);

        frame.add(
                buttonPanel,
                BorderLayout.WEST
        );

        /*
         * LIVE AUTOCOMPLETE
         */
        textField.getDocument()
                .addDocumentListener(

                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e) {

                                updateSuggestions();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e) {

                                updateSuggestions();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e) {

                                updateSuggestions();
                            }

                            /*
                             * UPDATE SUGGESTIONS
                             */
                            private void updateSuggestions() {

                                String text =
                                        textField
                                                .getText()
                                                .trim()
                                                .toLowerCase();

                                /*
                                 * CLEAR OLD SUGGESTIONS
                                 */
                                listModel.clear();

                                /*
                                 * EMPTY INPUT
                                 */
                                if (text.isEmpty()) {

                                    statusLabel.setText(
                                            "Ready."
                                    );

                                    return;
                                }

                                List<String> result =
                                        ac.search(text);

                                /*
                                 * NO RESULTS
                                 */
                                if (result.isEmpty()) {

                                    statusLabel.setText(
                                            "No suggestions found."
                                    );

                                    return;
                                }

                                /*
                                 * ADD RESULTS
                                 */
                                for (String word : result) {

                                    listModel.addElement(word);
                                }

                                statusLabel.setText(
                                        result.size()
                                                + " suggestion(s) found."
                                );

                                /*
                                 * AUTO SELECT FIRST
                                 */
                                suggestionList
                                        .setSelectedIndex(0);
                            }
                        });

        /*
         * INSERT BUTTON
         */
        insertButton.addActionListener(e -> {

            String text =
                    textField.getText()
                            .trim()
                            .toLowerCase();

            /*
             * EMPTY INPUT
             */
            if (text.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            ac.insert(text, 1);

            statusLabel.setText(
                    "Inserted: " + text
            );

            textField.setText("");
        });

        /*
         * DELETE BUTTON
         */
        deleteButton.addActionListener(e -> {

            String text =
                    textField.getText()
                            .trim()
                            .toLowerCase();

            /*
             * EMPTY INPUT
             */
            if (text.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            ac.remove(text);

            statusLabel.setText(
                    "Removed: " + text
            );

            textField.setText("");
        });

        /*
         * SEARCH BUTTON
         */
        searchButton.addActionListener(e -> {

            String text =
                    textField.getText()
                            .trim()
                            .toLowerCase();

            /*
             * EMPTY INPUT
             */
            if (text.isEmpty()) {

                statusLabel.setText(
                        "Please enter a word."
                );

                return;
            }

            boolean found =
                    ac.lookup(text)
                            .containsKey(text);

            /*
             * DISPLAY RESULT
             */
            if (found) {

                statusLabel.setText(
                        "'" + text
                                + "' FOUND."
                );

            } else {

                statusLabel.setText(
                        "'" + text
                                + "' NOT FOUND."
                );
            }
        });

        /*
         * SHOW WINDOW
         */
        frame.setVisible(true);

        /*
         * AUTO FOCUS CURSOR
         */
        textField.requestFocusInWindow();
    }
}