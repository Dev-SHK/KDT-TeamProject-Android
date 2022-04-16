package com.example.kdt_teamproject_mobile_kiosk_final.model;

public class EnterpriseUserAccount { // DTO 역할을 수행

    private String epEmailID, epPwd, epConfirmPwd, epCompanyName, epPhoneNum, epUserName, edtTxtAddress, epRegisterNum, epPostNum, epUserBirthday, epStartDate;

    public EnterpriseUserAccount(String epEmailID, String epPwd, String epConfirmPwd, String epCompanyName, String epPhoneNum, String epUserName, String edtTxtAddress, String epRegisterNum, String epPostNum, String epUserBirthday, String epStartDate) {
        this.epEmailID = epEmailID;
        this.epPwd = epPwd;
        this.epConfirmPwd = epConfirmPwd;
        this.epCompanyName = epCompanyName;
        this.epPhoneNum = epPhoneNum;
        this.epUserName = epUserName;
        this.edtTxtAddress = edtTxtAddress;
        this.epRegisterNum = epRegisterNum;
        this.epPostNum = epPostNum;
        this.epUserBirthday = epUserBirthday;
        this.epStartDate = epStartDate;
    }

    public EnterpriseUserAccount() {

    }

    public String getEpEmailID() {
        return epEmailID;
    }

    public void setEpEmailID(String epEmailID) {
        this.epEmailID = epEmailID;
    }

    public String getEpPwd() {
        return epPwd;
    }

    public void setEpPwd(String epPwd) {
        this.epPwd = epPwd;
    }

    public String getEpConfirmPwd() {
        return epConfirmPwd;
    }

    public void setEpConfirmPwd(String epConfirmPwd) {
        this.epConfirmPwd = epConfirmPwd;
    }

    public String getEpCompanyName() {
        return epCompanyName;
    }

    public void setEpCompanyName(String epCompanyName) {
        this.epCompanyName = epCompanyName;
    }

    public String getEpPhoneNum() {
        return epPhoneNum;
    }

    public void setEpPhoneNum(String epPhoneNum) {
        this.epPhoneNum = epPhoneNum;
    }

    public String getEpUserName() {
        return epUserName;
    }

    public void setEpUserName(String epUserName) {
        this.epUserName = epUserName;
    }

    public String getEdtTxtAddress() {
        return edtTxtAddress;
    }

    public void setEdtTxtAddress(String edtTxtAddress) {
        this.edtTxtAddress = edtTxtAddress;
    }

    public String getEpRegisterNum() {
        return epRegisterNum;
    }

    public void setEpRegisterNum(String epRegisterNum) {
        this.epRegisterNum = epRegisterNum;
    }

    public String getEpPostNum() {
        return epPostNum;
    }

    public void setEpPostNum(String epPostNum) {
        this.epPostNum = epPostNum;
    }

    public String getEpUserBirthday() {
        return epUserBirthday;
    }

    public void setEpUserBirthday(String epUserBirthday) {
        this.epUserBirthday = epUserBirthday;
    }

    public String getEpStartDate() {
        return epStartDate;
    }

    public void setEpStartDate(String epStartDate) {
        this.epStartDate = epStartDate;
    }
}