package hdenergy.mdground.com.hdenergy.application;

import android.app.Application;

import cn.smssdk.SMSSDK;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.models.UserInfo;
import hdenergy.mdground.com.hdenergy.utils.FileUtils;

/**
 * Created by yoghourt on 5/6/16.
 */
public class MDGroundApplication extends Application {

    /**
     * 对外提供整个应用生命周期的Context
     **/
    public static MDGroundApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        SMSSDK.initSDK(this, Constants.SMS_APP_KEY, Constants.SMS_APP_SECRECT);

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

}
