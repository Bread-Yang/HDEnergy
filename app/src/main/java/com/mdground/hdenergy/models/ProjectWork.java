package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 数据汇报实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWork implements Parcelable {

    private String CreatedTime;
    private float DailyExpense;   // 项目日常费用
    private String ExpenseDetail; // 费用明细
    private int ProjectID;      // 项目标识
    private String ProjectName; // 项目名称
    private List<ProjectWorkFurnace> ProjectWorkFurnaceList;    // 锅炉列表
    private String Remark;      // 其他问题
    private String SaleType;    // 项目名称
    private int UserID;
    private String UserName;
    private int WorkID;
    private String DayElectricityCost;  //电耗（电单耗）---新增
    private String DayWaterCost;  // 水耗（水单耗）
    private String DayFuelCost;   // 单耗（燃料单耗）
    public int FuelCost;//标准燃料单耗

    protected ProjectWork(Parcel in) {
        CreatedTime = in.readString();
        DailyExpense = in.readFloat();
        ExpenseDetail = in.readString();
        ProjectID = in.readInt();
        ProjectName = in.readString();
        ProjectWorkFurnaceList = in.createTypedArrayList(ProjectWorkFurnace.CREATOR);
        Remark = in.readString();
        SaleType = in.readString();
        UserID = in.readInt();
        UserName = in.readString();
        WorkID = in.readInt();
        DayElectricityCost = in.readString();
        DayWaterCost = in.readString();
        DayFuelCost = in.readString();
        FuelCost = in.readInt();
    }

    public static final Creator<ProjectWork> CREATOR = new Creator<ProjectWork>() {
        @Override
        public ProjectWork createFromParcel(Parcel in) {
            return new ProjectWork(in);
        }

        @Override
        public ProjectWork[] newArray(int size) {
            return new ProjectWork[size];
        }
    };

    public int getFuelCost() {
        return FuelCost;
    }

    public void setFuelCost(int fuelCost) {
        FuelCost = fuelCost;
    }

    public String getDayElectricityCost() {
        return DayElectricityCost;
    }

    public void setDayElectricityCost(String dayElectricityCost) {
        DayElectricityCost = dayElectricityCost;
    }

    public String getDayWaterCost() {
        return DayWaterCost;
    }

    public void setDayWaterCost(String dayWaterCost) {
        DayWaterCost = dayWaterCost;
    }

    public String getDayFuelCost() {
        return DayFuelCost;
    }

    public void setDayFuelCost(String dayFuelCost) {
        DayFuelCost = dayFuelCost;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public float getDailyExpense() {
        return DailyExpense;
    }

    public void setDailyExpense(float dailyExpense) {
        DailyExpense = dailyExpense;
    }

    public String getExpenseDetail() {
        return ExpenseDetail;
    }

    public void setExpenseDetail(String expenseDetail) {
        ExpenseDetail = expenseDetail;
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

    public List<ProjectWorkFurnace> getProjectWorkFurnaceList() {
        return ProjectWorkFurnaceList;
    }

    public void setProjectWorkFurnaceList(List<ProjectWorkFurnace> projectWorkFurnaceList) {
        ProjectWorkFurnaceList = projectWorkFurnaceList;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSaleType() {
        return SaleType;
    }

    public void setSaleType(String saleType) {
        SaleType = saleType;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getWorkID() {
        return WorkID;
    }

    public void setWorkID(int workID) {
        WorkID = workID;
    }

    public ProjectWork() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CreatedTime);
        dest.writeFloat(DailyExpense);
        dest.writeString(ExpenseDetail);
        dest.writeInt(ProjectID);
        dest.writeString(ProjectName);
        dest.writeTypedList(ProjectWorkFurnaceList);
        dest.writeString(Remark);
        dest.writeString(SaleType);
        dest.writeInt(UserID);
        dest.writeString(UserName);
        dest.writeInt(WorkID);
        dest.writeString(DayElectricityCost);
        dest.writeString(DayWaterCost);
        dest.writeString(DayFuelCost);
        dest.writeInt(FuelCost);
    }
}
