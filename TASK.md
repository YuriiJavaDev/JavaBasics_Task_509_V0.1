##  ⭐ Save Before Closing

### Create a window with a `JTextArea`.

#### The user can write text (this is a "document").

---

**🔥 When attempting to close the window:**

1️⃣ Check for changes:

```
textArea.getText().isEmpty()
```

---

2️⃣ If the text is NOT empty → ask:

```
Save changes before exit?
```

---

3️⃣ If YES → "save file"

#### Simulate saving:

- create a "file" as a string
- output to console:

```
Saving file...
File content:
<user text>
Saved successfully!
```
