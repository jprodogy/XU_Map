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
    Context context;

    public Objects(){
        newObjects = new HashMap();

    }

    public void CreateObjects(Context current){
        context = current;
        InputStream ip = context.getResources().openRawResource(R.raw.locations);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(ip, Charset.defaultCharset()));
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

}
