package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.util.List;
import java.util.Map;

public interface IAlgo
{
    List<Chemin> doAlgo(Graph graph, GeoPoint depart, GeoPoint arr);
    Chemin doAlgo(Graph graph, GeoPoint depart, GeoPoint arr,GeoPoint inter); //algo to pass by a specific point
    static Chemin Construct_Chemin(Map<GeoPoint,GeoPoint> interChemin,Graph G,GeoPoint arr,GeoPoint depart)
    {
        Chemin chemin =new Chemin();
        GeoPoint ge=arr;
        while (true)
        {
            GeoPoint another=interChemin.get(ge);
            if(another==null)break;
            chemin.Add_Route(G.findVertex(another,ge));
            ge=another;
        }
        if(ge.equals(depart)) return chemin;
        return new Chemin();
    }
}
