package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 公告实体
 * Created by yoghourt on 7/3/16.
 */
public class Bulletin implements Parcelable {

    private int AutoID;
    private int BannerIndex;
    private String CreatedTime;
    private int PhotoID;
    private int PhotoSID;
    private String Remark;
    private String Title;

    protected Bulletin(Parcel in) {
        AutoID = in.readInt();
        BannerIndex = in.readInt();
        CreatedTime = in.readString();
        PhotoID = in.readInt();
        PhotoSID = in.readInt();
        Remark = in.readString();
        Title = in.readString();
    }

    public static final Creator<Bulletin> CREATOR = new Creator<Bulletin>() {
        @Override
        public Bulletin createFromParcel(Parcel in) {
            return new Bulletin(in);
        }

        @Override
        public Bulletin[] newArray(int size) {
            return new Bulletin[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AutoID);
        dest.writeInt(BannerIndex);
        dest.writeString(CreatedTime);
        dest.writeInt(PhotoID);
        dest.writeInt(PhotoSID);
        dest.writeString(Remark);
        dest.writeString(Title);
    }
}
