import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * StudentServer — TCP Socket Server (Experiment 4: Client-Server Architecture).
 * Uses JDBC (Experiment 3) internally via DatabaseHelper.
 *
 * Protocol:
 *   ADD|name|roll|branch|gender|gpa   → OK  or  ERROR|message
 *   SEARCH|rollNumber                 → id|name|roll|branch|gender|gpa  or  NOT_FOUND
 *   LIST                              → multiple lines, each "id|name|roll|...", then "END"
 *   QUIT                              → closes the connection
 */
public class StudentServer {

    private static final int PORT            = 6000;
    private static final int THREAD_POOL_SIZE = 20;

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        DatabaseHelper db = new DatabaseHelper();

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║      CollegeHub — Student Server         ║");
        System.out.println("║   Listening on port " + PORT + "               ║");
        System.out.println("╚══════════════════════════════════════════╝");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] Ready. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Server] Client connected: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket, db));
            }
        }
    }

    // -----------------------------------------------------------------------
    // ClientHandler — one per connected client
    // -----------------------------------------------------------------------

    private static class ClientHandler implements Runnable {

        private final Socket socket;
        private final DatabaseHelper db;

        ClientHandler(Socket socket, DatabaseHelper db) {
            this.socket = socket;
            this.db     = db;
        }

        @Override
        public void run() {
            String clientAddr = socket.getInetAddress().toString();
            try (BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter   out  = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    System.out.println("[Server] [" + clientAddr + "] << " + line);

                    if (line.equalsIgnoreCase("QUIT")) {
                        System.out.println("[Server] Client disconnected: " + clientAddr);
                        break;
                    }

                    String response = handleCommand(line);

                    // For LIST, response is already multiline with END terminator
                    out.print(response);
                    out.flush();
                    System.out.println("[Server] [" + clientAddr + "] >> " +
                            (response.length() > 80 ? response.substring(0, 80) + "..." : response.trim()));
                }
            } catch (IOException e) {
                System.err.println("[Server] Client error (" + clientAddr + "): " + e.getMessage());
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
            }
        }

        private String handleCommand(String line) {
            String[] parts = line.split("\\|", -1);
            String cmd = parts[0].toUpperCase();

            switch (cmd) {
                case "ADD":
                    return handleAdd(parts);
                case "SEARCH":
                    return handleSearch(parts);
                case "LIST":
                    return handleList();
                default:
                    return "ERROR|Unknown command: " + cmd + "\n";
            }
        }

        // ADD|name|roll|branch|gender|gpa
        private String handleAdd(String[] parts) {
            if (parts.length < 6) return "ERROR|Invalid ADD format\n";
            try {
                String name   = parts[1].trim();
                String roll   = parts[2].trim();
                String branch = parts[3].trim();
                String gender = parts[4].trim();
                double gpa    = Double.parseDouble(parts[5].trim());

                Student s = new Student(name, roll, branch, gender, gpa);
                boolean ok = db.insertStudent(s);
                return ok ? "OK|Student added: " + name + "\n"
                          : "ERROR|Duplicate roll number or DB error\n";
            } catch (NumberFormatException e) {
                return "ERROR|Invalid GPA value\n";
            }
        }

        // SEARCH|rollNumber
        private String handleSearch(String[] parts) {
            if (parts.length < 2) return "NOT_FOUND\n";
            String roll = parts[1].trim();
            Student s = db.searchStudent(roll);
            return s != null ? s.toProtocolString() + "\n" : "NOT_FOUND\n";
        }

        // LIST → lines ending with END
        private String handleList() {
            List<Student> students = db.getAllStudents();
            StringBuilder sb = new StringBuilder();
            for (Student s : students) {
                sb.append(s.toProtocolString()).append("\n");
            }
            sb.append("END\n");
            return sb.toString();
        }
    }
}
