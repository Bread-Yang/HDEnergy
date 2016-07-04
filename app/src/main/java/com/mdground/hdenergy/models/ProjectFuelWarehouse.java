package com.mdground.hdenergy.models;

/**
 * 进料量实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectFuelWarehouse {

    private int Amount;         // 送货量
    private String CreatedTime;
    private String PlateNumber; // 车牌号
    private int ProjectID;
    private String Remark;      // 备注
    private String Supplier;    // 供应商
    private int WarehouseID;    // primarykey
    private int WorkID;

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
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
}
