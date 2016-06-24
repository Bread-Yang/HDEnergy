package hdenergy.mdground.com.hdenergy.application;

import android.app.Application;

import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.models.User;
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

//        initExceptionHandler();
    }

//    private void initDataBase() {
//        DatabaseOpenHelper helper = new DatabaseOpenHelper(this, Constants.DATABASE_NAME, null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        mDaoSession = daoMaster.newSession();
//    }

    public User getLoginUser() {
        return (User) FileUtils.getObject(Constants.KEY_ALREADY_LOGIN_USER);
    }

    public void setLoginUser(User loginUser) {
        FileUtils.setObject(Constants.KEY_ALREADY_LOGIN_USER, loginUser);
    }

}
