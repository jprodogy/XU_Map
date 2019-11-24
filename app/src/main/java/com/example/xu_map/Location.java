package com.example.xu_map;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Location {
    private String buildName;
    private int buildNum;
    private ArrayList<String> street;
    private double longitude;
    private double latitude;
    private ArrayList<String> category;
    private ArrayList<String> department;
    private Context context;



    public Location(){

    }

    public Location(String buildName, int buildNum, String[] street, double longitude, double latitude, String[] category, String[] department){
        this.street = new ArrayList();
        this.category = new ArrayList<>();
        this.department = new ArrayList<>();

        this.buildName = buildName;
        this.buildNum = buildNum;
        this.longitude = longitude;
        this.latitude = latitude;
        addStreet(street);
        addCategory(category);
        addDprt(department);


    }

    public int getBuildNum() {
        return buildNum;
    }

    public ArrayList<String> getStreet() {
        return street;
    }

    public String getBuildName() {
        return buildName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public ArrayList<String> getDepartment() {
        return department;
    }

    public void setBuildNum(int buildNum) {
        this.buildNum = buildNum;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void addStreet(String[] str){
        for (int i = 0; i < str.length; i++) {
            street.add(str[i]);
        }
    }

    public void addCategory(String[] str){
        for (int i = 0; i < str.length; i++) {
            street.add(str[i]);
        }
    }

    public void addDprt(String[] str){
        for (int i = 0; i < str.length; i++) {
            street.add(str[i]);
        }
    }

}
