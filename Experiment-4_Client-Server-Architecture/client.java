import java.io.*;
import java.net.*;

public class client {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 5000);

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);

            // Send message to server
            output.println("Hello Server");

            // Receive message from server
            String message = input.readLine();

            System.out.println("Server: " + message);

            socket.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}