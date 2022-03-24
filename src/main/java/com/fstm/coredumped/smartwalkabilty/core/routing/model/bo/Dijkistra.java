package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;
import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.DAOGraph;

import java.util.*;

public class Dijkistra implements IAlgo
{
    private static int numberOfAltCheminsToCreate=2;
    private static double infinite=999999999999.0;
    private static Point getPoint(List<Point> Q, GeoPoint p)
    {
        try {
            return Q.stream().filter(e->e.getGeoPoint().equals(p)).findFirst().get();
        }catch ( NoSuchElementException e){
            return null;
        }
    }
    private static GeoPoint get_Point_Not_in_Map(Chemin G,Graph graph,List<GeoPoint> Exlude)
    {
        Set<GeoPoint> points=graph.getGr().keySet();
        try {
            return points.stream().filter(geoPoint -> !G.ContainsGeoPoint(geoPoint) && !Exlude.contains(geoPoint)).findAny().get();
        }catch ( NoSuchElementException e){
            return null;
        }
    }
    class Point implements Comparable<Point>{
        private GeoPoint geoPoint;
        private double distance;
        private boolean visited=false;
        public Point(GeoPoint geoPoint, float distance) {
            this.geoPoint = geoPoint;
            this.distance = distance;
        }

        public GeoPoint getGeoPoint() {
            return geoPoint;
        }

        public void setGeoPoint(GeoPoint geoPoint) {
            this.geoPoint = geoPoint;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
        public Point(){

        }
        public Point(GeoPoint geoPoint){
            this.geoPoint=geoPoint;
            distance=infinite;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public int compareTo(Point o)
        {
            if(o.getDistance()<distance)return 1;
            if(o.getDistance()>distance)return -1;
            return 0;
        }
    }

    private Map<GeoPoint, GeoPoint> doAlgoDij(Graph graph, GeoPoint depart, GeoPoint arr)
    {
        if(!graph.contains(depart)&& !graph.contains(arr)){
            System.err.println("one of the points is not in the graph");
            return new HashMap<GeoPoint,GeoPoint>();
        }

        PriorityQueue<Point> Q =new PriorityQueue<Point>();
        List<Point> points=new LinkedList<Point>();
        Map<GeoPoint,GeoPoint> dictionary=new HashMap<GeoPoint,GeoPoint>();
        for (GeoPoint p: graph.getGr().keySet())
        {
            if(p!=null)
            {
                Point point;
                if(p.equals(depart)) point=new Point(p,0);
                else point=new Point(p);
                points.add(point);
                Q.add(point);
            }
        }
        while (!Q.isEmpty()){
            Point U=Q.remove();
            U.setVisited(true);
            Set<Vertex> neis =graph.getGr().get(U.getGeoPoint());
            for (Vertex v: neis)
            {
                Point neighbour=getPoint(points,v.getArrive());
                if(!neighbour.isVisited()){
                      double temp=U.getDistance()+v.getDistance();
                      if(temp<neighbour.getDistance())
                      {
                          neighbour.setDistance(temp);
                          Q.remove(neighbour);
                          Q.add(neighbour);
                          dictionary.put(neighbour.getGeoPoint(),U.getGeoPoint());
                      }
                }
            }
        }
        return dictionary;
    }
    private void MergePaths(Map<GeoPoint,GeoPoint> Half1,Map<GeoPoint,GeoPoint> Half2,GeoPoint link)//merging in Half2
    {
        GeoPoint ge=link;
        while (true)
        {
            GeoPoint another=Half1.get(ge);
            if(another==null)break;
            Half2.put(ge,another);
            ge=another;
        }
    }
    private Map<GeoPoint, GeoPoint> doAlgoDij(Graph graph, GeoPoint depart, GeoPoint arr,GeoPoint inter) //path to force the algo to pass by a point
    {
        if(!graph.contains(inter))return null;
        Map<GeoPoint,GeoPoint> Half1=doAlgoDij(graph, depart,inter);
        Map<GeoPoint,GeoPoint> Half2=doAlgoDij(graph, inter,arr);
        MergePaths(Half1,Half2,inter);
        return Half2;
    }

    @Override
    public List<Chemin> doAlgo(Graph graph, GeoPoint depart, GeoPoint arr)
    {
        List<Chemin> list=new LinkedList<Chemin>();
        Chemin chemin;
        double it=1.5;
        do {
             Map<GeoPoint,GeoPoint> G= doAlgoDij(graph,depart,arr);
             chemin = IAlgo.Construct_Chemin(G, graph, arr,depart);
             if(!chemin.getVertices().isEmpty())break;
             it=it+0.5;
             graph=new DAOGraph().getTheGraph(depart,arr,it);
        }while (chemin.getVertices().isEmpty());
        chemin.setPriority(1);
        list.add(chemin);
        List<GeoPoint> ToExclude=new ArrayList<GeoPoint>();
        for (int i = 0; i < numberOfAltCheminsToCreate; i++) {
            GeoPoint inter= get_Point_Not_in_Map(chemin, graph,ToExclude);
            if(inter==null)break;
            chemin=IAlgo.Construct_Chemin(Objects.requireNonNull(doAlgoDij(graph, depart, arr, inter)), graph, arr,depart);
            chemin.setPriority(2+i);
            list.add(chemin);
            ToExclude.add(inter);
        }
        return list;
    }
    @Override
    public Chemin doAlgo(Graph graph, GeoPoint depart, GeoPoint arr, GeoPoint inter) {
        Map<GeoPoint,GeoPoint> Ha=doAlgoDij(graph,depart,arr,inter);
        if(Ha==null)return null;
        return IAlgo.Construct_Chemin(Ha,graph,arr,depart);
    }
}
