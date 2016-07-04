package com.mdground.hdenergy.models;

/**
 * 公告实体
 * Created by yoghourt on 7/3/16.
 */
public class Bulletin {

    private int AutoID;
    private int BannerIndex;
    private String CreatedTime;
    private int PhotoID;
    private int PhotoSID;
    private String Remark;
    private String Title;

    public int getAutoID() {
        return AutoID;
    }

    public void setAutoID(int autoID) {
        AutoID = autoID;
    }

    public int getBannerIndex() {
        return BannerIndex;
    }

    public void setBannerIndex(int bannerIndex) {
        BannerIndex = bannerIndex;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getPhotoID() {
        return PhotoID;
    }

    public void setPhotoID(int photoID) {
        PhotoID = photoID;
    }

    public int getPhotoSID() {
        return PhotoSID;
    }

    public void setPhotoSID(int photoSID) {
        PhotoSID = photoSID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
