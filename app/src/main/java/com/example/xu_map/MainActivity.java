package com.example.xu_map;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Objects obj;
    private GraphMap gm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectCreator();
        MapCreator();
    }

    public void ObjectCreator(){
        obj = new Objects(this);
        obj.CreateObjects();

    }

    public void MapCreator(){

        gm = new GraphMap();

        for (Map.Entry<String,Location> entry : obj.newObjects.entrySet()){
            gm.addVertex(entry.getValue());
            //Log.d(TAG, String.valueOf(entry.getKey()));
        }

        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("NCF Science Annex"), 5, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("NCF Science Annex"), 4, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("NCF Science Annex"), 6, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("NCF Science Annex"), 3, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("NCF Science Annex"), 2, true);
        gm.addEdge(obj.getLocation("NCF Addition"), obj.getLocation("NCF Science Annex"), 7, true);

        Log.d(TAG, String.valueOf(gm.getEdges(obj.getLocation("NCF Addition"))));
    }

}
