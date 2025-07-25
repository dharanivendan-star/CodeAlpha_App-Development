# CodeAlpha_App-Development Internship
  $$Task:1 :📚 Flashcard Quiz App
  
A simple Java-based Flashcard Quiz App designed for easy studying.
This desktop application allows users to create, view, and manage flashcards with a clean, intuitive user interface built using Java Swing.

✅ Features

📄 Each flashcard has a question and an answer
👁️ Show or hide the answer with a "Show Answer" button
🔄 Navigate between flashcards using "Next" and "Previous"
➕ Add, ✏️ Edit, and 🗑️ Delete flashcards easily
💡 Lightweight desktop GUI with no database or internet required
🎨 Clean and minimal interface for distraction-free learning

🚀 How to Run
💻 Requirements
Java JDK 8 or above

Java-compatible IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans)

**OUTPUT SCREENSHOT**

+------------------------------------------------------+
| [Previous]  [Next]  [Show Answer]                    |
|                                                      |
| Q: What is the capital of France?                   |
|                                                      |
|                                                      |
+------------------------------------------------------+
| [Add]   [Edit]   [Delete]                            |
+------------------------------------------------------+


💡 Display

The center area displays either the question or the answer based on user action.
State variables track:
currentIndex — the index of the current flashcard

showingAnswer — boolean for toggling answer visibility

🔄 User Actions

.Add — Opens input fields using JOptionPane to create a new card
.Edit — Modifies the selected flashcard
.Delete — Removes the current card from the list


$$Task:2:📖 Random Quote Generator

A simple Java-based application that displays a random motivational quote every time the user starts the program or clicks a "New Quote" button. This project is built with the intention to demonstrate basic Java programming concepts such as arrays, event handling, and GUI design (if using Swing).

✅ Features

🎲 Random Quote Display: Shows a random quote each time the app is opened or a button is clicked.
👤 Author Display: Clearly displays both the quote and the author's name.
🖱️ New Quote Button: A simple button to get a different quote.
🎯 Minimal User Interface: Clean design with focus on readability (console or Swing GUI).
🚫 Offline Mode: No internet required, uses local list of quotes.

💡 How It Works

.The app stores a list of quotes (text + author) in an array or list.
.It uses Java’s Random class to select a quote randomly.
.If using Swing, the app has:
.A label for the quote text
.A label for the author
.A "New Quote" button that refreshes the display with another random quote.

🧱 Technology Stack

.Java SE
.(Optional) Java Swing for GUI
.JDK 8+



💻 Example Output (Console Version)

"Believe you can and you're halfway there."
— Theodore Roosevelt

[Press ENTER for new quote...]

"New Quote" button at the bottom

✏️ Sample Quotes Used

"The best way to get started is to quit talking and begin doing." - Walt Disney  
"Don’t let yesterday take up too much of today." - Will Rogers  
"Believe you can and you’re halfway there." - Theodore Roosevelt  


$$Task:3:Fitness Tracker App 🏃‍♀️📊

A Java Swing desktop app to track daily fitness activities like workouts, calories burned, and show weekly progress using charts.

## Features
- Add workouts (type, duration, calories)
- View history in a table
- Weekly bar chart using JFreeChart
- SQLite database for local storage

## Requirements
- Java 8+
- SQLite JDBC
- JFreeChart + JCommon
- SLF4J (optional logging)

## Run
Compile and run from IntelliJ or CLI.


