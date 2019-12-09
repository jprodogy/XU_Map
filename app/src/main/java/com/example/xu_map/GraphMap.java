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
        new HashMap<>();
    }

    public void addVertex(Location loc){
        try{
            XU_Map.put(loc, new LinkedList<Edge>());
        }catch (NullPointerException e){
           // Log.d("MainActivity", "It did not work");
        }
    }

    public void addEdge(Location source, Location destination, int weight, boolean bidirectional){

        Edge edge = new Edge(source, destination, weight);
        try{
            if (!XU_Map.containsKey(source)){
                addVertex(source);
            }

            if (!XU_Map.containsKey(destination)){
                addVertex(destination);
            }

            XU_Map.get(source).add(edge);
            Log.d("MainActivity", String.valueOf(XU_Map.get(source.getBuildName()).getFirst()));

            if (bidirectional == true) {
                XU_Map.get(destination).add(edge);
            }
        }catch (NullPointerException e){

        }

    }

    public LinkedList<Edge> getEdges(Location source){
        try{
            return XU_Map.get(source);
        }catch (NullPointerException e){
            return null;
        }

    }
    public Set<Location> getVertices(){
        try{
            return XU_Map.keySet();
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        try{
            for (Location v : XU_Map.keySet()) {
                builder.append(v.getBuildName() + ": ");
                for (Edge w : XU_Map.get(v)) {
                    builder.append(w.toString() + " ");
                }
                builder.append("\n");
            }
        }catch (NullPointerException e){

        }


        return (builder.toString());
    }
}
