package com.mdground.hdenergy.models;

/**
 * 设备实体
 * Created by yoghourt on 7/3/16.
 */
public class Device {

    private String CreatedTime;
    private int DeviceID;
    private String DeviceModel;
    private String DeviceToken;
    private int Platform;

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int deviceID) {
        DeviceID = deviceID;
    }

    public String getDeviceModel() {
        return DeviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        DeviceModel = deviceModel;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public int getPlatform() {
        return Platform;
    }

    public void setPlatform(int platform) {
        Platform = platform;
    }
}
