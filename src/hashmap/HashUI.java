package hashmap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.util.List;

/*
 * UI FOR HASHMAP AUTOCOMPLETE SYSTEM
 *
 * Includes runtime testing for:
 * - Insert
 * - Delete
 * - Prefix Search
 * - Exact Word Search
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
             * START INSERT RUNTIME
             */
            long startInsertLoad =
                    System.nanoTime();

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

            /*
             * END INSERT RUNTIME
             */
            long endInsertLoad =
                    System.nanoTime();

            br.close();

            double insertLoadMs =
                    (endInsertLoad
                            - startInsertLoad)
                            / 1_000_000.0;

            System.out.println(
                    "File Insert Runtime: "
                            + insertLoadMs
                            + " ms"
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

                                /*
                                 * PREFIX SEARCH RUNTIME
                                 */
                                long start =
                                        System.nanoTime();

                                List<String> result =
                                        ac.search(text);

                                long end =
                                        System.nanoTime();

                                double ms =
                                        (end - start)
                                                / 1_000_000.0;

                                System.out.println(
                                        "Prefix Search Runtime: "
                                                + ms
                                                + " ms"
                                );

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

            /*
             * INSERT RUNTIME
             */
            long start =
                    System.nanoTime();

            ac.insert(text, 1);

            long end =
                    System.nanoTime();

            double ms =
                    (end - start)
                            / 1_000_000.0;

            System.out.println(
                    "Insert Runtime: "
                            + ms
                            + " ms"
            );

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

            /*
             * DELETE RUNTIME
             */
            long start =
                    System.nanoTime();

            ac.remove(text);

            long end =
                    System.nanoTime();

            double ms =
                    (end - start)
                            / 1_000_000.0;

            System.out.println(
                    "Delete Runtime: "
                            + ms
                            + " ms"
            );

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

            /*
             * EXACT SEARCH RUNTIME
             */
            long start =
                    System.nanoTime();

            boolean found =
                    ac.lookup(text)
                            .containsKey(text);

            long end =
                    System.nanoTime();

            double ms =
                    (end - start)
                            / 1_000_000.0;

            System.out.println(
                    "Exact Word Search Runtime: "
                            + ms
                            + " ms"
            );

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