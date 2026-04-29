//Kan håndtere flere klienter ved at oprette en ny tråd for hver klient, der opretter forbindelse til serveren. 
//Hver tråd vil køre en instans af ClientHandler3, som håndterer kommunikationen med den specifikke klient.
//Brug SimmpleClient2 til at teste serveren ved at oprette flere klienter, der sender beskeder til serveren

import java.io.*;
import java.net.*;

public class ChatServer3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Chat server started on port 5000");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            System.out.println("New client connected");

            ClientHandler3 handler = new ClientHandler3(clientSocket);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }
}