import java.io.*;
import java.net.*;

public class ClientHandler3 implements Runnable{
    final private ChatServer3 server;
    ClientHandler3 replier;
    Socket socket;
    String addr;
    String name = null;
    PrintWriter output;
    public ClientHandler3(Socket socket, ChatServer3 server) throws IOException{
        this.server = server;
        this.socket = socket;
        this.addr = socket.getInetAddress().toString();
        this.output = new PrintWriter(socket.getOutputStream(), true);
    }
    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            output.println("Welcome "+addr);
            String newName = " ";
            while(newName==null||newName.contains(" ")||newName.isBlank()) {
                if(newName!=null) output.printf("Enter username without spaces: ");
                newName = input.readLine();
                if(newName.startsWith("�")) newName = newName.substring(21);
                if(server.nameTaken(newName)) {
                    newName = null;
                    output.printf("Username taken, try again: ");
                }
            }
            name = newName;
            output.println("Welcome "+name);
            server.announce(this, name+" joined");
            String message;
            while ((message = input.readLine()) != null) {
                String[] messageComp = message.split("\\s+");
                if(message.equals("list")) {
                    output.println(server.list());
                } else if(messageComp[0].equals("msg")) {
                    server.message(this, messageComp[1], message.split("\\s+",3)[2]);
                } else if (messageComp[0].equals("reply")) {
                    server.reply(this, message.split("\\s+",2)[1]);
                } else {
                    server.announce(this, name+": "+message);
                }
            }
        } catch (IOException e) {}
        server.announce(this, name+" disconnected");
        server.remove(this);
    }
}