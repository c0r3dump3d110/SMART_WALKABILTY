package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;

import java.io.Serializable;
import java.util.*;

public class Chemin implements Serializable
{
    private static final long serialVersionUID=10L;
    Set<Vertex> vertices =new HashSet<Vertex>();
    Set<Site> sites =new HashSet<Site>();
    int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }
    public void Add_Site(Site site)
    {
        sites.add(site);
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
