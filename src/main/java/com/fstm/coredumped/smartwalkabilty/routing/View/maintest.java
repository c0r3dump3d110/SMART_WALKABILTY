package com.fstm.coredumped.smartwalkabilty.routing.View;


import com.fstm.coredumped.smartwalkabilty.common.Model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.*;

import java.util.List;

public class maintest {

     public static void main(String[] args) {
         Graph graph=new Graph();
         GeoPoint depart=new GeoPoint(12.0,15),arr=new GeoPoint(50,50);
         GeoPoint inte=new GeoPoint(12,17);
         GeoPoint inte1=new GeoPoint(13,14);
         GeoPoint inte2=new GeoPoint(45,23);
         GeoPoint inte3=new GeoPoint(13,12);
         GeoPoint inte4=new GeoPoint(42,122);
         GeoPoint inte5=new GeoPoint(15,15);
         GeoPoint inte6=new GeoPoint(11,12);
         GeoPoint inte7=new GeoPoint(13,12);
         graph.Add_Route(new Vertex(depart,inte1,2));
         graph.Add_Route(new Vertex(inte1,inte2,4));
         graph.Add_Route(new Vertex(inte2,inte4,50));
         graph.Add_Route(new Vertex(inte1,inte3,1.5));
         graph.Add_Route(new Vertex(inte2,inte4,2));
         graph.Add_Route(new Vertex(inte4,inte5,12));
         graph.Add_Route(new Vertex(inte5,inte,15));
         graph.Add_Route(new Vertex(inte5,inte3,1));
         graph.Add_Route(new Vertex(inte3,inte6,1));
         graph.Add_Route(new Vertex(inte6,inte7,3));
         graph.Add_Route(new Vertex(inte4,arr,15));
         graph.Add_Route(new Vertex(inte,arr,1));
         graph.Add_Route(new Vertex(inte7,arr,13));
         List<Chemin> g= new Dijkistra().doAlgo(graph,depart,arr);
         System.out.println(g);
    }


}


