package com.mdground.hdenergy.models;

/**
 * 汇报流量实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFlowrate {

    private int AdjustFlow;     // 流量调整
    private int BeginFlow;      // 起始流量
    private String CreatedTime;
    private String Description; // 调整说明
    private int EndFlow;        // 截止流量
    private int FlowrateID;     // PrimaryKey
    private int WorkFurnaceID;  // WorkFurnaceID

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
