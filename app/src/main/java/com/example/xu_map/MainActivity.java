package com.example.xu_map;

import android.content.Intent;
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
    public static Objects obj;
    public static GraphMap gm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectCreator();
        MapCreator();
        ShortestPath();
        Intent intent = new Intent(this, MarkerDemoActivity.class);
        startActivity(intent);
    }

    public void ObjectCreator(){
        obj = new Objects(this);
        obj.CreateObjects();

    }

    public void MapCreator(){

        gm = new GraphMap();

        for (Map.Entry<String,Location> entry : obj.newObjects.entrySet()){
            gm.addVertex(entry.getValue());
            Log.d("Creating vertices", entry.getKey() + " " + entry.getValue().getBuildName());
        }

        for (Location val: gm.getVertices()){
            for (Location val2: gm.getVertices()){
                Double dist = distance(val.getLatitude(), val.getLongitude(), val2.getLatitude(), val2.getLongitude());
                gm.addEdge(val,val2, dist, true);

            }
        }

        for (Location val: gm.getVertices()) {

            for (GraphMap.Edge lv: gm.getEdges(val)){
                Log.d(val.getBuildName(),lv.weight + " " + lv.destination.getBuildName());
            }
        }

/*
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("Music Building"), 5, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("College of Pharmacy"), 6, true);
        gm.addEdge(obj.getLocation("Administration Building"), obj.getLocation("NCF Addition"), 4, true);
        gm.addEdge(obj.getLocation("Pharmacy Addition"), obj.getLocation("Administration Building"), 6, true);
        gm.addEdge(obj.getLocation("University Center"), obj.getLocation("Pharmacy Addition"), 3, true);
        gm.addEdge(obj.getLocation("College of Pharmacy"), obj.getLocation("University Center"), 2, true);
        gm.addEdge(obj.getLocation("Music Building"), obj.getLocation("College of Pharmacy"), 7, true);

 */


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

    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }

}
