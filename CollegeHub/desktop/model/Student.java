package model;

/**
 * Student POJO - Experiment 2 (MVC Model)
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
        this.name = name;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.gender = gender;
        this.gpa = gpa;
    }

    public Student(int id, String name, String rollNumber, String branch, String gender, double gpa) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.gender = gender;
        this.gpa = gpa;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getBranch() { return branch; }
    public String getGender() { return gender; }
    public double getGpa() { return gpa; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setGender(String gender) { this.gender = gender; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    /**
     * Serialise to pipe-delimited string for socket protocol.
     */
    public String toProtocolString() {
        return id + "|" + name + "|" + rollNumber + "|" + branch + "|" + gender + "|" + gpa;
    }

    /**
     * Deserialise from pipe-delimited string.
     */
    public static Student fromProtocolString(String s) {
        String[] parts = s.split("\\|", -1);
        if (parts.length < 6) return null;
        try {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String roll = parts[2].trim();
            String branch = parts[3].trim();
            String gender = parts[4].trim();
            double gpa = Double.parseDouble(parts[5].trim());
            return new Student(id, name, roll, branch, gender, gpa);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', roll='" + rollNumber
                + "', branch='" + branch + "', gender='" + gender + "', gpa=" + gpa + "}";
    }
}
