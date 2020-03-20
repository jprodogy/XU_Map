package com.example.xu_map;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String buildName;
    private int buildNum;
    //Todo: Change Arraylist to list
    private List<String> street;
    private double longitude;
    private double latitude;
    private List<String> purpose;
    private List<String> keyWords;



    public Location(String gpsProvider){

    }

    public Location(String buildName, int buildNum, String[] street, double longitude, double latitude, String[] category, String[] department){
        this.street = new ArrayList();
        this.purpose = new ArrayList<>();
        this.keyWords = new ArrayList<>();

        this.buildName = buildName;
        this.buildNum = buildNum;
        this.longitude = longitude;
        this.latitude = latitude;

        addStreet(street);
        addPurpose(category);
        addKeywords(department);

    }

    public int getBuildNum() {
        return buildNum;
    }

    public List<String> getStreet() {
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

    public List<String> getPurpose() {
        return purpose;
    }

    public List<String> getKeywords() {
        return keyWords;
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

    public void addPurpose(String[] str){

        for (int i = 0; i < str.length; i++) {

            purpose.add(str[i]);
        }
    }

    public void addKeywords(String[] str){
        for (int i = 0; i < str.length; i++) {
            keyWords.add(str[i]);
        }
    }

}
