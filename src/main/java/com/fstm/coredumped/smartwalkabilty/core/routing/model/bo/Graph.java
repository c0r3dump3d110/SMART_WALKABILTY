package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.util.*;

public class Graph
{
    Map<GeoPoint, Set<Vertex>> Gr=new HashMap<GeoPoint, Set<Vertex>>();

    public Map<GeoPoint, Set<Vertex>> getGr() {
        return Gr;
    }
    public void Add_Route(Vertex vertex) {

        if (!Gr.containsKey(vertex.getDepart())) {
            Gr.put(vertex.getDepart(), new HashSet<Vertex>());
        }
        if (!Gr.containsKey(vertex.getArrive())) {
            Gr.put(vertex.getArrive(), new HashSet<Vertex>());
        }
        Gr.get(vertex.getDepart()).add(vertex);
    }
    public Vertex findVertex(GeoPoint depart,GeoPoint dest)
    {
        Set<Vertex> s= Gr.get(depart);
        return s.stream().filter(e -> e.getArrive().equals(dest)).findFirst().get();
    }
    public boolean contains(GeoPoint s){
        return Gr.containsKey(s);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "Gr=" + Gr +
                '}';
    }

    public double getDistance(GeoPoint p1, GeoPoint p2){
        return Math.sqrt(
                Math.pow((p1.getLongtitude() - p2.getLongtitude()), 2)+
                        Math.pow((p1.getLaltittude() - p2.getLaltittude()), 2)
        );
    }
    public boolean isConnected(GeoPoint start,GeoPoint end)
    {
        Set<GeoPoint> visited =new HashSet<>();
        Queue<GeoPoint> Q=new LinkedList<>();
        visited.add(start);
        Q.offer(start);
        while (!Q.isEmpty())
        {
            GeoPoint v=Q.remove();
            if(v.equals(end))return true;
            for (Vertex ver: Gr.get(v))
            {
                GeoPoint w=ver.getArrive();
                if(!visited.contains(w)){
                    visited.add(w);
                    Q.offer(w);
                }
            }
        }
        return false;
    }
}
