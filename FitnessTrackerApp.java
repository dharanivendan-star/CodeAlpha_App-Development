
jVERTICAL,avax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


         class FitnessTrackerApp extends JFrame {
            private JTextField tfDuration, tfCalories, tfType;
            private JButton btnAdd, btnShowGraph;
            private JTable table;
            private DefaultTableModel model;
            private Connection conn;

            public FitnessTrackerApp() {
                setTitle("Fitness Tracker App");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(800, 600);
                setLayout(new BorderLayout());

                initDB();
                initUI();

                setVisible(true);
            }

            // Initialize SQLite database
            private void initDB() {
                try {
                    conn = DriverManager.getConnection("jdbc:sqlite:fitness.db");
                    Statement stmt = conn.createStatement();
                    stmt.execute("CREATE TABLE IF NOT EXISTS fitness_log (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, duration INTEGER, calories INTEGER, date TEXT)");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
                }
            }

            // Initialize UI components
            private void initUI() {
                JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));

                tfType = new JTextField();
                tfDuration = new JTextField();
                tfCalories = new JTextField();
                btnAdd = new JButton("Add Activity");

                inputPanel.add(new JLabel("Exercise Type:"));
                inputPanel.add(tfType);
                inputPanel.add(new JLabel("Duration (min):"));
                inputPanel.add(tfDuration);
                inputPanel.add(new JLabel("Calories Burned:"));
                inputPanel.add(tfCalories);
                inputPanel.add(new JLabel(""));
                inputPanel.add(btnAdd);

                model = new DefaultTableModel(new String[]{"Date", "Type", "Duration", "Calories"}, 0);
                table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);

                btnShowGraph = new JButton("Show Weekly Progress");

                add(inputPanel, BorderLayout.NORTH);
                add(scrollPane, BorderLayout.CENTER);
                add(btnShowGraph, BorderLayout.SOUTH);

                btnAdd.addActionListener(e -> addActivity());
                btnShowGraph.addActionListener(e -> showWeeklyGraph());

                loadActivities();
            }

            // Add activity to DB
            private void addActivity() {
                String type = tfType.getText();
                String durationStr = tfDuration.getText();
                String caloriesStr = tfCalories.getText();

                if (type.isEmpty() || durationStr.isEmpty() || caloriesStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                    return;
                }

                try {
                    int duration = Integer.parseInt(durationStr);
                    int calories = Integer.parseInt(caloriesStr);
                    String date = LocalDate.now().toString();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO fitness_log (type, duration, calories, date) VALUES (?, ?, ?, ?)");
                    ps.setString(1, type);
                    ps.setInt(2, duration);
                    ps.setInt(3, calories);
                    ps.setString(4, date);
                    ps.executeUpdate();

                    tfType.setText("");
                    tfDuration.setText("");
                    tfCalories.setText("");
                    loadActivities();
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Duration and Calories must be numbers.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
                }
            }

            // Load all activities from DB into table
            private void loadActivities() {
                model.setRowCount(0);
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM fitness_log ORDER BY date DESC");
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getString("date"),
                                rs.getString("type"),
                                rs.getInt("duration"),
                                rs.getInt("calories")
                        });
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error loading activities: " + e.getMessage());
                }
            }

            // Show bar chart of last 7 days' calories
            private void showWeeklyGraph() {
                Map<String, Integer> caloriesPerDay = new TreeMap<>();
                LocalDate today = LocalDate.now();

                for (int i = 6; i >= 0; i--) {
                    caloriesPerDay.put(today.minusDays(i).toString(), 0);
                }

                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT date, SUM(calories) as total FROM fitness_log WHERE date BETWEEN ? AND ? GROUP BY date");
                    ps.setString(1, today.minusDays(6).toString());
                    ps.setString(2, today.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String date = rs.getString("date");
                        int total = rs.getInt("total");
                        caloriesPerDay.put(date, total);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error fetching graph data: " + e.getMessage());
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Map.Entry<String, Integer> entry : caloriesPerDay.entrySet()) {
                    dataset.addValue(entry.getValue(), "Calories", entry.getKey());
                }

                JFreeChart barChart = ChartFactory.cr
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("Weekly Progress Chart");
        chartFrame.setSize(600, 400);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FitnessTrackerApp::new);
    }
}
