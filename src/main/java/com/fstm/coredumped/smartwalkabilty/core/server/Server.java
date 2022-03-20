package com.fstm.coredumped.smartwalkabilty.core.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public boolean start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        Socket s = serverSocket.accept();
        DataInputStream dos = new DataInputStream(s.getInputStream());


        return false;
    }
}
