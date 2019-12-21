package com.example.xu_map;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShortestPath {
    private GraphMap graph;
    List<Node> distPath;
    static final int V = 9;

    static class Node {
        Location loc;
        int dist;
        Boolean settled;
        String parent;

        public Node(Location loc, int dist, Boolean settled) {
            this.loc = loc;
            this.dist = dist;
            this.settled = settled;
        }
    }

    public ShortestPath(GraphMap graph) {
        this.graph = graph;
        distPath = new ArrayList<>();
    }

    public int MinDistance() {
        int min = Integer.MAX_VALUE;
        int min_loc = -1;

        for (int i = 0; i < distPath.size(); i++) {
            if (distPath.get(i).settled == false && distPath.get(i).dist <= min) {
                min = distPath.get(i).dist;
                min_loc = i;
            }
        }
        return min_loc;
    }

    public void PrintSolution() {
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < distPath.size(); i++) {
            Log.d("MainActivity", distPath.get(i).loc.getBuildName() + " tt " + distPath.get(i).dist);
        }
    }


    public void Dijkstra(Location src, Location dest) {


        for (Location value : graph.getVertices()) {
            if (value.equals(src)) {
                distPath.add(new Node(value, 0, false));
            } else {
                distPath.add(new Node(value, Integer.MAX_VALUE, false));
            }
        }


        for (int count = 0; count < distPath.size() - 1; count++) {

            int u = MinDistance();
            distPath.get(u).settled = true;

            for (int v = 0; v < distPath.size(); v++)
                if (graph.getEdge(distPath.get(u).loc, distPath.get(v).loc) != null) {

                    if (!distPath.get(v).settled &&
                            graph.getEdge(distPath.get(u).loc, distPath.get(v).loc).weight != 0 &&
                            distPath.get(u).dist != Integer.MAX_VALUE &&
                            distPath.get(u).dist + graph.getEdge(distPath.get(u).loc, distPath.get(v).loc).weight < distPath.get(v).dist) {
                        distPath.get(v).dist = distPath.get(u).dist + graph.getEdge(distPath.get(u).loc, distPath.get(v).loc).weight;
                        distPath.get(v).parent = distPath.get(u).loc.getBuildName();
                    }
                }
        }

        PrintSolution();
    }





/*
    public int CalcShortestDis(Queue<Location> locQ){

    }

    public int CalcShortestDis(List<Location> locList){

    }
    */

}