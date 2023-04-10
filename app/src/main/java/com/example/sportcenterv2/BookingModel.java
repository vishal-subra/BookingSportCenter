package com.example.sportcenterv2;

public class BookingModel {

    String DoTimestamp,DoName,DoState,DoContactNumber,DoEmail,DoDate,DoSport
            ,DoUid,DoStatusUid,DoStatus,DoTotalFee,DoTime,DoRemark,DoItem,DoCourt;

    public BookingModel() {
    }

    public BookingModel(String doTimestamp, String doName, String doState,
                        String doContactNumber, String doEmail, String doDate,
                        String doSport, String doUid, String doStatusUid,
                        String doStatus, String doTotalFee, String doTime,
                        String doRemark, String doItem, String doCourt) {
        DoTimestamp = doTimestamp;
        DoName = doName;
        DoState = doState;
        DoContactNumber = doContactNumber;
        DoEmail = doEmail;
        DoDate = doDate;
        DoSport = doSport;
        DoUid = doUid;
        DoStatusUid = doStatusUid;
        DoStatus = doStatus;
        DoTotalFee = doTotalFee;
        DoTime = doTime;
        DoRemark = doRemark;
        DoItem = doItem;
        DoCourt = doCourt;
    }

    public String getDoCourt() {
        return DoCourt;
    }

    public void setDoCourt(String doCourt) {
        DoCourt = doCourt;
    }

    public String getDoItem() {
        return DoItem;
    }

    public void setDoItem(String doItem) {
        DoItem = doItem;
    }

    public String getDoTimestamp() {
        return DoTimestamp;
    }

    public void setDoTimestamp(String doTimestamp) {
        DoTimestamp = doTimestamp;
    }

    public String getDoName() {
        return DoName;
    }

    public void setDoName(String doName) {
        DoName = doName;
    }

    public String getDoState() {
        return DoState;
    }

    public void setDoState(String doState) {
        DoState = doState;
    }

    public String getDoContactNumber() {
        return DoContactNumber;
    }

    public void setDoContactNumber(String doContactNumber) {
        DoContactNumber = doContactNumber;
    }

    public String getDoEmail() {
        return DoEmail;
    }

    public void setDoEmail(String doEmail) {
        DoEmail = doEmail;
    }

    public String getDoDate() {
        return DoDate;
    }

    public void setDoDate(String doDate) {
        DoDate = doDate;
    }

    public String getDoSport() {
        return DoSport;
    }

    public void setDoSport(String doSport) {
        DoSport = doSport;
    }

    public String getDoUid() {
        return DoUid;
    }

    public void setDoUid(String doUid) {
        DoUid = doUid;
    }

    public String getDoStatusUid() {
        return DoStatusUid;
    }

    public void setDoStatusUid(String doStatusUid) {
        DoStatusUid = doStatusUid;
    }

    public String getDoStatus() {
        return DoStatus;
    }

    public void setDoStatus(String doStatus) {
        DoStatus = doStatus;
    }

    public String getDoTotalFee() {
        return DoTotalFee;
    }

    public void setDoTotalFee(String doTotalFee) {
        DoTotalFee = doTotalFee;
    }

    public String getDoTime() {
        return DoTime;
    }

    public void setDoTime(String doTime) {
        DoTime = doTime;
    }

    public String getDoRemark() {
        return DoRemark;
    }

    public void setDoRemark(String doRemark) {
        DoRemark = doRemark;
    }
}
