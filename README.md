# Window Lifecycle Events: Save Before Closing Simulation (JavaBasics_Task_509_V0.1)

## 📖 Description
In data-centric software development, safeguarding active runtime user state against accidental loss is a primary architectural pattern. This project simulates a professional "save-on-exit" system within a graphical text processing application. Utilizing a **`JTextArea`** container wrapped in a **`JScrollPane`**, users can compose arbitrary textual data. When an operating system window closure dispatch is triggered, our system intercepts the pipeline, suppressing immediate destruction. If data validation confirms the text layout is not empty, a modal dialog presents three routing outcomes: saving the data structure to standard stream output logs, discarding changes to exit, or aborting the exit flow completely to resume editing.

## 📋 Requirements Compliance
- **Close Flow Interception**: Overrode default close operations using `JFrame.DO_NOTHING_ON_CLOSE`.
- **State Validation Checking**: Evaluated empty text logic safely via `.getText().isEmpty()`.
- **Tri-State Option Evaluation**: Processed YES (save and exit), NO (exit without saving), and CANCEL (abort termination) selections correctly via `JOptionPane`.
- **I/O Storage Simulation**: Configured dynamic standard console logging structures to mimic clean physical disk write cycles.

## 🚀 Architectural Stack
- Java 17+ (Java AWT Event Delegation Model, Java Swing)

## 🏗️ Implementation Details
- **SaveBeforeClosing**: Main workspace frame holding the interactive layout controls and routing close execution pipelines.

## 📋 Expected result
*(If the text area contains content when trying to close the window)*
```text
Saving file...
File content:
Hello World from Yurii!
Saved successfully!
```

## 💻 Code Example

Project Structure:

    JavaBasics_Task_509/
    ├── src/
    │   └── com/yurii/pavlenko/
    │                 └── app/
    │                     └── SaveBeforeClosing.java
    ├── LICENSE
    ├── TASK.md
    ├── THEORY.md
    └── README.md

Code
```java
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

public class SaveBeforeClosing extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SaveBeforeClosing app = new SaveBeforeClosing();
            app.setVisible(true);
        });
    }

    public SaveBeforeClosing() {
        super("Document Editor — Save Before Closing");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea() {
            @Override
            public void paste() {
                PrintStream originalErr = System.err;
                try {
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
```

## ⚖️ License
This project is licensed under the **MIT License**.

Copyright (c) 2026 Yurii Pavlenko

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files...

License: [MIT](LICENSE)
