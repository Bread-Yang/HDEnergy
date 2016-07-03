package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.activity.login.LoginActivity;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.databinding.ActivitySettingBinding;
import hdenergy.mdground.com.hdenergy.views.CheckUpdateDialog;


/**
 * Created by PC on 2016-06-29.
 */

public class SettingActivity extends ToolbarActivity<ActivitySettingBinding> implements CheckUpdateDialog.OnClickUpdateListener {
    CheckUpdateDialog mDialog;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        mDialog=new CheckUpdateDialog(this,getString(R.string.current_version),getString(R.string.none));
        mDialog.setButtonListen(this);

    }

    @Override
    protected void setListener() {

    }

    //region ACGTION
    public void onPopupUpdatePrompt(View view){
       mDialog.show();
    }

    @Override
    public void clickCancel() {
        if(mDialog!=null){
        mDialog.dismiss();}
    }

    @Override
    public void clickUpdate() {
        Toast.makeText(SettingActivity.this,getString(R.string.newest_version),Toast.LENGTH_SHORT).show();
        mDialog.dismiss();
    }

    public  void onExitLogin(View view){
        Intent intent=new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.KEY_IS_EXIT_LOGIN,Constants.KEY_IS_EXIT_LOGIN);
        startActivity(intent);
        finish();
    }
    public void toFeedActivity(View view){
        Intent intent=new Intent(this,FeedBackActivity.class);
        startActivity(intent);
    }


    //endregion
}
