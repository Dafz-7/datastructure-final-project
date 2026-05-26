package hashmap;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;

/*
 * MAIN CLASS
 *
 * Used for testing the autocomplete system.
 */

public class testingfebri {

        public static void main(String[] args) {

                HashmapSystem ac = new HashmapSystem(
                                new String[] {},
                                new int[] {});

                try {

                        /*
                         * Read words.txt
                         */

                        BufferedReader br = new BufferedReader(
                                        new FileReader("words_10000.txt"));

                        String line;

                        /*
                         * Read every line
                         */
                        while ((line = br.readLine()) != null) {

                                /*
                                 * Remove extra spaces
                                 */
                                line = line.trim();

                                /*
                                 * Ignore empty lines
                                 */
                                if (!line.isEmpty()) {

                                        /*
                                         * Insert word
                                         */
                                        ac.insert(line, 1);
                                }
                        }


                        br.close();

                } catch (IOException e) {

                        System.out.println(
                                        "Error reading file.");

                        e.printStackTrace();
                }

                /*
                 * CREATE WINDOW
                 */
                JFrame frame = new JFrame("Autocomplete System");

                frame.setSize(400, 300);

                frame.setDefaultCloseOperation(
                                JFrame.EXIT_ON_CLOSE);

                frame.setLayout(
                                new BorderLayout());

                /*
                 * TEXT FIELD
                 *
                 * User types here
                 */
                JTextField textField = new JTextField();

                /*
                 * AREA TO DISPLAY SUGGESTIONS
                 */
                JTextArea suggestions = new JTextArea();

                suggestions.setEditable(false);

                /*
                 * IMPORTANT CHANGE
                 *
                 * Detect every key press instantly.
                 */
                textField.addKeyListener(

                                new KeyAdapter() {

                                        public void keyReleased(
                                                        KeyEvent e) {

                                                /*
                                                 * Current text
                                                 */
                                                String text = textField.getText();

                                                /*
                                                 * ENTER KEY
                                                 *
                                                 * Insert word
                                                 */
                                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                                                        ac.insert(text, 1);

                                                        suggestions.setText(
                                                                        "Inserted: " + text);

                                                        textField.setText("");

                                                        return;
                                                }

                                                /*
                                                 * DELETE KEY
                                                 *
                                                 * Remove word
                                                 */
                                                if (e.isControlDown()
                                                                && e.getKeyCode() == KeyEvent.VK_D) {

                                                        ac.remove(text);

                                                        suggestions.setText(
                                                                        "Removed: " + text);

                                                        textField.setText("");

                                                        return;
                                                }

                                                /*
                                                 * Show autocomplete suggestions
                                                 */
                                                java.util.List<String> result = ac.search(text);

                                                /*
                                                 * Clear old suggestions
                                                 */
                                                suggestions.setText("");

                                                /*
                                                 * Display suggestions
                                                 */
                                                for (String word : result) {

                                                        suggestions.append(
                                                                        word + "\n");
                                                }
                                        }
                                });
                /*
                 * Add components to window
                 */
                frame.add(
                                textField,
                                BorderLayout.NORTH);

                frame.add(
                                new JScrollPane(suggestions),
                                BorderLayout.CENTER);

                /*
                 * Show window
                 */
                frame.setVisible(true);

                /*
                 * Automatically focus cursor
                 * into text field
                 */
                textField.requestFocusInWindow();
        }
}
