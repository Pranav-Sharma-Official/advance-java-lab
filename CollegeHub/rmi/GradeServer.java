import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * GradeServer — RMI Registry / Service Registrar (Experiment 5).
 *
 * Usage:
 *   javac GradeService.java GradeServiceImpl.java GradeServer.java
 *   java GradeServer
 *
 * RMI URL:  rmi://localhost:1099/GradeService
 */
public class GradeServer {

    public static void main(String[] args) {
        try {
            // Create and export the remote object
            GradeServiceImpl impl = new GradeServiceImpl();

            // Start the RMI registry on default port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the remote object under the name "GradeService"
            registry.rebind("GradeService", impl);

            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║    CollegeHub — RMI Grade Server         ║");
            System.out.println("║  Bound at rmi://localhost:1099/GradeService ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println("[RMI] GradeService is ready.");

        } catch (Exception e) {
            System.err.println("[RMI] Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
