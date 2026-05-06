//Kan håndtere flere klienter ved at oprette en ny tråd for hver klient, der opretter forbindelse til serveren. 
//Hver tråd vil køre en instans af ClientHandler3, som håndterer kommunikationen med den specifikke klient.
//Brug SimpleClient2 til at teste serveren ved at oprette flere klienter, der sender beskeder til serveren

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Chat server started on port "+serverSocket.getLocalPort());
        ArrayList<ClientHandler3> handlers = new ArrayList<>();
        Thread commands = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()){
                switch (scanner.nextLine()){
                    // case "kick" -> {
                    //     for(ClientHandler3 h:handlers){}
                    // }
                    case "list" -> {
                        for(ClientHandler3 h:handlers){
                            if(h.name==null){
                                System.out.println(h.addr+" unnamed");
                            } else {
                                System.out.println(h.addr+" - "+h.name);
                            }
                        }
                    }

                }
            }
        });
        commands.start();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getInetAddress()+" connected");
            ClientHandler3 handler = new ClientHandler3(clientSocket);
            handlers.add(handler);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }
}