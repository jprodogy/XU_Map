package com.example.xu_map;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Objects {
    Map<String, Location> newObjects;
    private Context context;
    private BufferedReader bReader;

    public Objects(Context current){
        newObjects = new HashMap();
        context = current;

    }

    public void CreateObjects(){
        InputStream ip = context.getResources().openRawResource(R.raw.locations);
        bReader = new BufferedReader(new InputStreamReader(ip, Charset.defaultCharset()));


        try{
            String line;
            while((line = bReader.readLine()) != null){
                String[] data = line.split(",");
                Location loc = new Location(data[0],Integer.parseInt(data[1]),data[2].split("/"),Double.valueOf(data[3]),Double.valueOf(data[4]),data[5].split("/"), data[6].split("/"));
                newObjects.put(data[0],loc);

            }
        }catch (IOException e){

        }



    }


    public Location getLocation(String str){
        return newObjects.get(str);
    }

    public Location getLocViaName(String name){
        for (Map.Entry<String,Location> entry : newObjects.entrySet()){
            if (entry.getValue().getBuildName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }

    public Location getLocViaNum(int num){
        for (Map.Entry<String,Location> entry : newObjects.entrySet()){
            if (entry.getValue().getBuildNum() == num){
                return entry.getValue();
            }
        }
        return null;
    }

    public ArrayList<Location> getAllViaStrt(String strt){
        ArrayList<Location> locs = new ArrayList<>();
        for (Map.Entry<String,Location> entry : newObjects.entrySet()){
            if (entry.getValue().getStreet().contains(strt)){
                locs.add(entry.getValue());
            }
        }
        return locs;
    }

    public ArrayList<Location> getAllViaLatLong(double lat, double lon){
        ArrayList<Location> locs = new ArrayList<>();
        for (Map.Entry<String,Location> entry : newObjects.entrySet()){
            if (entry.getValue().getLatitude() <= lat && entry.getValue().getLongitude() >= lon){
                locs.add(entry.getValue());
            }
        }
        return locs;

    }

    public ArrayList<Location> getAllViaPur(String cat){
        ArrayList<Location> locs = new ArrayList<>();
        for (Map.Entry<String,Location> entry : newObjects.entrySet()){
            if (entry.getValue().getPurpose().contains(cat)){
                locs.add(entry.getValue());
            }
        }
        return locs;
    }

    public ArrayList<Location> getAllViaKey(String key){
        ArrayList<Location> locs = new ArrayList<>();
        for (Map.Entry<String,Location> entry : newObjects.entrySet()){

            if (entry.getValue().getKeywords().contains(key)){
                locs.add(entry.getValue());
            }
        }
        return locs;
    }

    public ArrayList<Location> getAllLocs(){
        ArrayList<Location> locs = new ArrayList<>();
        for(Map.Entry<String,Location> entry: newObjects.entrySet()){
            locs.add(entry.getValue());
        }
        return locs;
    }


}
