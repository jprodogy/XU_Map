package com.example.xu_map;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Objects obj;
    private GraphMap gm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectCreator();
        MapCreator();
        ShortestPath();
    }

    public void ObjectCreator(){
        obj = new Objects(this);
        obj.CreateObjects();

    }

    public void MapCreator(){

        gm = new GraphMap();

        for (Map.Entry<String,Location> entry : obj.newObjects.entrySet()){
            gm.addVertex(entry.getValue());
        }

        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("Music Building"), 5, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("College of Pharmacy"), 6, true);
        gm.addEdge(obj.getLocation("Administration Building"), obj.getLocation("NCF Addition"), 4, true);
        gm.addEdge(obj.getLocation("Pharmacy Addition"), obj.getLocation("Administration Building"), 6, true);
        gm.addEdge(obj.getLocation("University Center"), obj.getLocation("Pharmacy Addition"), 3, true);
        gm.addEdge(obj.getLocation("College of Pharmacy"), obj.getLocation("University Center"), 2, true);
        gm.addEdge(obj.getLocation("Music Building"), obj.getLocation("College of Pharmacy"), 7, true);

    }

    public void ShortestPath(){
        ShortestPath spt = new ShortestPath(gm);
        Queue<Location> locations = new LinkedList<>();
        List<Location> locations1 = new ArrayList<>(Arrays.asList(obj.getLocation("NCF Addition"), obj.getLocation("Administration Building"),obj.getLocation("Pharmacy Addition")));

        locations.add(obj.getLocation("NCF Addition"));
        locations.add(obj.getLocation("College of Pharmacy"));
        locations.add(obj.getLocation("University Center"));

        spt.CalcShortestPath(obj.getLocation("NCF Addition"), obj.getLocation("Administration Building"));
        spt.CalcShortestPath(locations);
        spt.CalcShortestPath(locations1);
    }

}
