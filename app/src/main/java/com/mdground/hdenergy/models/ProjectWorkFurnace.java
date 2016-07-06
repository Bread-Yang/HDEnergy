package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 锅炉汇报实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFurnace implements Parcelable{

    private int FurnaceID;      // 锅炉标识
    private int WorkFurnaceID;  // PrimaryKey，标识
    private int WorkID;         // 数据汇报标识
    private String FurnaceName; // 锅炉名称
    private int ProjectID;      // 项目名称
    private int WorkingHour;    // 开炉时长（以分作为单位）
    private int Electricity1;   // 电量1
    private int Electricity2;   // 电量2
    private int Electricity3;   // 电量3
    private int ElectricitySingleCost; // 电单耗
    private int Water1;         // 水量1
    private int Water2;         // 水量2
    private int Water3;         // 水量3
    private int WaterSingleCost;// 水单耗
    private String CreatedTime;
    private List<ProjectWorkFlowrate> ProjectWorkFlowrateList; // 流量列表
    private List<ProjectWorkFuel> ProjectWorkFuelList;  // 燃料列表

    public ProjectWorkFurnace() {

    }


    protected ProjectWorkFurnace(Parcel in) {
        FurnaceID = in.readInt();
        WorkFurnaceID = in.readInt();
        WorkID = in.readInt();
        FurnaceName = in.readString();
        ProjectID = in.readInt();
        WorkingHour = in.readInt();
        Electricity1 = in.readInt();
        Electricity2 = in.readInt();
        Electricity3 = in.readInt();
        ElectricitySingleCost = in.readInt();
        Water1 = in.readInt();
        Water2 = in.readInt();
        Water3 = in.readInt();
        WaterSingleCost = in.readInt();
        CreatedTime = in.readString();
        ProjectWorkFlowrateList = in.createTypedArrayList(ProjectWorkFlowrate.CREATOR);
        ProjectWorkFuelList = in.createTypedArrayList(ProjectWorkFuel.CREATOR);
    }

    public static final Creator<ProjectWorkFurnace> CREATOR = new Creator<ProjectWorkFurnace>() {
        @Override
        public ProjectWorkFurnace createFromParcel(Parcel in) {
            return new ProjectWorkFurnace(in);
        }

        @Override
        public ProjectWorkFurnace[] newArray(int size) {
            return new ProjectWorkFurnace[size];
        }
    };

    public int getFurnaceID() {
        return FurnaceID;
    }

    public void setFurnaceID(int furnaceID) {
        FurnaceID = furnaceID;
    }

    public int getWorkFurnaceID() {
        return WorkFurnaceID;
    }

    public void setWorkFurnaceID(int workFurnaceID) {
        WorkFurnaceID = workFurnaceID;
    }

    public int getWorkID() {
        return WorkID;
    }

    public void setWorkID(int workID) {
        WorkID = workID;
    }

    public String getFurnaceName() {
        return FurnaceName;
    }

    public void setFurnaceName(String furnaceName) {
        FurnaceName = furnaceName;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public int getWorkingHour() {
        return WorkingHour;
    }

    public void setWorkingHour(int workingHour) {
        WorkingHour = workingHour;
    }

    public int getElectricity1() {
        return Electricity1;
    }

    public void setElectricity1(int electricity1) {
        Electricity1 = electricity1;
    }

    public int getElectricity2() {
        return Electricity2;
    }

    public void setElectricity2(int electricity2) {
        Electricity2 = electricity2;
    }

    public int getElectricity3() {
        return Electricity3;
    }

    public void setElectricity3(int electricity3) {
        Electricity3 = electricity3;
    }

    public int getElectricitySingleCost() {
        return ElectricitySingleCost;
    }

    public void setElectricitySingleCost(int electricitySingleCost) {
        ElectricitySingleCost = electricitySingleCost;
    }

    public int getWater1() {
        return Water1;
    }

    public void setWater1(int water1) {
        Water1 = water1;
    }

    public int getWater2() {
        return Water2;
    }

    public void setWater2(int water2) {
        Water2 = water2;
    }

    public int getWater3() {
        return Water3;
    }

    public void setWater3(int water3) {
        Water3 = water3;
    }

    public int getWaterSingleCost() {
        return WaterSingleCost;
    }

    public void setWaterSingleCost(int waterSingleCost) {
        WaterSingleCost = waterSingleCost;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public List<ProjectWorkFlowrate> getProjectWorkFlowrateList() {
        return ProjectWorkFlowrateList;
    }

    public void setProjectWorkFlowrateList(List<ProjectWorkFlowrate> projectWorkFlowrateList) {
        ProjectWorkFlowrateList = projectWorkFlowrateList;
    }

    public List<ProjectWorkFuel> getProjectWorkFuelList() {
        return ProjectWorkFuelList;
    }

    public void setProjectWorkFuelList(List<ProjectWorkFuel> projectWorkFuelList) {
        ProjectWorkFuelList = projectWorkFuelList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(FurnaceID);
        parcel.writeInt(WorkFurnaceID);
        parcel.writeInt(WorkID);
        parcel.writeString(FurnaceName);
        parcel.writeInt(ProjectID);
        parcel.writeInt(WorkingHour);
        parcel.writeInt(Electricity1);
        parcel.writeInt(Electricity2);
        parcel.writeInt(Electricity3);
        parcel.writeInt(ElectricitySingleCost);
        parcel.writeInt(Water1);
        parcel.writeInt(Water2);
        parcel.writeInt(Water3);
        parcel.writeInt(WaterSingleCost);
        parcel.writeString(CreatedTime);
        parcel.writeTypedList(ProjectWorkFlowrateList);
        parcel.writeTypedList(ProjectWorkFuelList);
    }
}

