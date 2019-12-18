package com.example.xu_map;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class ShortestPath {
    static final int V = 9;
    Location minDistance(Map<Location, Integer> dist, Map<Location, Boolean> settled)
    {
        int min = Integer.MAX_VALUE;
        Location min_loc = null;

        for (Location value : dist.keySet()) {
            if (settled.get(value) == false && dist.get(value) <= min){
                min = dist.get(value);
                min_loc = value;
            }
        }

        return min_loc;
    }

    void printSolution(int dist[], int n)
    {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " tt " + dist[i]);
    }


    void dijkstra(GraphMap graph, Location src, Location dest) {
        Map<Location,Integer> dist = new HashMap<>();
        Map<Location, Boolean> settled = new HashMap<>();

        for (Location value : graph.getVertices()) {
            dist.put(value, Integer.MAX_VALUE);
            settled.put(value, false);
        }

        dist.put(src, 0);

        for (int count = 0; count < dist.size() - 1; count++) {
            Location u = minDistance(dist, settled);
            settled.put(u, true);


            for (Location v : graph.getVertices()){
                if (!settled.get(v) &&
                        graph.getEdge(u,v).weight != 0 &&
                        dist.get(u) != Integer.MAX_VALUE &&
                        dist.get(u) + graph.getEdge(u,v).weight < dist.get(v)){
                    dist.put(v, dist.get(u) + graph.getEdge(u,v).weight);
                }
            }

        }
    }

/*
    public int CalcShortestDis(Queue<Location> locQ){

    }

    public int CalcShortestDis(List<Location> locList){

    }
    */

}