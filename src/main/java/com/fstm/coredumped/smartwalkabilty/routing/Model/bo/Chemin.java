package com.fstm.coredumped.smartwalkabilty.routing.model.bo;

import java.util.*;

public class Chemin
{
    Set<Vertex> Chem=new HashSet<Vertex>();
    Set Annonces;


    public Set<Vertex> getChem() {
        return Chem;
    }

    public void setChem(Set<Vertex> chem) {
        Chem = chem;
    }

    public Chemin() {}

    public void Add_Route(Vertex vertex)
    {
        Chem.add(vertex);
    }
    public boolean ContainsGeoPoint(GeoPoint g)
    {
        return Chem.stream().anyMatch(vertex -> vertex.getArrive().equals(g) || vertex.getDepart().equals(g));
    }
}
