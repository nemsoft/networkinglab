import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleClient2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.4.126", 5000);
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Connected. Type messages:");

        while (true) {
            String message = keyboard.nextLine();

            if (message.equalsIgnoreCase("/quit")) {
                break;
            }

            output.println(message);
        }

        socket.close();
        keyboard.close();
    }
}