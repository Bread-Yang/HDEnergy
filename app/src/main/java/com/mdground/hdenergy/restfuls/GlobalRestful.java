package com.mdground.hdenergy.restfuls;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.enumobject.ProjectStatus;
import com.mdground.hdenergy.enumobject.restfuls.BusinessType;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.models.UserContacts;
import com.mdground.hdenergy.models.UserProject;
import com.mdground.hdenergy.restfuls.bean.Device;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DeviceUtil;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by yoghourt on 5/6/16.
 */
public class GlobalRestful extends BaseRestful {

    private static GlobalRestful mIntance = new GlobalRestful();

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.Global;
    }

    @Override
    protected String getHost() {
        return Constants.HOST;
    }


    private GlobalRestful() {

    }

    public static GlobalRestful getInstance() {
        if (mIntance == null) {
            mIntance = new GlobalRestful();
        }
        return mIntance;
    }

    // 用户登录
    public void LoginUser(String userPhone, String pwd, Callback<ResponseData> callback) {
        Device device = DeviceUtil.getDeviceInfo(MDGroundApplication.mInstance);
        device.setDeviceToken("abc");   // 信鸽的token, XGPushConfig.getToken(this);
        device.setDeviceID(DeviceUtil.getDeviceId());

        JsonObject obj = new JsonObject();
        obj.addProperty("UserPhone", userPhone);
        obj.addProperty("Password", pwd);
        obj.add("Device", new Gson().toJsonTree(device));

        asynchronousPost("LoginUser", obj, callback);
    }

    // 修改密码
    public void ChangeUserPassword(String userPhone, String password, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserPhone", userPhone);
        obj.addProperty("Password", password);

        asynchronousPost("ChangeUserPassword", obj, callback);
    }

    // 保存用户建议
    public void SaveUserSuggestion(String suggestion, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        //   obj.addProperty("UserPhone", userPhone);
        obj.addProperty("Suggestion", suggestion);
        asynchronousPost("SaveUserSuggestion", obj, callback);
    }

    // 获取项目列表
    public void GetProjectList(Callback<ResponseData> callback) {
        asynchronousPost("GetProjectList", null, callback);
    }

    // 项目起停炉编辑保存
    public void UpdateProject(int projectID, ProjectStatus projectStatus,
                              String beginTime, String endTime,
                              String Remark, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("ProjectID", projectID);
        obj.addProperty("ProjectStatus", projectStatus.value());
        obj.addProperty("BeginTime", beginTime);
        obj.addProperty("EndTime", endTime);
        obj.addProperty("Remark", Remark);

        asynchronousPost("UpdateProject", obj, callback);
    }

    // 获取公告列表
    public void GetBulletinList(Callback<ResponseData> callback) {
        asynchronousPost("GetBulletinList", null, callback);
    }

    // 提交数据汇报
    public void SaveProjectWork(ProjectWork projectWork, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.add("ProjectWork", new Gson().toJsonTree(projectWork));

        asynchronousPost("SaveProjectWork", obj, callback);
    }

    // 考勤汇报保存
    public void SaveUserAttendance(UserAttendance userAttendance, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.add("UserAttendance", new Gson().toJsonTree(userAttendance));

        asynchronousPost("SaveUserAttendance", obj, callback);
    }

    // 根据日期获取所有用户考勤数据
    public void GetUserAttendanceByDate(String workDate, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("WorkDate", workDate);

        asynchronousPost("GetUserAttendanceByDate", obj, callback);
    }

    // 获取部门列表
    public void GetDepartmentList(Callback<ResponseData> callback) {
        asynchronousPost("GetDepartmentList", null, callback);
    }

    // 根据部门获取用户列表
    public void GetUserListByDepartment(String department, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("Department", department);

        asynchronousPost("GetUserListByDepartment", obj, callback);
    }

    //获取所有联系人
    public void GetAllUserList(Callback<ResponseData> callback) {

        asynchronousPost("GetAllUserList", null, callback);

    }

    //获取常用联系人
    public void GetUserContactList(Callback<ResponseData> callback) {
        asynchronousPost("GetUserContactList", null, callback);
    }

    //保存常用联系人
    public void SaveUserContactList(List<UserContacts> userContactsList, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.add("UserContactsList", new Gson().toJsonTree(userContactsList));

        asynchronousPost("SaveUserContactList", obj, callback);

    }

    //获取常用项目的接口
    public void GetUserProjectList(Callback<ResponseData> callback) {
        asynchronousPost("GetUserProjectList", null, callback);
    }

    //保存项目
    public void SaveProject(Project project, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.add("Project", new Gson().toJsonTree(project));
        asynchronousPost("SaveProject", obj, callback);
    }

    //保存常用项目
    public void SaveUserProjectList(List<UserProject> userProjectList, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.add("UserProjectList", new Gson().toJsonTree(userProjectList));
        asynchronousPost("SaveUserProjectList", obj, callback);
    }

    // 获取工作类别,请求工作类别ParentID传0，请求类别下的内容，将类别的CategoryID传入ParentID
    public void GetProjectCategoryList(int parentID, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("ParentID", parentID);
        asynchronousPost("GetProjectCategoryList", obj, callback);
    }

    // 获取项目锅炉列表
    public void GetProjectFurnaceList(int projectID, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("ProjectID", projectID);
        asynchronousPost("GetProjectFurnaceList", obj, callback);
    }

    //获取项目统计信息列表
    public void GetProjectSummeryList(int pageIndex,Callback<ResponseData> callback) {

        JsonObject obj=new JsonObject();
        obj.addProperty("PageIndex",pageIndex);
        asynchronousPost("GetProjectSummeryList",obj, callback);
    }

    //分页获取历史数据统计
    public void GetProjectWorkList(int ProjectID,int PageIndex, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.add("ProjectID",new Gson().toJsonTree(ProjectID));
        obj.addProperty("PageIndex", PageIndex);
        asynchronousPost("GetProjectWorkList", obj, callback);
    }


}
