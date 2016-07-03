package hdenergy.mdground.com.hdenergy.models;

import java.util.List;

/**
 * 汇报燃料实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFuel {

    private int AdjustInventory;    // 燃料调整
    private String CreatedTime;
    private int CurrentInventory;   // 本期库存
    private int FuelID;     // 燃料标识
    private String FuelName;// 燃料名称
    private int PreviousInventory;  // 上期库存
    private List<ProjectFuelWarehouse> ProjectFuelWarehouseList; // 进料量列表
    private int WorkFuelID;
    private int WorkFurnaceID;

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
}
