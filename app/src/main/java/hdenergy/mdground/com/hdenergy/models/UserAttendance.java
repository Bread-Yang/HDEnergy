package hdenergy.mdground.com.hdenergy.models;

/**
 * Created by yoghourt on 7/3/16.
 */

public class UserAttendance {

    private int AutoID;
    private int UserID;
    private String UserName;
    private int ProjectID;
    private int Status;         // 上班状态
    private String BeginTime;   // 上班时间
    private String EndTime;     // 下班时间
    private int OverTime;       // 加班时间
    private String OverTimeReason; // 加班事由
    private int CategoryID1;    // 类别标识
    private String CategoryName1; // 类别名称
    private int CategoryID2;    // 子类别标识
    private String CategoryName2; // 子类别名称
    private String BusinessAddress; // 出差地点
    private int Transportation; // 交通费（分）
    private int TrafficTime;    // 交通耗时（分钟）
    private int AccommodationFee;   // 住宿费
    private String Remark;      // 其它问题
    private int Score;          // 打分
    private String CreatedTime;

    public int getAutoID() {
        return AutoID;
    }

    public void setAutoID(int autoID) {
        AutoID = autoID;
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

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
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

    public int getCategoryID1() {
        return CategoryID1;
    }

    public void setCategoryID1(int categoryID1) {
        CategoryID1 = categoryID1;
    }

    public String getCategoryName1() {
        return CategoryName1;
    }

    public void setCategoryName1(String categoryName1) {
        CategoryName1 = categoryName1;
    }

    public int getCategoryID2() {
        return CategoryID2;
    }

    public void setCategoryID2(int categoryID2) {
        CategoryID2 = categoryID2;
    }

    public String getCategoryName2() {
        return CategoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        CategoryName2 = categoryName2;
    }

    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public int getTransportation() {
        return Transportation;
    }

    public void setTransportation(int transportation) {
        Transportation = transportation;
    }

    public int getTrafficTime() {
        return TrafficTime;
    }

    public void setTrafficTime(int trafficTime) {
        TrafficTime = trafficTime;
    }

    public int getAccommodationFee() {
        return AccommodationFee;
    }

    public void setAccommodationFee(int accommodationFee) {
        AccommodationFee = accommodationFee;
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

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }
}
