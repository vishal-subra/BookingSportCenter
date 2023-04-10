package com.example.sportcenterv2;

public class GroupModel {

    String MaUid,MaUserName,MaSportName,MaLocation,MaTime,MaStatus,MaMaxPeople,MaCourt,MaTimestamp,MaDate;

    public GroupModel() {
    }

    public GroupModel(String maUid, String maUserName, String maSportName, String maLocation, String maTime,
                      String maStatus, String maMaxPeople, String maCourt, String maTimestamp, String maDate) {
        MaUid = maUid;
        MaUserName = maUserName;
        MaSportName = maSportName;
        MaLocation = maLocation;
        MaTime = maTime;
        MaStatus = maStatus;
        MaMaxPeople = maMaxPeople;
        MaCourt = maCourt;
        MaTimestamp = maTimestamp;
        MaDate = maDate;
    }

    public String getMaDate() {
        return MaDate;
    }

    public void setMaDate(String maDate) {
        MaDate = maDate;
    }

    public String getMaUid() {
        return MaUid;
    }

    public void setMaUid(String maUid) {
        MaUid = maUid;
    }

    public String getMaUserName() {
        return MaUserName;
    }

    public void setMaUserName(String maUserName) {
        MaUserName = maUserName;
    }

    public String getMaSportName() {
        return MaSportName;
    }

    public void setMaSportName(String maSportName) {
        MaSportName = maSportName;
    }

    public String getMaLocation() {
        return MaLocation;
    }

    public void setMaLocation(String maLocation) {
        MaLocation = maLocation;
    }

    public String getMaTime() {
        return MaTime;
    }

    public void setMaTime(String maTime) {
        MaTime = maTime;
    }

    public String getMaStatus() {
        return MaStatus;
    }

    public void setMaStatus(String maStatus) {
        MaStatus = maStatus;
    }

    public String getMaMaxPeople() {
        return MaMaxPeople;
    }

    public void setMaMaxPeople(String maMaxPeople) {
        MaMaxPeople = maMaxPeople;
    }

    public String getMaCourt() {
        return MaCourt;
    }

    public void setMaCourt(String maCourt) {
        MaCourt = maCourt;
    }

    public String getMaTimestamp() {
        return MaTimestamp;
    }

    public void setMaTimestamp(String maTimestamp) {
        MaTimestamp = maTimestamp;
    }
}
