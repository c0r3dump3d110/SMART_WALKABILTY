package com.fstm.coredumped.smartwalkabilty.routing.Model.bo;

import com.fstm.coredumped.smartwalkabilty.common.Model.bo.GeoPoint;

import java.util.*;

public class Graph
{
    Map<GeoPoint, Set<Vertex>> Gr=new HashMap<GeoPoint, Set<Vertex>>();

    public Map<GeoPoint, Set<Vertex>> getGr() {
        return Gr;
    }
    public void Add_Route(Vertex vertex){
        if(!Gr.containsKey(vertex.getDepart())){
            Gr.put(vertex.getDepart(), new HashSet<Vertex>());
        }
        if(!Gr.containsKey(vertex.getArrive())){
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
}
