//abandoned, disregard
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatClient {
    String addr = null;
    public static void main(String[] args) throws IOException {
        // try {
            ServerSocket server = new ServerSocket(5000);
            Socket self = new Socket();
            System.out.println("Chat server started on port "+server.getLocalPort());
            BufferedReader input = new BufferedReader(new InputStreamReader(self.getInputStream()));
            DataOutputStream output = new DataOutputStream(self.getOutputStream());
            // try {
                output.writeBytes("Velkommen "+self.getInetAddress()+"\nIndtast dit rigtige navn: ");
                String name = input.readLine();
            // } catch (IOException e) {
            //     System.out.println(e);
            // }
            ArrayList<Socket> clientlist = new ArrayList<>();
            // new Thread(() -> {
                try {
                    while(true){
                        Socket newClient = server.accept();
                        clientlist.add(newClient);
                        System.out.println(newClient.getInetAddress()+" connected");
                        // String message;

                        // while ((message = input.readLine()) != null) {

                        //     System.out.println("("+name+"): " + message);
                        // }
                        // output.close();
                    }
                // } catch (IOException e) {
                // }
            // }).start();
            // self.close();
            // server.close();
        } catch (IOException e) {
            System.out.println("Client disconnected.\n"+e);
        }
    }
}