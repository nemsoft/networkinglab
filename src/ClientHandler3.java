import java.io.*;
import java.net.*;

public class ClientHandler3 implements Runnable {
    private Socket socket;

    public ClientHandler3(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            String message;

            while ((message = input.readLine()) != null) {
                System.out.println("Client says: " + message);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}