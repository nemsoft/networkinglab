//Kan håndtere flere klienter ved at oprette en ny tråd for hver klient, der opretter forbindelse til serveren. 
//Hver tråd vil køre en instans af ClientHandler3, som håndterer kommunikationen med den specifikke klient.
//Brug SimpleClient2 til at teste serveren ved at oprette flere klienter, der sender beskeder til serveren

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer3{
    static ArrayList<ClientHandler3> clients;
    public void announce(ClientHandler3 client, String message){
        System.out.println(message);
        for(ClientHandler3 c:clients){
            if(c.name==null) break;
            if(c!=client&&c.name!=null){
                try {
                    PrintWriter output = new PrintWriter(c.socket.getOutputStream(), true);
                    output.println(message);
                } catch (IOException e) {}
            }
        }
    }
    public boolean message(String sender, String receiver, String message){
        for(ClientHandler3 c:clients){
            if(c.name==null) break;
            if(c.name.equals(receiver)){
                try {
                    PrintWriter output = new PrintWriter(c.socket.getOutputStream(), true);
                    String messageComp = sender+" -> "+receiver+": "+message;
                    output.println(messageComp);
                    System.out.println(messageComp);
                    return true;
                } catch (IOException e) {
                    System.out.println("Kan ikke sende besked til "+c.name);
                } 
            }
        }
        return false;
    }
    public String list(){
        String list = "";
        int unnamed = 0;
        for(ClientHandler3 c:clients){
            if(c.name==null){
                unnamed+=1;
            } else {
                list = list.concat("- "+c.name+"\r\n");
            }
        }
        if(unnamed>0){
            list = list.concat("unnamed x"+unnamed);
        }
        return list.substring(0, list.length());
    }
    public boolean nameTaken(String name){
        for(ClientHandler3 c:clients){
            if(c.name==null) break;
            if(c.name.equals(name)) return true;
        }
        return false;
    }
    public void remove(ClientHandler3 client){
        clients.remove(client);
    }
    public static void main(String[] args) throws IOException{
        ChatServer3 server = new ChatServer3();
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Chat server started on port "+serverSocket.getLocalPort());
        clients = new ArrayList<>();
        Thread commands = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()){
                String input = scanner.nextLine();
                for(ClientHandler3 c:clients){
                    if(input.startsWith("list")){
                        if(c.name==null){
                            System.out.println(c.addr+" unnamed");
                        } else {
                            System.out.println(c.addr+" - "+c.name);
                        }
                    }
                    if(input.startsWith("kick")){
                        if(c.name.equals(input.substring(5))||c.addr.equals(input.substring(5))){
                            try {
                                c.socket.close();
                                clients.remove(c);
                            } catch (IOException e) {
                            }
                            clients.remove(c);
                        }
                    }
                }
            }
        });
        commands.start();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getInetAddress()+" connected");
            ClientHandler3 handler = new ClientHandler3(clientSocket, server);
            clients.add(handler);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }
}