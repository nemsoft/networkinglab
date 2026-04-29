import java.io.*;
import java.net.*;

public class SimpleServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Server waiting...");

        Socket socket = serverSocket.accept();

        BufferedReader input = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );

        String message;
        while ((message = input.readLine()) != null && !message.equals("close")) {
            System.out.println("Client says: " + message);
        }

        socket.close();

        serverSocket.close();
        System.out.println("Server closed.");
    }
}