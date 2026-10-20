import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Gaurav extends JFrame implements ActionListener {

    JLabel lblName, lblRoll, lblBranch, lblGender;
    JTextField txtName, txtRoll, txtBranch;
    JRadioButton rbMale, rbFemale;
    ButtonGroup genderGroup;
    JCheckBox chkTerms;
    JButton btnSubmit, btnReset;

    Gaurav() {

        setTitle("Student Registration Form");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Name
        lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 30, 180, 25);
        add(txtName);

        // Roll No
        lblRoll = new JLabel("Roll No:");
        lblRoll.setBounds(30, 70, 100, 25);
        add(lblRoll);

        txtRoll = new JTextField();
        txtRoll.setBounds(150, 70, 180, 25);
        add(txtRoll);

        // Branch
        lblBranch = new JLabel("Branch:");
        lblBranch.setBounds(30, 110, 100, 25);
        add(lblBranch);

        txtBranch = new JTextField();
        txtBranch.setBounds(150, 110, 180, 25);
        add(txtBranch);

        // Gender
        lblGender = new JLabel("Gender:");
        lblGender.setBounds(30, 150, 100, 25);
        add(lblGender);

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(150, 150, 70, 25);
        add(rbMale);

        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(230, 150, 90, 25);
        add(rbFemale);

        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        // Terms
        chkTerms = new JCheckBox("I accept Terms & Conditions");
        chkTerms.setBounds(80, 200, 220, 25);
        add(chkTerms);

        // Submit
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(80, 250, 100, 30);
        btnSubmit.addActionListener(this);
        add(btnSubmit);

        // Reset
        btnReset = new JButton("Reset");
        btnReset.setBounds(220, 250, 100, 30);
        btnReset.addActionListener(this);
        add(btnReset);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnReset) {

            txtName.setText("");
            txtRoll.setText("");
            txtBranch.setText("");

            genderGroup.clearSelection();
            chkTerms.setSelected(false);

            return;
        }

        if (e.getSource() == btnSubmit) {

            String name = txtName.getText().trim();
            String roll = txtRoll.getText().trim();
            String branch = txtBranch.getText().trim();

            String gender = "";

            if (rbMale.isSelected()) {
                gender = "Male";
            }
            else if (rbFemale.isSelected()) {
                gender = "Female";
            }

            if (name.isEmpty() || roll.isEmpty() || branch.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
                );

                return;
            }

            if (gender.equals("")) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please select gender."
                );

                return;
            }

            if (!chkTerms.isSelected()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please accept Terms & Conditions."
                );

                return;
            }

            insertStudent(name, roll, branch, gender);
        }
    }

    // Database method
    public void insertStudent(
            String name,
            String roll,
            String branch,
            String gender){

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/college",
            "root",
            "java@lab"
        );

        System.out.println("Connection created");

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO student(name, roll_number, branch, gender) VALUES(?,?,?,?)"
        );

        ps.setString(1, name);
        ps.setString(2, roll);
        ps.setString(3, branch);
        ps.setString(4, gender);

        ps.executeUpdate();

        System.out.println("Record Inserted");

        JOptionPane.showMessageDialog(
            this,
            "Registration Successful!\n\n"
            + "Name : " + name
            + "\nRoll No : " + roll
            + "\nBranch : " + branch
            + "\nGender : " + gender
        );

        ps.close();
        con.close();
    }

    public static void main(String[] args) {
        new Gaurav();
    }
}