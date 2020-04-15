package com.example.xu_map;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.Map;

public interface TaskMapComplete extends Serializable {
    void OnMarkerMapCreated(Map<Location, Marker> map);
}
