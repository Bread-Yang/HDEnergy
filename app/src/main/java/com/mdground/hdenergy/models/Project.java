package com.mdground.hdenergy.models;

/**
 * 项目实体
 * Created by yoghourt on 6/28/16.
 */
public class Project {

    private String BeginTime;       // 维修（停炉）开始时间
    private int BusinessFee;        // 业务费用
    private String CreatedTime;     // 维修（停炉）开始时间
    private int DepreciationCost;   // 项目折旧费用
    private int ElectricPrice;  // 电单价
    private String EndTime;
    private int FinanceFee;     // 财务费用
    private int FuelCost;       // 标准燃料单耗
    private int LaborCost;      // 人工费用
    private int MaintainCost;   // 维修费用
    private int ManageFee;      // 管理费用
    private int ProjectID;
    private String ProjectName;
    private int ProjectStatus;  // 停炉//正常//维修状态
    private String Remark;      // //停炉//维修//原因
    private int RemouldFee;     // 财务费用
    private int SteamPrice;      // 蒸汽单价
    private int ThermalPrice;   // 热力单价
    private int WaterPrice;     // 水单价
    private int WorkingFee;     // 运行费用

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public int getBusinessFee() {
        return BusinessFee;
    }

    public void setBusinessFee(int businessFee) {
        BusinessFee = businessFee;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getDepreciationCost() {
        return DepreciationCost;
    }

    public void setDepreciationCost(int depreciationCost) {
        DepreciationCost = depreciationCost;
    }

    public int getElectricPrice() {
        return ElectricPrice;
    }

    public void setElectricPrice(int electricPrice) {
        ElectricPrice = electricPrice;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getFinanceFee() {
        return FinanceFee;
    }

    public void setFinanceFee(int financeFee) {
        FinanceFee = financeFee;
    }

    public int getFuelCost() {
        return FuelCost;
    }

    public void setFuelCost(int fuelCost) {
        FuelCost = fuelCost;
    }

    public int getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(int laborCost) {
        LaborCost = laborCost;
    }

    public int getMaintainCost() {
        return MaintainCost;
    }

    public void setMaintainCost(int maintainCost) {
        MaintainCost = maintainCost;
    }

    public int getManageFee() {
        return ManageFee;
    }

    public void setManageFee(int manageFee) {
        ManageFee = manageFee;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public int getProjectStatus() {
        return ProjectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        ProjectStatus = projectStatus;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getRemouldFee() {
        return RemouldFee;
    }

    public void setRemouldFee(int remouldFee) {
        RemouldFee = remouldFee;
    }

    public int getSteamPrice() {
        return SteamPrice;
    }

    public void setSteamPrice(int steamPrice) {
        SteamPrice = steamPrice;
    }

    public int getThermalPrice() {
        return ThermalPrice;
    }

    public void setThermalPrice(int thermalPrice) {
        ThermalPrice = thermalPrice;
    }

    public int getWaterPrice() {
        return WaterPrice;
    }

    public void setWaterPrice(int waterPrice) {
        WaterPrice = waterPrice;
    }

    public int getWorkingFee() {
        return WorkingFee;
    }

    public void setWorkingFee(int workingFee) {
        WorkingFee = workingFee;
    }
}
