package com.mdground.hdenergy.application;

import android.app.Application;

import com.mdground.hdenergy.BuildConfig;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.utils.FileUtils;
import com.socks.library.KLog;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import cn.smssdk.SMSSDK;

/**
 * Created by yoghourt on 5/6/16.
 */
public class MDGroundApplication extends Application {

    /**
     * 对外提供整个应用生命周期的Context
     **/
    public static MDGroundApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        KLog.init(BuildConfig.DEBUG);

        SMSSDK.initSDK(this, Constants.SMS_APP_KEY, Constants.SMS_APP_SECRECT);

        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                KLog.e("信鸽注册成功");
            }

            @Override
            public void onFail(Object o, int i, String s) {
                KLog.e("信鸽注册失败");
            }
        });

//        initExceptionHandler();
    }

//    private void initDataBase() {
//        DatabaseOpenHelper helper = new DatabaseOpenHelper(this, Constants.DATABASE_NAME, null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        mDaoSession = daoMaster.newSession();
//    }

    public UserInfo getSaveUser() {
        return (UserInfo) FileUtils.getObject(Constants.KEY_SAVE_LOGIN_USER_INFO);
    }

    public UserInfo getLoginUser() {
        return (UserInfo) FileUtils.getObject(Constants.KEY_ALREADY_LOGIN_USER_INFO);
    }

    public void setLoginUserInfo(UserInfo loginUserInfo) {
        FileUtils.setObject(Constants.KEY_ALREADY_LOGIN_USER_INFO, loginUserInfo);
    }

    public void updateLoginUserInfo(UserInfo userInfo) {
        UserInfo loginUserInfo = getLoginUser();
        userInfo.setDeviceID(loginUserInfo.getDeviceID());      // 设置之前的deviceID
        userInfo.setServiceToken(loginUserInfo.getServiceToken());  // 设置之前的ServiceToken
        setLoginUserInfo(userInfo);
    }
}
