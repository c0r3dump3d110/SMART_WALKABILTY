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
            Set<Vertex> set=Gr.get(v);
            if(set!=null)
            for (Vertex ver: set )
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


    public boolean addPoint(GeoPoint p, GeoPoint end , double Radious){

        double Dist;
        Map<GeoPoint, Set<Vertex>> g = this.getGr();
        double MinDist = Double.MAX_VALUE;
        GeoPoint resPoint = null;
        for (GeoPoint entry: new HashSet<>(g.keySet())){
            //Dist = p.distanceToInMeters(entry.getKey());
            Dist = this.getDistance(p, entry);
            if(Dist < MinDist){
                if(end != null && Dist < Radious/3  )
                {
                    if( this.isConnected(entry, end)){
                        MinDist = Dist;
                        resPoint = entry;
                    }else g.remove(entry);
                } else if(end == null){
                    MinDist = Dist;
                    resPoint = entry;
                }
            }
        }
        if(MinDist==Double.MAX_VALUE)return false;
        Vertex v = new Vertex();
        if(end != null){
            v.setDepart(p);
            v.setArrive(resPoint);
        } else {
            v.setDepart(resPoint);
            v.setArrive(p);
        }
        v.setDistance(MinDist);
        this.Add_Route(v);
        return true;
    }
}
