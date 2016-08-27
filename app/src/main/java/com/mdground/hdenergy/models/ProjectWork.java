package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 数据汇报实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWork implements Parcelable {

    private String ReportedTime;
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
    private float DayElectricityCost;  //电耗（电单耗）---新增
    private float DayWaterCost;     // 水耗（水单耗）
    private float DayFuelCost;      // 单耗（燃料单耗）
    private float FuelCost;         //标准燃料单耗
    private float Profit;

    public float getFuelCost() {
        return FuelCost;
    }

    public void setFuelCost(float fuelCost) {
        FuelCost = fuelCost;
    }

    public float getDayElectricityCost() {
        return DayElectricityCost;
    }

    public void setDayElectricityCost(float dayElectricityCost) {
        DayElectricityCost = dayElectricityCost;
    }

    public float getDayWaterCost() {
        return DayWaterCost;
    }

    public void setDayWaterCost(float dayWaterCost) {
        DayWaterCost = dayWaterCost;
    }

    public float getDayFuelCost() {
        return DayFuelCost;
    }

    public void setDayFuelCost(float dayFuelCost) {
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

    public String getReportedTime() {
        return ReportedTime;
    }

    public void setReportedTime(String reportedTime) {
        ReportedTime = reportedTime;
    }

    public float getProfit() {
        return Profit;
    }

    public void setProfit(float profit) {
        Profit = profit;
    }

    public ProjectWork() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ReportedTime);
        dest.writeString(this.CreatedTime);
        dest.writeFloat(this.DailyExpense);
        dest.writeString(this.ExpenseDetail);
        dest.writeInt(this.ProjectID);
        dest.writeString(this.ProjectName);
        dest.writeTypedList(this.ProjectWorkFurnaceList);
        dest.writeString(this.Remark);
        dest.writeString(this.SaleType);
        dest.writeInt(this.UserID);
        dest.writeString(this.UserName);
        dest.writeInt(this.WorkID);
        dest.writeFloat(this.DayElectricityCost);
        dest.writeFloat(this.DayWaterCost);
        dest.writeFloat(this.DayFuelCost);
        dest.writeFloat(this.FuelCost);
        dest.writeFloat(this.Profit);
    }

    protected ProjectWork(Parcel in) {
        this.ReportedTime = in.readString();
        this.CreatedTime = in.readString();
        this.DailyExpense = in.readFloat();
        this.ExpenseDetail = in.readString();
        this.ProjectID = in.readInt();
        this.ProjectName = in.readString();
        this.ProjectWorkFurnaceList = in.createTypedArrayList(ProjectWorkFurnace.CREATOR);
        this.Remark = in.readString();
        this.SaleType = in.readString();
        this.UserID = in.readInt();
        this.UserName = in.readString();
        this.WorkID = in.readInt();
        this.DayElectricityCost = in.readFloat();
        this.DayWaterCost = in.readFloat();
        this.DayFuelCost = in.readFloat();
        this.FuelCost = in.readFloat();
        this.Profit = in.readFloat();
    }

    public static final Creator<ProjectWork> CREATOR = new Creator<ProjectWork>() {
        @Override
        public ProjectWork createFromParcel(Parcel source) {
            return new ProjectWork(source);
        }

        @Override
        public ProjectWork[] newArray(int size) {
            return new ProjectWork[size];
        }
    };
}
