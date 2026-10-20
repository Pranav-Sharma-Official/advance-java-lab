import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GradeService — RMI Remote Interface (Experiment 5).
 * Provides grade calculation services over RMI.
 */
public interface GradeService extends Remote {

    /**
     * Converts a 10-point GPA to a letter grade.
     * @param gpa GPA on a 0.0–10.0 scale
     * @return Letter grade: "O", "A+", "A", "B+", "B", "C", "F"
     */
    String calculateGrade(double gpa) throws RemoteException;

    /**
     * Computes the Cumulative GPA from an array of semester GPAs.
     * @param semesterGPAs array of per-semester GPA values
     * @return average CGPA rounded to 2 decimal places
     */
    double calculateCGPA(double[] semesterGPAs) throws RemoteException;

    /**
     * Returns a human-readable performance remark for the given GPA.
     */
    String getPerformanceRemark(double gpa) throws RemoteException;
}
