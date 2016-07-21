package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目实体
 * Created by yoghourt on 6/28/16.
 */
public class Project implements Parcelable {

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    private String BeginTime;       // 维修（停炉）开始时间
    private float BusinessFee;        // 业务费用
    private String CreatedTime;     // 维修（停炉）开始时间
    private float DepreciationCost;   // 项目折旧费用
    private float ElectricPrice;  // 电单价
    private String EndTime;
    private float FinanceFee;     // 财务费用
    private float FuelCost;       // 标准燃料单耗
    private float LaborCost;      // 人工费用
    private float MaintainCost;   // 维修费用
    private float ManageFee;      // 管理费用
    private int ProjectID;
    private String ProjectName;
    private int ProjectStatus;  // 停炉//正常//维修状态
    private String Remark;      // //停炉//维修//原因
    private float RemouldFee;     // 财务费用
    private String SaleType;
    private float SteamPrice;      // 蒸汽单价
    private float ThermalPrice;   // 热力单价
    private float WaterPrice;     // 水单价
    private float WorkingFee;     // 运行费用

    public Project() {

    }

    protected Project(Parcel in) {
        BeginTime = in.readString();
        BusinessFee = in.readFloat();
        CreatedTime = in.readString();
        DepreciationCost = in.readFloat();
        ElectricPrice = in.readFloat();
        EndTime = in.readString();
        FinanceFee = in.readFloat();
        FuelCost = in.readFloat();
        LaborCost = in.readFloat();
        MaintainCost = in.readFloat();
        ManageFee = in.readFloat();
        ProjectID = in.readInt();
        ProjectName = in.readString();
        ProjectStatus = in.readInt();
        Remark = in.readString();
        RemouldFee = in.readFloat();
        SaleType = in.readString();
        SteamPrice = in.readFloat();
        ThermalPrice = in.readFloat();
        WaterPrice = in.readFloat();
        WorkingFee = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BeginTime);
        dest.writeFloat(BusinessFee);
        dest.writeString(CreatedTime);
        dest.writeFloat(DepreciationCost);
        dest.writeFloat(ElectricPrice);
        dest.writeString(EndTime);
        dest.writeFloat(FinanceFee);
        dest.writeFloat(FuelCost);
        dest.writeFloat(LaborCost);
        dest.writeFloat(MaintainCost);
        dest.writeFloat(ManageFee);
        dest.writeInt(ProjectID);
        dest.writeString(ProjectName);
        dest.writeInt(ProjectStatus);
        dest.writeString(Remark);
        dest.writeFloat(RemouldFee);
        dest.writeString(SaleType);
        dest.writeFloat(SteamPrice);
        dest.writeFloat(ThermalPrice);
        dest.writeFloat(WaterPrice);
        dest.writeFloat(WorkingFee);
    }


    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public float getBusinessFee() {
        return BusinessFee;
    }

    public void setBusinessFee(float businessFee) {
        BusinessFee = businessFee;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public float getDepreciationCost() {
        return DepreciationCost;
    }

    public void setDepreciationCost(float depreciationCost) {
        DepreciationCost = depreciationCost;
    }

    public float getElectricPrice() {
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

    public float getFinanceFee() {
        return FinanceFee;
    }

    public void setFinanceFee(float financeFee) {
        FinanceFee = financeFee;
    }

    public float getFuelCost() {
        return FuelCost;
    }

    public void setFuelCost(float fuelCost) {
        FuelCost = fuelCost;
    }

    public float getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(float laborCost) {
        LaborCost = laborCost;
    }

    public float getMaintainCost() {
        return MaintainCost;
    }

    public void setMaintainCost(float maintainCost) {
        MaintainCost = maintainCost;
    }

    public float getManageFee() {
        return ManageFee;
    }

    public void setManageFee(float manageFee) {
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

    public float getRemouldFee() {
        return RemouldFee;
    }

    public void setRemouldFee(float remouldFee) {
        RemouldFee = remouldFee;
    }

    public String getSaleType() {
        return SaleType;
    }

    public void setSaleType(String saleType) {
        SaleType = saleType;
    }

    public float getSteamPrice() {
        return SteamPrice;
    }

    public void setSteamPrice(float steamPrice) {
        SteamPrice = steamPrice;
    }

    public float getThermalPrice() {
        return ThermalPrice;
    }

    public void setThermalPrice(float thermalPrice) {
        ThermalPrice = thermalPrice;
    }

    public float getWaterPrice() {
        return WaterPrice;
    }

    public void setWaterPrice(float waterPrice) {
        WaterPrice = waterPrice;
    }

    public float getWorkingFee() {
        return WorkingFee;
    }

    public void setWorkingFee(int workingFee) {
        WorkingFee = workingFee;
    }
}
