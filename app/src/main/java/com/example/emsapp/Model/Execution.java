package com.example.emsapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Execution implements Serializable {
    private String pushId;
    private ArrayList<String> accessibleName;
    private String callerName;
    private String callerContact;
    private String callingDate;
    private String callerOrganization;
    private String callerAddress;
    private String callerQuire;
    private String callerGivenAdvice;
    private String givenRemarks;

    public Execution() {
    }

    public Execution(String pushId,
                     ArrayList<String> accessibleName,
                     String callerName,
                     String callerContact,
                     String callingDate,
                     String callerOrganization,
                     String callerAddress,
                     String callerQuire,
                     String callerGivenAdvice,
                     String givenRemarks) {
        this.pushId = pushId;
        this.accessibleName = accessibleName;
        this.callerName = callerName;
        this.callerContact = callerContact;
        this.callingDate = callingDate;
        this.callerOrganization = callerOrganization;
        this.callerAddress = callerAddress;
        this.callerQuire = callerQuire;
        this.callerGivenAdvice = callerGivenAdvice;
        this.givenRemarks = givenRemarks;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public ArrayList<String> getAccessibleName() {
        return accessibleName;
    }

    public void setAccessibleName(ArrayList<String> accessibleName) {
        this.accessibleName = accessibleName;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallerContact() {
        return callerContact;
    }

    public void setCallerContact(String callerContact) {
        this.callerContact = callerContact;
    }

    public String getCallingDate() {
        return callingDate;
    }

    public void setCallingDate(String callingDate) {
        this.callingDate = callingDate;
    }

    public String getCallerOrganization() {
        return callerOrganization;
    }

    public void setCallerOrganization(String callerOrganization) {
        this.callerOrganization = callerOrganization;
    }

    public String getCallerAddress() {
        return callerAddress;
    }

    public void setCallerAddress(String callerAddress) {
        this.callerAddress = callerAddress;
    }

    public String getCallerQuire() {
        return callerQuire;
    }

    public void setCallerQuire(String callerQuire) {
        this.callerQuire = callerQuire;
    }

    public String getCallerGivenAdvice() {
        return callerGivenAdvice;
    }

    public void setCallerGivenAdvice(String callerGivenAdvice) {
        this.callerGivenAdvice = callerGivenAdvice;
    }

    public String getGivenRemarks() {
        return givenRemarks;
    }

    public void setGivenRemarks(String givenRemarks) {
        this.givenRemarks = givenRemarks;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "pushId='" + pushId + '\'' +
                ", accessibleName=" + accessibleName +
                ", callerName='" + callerName + '\'' +
                ", callerContact='" + callerContact + '\'' +
                ", callingDate='" + callingDate + '\'' +
                ", callerOrganization='" + callerOrganization + '\'' +
                ", callerAddress='" + callerAddress + '\'' +
                ", callerQuire='" + callerQuire + '\'' +
                ", callerGivenAdvice='" + callerGivenAdvice + '\'' +
                ", givenRemarks='" + givenRemarks + '\'' +
                '}';
    }
}
