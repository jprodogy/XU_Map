package com.example.xu_map;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class GraphMap {
    Map<Location, LinkedList<Edge>> xuMap;
    private int vertCount;

    static class Edge {
        Location destination;
        int weight;

        public Edge(Location destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public GraphMap(){
        xuMap = new HashMap<>();
    }

    public void addVertex(Location loc){
        if (!xuMap.containsKey(loc)){
            xuMap.put(loc, new LinkedList<Edge>());
        }else{
            Log.d("MainActivity", "The graph already contains this Location");
        }
    }

    public void addEdge(Location source, Location destination, int weight, boolean bidirectional){

        Edge edge = new Edge(destination, weight);

        if (!xuMap.containsKey(source)){
            addVertex(source);
        }

        if (!xuMap.containsKey(destination)){
            addVertex(destination);
        }

        xuMap.get(source).add(edge);

        if (bidirectional == true) {
            xuMap.get(destination).add(edge);
        }
    }

    public LinkedList<Edge> getEdges(Location source){
        return xuMap.get(source);
    }

    public Edge getEdge(Location source, Location dest){
        Log.d("MainActivity", source.getBuildName());

        LinkedList<Edge> temp = xuMap.get(source);
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).destination.equals(dest)){
                return temp.get(i);
            }
            //Log.d("MainActivity", temp.get(i).destination.getBuildName() + "||" + dest.getBuildName());

        }

        return null;
    }

    public Set<Location> getVertices(){
        return xuMap.keySet();

    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        try{
            for (Location v : xuMap.keySet()) {
                builder.append(v.getBuildName() + ": ");
                for (Edge w : xuMap.get(v)) {
                    builder.append(w.destination.getBuildName() + " ");
                }
                builder.append("\n");
            }
        }catch (NullPointerException e) {

        }
        return (builder.toString());
    }
}
