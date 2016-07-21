package com.mdground.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivitySettingBinding;
import com.mdground.hdenergy.utils.DeviceUtils;
import com.mdground.hdenergy.utils.NavUtils;
import com.mdground.hdenergy.views.CheckUpdateDialog;


/**
 * Created by PC on 2016-06-29.
 */

public class SettingActivity extends ToolbarActivity<ActivitySettingBinding>
        implements CheckUpdateDialog.OnClickUpdateListener {

    private CheckUpdateDialog mDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        mDialog = new CheckUpdateDialog(this, getString(R.string.current_version), getString(R.string.none));
        mDialog.setButtonListen(this);

    }

    @Override
    protected void setListener() {

    }

    //region ACGTION
    public void onPopupUpdatePrompt(View view) {
        mDialog.show();
    }

    @Override
    public void clickCancel() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void clickUpdate() {
        Toast.makeText(SettingActivity.this, getString(R.string.newest_version), Toast.LENGTH_SHORT).show();
        mDialog.dismiss();
    }

    public void onExitLogin(View view) {
        DeviceUtils.logoutUser();
        NavUtils.toLoginActivity(SettingActivity.this);
    }

    public void toFeedActivity(View view) {
        Intent intent = new Intent(this, FeedBackActivity.class);
        startActivity(intent);
    }
    //endregion
}
