package com.example.xu_map;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

public class TouchableWrapper extends FrameLayout implements TaskMapComplete{
    Map<Location, Marker> markerMap;
    GoogleMap map;

    public TouchableWrapper(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        map = XUMapActivity.mMap;
        Point point = new Point();
        point.set((int) event.getX(), (int)event.getY());
        LatLng geoPoint = map.getProjection().fromScreenLocation(point);


        markerMap = XUMapActivity.markerMap;
        double latitude= Math.round(geoPoint.latitude * 1000.0) / 1000.0;
        double longitude = Math.round(geoPoint.longitude * 1000.0) / 1000.0;


        for (Marker marker: markerMap.values()){
            double latitude2= Math.round(marker.getPosition().latitude * 1000.0) / 1000.0;
            double longitude2 = Math.round(marker.getPosition().longitude * 1000.0) / 1000.0;
            Log.d("TAG", marker.getTitle() + " " + longitude2 + " " + latitude2 + " " + latitude + " " + longitude);
            if (latitude == latitude2 && longitude == longitude2){
                marker.showInfoWindow();
            }
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                XUMapActivity.mMapIsTouched = true;
                break;

            case MotionEvent.ACTION_UP:
                XUMapActivity.mMapIsTouched = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void OnMarkerMapCreated(Map<Location, Marker> map) {
        markerMap = map;
        Log.d("TAG", "OnMarkerMapCreated: ");
    }
}