import javax.swing.*;
import java.awt.*;
import java.util.Random;

class QuoteGenerator extends JFrame {
    private static final String[][] QUOTES = {
            {"To be or not to be, that is the question.", "William Shakespeare"},
            {"Stay hungry, stay foolish.", "Steve Jobs"},
            {"Life is what happens when you're busy making other plans.", "John Lennon"},
            {"In the middle of difficulty lies opportunity.", "Albert Einstein"}
    };

    private final JLabel quoteLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel authorLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton newQuoteBtn = new JButton("New Quote");
    private final Random rand = new Random();

    public QuoteGenerator() {
        super("Random Quote");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Style quote label
        quoteLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        quoteLabel.setPreferredSize(new Dimension(400, 100));
        quoteLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Style author label
        authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        authorLabel.setForeground(Color.DARK_GRAY);

        // Button settings
        newQuoteBtn.setFocusPainted(false);
        newQuoteBtn.addActionListener(e -> showRandomQuote());

        // Assemble layout
        JPanel center = new JPanel(new BorderLayout());
        center.add(quoteLabel, BorderLayout.CENTER);
        center.add(authorLabel, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);
        add(newQuoteBtn, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);

        // Show initial random quote
        showRandomQuote();
    }

    private void showRandomQuote() {
        int i;
        do {
            i = rand.nextInt(QUOTES.length);
        } while (quoteLabel.getText().contains(QUOTES[i][0]));

        quoteLabel.setText("<html><body style='text-align:center;'>" + QUOTES[i][0] + "</body></html>");
        authorLabel.setText("— " + QUOTES[i][1]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuoteGenerator().setVisible(true));
    }
}
