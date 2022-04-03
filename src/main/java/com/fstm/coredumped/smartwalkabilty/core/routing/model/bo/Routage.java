package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.controller.ShortestPathReq;
import com.fstm.coredumped.smartwalkabilty.common.controller.ShortestPathWithAnnounces;
import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.geofencing.model.bo.Geofencing;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.DAOGraph;

import java.util.List;

public class Routage extends Subject
{
    private List<Chemin> chemins;
    private  IAlgo algo;
    private Graph graph;
    private GeoPoint depart;
    private GeoPoint arr;

    public Routage(IAlgo algo) {
        this.algo = algo;
    }

    public Routage(ShortestPathReq shortestPathReq){
        this(new Dijkistra(), shortestPathReq.getActualPoint(), shortestPathReq.getArrPoint());
        this.graph = new DAOGraph().getTheGraph(this.depart, this.arr);

    }
    public Routage(ShortestPathWithAnnounces announcesReq){
        this(new Dijkistra(), announcesReq.getActualPoint(), announcesReq.getPointArrivee());
        this.graph = new DAOGraph().getTheGraph(this.depart, this.arr);
        this.Attach(new Geofencing(this, announcesReq.getPerimetre(), announcesReq.getCategorieList()));
    }

    public Routage(IAlgo algo, GeoPoint depart, GeoPoint arr) {
        this.chemins = chemins;
        this.algo = algo;
        this.graph = graph;
        this.depart = depart;
        this.arr = arr;
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

    public void calculerChemins(){
        this.chemins = this.algo.doAlgo(this.graph, this.depart, this.arr);
        this.NotifyAll();
    }
}
