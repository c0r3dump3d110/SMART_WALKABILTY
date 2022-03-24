package com.fstm.coredumped.smartwalkabilty.core.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        try{

            server = new ServerSocket(1337);

            while (true){
                System.out.println("Server is Lestning "+ InetAddress.getLocalHost().getHostAddress());


                Socket client = server.accept();
                System.out.println("New client connected: "+
                        client.getInetAddress().getHostAddress());


                ClientHandler clientHandler = new ClientHandler(client);

                // launching a new client handler
                new Thread(clientHandler).start();
                System.gc();
            }

        }catch (Exception e){

        }finally {
            if(server != null){
                try {
                    server.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
