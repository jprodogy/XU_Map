package com.example.xu_map;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class GraphMap {
    Map<Location, LinkedList<Edge>> XU_Map;

    static class Edge {
        Location source;
        Location destination;
        int weight;

        public Edge(Location source, Location destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    public GraphMap(){
        XU_Map = new HashMap<>();
    }

    public void addVertex(Location loc){
        XU_Map.put(loc, new LinkedList<Edge>());

    }

    public void addEdge(Location source, Location destination, int weight, boolean bidirectional){

        Edge edge = new Edge(source, destination, weight);

        if (!XU_Map.containsKey(source)){
            addVertex(source);
        }

        if (!XU_Map.containsKey(destination)){
            addVertex(destination);
        }

        XU_Map.get(source).add(edge);

        if (bidirectional == true) {
            XU_Map.get(destination).add(edge);
        }


    }

    public LinkedList<Edge> getEdges(Location source){

        return XU_Map.get(source);


    }
    public Set<Location> getVertices(){
        return XU_Map.keySet();

    }

    public void OptimalPath(){

    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        try{
            for (Location v : XU_Map.keySet()) {
                builder.append(v.getBuildName() + ": ");
                for (Edge w : XU_Map.get(v)) {
                    builder.append(w.destination.getBuildName() + " ");
                }
                builder.append("\n");
            }
        }catch (NullPointerException e){

        }


        return (builder.toString());
    }
}
