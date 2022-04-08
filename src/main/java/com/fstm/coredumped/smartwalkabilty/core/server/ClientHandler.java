package com.fstm.coredumped.smartwalkabilty.core.server;

import com.fstm.coredumped.smartwalkabilty.common.controller.*;
import com.fstm.coredumped.smartwalkabilty.core.danger.model.bo.Declaration;
import com.fstm.coredumped.smartwalkabilty.core.danger.controller.DangerCtrl;
import com.fstm.coredumped.smartwalkabilty.core.geofencing.model.bo.Geofencing;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Routage;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private Routage routage;
    LocalDateTime d1;
    LocalDateTime d2;

    public ClientHandler(Socket s){
        this.clientSocket = s;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            Object req = ois.readObject();
            d1 = LocalDateTime.now();

            if(req instanceof DangerReq){
                System.out.println("["+d1+"] user requesting dangers ...");
                DangerReq req1 = (DangerReq) req;
                List<Declaration> declarations = new DangerCtrl().requestDangers(req1);
                oos.writeObject(declarations);
            }

            else if(req instanceof ShortestPathReq || req instanceof ShortestPathWithAnnounces){
                System.out.println("["+d1+"] starting routing ...");
                if(req instanceof ShortestPathReq)
                    routage = new Routage((ShortestPathReq) req);
                else
                    routage = new Routage((ShortestPathWithAnnounces) req);

                routage.calculerChemins();
                oos.writeObject(routage.getChemins());
            }

            else if(req instanceof RequestPerimetreAnnonce){
                System.out.println("["+d1+"] user request announces in Radius: starting geofencing ...");
                RequestPerimetreAnnonce req1 = (RequestPerimetreAnnonce) req;
                // handle the case where the user requested juste the available announces
                // in a given Radius
                List<Site> list = Geofencing.findAllAnnoncesByRadius(req1.getActualPoint(), req1.getPerimetre(),req1.getCategorieList());
                oos.writeObject(list);
            }

            else if(req instanceof DeclareDangerReq){
                System.out.println("["+d1+"] user request Declaring danger ...");
                DeclareDangerReq req1 = (DeclareDangerReq) req;
                new DangerCtrl().danger_ctrl(req1);
                oos.writeBoolean(true);
            }

            d2 = LocalDateTime.now();
            long d = Duration.between(d1, d2).toMillis();

            System.out.println(" ==== Work has finished ===");
            System.out.println("it tooks: "+ d + " millis");
            System.out.println("in seconds: "+ TimeUnit.MICROSECONDS.toSeconds(d) + " seconds");

            oos.flush();
        } catch (IOException e) {
            System.out.println("IO Problems: "+e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found "+ e.getMessage());
        }
    }
}
