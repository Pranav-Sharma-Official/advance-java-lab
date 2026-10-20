package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * StudentView — MVC View (Experiment 2).
 * A polished Swing JFrame with two tabs: Register and Search.
 */
public class StudentView extends JFrame {

    // ── Register Tab ────────────────────────────────────────────────────────
    private JTextField tfName       = new JTextField(20);
    private JTextField tfRoll       = new JTextField(20);
    private JComboBox<String> cbBranch = new JComboBox<>(new String[]{
            "Computer Science", "Electronics", "Mechanical",
            "Civil", "Information Technology", "Chemical"});
    private JRadioButton rbMale     = new JRadioButton("Male");
    private JRadioButton rbFemale   = new JRadioButton("Female");
    private JRadioButton rbOther    = new JRadioButton("Other");
    private ButtonGroup  genderGroup = new ButtonGroup();
    private JTextField tfGpa        = new JTextField(20);
    private JButton btnRegister     = new JButton("Register Student");
    private JButton btnClear        = new JButton("Clear");

    // ── Search Tab ──────────────────────────────────────────────────────────
    private JTextField tfSearch     = new JTextField(20);
    private JButton btnSearch       = new JButton("Search");
    private JButton btnListAll      = new JButton("List All");
    private String[] tableColumns   = {"ID","Name","Roll No","Branch","Gender","GPA"};
    private DefaultTableModel tableModel = new DefaultTableModel(tableColumns, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private JTable resultTable      = new JTable(tableModel);

    // ── Connection Panel ────────────────────────────────────────────────────
    private JTextField tfHost       = new JTextField("localhost", 12);
    private JTextField tfPort       = new JTextField("6000", 6);
    private JButton btnConnect      = new JButton("Connect");
    private JLabel  lblConnStatus   = new JLabel("● Disconnected");

    // ── Status Bar ──────────────────────────────────────────────────────────
    private JLabel statusBar = new JLabel(" Ready");

    // Colours
    private static final Color PRIMARY   = new Color(25, 118, 210);
    private static final Color SECONDARY = new Color(240, 245, 255);
    private static final Color SUCCESS   = new Color(56, 142, 60);
    private static final Color ERROR_COL = new Color(211, 47, 47);

    public StudentView() {
        super("CollegeHub — Student Management System");
        setLookAndFeel();
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(780, 580);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // -----------------------------------------------------------------------
    // UI Construction
    // -----------------------------------------------------------------------

    private void setLookAndFeel() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
    }

    private void buildUI() {
        setLayout(new BorderLayout(5, 5));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(12, 16, 12, 16));
        JLabel title = new JLabel("CollegeHub — Distributed Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Connection Panel
        add(buildConnectionPanel(), BorderLayout.SOUTH);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabs.addTab("📋  Register Student", buildRegisterPanel());
        tabs.addTab("🔍  Search Students",  buildSearchPanel());
        add(tabs, BorderLayout.CENTER);

        // Status Bar (sits above connection panel — restructure)
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.setBorder(new EmptyBorder(4, 10, 4, 10));
        statusBar.setOpaque(true);
        statusBar.setBackground(new Color(230, 230, 230));
    }

    private JPanel buildConnectionPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        p.setBackground(new Color(245, 245, 245));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        p.add(new JLabel("Server:"));
        p.add(tfHost);
        p.add(new JLabel("Port:"));
        p.add(tfPort);

        styleButton(btnConnect, PRIMARY);
        p.add(btnConnect);

        lblConnStatus.setForeground(ERROR_COL);
        lblConnStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        p.add(lblConnStatus);

        // Status bar embedded here
        p.add(Box.createHorizontalStrut(20));
        p.add(statusBar);

        return p;
    }

