package com.example.xu_map;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;


public class ShortestPath {
    private GraphMap graph;
    List<Node> distPath;
    static final int V = 9;

    static class Node {
        Location loc;
        double dist;
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
        double min = Integer.MAX_VALUE;
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

    public String PathtoString(List<Location> pathList){
        StringBuilder strB = new StringBuilder();
        strB.append("Path: ");
        for (int i = 0; i < pathList.size(); i++) {
            strB.append(pathList.get(i).getBuildName() + " ");
        }
        Log.d("PrintPath", strB.toString());
        return strB.toString();
    }

    public List<Location> PathFinder(int i){
        ArrayList<Location> pathList = new ArrayList<>();
        Node temp = distPath.get(i);
        if(distPath.get(i).parent != null){
            while(temp != null){
                pathList.add(0, temp.loc);
                temp = temp.parent;
            }
        }
        return pathList;
    }



    public void Dijkstra(Location src) {
        distPath = new ArrayList<>();
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

        //PrintSolution();
    }




    public List<Location> CalcShortestPath(Location src, Location dest){
        Dijkstra(src);
        double min = Integer.MAX_VALUE;
        int min_index = 0;
        for (int i = 0; i < distPath.size(); i++) {
            if (distPath.get(i).loc.equals(dest) && distPath.get(i).dist < min){
                min = distPath.get(i).dist;
                min_index = i;
            }
        }

        if (min == Integer.MAX_VALUE){
            return Collections.EMPTY_LIST;
        }else{
            return PathFinder(min_index);
        }
    }


    public List<Location> CalcShortestPath(Queue<Location> locQ){
        List<Location> pathList = new ArrayList<>();
        if (locQ.isEmpty()){
            return pathList;
        }
        Location temp = locQ.poll();

        pathList.add(temp);
        Dijkstra(temp);

        while(!locQ.isEmpty()){
            Location minLoc = null;
            for (int i = 0; i < distPath.size(); i++) {
                int minDist = Integer.MAX_VALUE;
                if (distPath.get(i).parent != null){
                    if (distPath.get(i).parent.loc == temp && distPath.get(i).dist < minDist){
                        minLoc = distPath.get(i).loc;
                    }
                }
            }
            if (minLoc == null){
                return Collections.emptyList();
            }
            pathList.add(minLoc);
            temp = locQ.poll();
        }

        return pathList;
    }

    public List<Location> CalcShortestPath(List<Location> locList){
        List<Location> pathList = new ArrayList<>();
        if (locList.size() == 0){
            return pathList;
        }
        int pathCounter = 0;
        for (int i = 0; i < locList.size(); i++) {
            double min = Integer.MAX_VALUE;

            Dijkstra(locList.get(i));

            for (int j = 0; j < distPath.size(); j++) {
                List<Location> tempPath = PathFinder(j);
                if (tempPath.containsAll(locList) && distPath.get(j).dist < min){
                    pathList = tempPath;
                    min = distPath.get(j).dist;
                }
            }
        }

        return pathList;
    }
}