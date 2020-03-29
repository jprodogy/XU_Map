/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.xu_map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This shows how to place markers on a map.
 */
public class MarkerDemoActivity extends AppCompatActivity implements
        OnMarkerClickListener,
        OnInfoWindowClickListener,
        OnMarkerDragListener,
        OnSeekBarChangeListener,
        OnInfoWindowLongClickListener,
        OnInfoWindowCloseListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        TaskLoadedCallback,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener, SearchView.OnQueryTextListener {




    /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;
        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            /*
            render(marker, mWindow);
            return mWindow;
             */
            return null;
        }


        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);

            return mContents;
        }



        private void render(Marker marker, View view) {

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }
    private static final String TAG = "MarkerDemoActivity";
    private GoogleMap mMap;
    private Objects objList;
    private GraphMap graphMap;
    private Map< Marker, List<String>> markerMap;
    private Map<Location, Marker> markerMap2;
    private Marker mLastSelectedMarker;
    private final List<Marker> mMarkerRainbow = new ArrayList<Marker>();
    private final Random mRandom = new Random();
    private Button catSelectBut;
    private String[] listItems;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private List<Polyline> mPolyList;
    private List<Marker> markerPoints;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private Polyline currentPolyline;
    private SearchView searhBar;
    public static boolean mMapIsTouched = false;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_demo);

        /*
        Intent startServiceIntent = new Intent(MarkerDemoActivity.this, MyBackgroundService.class);
        startService(startServiceIntent);
        */


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        new OnMapAndViewReadyListener(mapFragment, this);

        catSelectBut = findViewById(R.id.action_select_category);
        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];
        objList = MainActivity.obj;
        graphMap = MainActivity.gm;
        markerMap = new HashMap<>();
        markerMap2 = new HashMap<>();
        markerPoints =  new ArrayList<>();
        mPolyList = new ArrayList<>();
        searhBar = findViewById(R.id.search_bar);
        searhBar.setOnQueryTextListener(this);

        for (Location loc: objList.getAllLocs()){
            Log.d(TAG, loc.getBuildName() + " " + loc.getKeywords().toString());
        }


    }

    public void CreateDropdownMenu(){

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MarkerDemoActivity.this);
            mBuilder.setTitle(R.string.dialog_title);
            mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                    if(isChecked){
                        mUserItems.add(position);
                    }else{
                        mUserItems.remove((Integer.valueOf(position)));
                    }
                }
            });

            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    List<String> item = new ArrayList<>();

                    for (int i = 0; i < mUserItems.size(); i++) {
                        item.add(listItems[mUserItems.get(i)]);
                    }

                    for (Map.Entry<Marker, List<String>> entry : markerMap.entrySet()){
                        entry.getKey().setVisible(listCheck(item, entry.getValue()));

                    }
                }
            });

            mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    for (int i = 0; i < checkedItems.length; i++) {
                        checkedItems[i] = false;
                        mUserItems.clear();
                    }

                    for (Map.Entry<Marker, List<String>> entry : markerMap.entrySet()) {
                        entry.getKey().setVisible(true);
                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
    }

    public Boolean listCheck(List<String> list1, List<String> list2){
        for (int i = 0; i < list1.size(); i++){
            if (list2.contains(list1.get(i))){
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());


        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Double min = Double.MAX_VALUE;
                Marker minMarker = null;

                for (Marker keys: markerMap.keySet()) {
                    Double dist = distance2(latLng, keys.getPosition());

                    if (dist < min){
                        min = dist;
                        minMarker = keys;
                    }
                }

                onClearPath();
                for (Marker keys: markerMap.keySet()){
                    keys.setVisible(false);
                }
                minMarker.setVisible(true);
            }

        });

        enableMyLocation();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMarkersToMap() {
        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        mMap.setContentDescription("Map with lots of markers.");

        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        for(Location loc: objList.getAllLocs()){
            LatLng coord = new LatLng(loc.getLongitude(), loc.getLatitude());

            String pur = loc.getPurpose().stream()
                    .collect(Collectors.joining(" "));

            bounds.include(coord);

            IconGenerator mIconGenerator = new IconGenerator(this);
            mIconGenerator.setStyle(IconGenerator.STYLE_GREEN);
            mIconGenerator.setContentRotation(90);
            Bitmap iconBitmap = mIconGenerator.makeIcon(WordUtils.capitalize(loc.getBuildName()));


            Marker tempMark = mMap.addMarker(new MarkerOptions()
                    //.icon(BitmapDescriptorFactory.fromBitmap(iconBitmap))
                    .position(coord)
                    .title(WordUtils.capitalize(loc.getBuildName()))
                    .snippet("Building #"+ loc.getBuildNum()  +"\n" + "Purpose: "  + pur)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            Log.d(TAG, "addMarkersToMap: " + tempMark.getId());

            markerMap.put(tempMark, loc.getPurpose());
            markerMap2.put(loc, tempMark);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));

    }

    /**
     * Demonstrates converting a {@link Drawable} to a {@link BitmapDescriptor},
     * for use as a marker icon.
     */
    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /** Called when the Clear button is clicked. */
    public void onClearMap() {
        if (!checkReady()) {
            return;
        }
        for (Map.Entry<Marker, List<String>> entry : markerMap.entrySet()) {
            entry.getKey().setVisible(false);
        }
        for (Polyline poly: mPolyList){
            poly.remove();
        }
        Log.d(TAG, "what ");
        for (Marker mp: markerPoints){
            mp.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        markerPoints.removeAll(markerPoints);

    }

    /** Called when the Reset button is clicked. */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResetMap() {
        if (!checkReady()) {
            return;
        }
        for (Map.Entry<Marker, List<String>> entry : markerMap.entrySet()) {
            entry.getKey().setVisible(true);
        }
        addMarkersToMap();
        onClearPath();
    }

    public void onClearPath() {
        for (Polyline poly: mPolyList){
            poly.remove();
        }
        for (Marker mp: markerPoints){
            mp.hideInfoWindow();
            mp.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        markerPoints.removeAll(markerPoints);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!checkReady()) {
            return;
        }
        float rotation = seekBar.getProgress();
        for (Marker marker : mMarkerRainbow) {
            marker.setRotation(rotation);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Do nothing.
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Do nothing.
    }



    //
    // Marker related listeners.
    //

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if(!markerPoints.contains(marker)){
            markerPoints.add(marker);
        }
        if (markerPoints.size() >= 2 && markerPoints.get(markerPoints.size()-1).isVisible() &&
                markerPoints.get(markerPoints.size()-2).isVisible()){
            Marker origin = markerPoints.get(markerPoints.size()-2);
            Marker dest = markerPoints.get(markerPoints.size()-1);
            FetchURL FetchUrl = new FetchURL(MarkerDemoActivity.this);
            origin.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            dest.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            String url = getUrl(origin.getPosition(), dest.getPosition(), "driving");

            FetchUrl.execute(url,"driving");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(origin.getPosition()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        }


        // Markers have a z-index that is settable and gettable.
        float zIndex = marker.getZIndex() + 1.0f;
        marker.setZIndex(zIndex);
        Toast.makeText(this, marker.getTitle() + " z-index set to " + zIndex,
                Toast.LENGTH_SHORT).show();

        mLastSelectedMarker = marker;
        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).

        return false;
    }






    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        mPolyList.add(currentPolyline);

        Log.d(TAG, "onTaskDone: ");
    }

    @Override
    public void onRouteFound(ArrayList<String> routeList) {
        Log.d("stuff", routeList.toString());
    }

    public void addDirections(){
        //ArrayList<String> directions = FetchUrl.listDirections;

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        //Toast.makeText(this, "Close Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        Toast.makeText(this, "Info Window long click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "onMarkerDrag: ");
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull android.location.Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    public static double distance2(LatLng point1, LatLng point2) {
        double lat1 = point1.latitude;
        double lon1 = point1.longitude;
        double lat2 = point2.latitude;
        double lon2 = point2.longitude;

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_select_category:
                CreateDropdownMenu();
                return true;
            case R.id.action_clear:
                onClearMap();
                return true;
            case R.id.action_reset:
                onResetMap();
                return true;
            case R.id.action_remove_path:
                onClearPath();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        onClearPath();
        s = s.toLowerCase();
        markerPoints = MarkerSearch(s.split(","));
        for (int i = 0; i < markerPoints.size(); i++) {
            Log.d(TAG, markerPoints.get(i).getTitle());
        }

        if(!s.contains(",")){
            if(markerPoints.size() == 1){
                for (Marker markers : markerMap2.values()){
                    markers.setVisible(false);
                }
                markerPoints.get(0).setVisible(true);
                markerPoints.get(0).showInfoWindow();
            }else{
                for (Marker markers : markerMap2.values()){
                    markers.setVisible(false);
                }
                for(Marker mp: markerPoints){
                    mp.setVisible(true);
                }
            }
        }else {

            if (markerPoints.size() >= 2) {
                for (int i = 1; i < markerPoints.size(); i++) {

                    Marker origin = markerPoints.get(i - 1);
                    Marker dest = markerPoints.get(i);

                    if (!origin.isVisible() || !dest.isVisible()){
                        origin.setVisible(true);
                        dest.setVisible(true);

                    }

                    origin.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    dest.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    String url = getUrl(origin.getPosition(), dest.getPosition(), "driving");
                    FetchURL FetchUrl = new FetchURL(MarkerDemoActivity.this);
                    FetchUrl.execute(url, "driving");

                }
            }
        }
        markerPoints = new ArrayList<>();
        return false;
    }


    public ArrayList<Marker> MarkerSearch(String[] keywords){
        onClearPath();
        ArrayList<Marker> foundMarkers = new ArrayList<>();
        for(String key: keywords){
            key = key.trim();

            Log.d(TAG, key);
            try{
                int val = Integer.valueOf(key);
                Location loc = null;
                if((loc = objList.getLocViaNum(val)) != null){
                    foundMarkers.add(markerMap2.get(loc));
                }
            }catch (Exception e) {
                Location loc = null;
                ArrayList<Location> locs = null;
                if((loc = objList.getLocation(key)) != null){
                    foundMarkers.add(markerMap2.get(loc));

                }else if((locs = objList.getAllViaKey(key)) != null){
                    for (Location val: locs){
                        foundMarkers.add(markerMap2.get(val));
                    }
                }
            }
        }
        return foundMarkers;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

}