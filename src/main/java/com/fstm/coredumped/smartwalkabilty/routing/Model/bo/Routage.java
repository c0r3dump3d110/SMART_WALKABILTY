package com.fstm.coredumped.smartwalkabilty.routing.model.bo;

import java.util.List;

public class Routage
{
    private List<Chemin> chemins;
    private  IAlgo algo;
    private Graph graph;
    private GeoPoint depart,arr;

    public Routage(IAlgo algo) {
        this.algo = algo;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public List<Chemin> getChemins() {
        return chemins;
    }

    public void setChemins(List<Chemin> chemins) {
        this.chemins = chemins;
    }

    public IAlgo getAlgo() {
        return algo;
    }

    public void setAlgo(IAlgo algo) {
        this.algo = algo;
    }

    public GeoPoint getDepart() {
        return depart;
    }

    public void setDepart(GeoPoint depart) {
        this.depart = depart;
    }

    public GeoPoint getArr() {
        return arr;
    }

    public void setArr(GeoPoint arr) {
        this.arr = arr;
    }
}
