package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;
import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.DAOGraph;

import java.util.*;

public class Dijkistra implements IAlgo
{
    private static int numberOfAltCheminsToCreate=2;
    private static double infinite=999999999999.0;
    private static Point getPoint(Set<Point> Q, GeoPoint p)
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
        private double risk;
        private boolean visitedRisk=false;
        private boolean visitedDist =false;

        public boolean isVisitedRisk() {
            return visitedRisk;
        }

        public void setVisitedRisk(boolean visitedRisk) {
            this.visitedRisk = visitedRisk;
        }

        public double getRisk() {
            return risk;
        }

        public void setRisk(double risk) {
            this.risk = risk;
        }

        public Point(GeoPoint geoPoint, double distance,double risk) {
            this.geoPoint = geoPoint;
            this.distance = distance;
            this.risk=risk;
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
            risk=infinite;
        }

        public boolean isVisitedDist() {
            return visitedDist;
        }

        public void setVisitedDist(boolean visitedDist) {
            this.visitedDist = visitedDist;
        }

        @Override
        public int compareTo(Point o)
        {
            if(o.getDistance()<distance)return 1;
            if(o.getDistance()>distance)return -1;
            return 0;
        }

    }
    class RiskComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            if(o2.getRisk()<o1.getRisk())return 1;
            if(o2.getRisk()>o1.getRisk())return -1;
            return 0;
        }
    }
    private Map<GeoPoint, GeoPoint> doAlgoDijDistance(Graph graph, GeoPoint depart, GeoPoint arr)
    {
        if(graph.getGr().isEmpty())
        {
            System.err.println("Graph is empty");
            return new HashMap<GeoPoint,GeoPoint>();
        }
        if(!graph.contains(depart)&& !graph.contains(arr)){
            System.err.println("one of the points is not in the graph");
            return new HashMap<GeoPoint,GeoPoint>();
        }
        PriorityQueue<Point> QD =new PriorityQueue<Point>();
        Set<Point> points=new HashSet<>();
        Map<GeoPoint,GeoPoint> dictioDistance=new HashMap<GeoPoint,GeoPoint>();
        for (GeoPoint p: graph.getGr().keySet())
        {
            if(p!=null)
            {
                Point point;
                if(p.equals(depart)) point=new Point(p,0,0);
                else point=new Point(p);
                points.add(point);
                QD.add(point);
            }
        }
        while (!QD.isEmpty()){
            Point U=QD.remove();
            U.setVisitedDist(true);
            Set<Vertex> neis =graph.getGr().get(U.getGeoPoint());
            for (Vertex v: neis)
            {
                Point neighbour=getPoint(points,v.getArrive());
                if(!neighbour.isVisitedDist()){
                      double temp=U.getDistance()+v.getDistance();
                      if(temp<neighbour.getDistance())
                      {
                          neighbour.setDistance(temp);
                          QD.remove(neighbour);
                          QD.add(neighbour);
                          dictioDistance.put(neighbour.getGeoPoint(),U.getGeoPoint());
                      }
                }
            }
        }
        return dictioDistance;
    }
    private ArrayList<Map<GeoPoint, GeoPoint>> doAlgoDijDistanceAndRisk(Graph graph, GeoPoint depart, GeoPoint arr)
    {
        if (graph.getGr().isEmpty()){
            System.err.println("Graph is empty");
            return new ArrayList<>();
        }
        if(!graph.contains(depart)|| !graph.contains(arr)){
            System.err.println("one of the points is not in the graph");
            return new ArrayList<>();
        }
        PriorityQueue<Point> QD =new PriorityQueue<Point>();
        PriorityQueue<Point> QR =new PriorityQueue<Point>(new RiskComparator());
        Set<Point> points=new HashSet<>();
        Map<GeoPoint,GeoPoint> dictDistance=new HashMap<GeoPoint,GeoPoint>();
        Map<GeoPoint,GeoPoint> dictRisk=new HashMap<GeoPoint,GeoPoint>();
        for (GeoPoint p: graph.getGr().keySet())
        {
            if(p!=null)
            {
                Point point;
                if(p.equals(depart)) point=new Point(p,0,0);
                else point=new Point(p);
                points.add(point);
                QD.add(point);
                QR.add(point);
            }
        }
        while (!QD.isEmpty()){
            Point U=QD.remove();
            U.setVisitedDist(true);
            Set<Vertex> neis =graph.getGr().get(U.getGeoPoint());
            for (Vertex v: neis)
            {
                Point neighbour=getPoint(points,v.getArrive());
                if(!neighbour.isVisitedDist()){
                    double temp=U.getDistance()+v.getDistance();
                    if(temp<neighbour.getDistance())
                    {
                        neighbour.setDistance(temp);
                        QD.remove(neighbour);
                        QD.add(neighbour);
                        dictDistance.put(neighbour.getGeoPoint(),U.getGeoPoint());
                    }
                }
            }
            U=QR.remove();
            U.setVisitedRisk(true);
            neis =graph.getGr().get(U.getGeoPoint());
            for (Vertex v: neis)
            {
                Point neighbour=getPoint(points,v.getArrive());
                if(!neighbour.isVisitedRisk()){
                    double temp=U.getRisk()+v.getRisk();
                    if(temp<neighbour.getRisk())
                    {
                        neighbour.setRisk(temp);
                        QR.remove(neighbour);
                        QR.add(neighbour);
                        dictRisk.put(neighbour.getGeoPoint(),U.getGeoPoint());
                    }
                }
            }

        }
        ArrayList<Map<GeoPoint,GeoPoint>> arrayList=new ArrayList<>();
        arrayList.add(dictDistance);
        arrayList.add(dictRisk);
        return arrayList;
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
    private Map<GeoPoint, GeoPoint> doAlgoDijDistance(Graph graph, GeoPoint depart, GeoPoint arr, GeoPoint inter) //path to force the algo to pass by a point
    {
        if(!graph.contains(inter))return null;
        Map<GeoPoint,GeoPoint> Half1= doAlgoDijDistance(graph, depart,inter);
        Map<GeoPoint,GeoPoint> Half2= doAlgoDijDistance(graph, inter,arr);
        MergePaths(Half1,Half2,inter);
        return Half2;
    }

    @Override
    public List<Chemin> doAlgo(Graph graph, GeoPoint depart, GeoPoint arr)
    {
        List<Chemin> list=new LinkedList<Chemin>();
        Chemin cheminDist;
        Chemin cheminRisk;
        System.out.println("Starting Algo");
        List<Map<GeoPoint,GeoPoint>> G= doAlgoDijDistanceAndRisk(graph,depart,arr);

        cheminDist = IAlgo.Construct_Chemin(G.get(0), graph, arr,depart);
        cheminRisk = IAlgo.Construct_Chemin(G.get(1), graph, arr,depart);

        cheminDist.setPriority(1);
        cheminRisk.setPriority(-1);

        list.add(cheminDist);
        list.add(cheminRisk);

        List<GeoPoint> ToExclude=new ArrayList<GeoPoint>();

        for (int i = 0; i < numberOfAltCheminsToCreate; i++) {
            GeoPoint inter= get_Point_Not_in_Map(cheminDist, graph,ToExclude);
            if(inter==null)
                break;

            cheminDist=IAlgo.Construct_Chemin(Objects.requireNonNull(doAlgoDijDistance(graph, depart, arr, inter)), graph, arr,depart);
            cheminDist.setPriority(2+i);

            list.add(cheminDist);
            ToExclude.add(inter);
        }
        return list;
    }
    @Override
    public Chemin doAlgo(Graph graph, GeoPoint depart, GeoPoint arr, GeoPoint inter) {
        Map<GeoPoint,GeoPoint> Ha= doAlgoDijDistance(graph,depart,arr,inter);
        if(Ha==null)return null;
        return IAlgo.Construct_Chemin(Ha,graph,arr,depart);
    }
}
