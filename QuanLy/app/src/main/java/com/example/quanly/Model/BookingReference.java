package com.example.quanly.Model;

import java.io.Serializable;

public class BookingReference implements Serializable {
    private String dateIn, dateOut, email, nameResidence, countRoom,countPeople, phone;
    private String status;
    private String id;
    public BookingReference(String id,String dateIn, String dateOut, String email, String phone, String nameResidence, String countRoom,String countPeople,String status) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.email = email;
        this.nameResidence = nameResidence;
        this.countRoom = countRoom;
        this.countPeople = countPeople;
        this.phone=phone;
        this.status=status;
        this.id=id;
    }
    public BookingReference() {
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNameResidence(String nameResidence) {
        this.nameResidence = nameResidence;
    }

    public void setCountRoom(String countRoom) {
        this.countRoom = countRoom;
    }

    public void setCountPeople(String countPeople) {
        this.countPeople = countPeople;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public String getEmail() {
        return email;
    }

    public String getNameResidence() {
        return nameResidence;
    }

    public String getCountRoom() {
        return countRoom;
    }

    public String getCountPeople() {
        return countPeople;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
