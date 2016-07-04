package com.mdground.hdenergy.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoghourt on 5/12/16.
 */
public class MDImage extends BaseObservable implements Parcelable {

    public static final Creator<MDImage> CREATOR = new Creator<MDImage>() {
        @Override
        public MDImage createFromParcel(Parcel in) {
            return new MDImage(in);
        }

        @Override
        public MDImage[] newArray(int size) {
            return new MDImage[size];
        }
    };
    private int AutoID;
    private int PhotoID;
    private int PhotoSID;
    private int UserID;
    private long duration;
    private String imageLocalPath;
    private long lastUpdateAt;

    public MDImage() {
    }

    public MDImage(String imageLocalPath, long lastUpdateAt, long duration) {
        this.imageLocalPath = imageLocalPath;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
    }

    protected MDImage(Parcel in) {
        AutoID = in.readInt();
        PhotoID = in.readInt();
        PhotoSID = in.readInt();
        UserID = in.readInt();
        imageLocalPath = in.readString();
        duration = in.readLong();
        lastUpdateAt = in.readLong();
    }

    public boolean hasPhotoSID() {
        return PhotoSID != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AutoID);
        dest.writeInt(PhotoID);
        dest.writeInt(PhotoSID);
        dest.writeInt(UserID);
        dest.writeString(imageLocalPath);
        dest.writeLong(duration);
        dest.writeLong(lastUpdateAt);
    }

    public int getAutoID() {
        return AutoID;
    }

    public void setAutoID(int autoID) {
        AutoID = autoID;
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

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(long lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

}
