package com.example.xu_map;

import java.util.List;

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onRouteFound(List<String> values);

}
