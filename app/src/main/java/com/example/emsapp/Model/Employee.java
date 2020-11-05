package com.example.emsapp.Model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String eId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userNid;
    private String userCurrentCity;
    private String userCurrentLocation;
    private String userVillage;
    private String userUpazilla;
    private String userZilla;
    private String userDivision;
    private String userPgId;
    private String userDepartment;
    private String userDesignation;
    private String userJoiningDate;
    private String userPassword;
    private String userRole;
    private String userConcern;

    public Employee() {
    }

    public Employee(String eId,
                    String userName,
                    String userEmail,
                    String userPhone,
                    String userNid,
                    String userCurrentCity,
                    String userCurrentLocation,
                    String userVillage,
                    String userUpazilla,
                    String userZilla,
                    String userDivision,
                    String userPgId,
                    String userDepartment,
                    String userDesignation,
                    String userJoiningDate,
                    String userRole,
                    String userConcern) {
        this.eId = eId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userNid = userNid;
        this.userCurrentCity = userCurrentCity;
        this.userCurrentLocation = userCurrentLocation;
        this.userVillage = userVillage;
        this.userUpazilla = userUpazilla;
        this.userZilla = userZilla;
        this.userDivision = userDivision;
        this.userPgId = userPgId;
        this.userDepartment = userDepartment;
        this.userDesignation = userDesignation;
        this.userJoiningDate = userJoiningDate;
        this.userRole = userRole;
        this.userConcern = userConcern;
    }

    public Employee(String eId,
                    String userName,
                    String userEmail,
                    String userPhone,
                    String userNid,
                    String userCurrentCity,
                    String userCurrentLocation,
                    String userVillage,
                    String userUpazilla,
                    String userZilla,
                    String userDivision,
                    String userPgId,
                    String userDepartment,
                    String userDesignation,
                    String userJoiningDate,
                    String userPassword,
                    String userRole,
                    String userConcern) {
        this.eId = eId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userNid = userNid;
        this.userCurrentCity = userCurrentCity;
        this.userCurrentLocation = userCurrentLocation;
        this.userVillage = userVillage;
        this.userUpazilla = userUpazilla;
        this.userZilla = userZilla;
        this.userDivision = userDivision;
        this.userPgId = userPgId;
        this.userDepartment = userDepartment;
        this.userDesignation = userDesignation;
        this.userJoiningDate = userJoiningDate;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userConcern = userConcern;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserNid() {
        return userNid;
    }

    public void setUserNid(String userNid) {
        this.userNid = userNid;
    }

    public String getUserCurrentCity() {
        return userCurrentCity;
    }

    public void setUserCurrentCity(String userCurrentCity) {
        this.userCurrentCity = userCurrentCity;
    }

    public String getUserCurrentLocation() {
        return userCurrentLocation;
    }

    public void setUserCurrentLocation(String userCurrentLocation) {
        this.userCurrentLocation = userCurrentLocation;
    }

    public String getUserVillage() {
        return userVillage;
    }

    public void setUserVillage(String userVillage) {
        this.userVillage = userVillage;
    }

    public String getUserUpazilla() {
        return userUpazilla;
    }

    public void setUserUpazilla(String userUpazilla) {
        this.userUpazilla = userUpazilla;
    }

    public String getUserZilla() {
        return userZilla;
    }

    public void setUserZilla(String userZilla) {
        this.userZilla = userZilla;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getUserPgId() {
        return userPgId;
    }

    public void setUserPgId(String userPgId) {
        this.userPgId = userPgId;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getUserJoiningDate() {
        return userJoiningDate;
    }

    public void setUserJoiningDate(String userJoiningDate) {
        this.userJoiningDate = userJoiningDate;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserConcern() {
        return userConcern;
    }

    public void setUserConcern(String userConcern) {
        this.userConcern = userConcern;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eId='" + eId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userNid='" + userNid + '\'' +
                ", userCurrentCity='" + userCurrentCity + '\'' +
                ", userCurrentLocation='" + userCurrentLocation + '\'' +
                ", userVillage='" + userVillage + '\'' +
                ", userUpazilla='" + userUpazilla + '\'' +
                ", userZilla='" + userZilla + '\'' +
                ", userDivision='" + userDivision + '\'' +
                ", userPgId='" + userPgId + '\'' +
                ", userDepartment='" + userDepartment + '\'' +
                ", userDesignation='" + userDesignation + '\'' +
                ", userJoiningDate='" + userJoiningDate + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userConcern='" + userConcern + '\'' +
                '}';
    }
}
