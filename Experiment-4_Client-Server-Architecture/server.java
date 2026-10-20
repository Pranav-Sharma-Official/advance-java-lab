import java.io.*;
import java.net.*;


public class server { 

    public static void main(String[] args) {


        try{

            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println("Server Started....");
            System.out.println("Waiting For Client.....");


            Socket socket = serverSocket.accept();

           BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

                  PrintWriter output = new PrintWriter(
                socket.getOutputStream(), true);


                String message = input.readLine();

                 System.out.println("Client: " + message);


                 output.println("Hello Client");


                  socket.close();
                  serverSocket.close();

        }

        catch(IOException e){
            System.out.println(e);
        }
        
    }

}

