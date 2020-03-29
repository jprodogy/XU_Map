package com.example.xu_map;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class TouchableWrapper extends FrameLayout {

    public TouchableWrapper(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("Touch", "Map has been touched ");
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                MarkerDemoActivity.mMapIsTouched = true;
                break;

            case MotionEvent.ACTION_UP:
                MarkerDemoActivity.mMapIsTouched = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}