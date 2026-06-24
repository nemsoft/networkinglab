import java.io.*;
import java.net.*;

public class ClientHandler3 implements Runnable{
    private ChatServer3 server;
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
            while(newName==null||newName.contains(" ")||newName.isBlank()){
                if(newName!=null) output.printf("Enter username without spaces: ");
                newName = input.readLine();
                if(newName.startsWith("�")) newName = newName.substring(21);
                if(server.nameTaken(newName)){
                    newName = null;
                    output.printf("Username taken, try again: ");
                }
            }
            name = newName;
            output.println("Welcome "+name);
            System.out.println(name+" joined");
            server.announce(this, name+" joined");
            String message;
            while ((message = input.readLine()) != null){
                if(message.equals("list")){
                    output.println(server.list());
                } if(message.startsWith("msg")){
                    // server.message(message.replaceAll("",""), message.replaceAll("", ""));
                } else {
                    System.out.println(name+": " + message);
                    server.announce(this, name+": " + message);
                }
            }
        } catch (IOException e){}
        System.out.println(name+" disconnected");
        server.announce(this, name+" disconnected");
        server.remove(this);
    }
}