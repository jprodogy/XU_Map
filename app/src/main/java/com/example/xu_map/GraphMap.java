package com.example.xu_map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphMap {
    int vertices;
    LinkedList<Edge>[] adjacencylist;

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


    public GraphMap(int vertices) {
        this.vertices = vertices;
        adjacencylist = new LinkedList[vertices];

        for (int i = 0; i < vertices; i++) {
            adjacencylist[i] = new LinkedList<>();
        }
    }

    public void addEgde(Location source, Location destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencylist[0].addFirst(edge);
    }

    public Edge getEdge(int source, int destination){
        return adjacencylist[source].get(destination);
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            LinkedList<Edge> list = adjacencylist[i];
            for (int j = 0; j < list.size(); j++) {
                System.out.println("vertex-" + i + " is connected to " +
                        list.get(j).destination + " with weight " + list.get(j).weight);
            }
        }
    }

}