package com.yurii.pavlenko.app;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

/**
 * Custom frame demonstrating data verification checks before closing the application context.
 */
public class SaveBeforeClosing extends JFrame {

    /**
     * Entry point forced at the top of the class structure.
     */
    public static void main(String[] args) {
        // Enforce thread-safe Swing execution context mapping on the EDT
        SwingUtilities.invokeLater(() -> {
            SaveBeforeClosing app = new SaveBeforeClosing();
            app.setVisible(true);
        });
    }

    /**
     * Constructs the text frame layout and binds the conditional closing observers.
     */
    public SaveBeforeClosing() {
        super("Document Editor — Save Before Closing");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Subclass JTextArea to filter out internal IntelliJ IDEA clipboard exceptions
        JTextArea textArea = new JTextArea() {
            @Override
            public void paste() {
                // Save the original system error stream
                PrintStream originalErr = System.err;
                try {
                    // Mute the noisy IntelliJ IDEA metadata outputs during clipboard fetching
                    System.setErr(new PrintStream(originalErr) {
                        @Override
                        public void println(String x) {
                            if (x == null || !x.contains("com/intellij/")) {
                                super.println(x);
                            }
                        }
                    });
                    super.paste();
                } finally {
                    // Restore the default system error stream immediately after pasting
                    System.setErr(originalErr);
                }
            }
        };

        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (textArea.getText().isEmpty()) {
                    System.exit(0);
                    return;
                }

                int option = JOptionPane.showConfirmDialog(
                        SaveBeforeClosing.this,
                        "Save changes before exit?",
                        "Unsaved Changes",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    saveFileSimulation(textArea.getText());
                    System.exit(0);
                } else if (option == JOptionPane.NO_OPTION) {
                    System.out.println("Exit without saving changes!");
                    System.exit(0);
                }
            }

            private void saveFileSimulation(String content) {
                System.out.println("Saving file...");
                System.out.println("File content:");
                System.out.println(content);
                System.out.println("Saved successfully!");
            }
        });
    }
}