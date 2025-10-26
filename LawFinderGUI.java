import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class LawFinderGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultListModel<String> historyListModel;
    private List<String> queryHistory;

    public LawFinderGUI() {
        setTitle("🏛️ Offline Law Finder & Penalty Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        queryHistory = new ArrayList<>();
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("🔍 Search Law", createSearchPanel());
        tabbedPane.addTab("📂 Browse Category", createBrowsePanel());
        tabbedPane.addTab("💰 Calculate Penalty", createPenaltyPanel());
        tabbedPane.addTab("📚 View All Laws", createAllLawsPanel());
        tabbedPane.addTab("📜 History", createHistoryPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private void performSearch(String keyword, JTextArea resultArea) {
        if (keyword.isEmpty()) {
            resultArea.setText("❌ Keyword cannot be empty!");
            return;
        }

        try {
            List<Law> results = LawDatabase.searchLaws(keyword);
            if (results.isEmpty()) {
                resultArea.setText("❌ No laws found for: " + keyword);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("✅ Found ").append(results.size()).append(" matching law(s):\n");
                sb.append("═".repeat(80)).append("\n\n");
                
                for (int i = 0; i < results.size(); i++) {
                    Law law = results.get(i);
                    sb.append("[LAW ").append(i + 1).append("]\n");
                    sb.append("─".repeat(80)).append("\n");
                    sb.append("📋 Section: ").append(law.getSection()).append("\n");
                    sb.append("📝 Description: ").append(law.getDescription()).append("\n");
                    sb.append("📂 Category: ").append(law.getCategory()).append("\n");
                    sb.append("💰 Basic Fine: ₹").append(law.getBasicFine()).append("\n");
                    sb.append("⚖️  Punishment: ").append(law.getPunishment()).append("\n\n");
                    
                    sb.append("📊 PENALTY EXAMPLES:\n");
                    sb.append("   • First Time (0 repeats): ₹").append(law.calculatePenalty(0)).append("\n");
                    sb.append("   • 2nd Time (1 repeat): ₹").append(law.calculatePenalty(1)).append("\n");
                    sb.append("   • 3rd Time (2 repeats): ₹").append(law.calculatePenalty(2)).append("\n");
                    sb.append("   • 4th Time (3 repeats): ₹").append(law.calculatePenalty(3)).append("\n");
                    sb.append("   • 5th Time (4 repeats): ₹").append(law.calculatePenalty(4)).append("\n\n");
                    
                    sb.append("═".repeat(80)).append("\n\n");
                }
                resultArea.setText(sb.toString());
                queryHistory.add("Searched: " + keyword);
            }
        } catch (Exception ex) {
            resultArea.setText("❌ Error: " + ex.getMessage());
        }
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        inputPanel.add(new JLabel("Enter keyword:"));
        JTextField keywordField = new JTextField(20);
        inputPanel.add(keywordField);

        JButton searchBtn = new JButton("Search");
        inputPanel.add(searchBtn);

        JPanel suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new BoxLayout(suggestionsPanel, BoxLayout.Y_AXIS));
        suggestionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane suggestionsScroll = new JScrollPane(suggestionsPanel);
        suggestionsScroll.setPreferredSize(new Dimension(400, 150));
        suggestionsScroll.setBorder(BorderFactory.createTitledBorder("Suggestions"));

        List<String> allKeywords = LawDatabase.getAllKeywords();

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane resultScroll = new JScrollPane(resultArea);

        keywordField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateSuggestions() {
                String text = keywordField.getText().toLowerCase().trim();
                suggestionsPanel.removeAll();

                if (!text.isEmpty()) {
                    for (String keyword : allKeywords) {
                        if (keyword.toLowerCase().contains(text)) {
                            JButton suggestionBtn = new JButton(keyword);
                            suggestionBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
                            suggestionBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    keywordField.setText(keyword);
                                    performSearch(keyword, resultArea);
                                }
                            });
                            suggestionsPanel.add(suggestionBtn);
                            suggestionsPanel.add(Box.createVerticalStrut(3));
                        }
                    }
                }

                suggestionsPanel.revalidate();
                suggestionsPanel.repaint();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(suggestionsScroll, BorderLayout.CENTER);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = keywordField.getText().trim();
                performSearch(keyword, resultArea);
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchPanel, resultScroll);
        splitPane.setDividerLocation(200);
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBrowsePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        List<String> categories = LawDatabase.getAllCategories();
        JComboBox<String> categoryCombo = new JComboBox<>(categories.toArray(new String[0]));
        categoryPanel.add(new JLabel("Select category:"));
        categoryPanel.add(categoryCombo);

        JButton browseBtn = new JButton("Browse");
        categoryPanel.add(browseBtn);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryCombo.getSelectedItem();
                List<Law> laws = LawDatabase.getLawsByCategory(selectedCategory);

                StringBuilder sb = new StringBuilder();
                sb.append("📋 Laws in ").append(selectedCategory).append(" (").append(laws.size()).append(" laws)\n");
                sb.append("═".repeat(80)).append("\n\n");
                for (int i = 0; i < laws.size(); i++) {
                    Law law = laws.get(i);
                    sb.append("[LAW ").append(i + 1).append("]\n");
                    sb.append("─".repeat(80)).append("\n");
                    sb.append("📋 Section: ").append(law.getSection()).append("\n");
                    sb.append("📝 Description: ").append(law.getDescription()).append("\n");
                    sb.append("💰 Basic Fine: ₹").append(law.getBasicFine()).append("\n");
                    sb.append("⚖️  Punishment: ").append(law.getPunishment()).append("\n\n");
                    
                    sb.append("📊 PENALTY EXAMPLES:\n");
                    sb.append("   • First Time (0 repeats): ₹").append(law.calculatePenalty(0)).append("\n");
                    sb.append("   • 2nd Time (1 repeat): ₹").append(law.calculatePenalty(1)).append("\n");
                    sb.append("   • 3rd Time (2 repeats): ₹").append(law.calculatePenalty(2)).append("\n");
                    sb.append("   • 4th Time (3 repeats): ₹").append(law.calculatePenalty(3)).append("\n");
                    sb.append("   • 5th Time (4 repeats): ₹").append(law.calculatePenalty(4)).append("\n\n");
                    
                    sb.append("═".repeat(80)).append("\n\n");
                }
                resultArea.setText(sb.toString());
                queryHistory.add("Browsed: " + selectedCategory);
            }
        });

        panel.add(categoryPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPenaltyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel mainInputPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel keywordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        keywordPanel.add(new JLabel("Enter law keyword:"));
        JTextField keywordField = new JTextField(20);
        keywordPanel.add(keywordField);

        JPanel offensePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        offensePanel.add(new JLabel("Repeat offenses (0 for first time):"));
        JSpinner offenseSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        offensePanel.add(offenseSpinner);

        JButton calculateBtn = new JButton("Calculate Penalty");
        offensePanel.add(calculateBtn);

        topPanel.add(keywordPanel, BorderLayout.NORTH);
        topPanel.add(offensePanel, BorderLayout.CENTER);

        JPanel suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new BoxLayout(suggestionsPanel, BoxLayout.Y_AXIS));
        suggestionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane suggestionsScroll = new JScrollPane(suggestionsPanel);
        suggestionsScroll.setPreferredSize(new Dimension(400, 120));
        suggestionsScroll.setBorder(BorderFactory.createTitledBorder("Suggestions"));

        List<String> allKeywords = LawDatabase.getAllKeywords();

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane resultScroll = new JScrollPane(resultArea);

        keywordField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateSuggestions() {
                String text = keywordField.getText().toLowerCase().trim();
                suggestionsPanel.removeAll();

                if (!text.isEmpty()) {
                    for (String keyword : allKeywords) {
                        if (keyword.toLowerCase().contains(text)) {
                            JButton suggestionBtn = new JButton(keyword);
                            suggestionBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
                            suggestionBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    keywordField.setText(keyword);
                                }
                            });
                            suggestionsPanel.add(suggestionBtn);
                            suggestionsPanel.add(Box.createVerticalStrut(3));
                        }
                    }
                }

                suggestionsPanel.revalidate();
                suggestionsPanel.repaint();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });

        mainInputPanel.add(topPanel, BorderLayout.NORTH);
        mainInputPanel.add(suggestionsScroll, BorderLayout.CENTER);

        calculateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = keywordField.getText().trim();
                int repeatOffenses = (int) offenseSpinner.getValue();

                try {
                    Law law = LawDatabase.findLawByKeyword(keyword);
                    int penalty = law.calculatePenalty(repeatOffenses);

                    StringBuilder sb = new StringBuilder();
                    sb.append("💰 PENALTY CALCULATION\n");
                    sb.append("════════════════════════════════════════\n");
                    sb.append("Law Section: ").append(law.getSection()).append("\n");
                    sb.append("Violation: ").append(law.getDescription()).append("\n");
                    sb.append("Category: ").append(law.getCategory()).append("\n");
                    sb.append("Basic Fine: ₹").append(law.getBasicFine()).append("\n");
                    sb.append("Repeat Offenses: ").append(repeatOffenses).append("\n");
                    sb.append("────────────────────────────────────────\n");
                    sb.append("Total Penalty: ₹").append(penalty).append("\n");
                    sb.append("Punishment: ").append(law.getPunishment()).append("\n");
                    sb.append("════════════════════════════════════════\n");

                    resultArea.setText(sb.toString());
                    queryHistory.add("Calculated penalty for: " + law.getSection() + " (₹" + penalty + ")");
                } catch (LawNotFoundException ex) {
                    resultArea.setText("❌ " + ex.getMessage());
                } catch (Exception ex) {
                    resultArea.setText("❌ Error: " + ex.getMessage());
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainInputPanel, resultScroll);
        splitPane.setDividerLocation(220);
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAllLawsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton loadBtn = new JButton("Load All Laws");
        panel.add(loadBtn, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Law> allLaws = LawDatabase.getAllLaws();
                StringBuilder sb = new StringBuilder();
                sb.append("📚 ALL LAWS IN DATABASE (").append(allLaws.size()).append(" laws)\n");
                sb.append("═".repeat(80)).append("\n\n");

                for (int i = 0; i < allLaws.size(); i++) {
                    Law law = allLaws.get(i);
                    sb.append("[LAW ").append(i + 1).append("]\n");
                    sb.append("─".repeat(80)).append("\n");
                    sb.append("📋 Section: ").append(law.getSection()).append("\n");
                    sb.append("📝 Description: ").append(law.getDescription()).append("\n");
                    sb.append("📂 Category: ").append(law.getCategory()).append("\n");
                    sb.append("💰 Basic Fine: ₹").append(law.getBasicFine()).append("\n");
                    sb.append("⚖️  Punishment: ").append(law.getPunishment()).append("\n\n");
                    
                    sb.append("📊 PENALTY EXAMPLES:\n");
                    sb.append("   • First Time (0 repeats): ₹").append(law.calculatePenalty(0)).append("\n");
                    sb.append("   • 2nd Time (1 repeat): ₹").append(law.calculatePenalty(1)).append("\n");
                    sb.append("   • 3rd Time (2 repeats): ₹").append(law.calculatePenalty(2)).append("\n");
                    sb.append("   • 4th Time (3 repeats): ₹").append(law.calculatePenalty(3)).append("\n");
                    sb.append("   • 5th Time (4 repeats): ₹").append(law.calculatePenalty(4)).append("\n\n");
                    
                    sb.append("═".repeat(80)).append("\n\n");
                }

                resultArea.setText(sb.toString());
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        historyListModel = new DefaultListModel<>();
        JList<String> historyList = new JList<>(historyListModel);
        historyList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(historyList);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton refreshBtn = new JButton("Refresh");
        JButton clearBtn = new JButton("Clear History");

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyListModel.clear();
                for (int i = 0; i < queryHistory.size(); i++) {
                    historyListModel.addElement((i + 1) + ". " + queryHistory.get(i));
                }
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryHistory.clear();
                historyListModel.clear();
            }
        });

        buttonPanel.add(refreshBtn);
        buttonPanel.add(clearBtn);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LawFinderGUI();
            }
        });
    }
}
