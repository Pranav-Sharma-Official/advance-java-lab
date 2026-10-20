import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * GradeClient — RMI Client demo (Experiment 5).
 * Run after GradeServer to test the RMI service.
 */
public class GradeClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GradeService service = (GradeService) registry.lookup("GradeService");

            // Test 1: calculateGrade
            double[] gpas = {9.5, 8.7, 8.2, 7.4, 6.3, 5.1, 3.8};
            System.out.println("=== Grade Calculator ===");
            for (double gpa : gpas) {
                System.out.printf("GPA %.1f → Grade: %-3s | %s%n",
                        gpa, service.calculateGrade(gpa), service.getPerformanceRemark(gpa));
            }

            // Test 2: calculateCGPA
            double[] sems = {8.5, 9.0, 8.7, 7.9, 8.2, 9.1, 8.8, 8.6};
            double cgpa = service.calculateCGPA(sems);
            System.out.printf("%n=== CGPA ===%nSemesters: ");
            for (double s : sems) System.out.printf("%.1f ", s);
            System.out.printf("%nCGPA: %.2f | Grade: %s%n", cgpa, service.calculateGrade(cgpa));

        } catch (Exception e) {
            System.err.println("[RMI Client] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
