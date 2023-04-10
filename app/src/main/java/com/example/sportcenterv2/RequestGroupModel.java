package com.example.sportcenterv2;

public class RequestGroupModel {

    String ReqHostUid,ReqUserName,ReqSportName,ReqLocation,ReqTime,ReqDate,ReqStatus,ReqMaxPeople,
            ReqCourt,ReqTimestamp,ReqAskedUid,ReqHostStatus,ReqAskedStatus,ReqAskedName,ReqContactNumber;

    public RequestGroupModel() {
    }

    public RequestGroupModel(String reqHostUid, String reqUserName, String reqSportName, String reqLocation,
                             String reqTime, String reqDate, String reqStatus, String reqMaxPeople, String reqCourt,
                             String reqTimestamp, String reqAskedUid, String  reqHostStatus, String reqAskedStatus,
                             String reqAskedName, String reqContactNumber) {
        ReqHostUid = reqHostUid;
        ReqUserName = reqUserName;
        ReqSportName = reqSportName;
        ReqLocation = reqLocation;
        ReqTime = reqTime;
        ReqDate = reqDate;
        ReqStatus = reqStatus;
        ReqMaxPeople = reqMaxPeople;
        ReqCourt = reqCourt;
        ReqTimestamp = reqTimestamp;
        ReqAskedUid = reqAskedUid;
        ReqHostStatus = reqHostStatus;
        ReqAskedStatus = reqAskedStatus;
        ReqAskedName = reqAskedName;
        ReqContactNumber = reqContactNumber;
    }

    public String getReqContactNumber() {
        return ReqContactNumber;
    }

    public void setReqContactNumber(String reqContactNumber) {
        ReqContactNumber = reqContactNumber;
    }

    public String getReqAskedName() {
        return ReqAskedName;
    }

    public void setReqAskedName(String reqAskedName) {
        ReqAskedName = reqAskedName;
    }

    public String getReqAskedStatus() {
        return ReqAskedStatus;
    }

    public void setReqAskedStatus(String reqAskedStatus) {
        ReqAskedStatus = reqAskedStatus;
    }

    public String getReqHostStatus() {
        return ReqHostStatus;
    }

    public void setReqHostStatus(String reqHostStatus) {
        ReqHostStatus = reqHostStatus;
    }

    public String getReqAskedUid() {
        return ReqAskedUid;
    }

    public void setReqAskedUid(String reqAskedUid) {
        ReqAskedUid = reqAskedUid;
    }

    public String getReqHostUid() {
        return ReqHostUid;
    }

    public void setReqHostUid(String reqHostUid) {
        ReqHostUid = reqHostUid;
    }

    public String getReqUserName() {
        return ReqUserName;
    }

    public void setReqUserName(String reqUserName) {
        ReqUserName = reqUserName;
    }

    public String getReqSportName() {
        return ReqSportName;
    }

    public void setReqSportName(String reqSportName) {
        ReqSportName = reqSportName;
    }

    public String getReqLocation() {
        return ReqLocation;
    }

    public void setReqLocation(String reqLocation) {
        ReqLocation = reqLocation;
    }

    public String getReqTime() {
        return ReqTime;
    }

    public void setReqTime(String reqTime) {
        ReqTime = reqTime;
    }

    public String getReqDate() {
        return ReqDate;
    }

    public void setReqDate(String reqDate) {
        ReqDate = reqDate;
    }

    public String getReqStatus() {
        return ReqStatus;
    }

    public void setReqStatus(String reqStatus) {
        ReqStatus = reqStatus;
    }

    public String getReqMaxPeople() {
        return ReqMaxPeople;
    }

    public void setReqMaxPeople(String reqMaxPeople) {
        ReqMaxPeople = reqMaxPeople;
    }

    public String getReqCourt() {
        return ReqCourt;
    }

    public void setReqCourt(String reqCourt) {
        ReqCourt = reqCourt;
    }

    public String getReqTimestamp() {
        return ReqTimestamp;
    }

    public void setReqTimestamp(String reqTimestamp) {
        ReqTimestamp = reqTimestamp;
    }
}
