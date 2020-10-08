package com.example.emsapp.Model.Equipment;

import java.io.Serializable;
import java.util.ArrayList;

public class Equipment implements Serializable {

    private ArrayList<String> equipmentName;
    private String pgId;

    public Equipment() {
    }

    public Equipment(ArrayList<String> equipmentName, String pgId) {
        this.equipmentName = equipmentName;
        this.pgId = pgId;
    }

    public ArrayList<String> getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(ArrayList<String> equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentName=" + equipmentName +
                ", pgId='" + pgId + '\'' +
                '}';
    }
}
