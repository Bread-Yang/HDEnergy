package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 进料量实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectFuelWarehouse implements Parcelable {

    private float Amount;         // 送货量
    private String CreatedTime;
    private String FuelName;
    private String PlateNumber; // 车牌号
    private int ProjectID;
    private String Remark;      // 备注
    private String Supplier;    // 供应商
    private int WarehouseID;    // primarykey
    private int WorkID;

    public ProjectFuelWarehouse() {

    }

    protected ProjectFuelWarehouse(Parcel in) {
        Amount = in.readFloat();
        CreatedTime = in.readString();
        FuelName = in.readString();
        PlateNumber = in.readString();
        ProjectID = in.readInt();
        Remark = in.readString();
        Supplier = in.readString();
        WarehouseID = in.readInt();
        WorkID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(Amount);
        dest.writeString(CreatedTime);
        dest.writeString(FuelName);
        dest.writeString(PlateNumber);
        dest.writeInt(ProjectID);
        dest.writeString(Remark);
        dest.writeString(Supplier);
        dest.writeInt(WarehouseID);
        dest.writeInt(WorkID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectFuelWarehouse> CREATOR = new Creator<ProjectFuelWarehouse>() {
        @Override
        public ProjectFuelWarehouse createFromParcel(Parcel in) {
            return new ProjectFuelWarehouse(in);
        }

        @Override
        public ProjectFuelWarehouse[] newArray(int size) {
            return new ProjectFuelWarehouse[size];
        }
    };

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public int getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        WarehouseID = warehouseID;
    }

    public int getWorkID() {
        return WorkID;
    }

    public void setWorkID(int workID) {
        WorkID = workID;
    }

    public String getFuelName() {
        return FuelName;
    }

    public void setFuelName(String fuelName) {
        FuelName = fuelName;
    }
}
