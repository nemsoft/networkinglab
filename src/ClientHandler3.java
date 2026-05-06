import java.io.*;
import java.net.*;

public class ClientHandler3 implements Runnable {
    private Socket socket;
    String addr;
    String name;
    public ClientHandler3(Socket socket) {
        this.socket = socket;
        this.addr = socket.getInetAddress().toString();
        this.name = null;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            try {
                output.writeBytes("Velkommen "+addr+"\nIndtast dit rigtige navn: ");
                name = input.readLine();
            } catch (IOException e) {
                System.out.println(e);
            }
            String message;

            while ((message = input.readLine()) != null) {
                System.out.println(name+" says: " + message);
            }
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}