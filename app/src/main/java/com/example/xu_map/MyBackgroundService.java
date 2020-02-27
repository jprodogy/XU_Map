package com.example.xu_map;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.example.xu_map.MainActivity.gm;
import static com.example.xu_map.MainActivity.obj;

public class MyBackgroundService extends Service {
    private static final String TAG = "MyBackgroundService";
    public static Objects obj;
    public static GraphMap gm;

    public MyBackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {


        Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        super.onCreate();
        ObjectCreator();
        MapCreator();
        ShortestPath();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Invoke background service onStartCommand method.", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
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

        for (Location val: gm.getVertices()){
            for (Location val2: gm.getVertices()){
                Double dist = distance(val.getLatitude(), val.getLongitude(), val2.getLatitude(), val2.getLongitude());
                gm.addEdge(val,val2, dist, true);
            }
        }

        for (Location val: gm.getVertices()){
            Log.d(TAG,gm.getEdges(val).toString());
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