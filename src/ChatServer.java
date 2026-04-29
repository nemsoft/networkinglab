import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Server started. Waiting for client...");

        Socket clientSocket = serverSocket.accept();

        System.out.println("Client connected!");

        clientSocket.close();
        serverSocket.close();
    }
}

//Test with (in CMD prompt): 
// curl telnet://localhost:5000
// ... or curl telnet://<your_ip_address>:5000