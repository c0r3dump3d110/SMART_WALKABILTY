package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;

import java.util.*;

public class Chemin
{
    Set<Vertex> vertices =new HashSet<Vertex>();
    Set<Annonce> annonces =new HashSet<Annonce>();
    int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Set<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }
    public void Add_Annonce(Annonce annonce)
    {
        annonces.add(annonce);
    }
    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Chemin() {}

    public void Add_Route(Vertex vertex)
    {
        vertices.add(vertex);
    }
    public boolean ContainsGeoPoint(GeoPoint g)
    {
        return vertices.stream().anyMatch(vertex -> vertex.getArrive().equals(g) || vertex.getDepart().equals(g));
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":"+ "\"LineString\","+
                "\"coordinates\": "
                 +vertices +
                '}';
    }
}
