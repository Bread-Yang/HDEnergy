package com.mdground.hdenergy.models;

/**
 * Created by yoghourt on 7/19/16.
 */

public class FuelCategory {

    private int FuelID;
    private String FuelName;
    private int Inventory;
    private int ProjectID;

    public int getFuelID() {
        return FuelID;
    }

    public void setFuelID(int fuelID) {
        FuelID = fuelID;
    }

    public String getFuelName() {
        return FuelName;
    }

    public void setFuelName(String fuelName) {
        FuelName = fuelName;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }
}
