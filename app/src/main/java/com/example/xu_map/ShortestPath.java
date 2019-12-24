package com.example.xu_map;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class ShortestPath {
    private GraphMap graph;
    List<Node> distPath;
    static final int V = 9;

    static class Node {
        Location loc;
        int dist;
        Boolean settled;
        Node parent;

        public Node(Location loc, int dist, Boolean settled) {
            this.loc = loc;
            this.dist = dist;
            this.settled = settled;
            parent = null;
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
            Log.d("SideActivity", distPath.get(i).loc.getBuildName() + " tt " + distPath.get(i).dist);
            StringBuilder sb = new StringBuilder();
            Node temp = distPath.get(i);

            if(distPath.get(i).parent != null){
                while(temp != null){
                    sb.insert(0,temp.loc.getBuildName() + " ");
                    temp = temp.parent;
                }
                Log.d("RealActivity", "Path: " + sb.toString());
            }

        }
    }

    public String PathFinder(int i){
        StringBuilder sb = new StringBuilder();
        Node temp = distPath.get(i);

        if(distPath.get(i).parent != null){
            while(temp != null){
                sb.insert(0,temp.loc.getBuildName() + " ");
                temp = temp.parent;
            }
            Log.d("RealActivity", "Path: " + sb.toString());
        }
        return sb.toString();
    }


    public void Dijkstra(Location src) {


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
                        distPath.get(v).parent = distPath.get(u);


                    }
                }
        }

        PrintSolution();
    }




    public String CalcShortestDis(Location src, Location dest){
        Dijkstra(src);
        int min = Integer.MAX_VALUE;
        int min_index = 0;
        for (int i = 0; i < distPath.size(); i++) {
            if (distPath.get(i).loc.equals(dest) && distPath.get(i).dist < min){
                min = distPath.get(i).dist;
                min_index = i;
            }
        }

        if (min == Integer.MAX_VALUE){
            return " ";
        }else{
            return PathFinder(min_index);
        }
    }
/*

    public int CalcShortestPath(Queue<Location> locQ){

    }

    public int CalcShortestPath(List<Location> locList){

    }
*/

}