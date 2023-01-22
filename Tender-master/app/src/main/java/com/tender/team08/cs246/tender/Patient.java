package com.tender.team08.cs246.tender;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Point;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Entity
public class Patient implements Comparable<Patient>, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "zip")
    private String zip;

    @ColumnInfo(name = "appointments")
    private String appointments;

    @ColumnInfo(name = "pin")
    private String pin;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "points")
    private String points;

    public Patient(String email, String firstName, String lastName, String phone, String street, String city, String state, String zip, String appointments, String pin, String points) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.appointments = appointments;
        this.pin = pin;
        this.points = points;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() { return state;}

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    public String getPin() { return pin; }

    public void setPin(String pin) { this.pin = pin; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes;}

    public String getFullName() { return (firstName + " " + lastName); }

    public String getAddress() { return (street + "\n" + city + ", " + state + " " + zip); }

    public String getContact() { return (phone + " " + email); }


    @Override
    public int compareTo(Patient patient) {
        return (this.lastName.toLowerCase()).compareTo(patient.lastName.toLowerCase());
    }

    public String getPoints(){
        return points;
    }

    public List<Point> getPointsList(){
        Gson gson = new Gson();

        if(points.equals("")){
            return new ArrayList<Point>();
        }

        List<Point> pointsList = new ArrayList<>();
        List<String> pointsToString = Arrays.asList(points.split("&"));
        for (String point : pointsToString){
            if(!point.equals("")){
                pointsList.add(gson.fromJson(point,Point.class));
            }
        }
        return pointsList;
    }

    public void setPoints(List<Point> points) {
        Gson gson = new Gson();

        String pointsString = "";
        for (Point point : points){
            pointsString += gson.toJson(point) + "&";
        }
        this.points = pointsString;
    }


}
