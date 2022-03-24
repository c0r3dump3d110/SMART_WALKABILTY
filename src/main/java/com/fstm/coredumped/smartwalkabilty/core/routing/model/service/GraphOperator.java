package com.fstm.coredumped.smartwalkabilty.core.routing.model.service;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Graph;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Vertex;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.util.Map;
import java.util.Set;

public class GraphOperator {
    Graph graph;
    public GraphOperator(Graph graph){
        this.graph = graph;
    }
    public GraphOperator(){

    }

    public boolean addPoint(GeoPoint p, int nature){
        // nature = 0 => source
        // nature = 1 => target
        Map<GeoPoint, Set<Vertex>> g = this.graph.getGr();
        double MinDist = Double.MAX_VALUE;
        GeoPoint resPoint = null;
        for (Map.Entry<GeoPoint, Set<Vertex>> entry: g.entrySet()){
            double Dist = Double.MAX_VALUE;
            if(p!= null && entry.getKey() != null){
                Dist = graph.getDistance(p, entry.getKey());
            }

            if(Dist < MinDist){
                MinDist = Dist;
                resPoint = entry.getKey();
            }
        }
        Vertex v = new Vertex();

        if(nature == 0){
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
