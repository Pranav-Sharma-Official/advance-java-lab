import controller.StudentController;
import model.StudentModel;
import view.StudentView;

import javax.swing.*;

/**
 * Main entry point for CollegeHub Desktop Client.
 * Demonstrates: Swing GUI (Exp 1), MVC Pattern (Exp 2), Socket Client (Exp 4).
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentModel model = new StudentModel();
            StudentView  view  = new StudentView();
            new StudentController(model, view);

            // Graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(model::close));
        });
    }
}
