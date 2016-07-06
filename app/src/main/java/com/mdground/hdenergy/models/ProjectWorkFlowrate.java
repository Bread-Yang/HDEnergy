package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 汇报流量实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFlowrate implements Parcelable {

    private int AdjustFlow;     // 流量调整
    private int BeginFlow;      // 起始流量
    private String CreatedTime;
    private String Description; // 调整说明
    private int EndFlow;        // 截止流量
    private int FlowrateID;     // PrimaryKey
    private int WorkFurnaceID;  // WorkFurnaceID

    public ProjectWorkFlowrate() {

    }

    protected ProjectWorkFlowrate(Parcel in) {
        AdjustFlow = in.readInt();
        BeginFlow = in.readInt();
        CreatedTime = in.readString();
        Description = in.readString();
        EndFlow = in.readInt();
        FlowrateID = in.readInt();
        WorkFurnaceID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AdjustFlow);
        dest.writeInt(BeginFlow);
        dest.writeString(CreatedTime);
        dest.writeString(Description);
        dest.writeInt(EndFlow);
        dest.writeInt(FlowrateID);
        dest.writeInt(WorkFurnaceID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectWorkFlowrate> CREATOR = new Creator<ProjectWorkFlowrate>() {
        @Override
        public ProjectWorkFlowrate createFromParcel(Parcel in) {
            return new ProjectWorkFlowrate(in);
        }

        @Override
        public ProjectWorkFlowrate[] newArray(int size) {
            return new ProjectWorkFlowrate[size];
        }
    };

    public int getAdjustFlow() {
        return AdjustFlow;
    }

    public void setAdjustFlow(int adjustFlow) {
        AdjustFlow = adjustFlow;
    }

    public int getBeginFlow() {
        return BeginFlow;
    }

    public void setBeginFlow(int beginFlow) {
        BeginFlow = beginFlow;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getEndFlow() {
        return EndFlow;
    }

    public void setEndFlow(int endFlow) {
        EndFlow = endFlow;
    }

    public int getFlowrateID() {
        return FlowrateID;
    }

    public void setFlowrateID(int flowrateID) {
        FlowrateID = flowrateID;
    }

    public int getWorkFurnaceID() {
        return WorkFurnaceID;
    }

    public void setWorkFurnaceID(int workFurnaceID) {
        WorkFurnaceID = workFurnaceID;
    }
}
