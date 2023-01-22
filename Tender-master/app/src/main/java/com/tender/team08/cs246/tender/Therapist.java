package com.tender.team08.cs246.tender;

import java.io.Serializable;

/**
 * Created by Wearth on 3/2/2018.
 */

public class Therapist implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String appointments;
    private String pin;

    public Therapist(String email, String firstName, String lastName, String phone, String street, String city, String state, String zip, String appointments, String pin) {
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

    public String getFullName() { return (firstName + " " + lastName); }

    public String getAddress() { return (street + "\n" + city + ", " + state + " " + zip); }

    public String getContact() { return (phone + " " + email); }
}