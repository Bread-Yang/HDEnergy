package hdenergy.mdground.com.hdenergy.models;

import java.io.Serializable;

/**
 * 用户实体
 * Created by yoghourt on 7/3/16.
 */
public class UserInfo implements Serializable {

    private int AuthorityLevel;
    private String CreatedTime;
    private String Department;
    private int DeviceID;
    private String LoginTime;
    private String Password;
    private int PhotoID;
    private int PhotoSID;
    private String ServiceToken;
    private int UserID;
    private String UserName;
    private String UserNo;
    private String UserPhone;
    private int UserRole;

    public int getAuthorityLevel() {
        return AuthorityLevel;
    }

    public void setAuthorityLevel(int authorityLevel) {
        AuthorityLevel = authorityLevel;
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

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int deviceID) {
        DeviceID = deviceID;
    }

    public String getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(String loginTime) {
        LoginTime = loginTime;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getServiceToken() {
        return ServiceToken;
    }

    public void setServiceToken(String serviceToken) {
        ServiceToken = serviceToken;
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

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) {
        UserNo = userNo;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int userRole) {
        UserRole = userRole;
    }
}
