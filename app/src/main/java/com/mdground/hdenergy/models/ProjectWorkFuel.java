package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 汇报燃料实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFuel implements Parcelable{

    private float AdjustInventory;    // 燃料调整
    private String CreatedTime;
    private float CurrentInventory;   // 本期库存
    private int FuelID;     // 燃料标识
    private String FuelName;// 燃料名称
    private float PreviousInventory;  // 上期库存
    private List<ProjectFuelWarehouse> ProjectFuelWarehouseList; // 进料量列表
    private int WorkFuelID;
    private int WorkFurnaceID;
    private String AdjustReason;

    public ProjectWorkFuel() {

    }

    protected ProjectWorkFuel(Parcel in) {
        AdjustInventory = in.readFloat();
        CreatedTime = in.readString();
        CurrentInventory = in.readFloat();
        FuelID = in.readInt();
        FuelName = in.readString();
        PreviousInventory = in.readFloat();
        ProjectFuelWarehouseList = in.createTypedArrayList(ProjectFuelWarehouse.CREATOR);
        WorkFuelID = in.readInt();
        WorkFurnaceID = in.readInt();
        AdjustReason=in.readString();
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

    public String getAdjustReason() {
        return AdjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        AdjustReason = adjustReason;
    }
    public float getAdjustInventory() {
        return AdjustInventory;
    }

    public void setAdjustInventory(float adjustInventory) {
        AdjustInventory = adjustInventory;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public float getCurrentInventory() {
        return CurrentInventory;
    }

    public void setCurrentInventory(float currentInventory) {
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

    public float getPreviousInventory() {
        return PreviousInventory;
    }

    public void setPreviousInventory(float previousInventory) {
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
        parcel.writeFloat(AdjustInventory);
        parcel.writeString(CreatedTime);
        parcel.writeFloat(CurrentInventory);
        parcel.writeInt(FuelID);
        parcel.writeString(FuelName);
        parcel.writeFloat(PreviousInventory);
        parcel.writeTypedList(ProjectFuelWarehouseList);
        parcel.writeInt(WorkFuelID);
        parcel.writeInt(WorkFurnaceID);
        parcel.writeString(AdjustReason);
    }
}
