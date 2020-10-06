package com.example.emsapp.Model;

import java.io.Serializable;

public class Attendance implements Serializable {
    private String pushId;
    private String pgId;
    private String date;
    private String startTime;
    private String startLocation;
    private String finishTime;
    private String finishLocation;
    private String movementReason;

    public Attendance() {
    }

    public Attendance(String pgId,
                      String date,
                      String startTime,
                      String startLocation,
                      String pushId) {
        this.pgId = pgId;
        this.date = date;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.pushId = pushId;
    }



    public Attendance(String pgId,
                      String date,
                      String startTime,
                      String startLocation,
                      String finishTime,
                      String finishLocation,
                      String pushId) {
        this.pgId = pgId;
        this.date = date;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.finishTime = finishTime;
        this.finishLocation = finishLocation;
        this.pushId = pushId;
    }

    public Attendance(String pgId,
                      String date,
                      String startTime,
                      String startLocation,
                      String movementReason,
                      String pushId) {
        this.pgId = pgId;
        this.date = date;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.movementReason = movementReason;
        this.pushId = pushId;
    }

    public Attendance(String pgId,
                      String date,
                      String startTime,
                      String startLocation,
                      String finishTime,
                      String finishLocation,
                      String movementReason,
                      String pushId) {
        this.pgId = pgId;
        this.date = date;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.finishTime = finishTime;
        this.finishLocation = finishLocation;
        this.movementReason = movementReason;
        this.pushId = pushId;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishLocation() {
        return finishLocation;
    }

    public void setFinishLocation(String finishLocation) {
        this.finishLocation = finishLocation;
    }

    public String getMovementReason() {
        return movementReason;
    }

    public void setMovementReason(String movementReason) {
        this.movementReason = movementReason;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
