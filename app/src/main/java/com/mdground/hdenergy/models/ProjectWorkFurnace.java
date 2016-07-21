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
    private float Electricity1;   // 电量1
    private float Electricity2;   // 电量2
    private float Electricity3;   // 电量3
    private float ElectricitySingleCost; // 电单耗
    private float Water1;         // 水量1
    private float Water2;         // 水量2
    private float Water3;         // 水量3
    private float WaterSingleCost;// 水单耗
    private String CreatedTime;
    private List<ProjectWorkFlowrate> ProjectWorkFlowrateList; // 流量列表
    private List<ProjectWorkFuel> ProjectWorkFuelList;  // 燃料列表
    private String Description;



    public ProjectWorkFurnace() {

    }


    protected ProjectWorkFurnace(Parcel in) {
        FurnaceID = in.readInt();
        WorkFurnaceID = in.readInt();
        WorkID = in.readInt();
        FurnaceName = in.readString();
        ProjectID = in.readInt();
        WorkingHour = in.readInt();
        Electricity1 = in.readFloat();
        Electricity2 = in.readFloat();
        Electricity3 = in.readFloat();
        ElectricitySingleCost = in.readInt();
        Water1 = in.readFloat();
        Water2 = in.readFloat();
        Water3 = in.readFloat();
        WaterSingleCost = in.readFloat();
        CreatedTime = in.readString();
        ProjectWorkFlowrateList = in.createTypedArrayList(ProjectWorkFlowrate.CREATOR);
        ProjectWorkFuelList = in.createTypedArrayList(ProjectWorkFuel.CREATOR);
        Description=in.readString();
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

    public float getElectricity1() {
        return Electricity1;
    }

    public void setElectricity1(float electricity1) {
        Electricity1 = electricity1;
    }

    public float getElectricity2() {
        return Electricity2;
    }

    public void setElectricity2(float electricity2) {
        Electricity2 = electricity2;
    }

    public float getElectricity3() {
        return Electricity3;
    }

    public void setElectricity3(float electricity3) {
        Electricity3 = electricity3;
    }

    public float getElectricitySingleCost() {
        return ElectricitySingleCost;
    }

    public void setElectricitySingleCost(int electricitySingleCost) {
        ElectricitySingleCost = electricitySingleCost;
    }

    public float getWater1() {
        return Water1;
    }

    public void setWater1(float water1) {
        Water1 = water1;
    }

    public float getWater2() {
        return Water2;
    }

    public void setWater2(float water2) {
        Water2 = water2;
    }

    public float getWater3() {
        return Water3;
    }

    public void setWater3(float water3) {
        Water3 = water3;
    }

    public float getWaterSingleCost() {
        return WaterSingleCost;
    }

    public void setWaterSingleCost(float waterSingleCost) {
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
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
        parcel.writeFloat(Electricity1);
        parcel.writeFloat(Electricity2);
        parcel.writeFloat(Electricity3);
        parcel.writeFloat(ElectricitySingleCost);
        parcel.writeFloat(Water1);
        parcel.writeFloat(Water2);
        parcel.writeFloat(Water3);
        parcel.writeFloat(WaterSingleCost);
        parcel.writeString(CreatedTime);
        parcel.writeTypedList(ProjectWorkFlowrateList);
        parcel.writeTypedList(ProjectWorkFuelList);
        parcel.writeString(Description);
    }
}