    private JPanel buildRegisterPanel() {
        JPanel outer = new JPanel(new BorderLayout(10, 10));
        outer.setBackground(SECONDARY);
        outer.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 200), 1, true),
                new EmptyBorder(20, 30, 20, 30)));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill   = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        int row = 0;
        addFormRow(form, gc, row++, labelFont, "Full Name:", tfName);
        addFormRow(form, gc, row++, labelFont, "Roll Number:", tfRoll);

        // Branch
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        JLabel lBranch = new JLabel("Branch:"); lBranch.setFont(labelFont);
        form.add(lBranch, gc);
        gc.gridx = 1; gc.weightx = 1;
        form.add(cbBranch, gc);
        row++;

        // Gender
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        JLabel lGender = new JLabel("Gender:"); lGender.setFont(labelFont);
        form.add(lGender, gc);
        gc.gridx = 1; gc.weightx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.setBackground(Color.WHITE);
        genderGroup.add(rbMale); genderGroup.add(rbFemale); genderGroup.add(rbOther);
        rbMale.setBackground(Color.WHITE);
        rbFemale.setBackground(Color.WHITE);
        rbOther.setBackground(Color.WHITE);
        rbMale.setSelected(true);
        genderPanel.add(rbMale); genderPanel.add(rbFemale); genderPanel.add(rbOther);
        form.add(genderPanel, gc);
        row++;

        addFormRow(form, gc, row++, labelFont, "GPA (0.0–10.0):", tfGpa);

        // Buttons
        gc.gridx = 1; gc.gridy = row; gc.fill = GridBagConstraints.NONE;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(Color.WHITE);
        styleButton(btnRegister, SUCCESS);
        styleButton(btnClear, new Color(120, 120, 120));
        btnPanel.add(btnRegister);
        btnPanel.add(btnClear);
        form.add(btnPanel, gc);

        outer.add(form, BorderLayout.CENTER);
        return outer;
    }

    private void addFormRow(JPanel form, GridBagConstraints gc, int row,
                            Font labelFont, String label, JTextField field) {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        JLabel lbl = new JLabel(label); lbl.setFont(labelFont);
        form.add(lbl, gc);
        gc.gridx = 1; gc.weightx = 1;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(field, gc);
    }

    private JPanel buildSearchPanel() {
        JPanel outer = new JPanel(new BorderLayout(10, 10));
        outer.setBackground(SECONDARY);
        outer.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Search bar
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        searchBar.setBackground(SECONDARY);
        searchBar.add(new JLabel("Roll Number:"));
        tfSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchBar.add(tfSearch);
        styleButton(btnSearch, PRIMARY);
        styleButton(btnListAll, new Color(0, 150, 136));
        searchBar.add(btnSearch);
        searchBar.add(btnListAll);
        outer.add(searchBar, BorderLayout.NORTH);

        // Table
        resultTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultTable.setRowHeight(26);
        resultTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultTable.getTableHeader().setBackground(PRIMARY);
        resultTable.getTableHeader().setForeground(Color.WHITE);
        resultTable.setSelectionBackground(new Color(187, 222, 251));
        JScrollPane scroll = new JScrollPane(resultTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200)));
        outer.add(scroll, BorderLayout.CENTER);

        return outer;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // -----------------------------------------------------------------------
    // Public API used by Controller
    // -----------------------------------------------------------------------

    public String getNameInput()       { return tfName.getText().trim(); }
    public String getRollInput()       { return tfRoll.getText().trim(); }
    public String getBranchInput()     { return (String) cbBranch.getSelectedItem(); }
    public String getGenderInput() {
        if (rbFemale.isSelected()) return "Female";
        if (rbOther.isSelected())  return "Other";
        return "Male";
    }
    public String getGpaInput()        { return tfGpa.getText().trim(); }
    public String getServerHost()      { return tfHost.getText().trim(); }
    public String getSearchRoll()      { return tfSearch.getText().trim(); }
    public int    getServerPort() {
        try { return Integer.parseInt(tfPort.getText().trim()); }
        catch (NumberFormatException e) { return 6000; }
    }

    public void clearRegisterForm() {
        tfName.setText(""); tfRoll.setText("");
        tfGpa.setText(""); cbBranch.setSelectedIndex(0);
        rbMale.setSelected(true);
    }

    public void setStatus(String msg, boolean success) {
        statusBar.setText(" " + msg);
        statusBar.setForeground(success ? SUCCESS : ERROR_COL);
    }

    public void setConnectionStatus(boolean connected) {
        if (connected) {
            lblConnStatus.setText("● Connected");
            lblConnStatus.setForeground(SUCCESS);
            btnConnect.setText("Disconnect");
        } else {
            lblConnStatus.setText("● Disconnected");
            lblConnStatus.setForeground(ERROR_COL);
            btnConnect.setText("Connect");
        }
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addTableRow(Object[] row) {
        tableModel.addRow(row);
    }

    // Button listener registration
    public void addConnectListener(ActionListener l)   { btnConnect.addActionListener(l); }
    public void addRegisterListener(ActionListener l)  { btnRegister.addActionListener(l); }
    public void addClearListener(ActionListener l)     { btnClear.addActionListener(l); }
    public void addSearchListener(ActionListener l)    { btnSearch.addActionListener(l); }
    public void addListAllListener(ActionListener l)   { btnListAll.addActionListener(l); }
}
