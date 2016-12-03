package com.jason9075.womanyhackathon.model;

/**
 * Created by jason9075 on 2016/12/3.
 */

public class StudentLocationData {

    private String id;
    private String studentName;
    private double latitude; //經度
    private double longitude;//緯度

    private String address;
    private String date;

    public StudentLocationData(String id,String studentName) {
        this.id = id;
        this.studentName = studentName;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }
}
