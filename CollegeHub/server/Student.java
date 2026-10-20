/**
 * Student POJO — shared by server package.
 * (Mirrors desktop/model/Student.java for standalone server compilation.)
 */
public class Student {
    private int id;
    private String name;
    private String rollNumber;
    private String branch;
    private String gender;
    private double gpa;

    public Student() {}

    public Student(String name, String rollNumber, String branch, String gender, double gpa) {
        this.name = name; this.rollNumber = rollNumber; this.branch = branch;
        this.gender = gender; this.gpa = gpa;
    }

    public Student(int id, String name, String rollNumber, String branch, String gender, double gpa) {
        this.id = id; this.name = name; this.rollNumber = rollNumber;
        this.branch = branch; this.gender = gender; this.gpa = gpa;
    }

    public int    getId()          { return id; }
    public String getName()        { return name; }
    public String getRollNumber()  { return rollNumber; }
    public String getBranch()      { return branch; }
    public String getGender()      { return gender; }
    public double getGpa()         { return gpa; }

    public void setId(int id)               { this.id = id; }
    public void setName(String name)        { this.name = name; }
    public void setRollNumber(String r)     { this.rollNumber = r; }
    public void setBranch(String b)         { this.branch = b; }
    public void setGender(String g)         { this.gender = g; }
    public void setGpa(double gpa)          { this.gpa = gpa; }

    /** Serialise to pipe-delimited string for socket protocol. */
    public String toProtocolString() {
        return id + "|" + name + "|" + rollNumber + "|" + branch + "|" + gender + "|" + gpa;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', roll='" + rollNumber
                + "', branch='" + branch + "', gpa=" + gpa + "}";
    }
}
