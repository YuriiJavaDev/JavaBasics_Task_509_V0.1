## WindowListener – Handling window events in Swing.

### When the user interacts with a JFrame window, the following events occur:

- the window opens
- minimizes
- closes
- becomes active
- loses focus, etc.

The following interface is used to track these events:

```
WindowListener
```

### 🔥 How to attach a listener

```java
jFrame.addWindowListener(...);
```

We "subscribe" to window events.

---

WindowListener is an interface

```java
public interface WindowListener
```

It has 7 methods.

Therefore, if we implement the interface, we must override **ALL** methods.

---

## Details about each method

---

### 1️⃣ windowOpened

```java
@Override
public void windowOpened(WindowEvent e)
```

**📌 When called**

When the window first becomes visible.

---

🔥 Used for:

- loading data
- starting an animation
- displaying a greeting
- connecting to a database

---

Example

```java
public void windowOpened(WindowEvent e) {
    System.out.println("The window has opened");
}
```

### 2️⃣ windowClosing

```java
@Override
public void windowClosing(WindowEvent e)
```

📌 When called

When the user CLICKS the close cross.

⚠️ The window is NOT closed yet.

---

🔥 The most important WindowListener method

Used for:

- exit confirmation
- file saving
- resource cleanup
- warnings

---

Example

```java
public void windowClosing(WindowEvent e) {
    
    int result = JOptionPane.showConfirmDialog(null, "Close the program without saving data?");
    
    if(result == JOptionPane.YES_OPTION){
        System.exit(0);
    }
    //JOptionPane.NO_OPTION - the No button was clicked
    //JOptionPane.CANCEL_OPTION - the Close button was clicked
}
```

**🔹 Disable default closing**

```java
frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
```

👉 Now the cross does NOT close the window automatically.

### 3️⃣ windowClosed

```java
@Override
public void windowClosed(WindowEvent e)
```

📌 When called

After the window has ALREADY been destroyed.

---

Used for:

- logging
- resource cleanup
- notifications

---

Difference:

| Method | When |
| --- | --- |
| windowClosing | BEFORE closing |
| windowClosed | AFTER closing |

### 4️⃣ windowIconified

```java
@Override
public void windowIconified(WindowEvent e)
```

📌 When called

When the window is minimized.

---

Used:

- pause the game
- stop the timer
- reduce load

### 5️⃣ windowDeiconified

```java
@Override
public void windowDeiconified(WindowEvent e)
```

📌 When called

When the window is maximized.

---

Uses:

- resume timer
- update interface

### 6️⃣ windowActivated

```java
@Override
public void windowActivated(WindowEvent e)
```

📌 When Called

When a window becomes active.

For example:

- the user switches back to it

---

Uses:

- update data
- check for changes

### 7️⃣ windowDeactivated

```java
@Override
public void windowDeactivated(WindowEvent e)
```

📌 When Called

When a window loses activity.

For example:

- the user opened another window

---
Uses:

- autosave
- pause

---

The main problem with WindowListener

ALL methods need to be written:

```
windowOpened()
windowClosing()
windowClosed()
...
```

```java
jFrame.addWindowListener(new WindowListener() {
    @Override
    public void windowOpened(WindowEvent e) {
    
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
    
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
    
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {
    
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
    
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }
});
```

Even if you only need one.

This is inconvenient.

---

That's why **WindowAdapter** exists

## WindowAdapter

This is a special class that already contains empty implementations of all methods.

---

That is, instead of:

```java
implements Window Listener
```

you can:

```java
new WindowAdapter()
```

and override only what you need.

```java
jFrame.addWindowListener(new WindowAdapter() {
    
    @Override
    public void windowClosing(WindowEvent e) {
    
    }
});
```

---

Which is used in real code because:

- shorter
- cleaner
- more convenient

---

A common combination

```java
jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

jFrame.addWindowListener(new WindowAdapter() {
    
    @Override
    public void windowClosing(WindowEvent e) {
        
        int result = JOptionPane.showConfirmDialog(jFrame, "Exit?");
        
        if(result == JOptionPane.YES_OPTION){
        System.exit(0);
        }
    }
});
```

---

Briefly

| What | Why |
| --- | --- |
| WindowListener | listen for window events |
| WindowAdapter | convenient version with empty methods |
| windowClosing | handling closing |
| windowClosed | after closing |
| windowActivated | the window became active |
| windowIconified | minimized the window |
