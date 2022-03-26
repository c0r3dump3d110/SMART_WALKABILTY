package com.fstm.coredumped.smartwalkabilty.core.routing.model.service;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Graph;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Vertex;

import java.util.Map;
import java.util.Set;

public class GraphOperator {
    Graph graph;
    public GraphOperator(Graph graph){
        this.graph = graph;
    }
    public GraphOperator(){

    }

    public boolean addPoint(GeoPoint p, GeoPoint end , double Radious){

        double Dist;
        Map<GeoPoint, Set<Vertex>> g = this.graph.getGr();
        double MinDist = Double.MAX_VALUE;
        GeoPoint resPoint = null;
        for (Map.Entry<GeoPoint, Set<Vertex>> entry: g.entrySet()){
            //Dist = p.distanceToInMeters(entry.getKey());
            Dist = graph.getDistance(p, entry.getKey());
            if(Dist < MinDist){
                if(end != null && graph.isConnected(entry.getKey(), end)&& Dist < Radious/3 )
                {
                    MinDist = Dist;
                    resPoint = entry.getKey();
                } else if(end == null){
                    MinDist = Dist;
                    resPoint = entry.getKey();
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
        graph.Add_Route(v);
        return true;
    }
}
