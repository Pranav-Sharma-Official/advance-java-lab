package model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentModel — MVC Model (Experiment 2).
 * Communicates with StudentServer over TCP sockets (Experiment 4).
 */
public class StudentModel {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    // -----------------------------------------------------------------------
    // Connection Management
    // -----------------------------------------------------------------------

    /**
     * Opens a TCP socket connection to the StudentServer.
     */
    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    /**
     * Closes the socket connection gracefully.
     */
    public void close() {
        try {
            if (writer != null) {
                writer.println("QUIT");
                writer.flush();
            }
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
    }

    // -----------------------------------------------------------------------
    // Protocol Helpers
    // -----------------------------------------------------------------------

    private String sendCommand(String command) throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new IOException("Not connected to server.");
        }
        writer.println(command);
        writer.flush();
        return reader.readLine();
    }

    // -----------------------------------------------------------------------
    // CRUD Operations (forwarded to server via socket)
    // -----------------------------------------------------------------------

    /**
     * ADD|name|roll|branch|gender|gpa
     */
    public String addStudent(Student s) throws IOException {
        String command = "ADD|" + s.getName() + "|" + s.getRollNumber()
                + "|" + s.getBranch() + "|" + s.getGender() + "|" + s.getGpa();
        return sendCommand(command);
    }

    /**
     * SEARCH|rollNumber  →  "id|name|roll|branch|gender|gpa"  or  "NOT_FOUND"
     */
    public Student searchStudent(String rollNumber) throws IOException {
        String response = sendCommand("SEARCH|" + rollNumber);
        if (response == null || response.startsWith("NOT_FOUND") || response.startsWith("ERROR")) {
            return null;
        }
        return Student.fromProtocolString(response);
    }

    /**
     * LIST  →  multiple lines, each "id|name|roll|branch|gender|gpa", ending with "END"
     */
    public List<Student> getAllStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        writer.println("LIST");
        writer.flush();
        String line;
        while ((line = reader.readLine()) != null && !line.equals("END")) {
            if (!line.startsWith("ERROR")) {
                Student s = Student.fromProtocolString(line);
                if (s != null) students.add(s);
            }
        }
        return students;
    }

    public boolean isConnected() {
        return socket != null && !socket.isClosed() && socket.isConnected();
    }
}
