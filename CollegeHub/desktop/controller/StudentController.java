package controller;

import model.Student;
import model.StudentModel;
import view.StudentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

/**
 * StudentController — MVC Controller (Experiment 2).
 * Wires the View's events to Model operations.
 */
public class StudentController {

    private final StudentModel model;
    private final StudentView  view;

    public StudentController(StudentModel model, StudentView view) {
        this.model = model;
        this.view  = view;
        attachListeners();
    }

    private void attachListeners() {
        view.addConnectListener(this::onConnect);
        view.addRegisterListener(this::onRegister);
        view.addClearListener(e -> view.clearRegisterForm());
        view.addSearchListener(this::onSearch);
        view.addListAllListener(this::onListAll);
    }

    // -----------------------------------------------------------------------
    // Connect / Disconnect
    // -----------------------------------------------------------------------

    private void onConnect(ActionEvent e) {
        if (model.isConnected()) {
            model.close();
            view.setConnectionStatus(false);
            view.setStatus("Disconnected from server.", false);
        } else {
            String host = view.getServerHost();
            int    port = view.getServerPort();
            try {
                model.connect(host, port);
                view.setConnectionStatus(true);
                view.setStatus("Connected to " + host + ":" + port, true);
            } catch (IOException ex) {
                view.setStatus("Connection failed: " + ex.getMessage(), false);
                view.showMessage("Cannot connect to StudentServer.\n" +
                        "Make sure it is running on " + host + ":" + port + "\n\nError: " + ex.getMessage(),
                        "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // -----------------------------------------------------------------------
    // Register Student
    // -----------------------------------------------------------------------

    private void onRegister(ActionEvent e) {
        if (!checkConnected()) return;

        String name   = view.getNameInput();
        String roll   = view.getRollInput();
        String branch = view.getBranchInput();
        String gender = view.getGenderInput();
        String gpaStr = view.getGpaInput();

        // Validation
        if (name.isEmpty() || roll.isEmpty() || gpaStr.isEmpty()) {
            view.setStatus("All fields are required.", false);
            view.showMessage("Please fill in all required fields.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(gpaStr);
            if (gpa < 0 || gpa > 10) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            view.setStatus("GPA must be a number between 0.0 and 10.0.", false);
            return;
        }

        Student student = new Student(name, roll, branch, gender, gpa);

        // Run in background thread to avoid freezing the UI
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override protected String doInBackground() throws Exception {
                return model.addStudent(student);
            }
            @Override protected void done() {
                try {
                    String response = get();
                    if (response != null && response.startsWith("OK")) {
                        view.setStatus("Student registered successfully: " + name, true);
                        view.showMessage("Student registered successfully!\n" + student,
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        view.clearRegisterForm();
                    } else {
                        view.setStatus("Registration failed: " + response, false);
                        view.showMessage("Registration failed.\nServer: " + response,
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    view.setStatus("Error: " + ex.getMessage(), false);
                }
            }
        };
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // Search Student
    // -----------------------------------------------------------------------

    private void onSearch(ActionEvent e) {
        if (!checkConnected()) return;

        String roll = view.getSearchRoll();
        if (roll.isEmpty()) {
            view.setStatus("Enter a roll number to search.", false);
            return;
        }

        SwingWorker<Student, Void> worker = new SwingWorker<>() {
            @Override protected Student doInBackground() throws Exception {
                return model.searchStudent(roll);
            }
            @Override protected void done() {
                try {
                    Student s = get();
                    view.clearTable();
                    if (s != null) {
                        view.addTableRow(new Object[]{
                                s.getId(), s.getName(), s.getRollNumber(),
                                s.getBranch(), s.getGender(), s.getGpa()});
                        view.setStatus("Found: " + s.getName(), true);
                    } else {
                        view.setStatus("No student found with roll: " + roll, false);
                    }
                } catch (Exception ex) {
                    view.setStatus("Error during search: " + ex.getMessage(), false);
                }
            }
        };
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // List All Students
    // -----------------------------------------------------------------------

    private void onListAll(ActionEvent e) {
        if (!checkConnected()) return;

        SwingWorker<List<Student>, Void> worker = new SwingWorker<>() {
            @Override protected List<Student> doInBackground() throws Exception {
                return model.getAllStudents();
            }
            @Override protected void done() {
                try {
                    List<Student> students = get();
                    view.clearTable();
                    for (Student s : students) {
                        view.addTableRow(new Object[]{
                                s.getId(), s.getName(), s.getRollNumber(),
                                s.getBranch(), s.getGender(), s.getGpa()});
                    }
                    view.setStatus("Loaded " + students.size() + " student(s).", true);
                } catch (Exception ex) {
                    view.setStatus("Error fetching list: " + ex.getMessage(), false);
                }
            }
        };
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // Utility
    // -----------------------------------------------------------------------

    private boolean checkConnected() {
        if (!model.isConnected()) {
            view.setStatus("Not connected. Please connect to the server first.", false);
            view.showMessage("Please connect to the StudentServer first (bottom panel).",
                    "Not Connected", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
