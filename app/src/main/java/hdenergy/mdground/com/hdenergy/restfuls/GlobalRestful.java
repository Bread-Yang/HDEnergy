package hdenergy.mdground.com.hdenergy.restfuls;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.enumobject.restfuls.BusinessType;
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
    public void SaveUserSuggestion(String userPhone, String suggestion, Callback<ResponseData> callback) {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserPhone ", userPhone);
        obj.addProperty("Suggestion ", suggestion);

        asynchronousPost("SaveUserSuggestion", obj, callback);
    }

    // 获取项目列表
    public void GetProjectList(Callback<ResponseData> callback) {
        asynchronousPost("GetProjectList", null, callback);
    }

    // 获取公告列表
    public void GetBulletinList(Callback<ResponseData> callback) {
        asynchronousPost("GetBulletinList", null, callback);
    }
}
