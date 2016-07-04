package hdenergy.mdground.com.hdenergy.restfuls;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.enumobject.ProjectStatus;
import hdenergy.mdground.com.hdenergy.enumobject.restfuls.BusinessType;
import hdenergy.mdground.com.hdenergy.models.ProjectWork;
import hdenergy.mdground.com.hdenergy.models.UserAttendance;
import hdenergy.mdground.com.hdenergy.restfuls.bean.Device;
import hdenergy.mdground.com.hdenergy.restfuls.bean.ResponseData;
import hdenergy.mdground.com.hdenergy.utils.DeviceUtil;
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
        obj.addProperty("Suggestion",suggestion);
        asynchronousPost("SaveUserSuggestion", obj, callback);
    }

    // 获取项目列表
    public void GetProjectList(Callback<ResponseData> callback) {
        asynchronousPost("GetProjectList", null, callback);
    }

    // 项目起停炉编辑保存
    public void UpdateProject(int projectID , ProjectStatus projectStatus,
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
<<<<<<< HEAD

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
    public void GetUserAttendanceByDate(String workDate , Callback<ResponseData> callback) {
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
=======
    //分页获取历史数据统计
    public void GetProjectWorkList(int PageIndex,Callback<ResponseData> callback){
        JsonObject obj=new JsonObject();
        obj.addProperty("PageIndex",PageIndex);
        asynchronousPost("GetProjectWorkList",obj,callback);
    }

>>>>>>> 9fe7f7290273e48122e92d69443e74494d9ec7bb
}
