package com.example.xu_map;

import java.util.ArrayList;

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onRouteFound(ArrayList<String> values);

}
