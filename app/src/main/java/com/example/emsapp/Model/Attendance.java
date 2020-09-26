package com.example.emsapp.Model;

public class Attendance {
    private String pgId;
    private String date;
    private String startTime;
    private String startLocation;
    private String finishTime;
    private String finishLocation;

    public Attendance() {
    }

    public Attendance(String pgId,
                      String date,
                      String startTime,
                      String startLocation) {
        this.pgId = pgId;
        this.date = date;
        this.startTime = startTime;
        this.startLocation = startLocation;
    }

    public Attendance(String pgId,
                      String date,
                      String startTime,
                      String startLocation,
                      String finishTime,
                      String finishLocation) {
        this.pgId = pgId;
        this.date = date;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.finishTime = finishTime;
        this.finishLocation = finishLocation;
    }

    public Attendance(String finishTime, String finishLocation) {
        this.finishTime = finishTime;
        this.finishLocation = finishLocation;
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
}
