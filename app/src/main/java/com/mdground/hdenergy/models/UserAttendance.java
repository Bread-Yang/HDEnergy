package com.mdground.hdenergy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoghourt on 7/3/16.
 */

public class UserAttendance implements Parcelable{

    private float AccommodationFee;   // 住宿费
    private int AutoID;
    private String BeginTime;   // 上班时间
    private String BusinessAddress; // 出差地点
    private int CategoryID1;    // 类别标识
    private int CategoryID2;    // 子类别标识
    private String CategoryName1; // 类别名称
    private String CategoryName2; // 子类别名称
    private String CreatedTime;
    private String Department;
    private int DepartmentID;
    private String EndTime;     // 下班时间
    private int OverTime;       // 加班时间
    private String OverTimeReason; // 加班事由
    private int PhotoSID;
    private int ProjectID;
    private String ProjectName;
    private String Remark;      // 其它问题
    private int ReportUserID;   // 汇报人UserID
    private String ReportUserName; // 汇报人UserID
    private int Score;          // 打分
    private int Status;         // 上班状态
    private int TrafficTime;    // 交通耗时（分钟）
    private float Transportation; // 交通费（分）
    private int UserID;
    private String UserName;

    public UserAttendance() {

    }

    protected UserAttendance(Parcel in) {
        AccommodationFee = in.readFloat();
        AutoID = in.readInt();
        BeginTime = in.readString();
        BusinessAddress = in.readString();
        CategoryID1 = in.readInt();
        CategoryID2 = in.readInt();
        CategoryName1 = in.readString();
        CategoryName2 = in.readString();
        CreatedTime = in.readString();
        Department = in.readString();
        DepartmentID = in.readInt();
        EndTime = in.readString();
        OverTime = in.readInt();
        OverTimeReason = in.readString();
        PhotoSID = in.readInt();
        ProjectID = in.readInt();
        ProjectName = in.readString();
        Remark = in.readString();
        ReportUserID = in.readInt();
        ReportUserName = in.readString();
        Score = in.readInt();
        Status = in.readInt();
        TrafficTime = in.readInt();
        Transportation = in.readFloat();
        UserID = in.readInt();
        UserName = in.readString();
    }

    public static final Creator<UserAttendance> CREATOR = new Creator<UserAttendance>() {
        @Override
        public UserAttendance createFromParcel(Parcel in) {
            return new UserAttendance(in);
        }

        @Override
        public UserAttendance[] newArray(int size) {
            return new UserAttendance[size];
        }
    };

    public float getAccommodationFee() {
        return AccommodationFee;
    }

    public void setAccommodationFee(float accommodationFee) {
        AccommodationFee = accommodationFee;
    }

    public int getAutoID() {
        return AutoID;
    }

    public void setAutoID(int autoID) {
        AutoID = autoID;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public int getCategoryID1() {
        return CategoryID1;
    }

    public void setCategoryID1(int categoryID1) {
        CategoryID1 = categoryID1;
    }

    public int getCategoryID2() {
        return CategoryID2;
    }

    public void setCategoryID2(int categoryID2) {
        CategoryID2 = categoryID2;
    }

    public String getCategoryName1() {
        return CategoryName1;
    }

    public void setCategoryName1(String categoryName1) {
        CategoryName1 = categoryName1;
    }

    public String getCategoryName2() {
        return CategoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        CategoryName2 = categoryName2;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getOverTime() {
        return OverTime;
    }

    public void setOverTime(int overTime) {
        OverTime = overTime;
    }

    public String getOverTimeReason() {
        return OverTimeReason;
    }

    public void setOverTimeReason(String overTimeReason) {
        OverTimeReason = overTimeReason;
    }

    public int getPhotoSID() {
        return PhotoSID;
    }

    public void setPhotoSID(int photoSID) {
        PhotoSID = photoSID;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getTrafficTime() {
        return TrafficTime;
    }

    public void setTrafficTime(int trafficTime) {
        TrafficTime = trafficTime;
    }

    public float getTransportation() {
        return Transportation;
    }

    public void setTransportation(float transportation) {
        Transportation = transportation;
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

    public int getReportUserID() {
        return ReportUserID;
    }

    public void setReportUserID(int reportUserID) {
        ReportUserID = reportUserID;
    }

    public String getReportUserName() {
        return ReportUserName;
    }

    public void setReportUserName(String reportUserName) {
        ReportUserName = reportUserName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(AccommodationFee);
        parcel.writeInt(AutoID);
        parcel.writeString(BeginTime);
        parcel.writeString(BusinessAddress);
        parcel.writeInt(CategoryID1);
        parcel.writeInt(CategoryID2);
        parcel.writeString(CategoryName1);
        parcel.writeString(CategoryName2);
        parcel.writeString(CreatedTime);
        parcel.writeString(Department);
        parcel.writeInt(DepartmentID);
        parcel.writeString(EndTime);
        parcel.writeInt(OverTime);
        parcel.writeString(OverTimeReason);
        parcel.writeInt(PhotoSID);
        parcel.writeInt(ProjectID);
        parcel.writeString(ProjectName);
        parcel.writeString(Remark);
        parcel.writeInt(ReportUserID);
        parcel.writeString(ReportUserName);
        parcel.writeInt(Score);
        parcel.writeInt(Status);
        parcel.writeInt(TrafficTime);
        parcel.writeFloat(Transportation);
        parcel.writeInt(UserID);
        parcel.writeString(UserName);
    }
}
