import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class FlashcardApp extends JFrame {
    private ArrayList<Flashcard> flashcards = new ArrayList<>();
    private int currentIndex = 0;
    private boolean showingAnswer = false;

    private JTextArea cardTextArea;
    private JButton showAnswerBtn, nextBtn, prevBtn, addBtn, editBtn, deleteBtn;

    public FlashcardApp() {
        setTitle("Flashcard Quiz App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Sample flashcards
        flashcards.add(new Flashcard("What is the capital of France?", "Paris"));
        flashcards.add(new Flashcard("What is 2 + 2?", "4"));
        flashcards.add(new Flashcard("Largest planet?", "Jupiter"));

        cardTextArea = new JTextArea();
        cardTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        cardTextArea.setLineWrap(true);
        cardTextArea.setWrapStyleWord(true);
        cardTextArea.setEditable(false);

        showAnswerBtn = new JButton("Show Answer");
        nextBtn = new JButton("Next");
        prevBtn = new JButton("Previous");
        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevBtn);
        buttonPanel.add(nextBtn);
        buttonPanel.add(showAnswerBtn);

        JPanel managePanel = new JPanel();
        managePanel.add(addBtn);
        managePanel.add(editBtn);
        managePanel.add(deleteBtn);

        add(new JScrollPane(cardTextArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        add(managePanel, BorderLayout.SOUTH);

        updateCardDisplay();

        showAnswerBtn.addActionListener(e -> toggleAnswer());
        nextBtn.addActionListener(e -> nextCard());
        prevBtn.addActionListener(e -> prevCard());
        addBtn.addActionListener(e -> addFlashcard());
        editBtn.addActionListener(e -> editFlashcard());
        deleteBtn.addActionListener(e -> deleteFlashcard());

        setVisible(true);
    }

    private void updateCardDisplay() {
        if (flashcards.isEmpty()) {
            cardTextArea.setText("No flashcards available.");
            showAnswerBtn.setEnabled(false);
            editBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        } else {
            Flashcard card = flashcards.get(currentIndex);
            cardTextArea.setText(showingAnswer ? "A: " + card.answer : "Q: " + card.question);
            showAnswerBtn.setEnabled(true);
            editBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
        }

        prevBtn.setEnabled(currentIndex > 0);
        nextBtn.setEnabled(currentIndex < flashcards.size() - 1);
    }

    private void toggleAnswer() {
        showingAnswer = !showingAnswer;
        updateCardDisplay();
        showAnswerBtn.setText(showingAnswer ? "Hide Answer" : "Show Answer");
    }

    private void nextCard() {
        if (currentIndex < flashcards.size() - 1) {
            currentIndex++;
            showingAnswer = false;
            showAnswerBtn.setText("Show Answer");
            updateCardDisplay();
        }
    }

    private void prevCard() {
        if (currentIndex > 0) {
            currentIndex--;
            showingAnswer = false;
            showAnswerBtn.setText("Show Answer");
            updateCardDisplay();
        }
    }

    private void addFlashcard() {
        JTextField questionField = new JTextField();
        JTextField answerField = new JTextField();

        Object[] message = {
                "Question:", questionField,
                "Answer:", answerField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Flashcard", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String question = questionField.getText().trim();
            String answer = answerField.getText().trim();
            if (!question.isEmpty() && !answer.isEmpty()) {
                flashcards.add(new Flashcard(question, answer));
                currentIndex = flashcards.size() - 1;
                showingAnswer = false;
                showAnswerBtn.setText("Show Answer");
                updateCardDisplay();
            }
        }
    }

    private void editFlashcard() {
        if (flashcards.isEmpty()) return;

        Flashcard current = flashcards.get(currentIndex);
        JTextField questionField = new JTextField(current.question);
        JTextField answerField = new JTextField(current.answer);

        Object[] message = {
                "Question:", questionField,
                "Answer:", answerField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Flashcard", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            current.question = questionField.getText().trim();
            current.answer = answerField.getText().trim();
            showingAnswer = false;
            showAnswerBtn.setText("Show Answer");
            updateCardDisplay();
        }
    }

    private void deleteFlashcard() {
        if (flashcards.isEmpty()) return;
        flashcards.remove(currentIndex);
        if (currentIndex >= flashcards.size()) currentIndex = flashcards.size() - 1;
        showingAnswer = false;
        showAnswerBtn.setText("Show Answer");
        updateCardDisplay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlashcardApp());
    }

    class Flashcard {
        String question;
        String answer;

        Flashcard(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }
}
