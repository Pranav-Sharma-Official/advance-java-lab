import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * GradeServiceImpl — RMI Remote Object Implementation (Experiment 5).
 * Extends UnicastRemoteObject to be exported as an RMI service.
 */
public class GradeServiceImpl extends UnicastRemoteObject implements GradeService {

    public GradeServiceImpl() throws RemoteException {
        super();
    }

    // -----------------------------------------------------------------------
    // Grade Calculation (10-point scale, common in Indian universities)
    // -----------------------------------------------------------------------

    /**
     * Converts GPA to letter grade:
     *   >= 9.0  → O  (Outstanding)
     *   >= 8.5  → A+ (Excellent)
     *   >= 8.0  → A  (Very Good)
     *   >= 7.0  → B+ (Good)
     *   >= 6.0  → B  (Above Average)
     *   >= 5.0  → C  (Average)
     *   < 5.0   → F  (Fail)
     */
    @Override
    public String calculateGrade(double gpa) throws RemoteException {
        if (gpa >= 9.0) return "O";
        if (gpa >= 8.5) return "A+";
        if (gpa >= 8.0) return "A";
        if (gpa >= 7.0) return "B+";
        if (gpa >= 6.0) return "B";
        if (gpa >= 5.0) return "C";
        return "F";
    }

    // -----------------------------------------------------------------------
    // CGPA Calculation
    // -----------------------------------------------------------------------

    @Override
    public double calculateCGPA(double[] semesterGPAs) throws RemoteException {
        if (semesterGPAs == null || semesterGPAs.length == 0) return 0.0;
        double sum = 0;
        for (double g : semesterGPAs) sum += g;
        double avg = sum / semesterGPAs.length;
        // Round to 2 decimal places
        return BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    // -----------------------------------------------------------------------
    // Performance Remark
    // -----------------------------------------------------------------------

    @Override
    public String getPerformanceRemark(double gpa) throws RemoteException {
        if (gpa >= 9.0) return "Outstanding performance — distinction!";
        if (gpa >= 8.5) return "Excellent performance — keep it up!";
        if (gpa >= 8.0) return "Very good — minor improvements needed.";
        if (gpa >= 7.0) return "Good performance — work harder for excellence.";
        if (gpa >= 6.0) return "Average — significant improvement required.";
        if (gpa >= 5.0) return "Passing — you need to work a lot harder.";
        return "Failed — please seek academic support immediately.";
    }
}
