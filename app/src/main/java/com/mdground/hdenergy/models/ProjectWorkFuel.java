package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 汇报燃料实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFuel implements Parcelable{

    private int AdjustInventory;    // 燃料调整
    private String CreatedTime;
    private int CurrentInventory;   // 本期库存
    private int FuelID;     // 燃料标识
    private String FuelName;// 燃料名称
    private int PreviousInventory;  // 上期库存
    private List<ProjectFuelWarehouse> ProjectFuelWarehouseList; // 进料量列表
    private int WorkFuelID;
    private int WorkFurnaceID;

    public ProjectWorkFuel() {

    }

    protected ProjectWorkFuel(Parcel in) {
        AdjustInventory = in.readInt();
        CreatedTime = in.readString();
        CurrentInventory = in.readInt();
        FuelID = in.readInt();
        FuelName = in.readString();
        PreviousInventory = in.readInt();
        ProjectFuelWarehouseList = in.createTypedArrayList(ProjectFuelWarehouse.CREATOR);
        WorkFuelID = in.readInt();
        WorkFurnaceID = in.readInt();
    }

    public static final Creator<ProjectWorkFuel> CREATOR = new Creator<ProjectWorkFuel>() {
        @Override
        public ProjectWorkFuel createFromParcel(Parcel in) {
            return new ProjectWorkFuel(in);
        }

        @Override
        public ProjectWorkFuel[] newArray(int size) {
            return new ProjectWorkFuel[size];
        }
    };

    public int getAdjustInventory() {
        return AdjustInventory;
    }

    public void setAdjustInventory(int adjustInventory) {
        AdjustInventory = adjustInventory;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getCurrentInventory() {
        return CurrentInventory;
    }

    public void setCurrentInventory(int currentInventory) {
        CurrentInventory = currentInventory;
    }

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

    public int getPreviousInventory() {
        return PreviousInventory;
    }

    public void setPreviousInventory(int previousInventory) {
        PreviousInventory = previousInventory;
    }

    public List<ProjectFuelWarehouse> getProjectFuelWarehouseList() {
        return ProjectFuelWarehouseList;
    }

    public void setProjectFuelWarehouseList(List<ProjectFuelWarehouse> projectFuelWarehouseList) {
        ProjectFuelWarehouseList = projectFuelWarehouseList;
    }

    public int getWorkFuelID() {
        return WorkFuelID;
    }

    public void setWorkFuelID(int workFuelID) {
        WorkFuelID = workFuelID;
    }

    public int getWorkFurnaceID() {
        return WorkFurnaceID;
    }

    public void setWorkFurnaceID(int workFurnaceID) {
        WorkFurnaceID = workFurnaceID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(AdjustInventory);
        parcel.writeString(CreatedTime);
        parcel.writeInt(CurrentInventory);
        parcel.writeInt(FuelID);
        parcel.writeString(FuelName);
        parcel.writeInt(PreviousInventory);
        parcel.writeTypedList(ProjectFuelWarehouseList);
        parcel.writeInt(WorkFuelID);
        parcel.writeInt(WorkFurnaceID);
    }
}
