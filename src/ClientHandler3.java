import java.io.*;
import java.net.*;

public class ClientHandler3 implements Runnable {
    private ChatServer3 server;
    Socket socket;
    String addr;
    String name = null;
    PrintWriter output;
    public ClientHandler3(Socket socket, ChatServer3 server) {
        this.server = server;
        this.socket = socket;
        this.addr = socket.getInetAddress().toString();
        try{
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch(IOException e){}
    }
    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            output.println("Velkommen "+addr+"\r\nIndtast dit rigtige navn:");
            name = input.readLine();
            if(name.startsWith("�")) name = name.substring(20);
            System.out.println(name+" joined");
            server.message(this, name+" joined");
            String message;
            while ((message = input.readLine()) != null) {
                System.out.println(name+": " + message);
                server.message(this, name+": " + message);
            }
            
        } catch (IOException e) {}
        System.out.println(name+" disconnected");
        server.message(this, name+" disconnected");
        server.remove(this);
    }
}