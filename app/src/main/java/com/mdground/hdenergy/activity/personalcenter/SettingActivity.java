package com.mdground.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivitySettingBinding;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.ApkUpdateUtils;
import com.mdground.hdenergy.utils.DeviceUtils;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.CheckUpdateDialog;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by RobinYang on 2016-06-29.
 */

public class SettingActivity extends ToolbarActivity<ActivitySettingBinding>
        implements CheckUpdateDialog.OnClickUpdateListener {

    private CheckUpdateDialog mDialog;

    private String mUpdateUrl = "http://fuat.yideguan.com/app/huidi.apk";

    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }

    private void downloadApk() {
        if (!canDownloadState()) {
            ViewUtils.toast(R.string.system_download_service);
            return;
        }

        ViewUtils.toast(R.string.start_download_apk);
        ApkUpdateUtils.download(this, mUpdateUrl, getResources().getString(R.string.app_name));
    }

    private boolean canDownloadState() {
        try {
            int state = this.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //region ACTION
    public void onPopupUpdatePrompt(View view) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUpdateMessageList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response.body().getContent());

                    String serverVersionName = jsonObject.getString("Version");

                    String currentVersionName = StringUtils.getVersionName();

                    if (!currentVersionName.equals(serverVersionName)) {
                        String description = jsonObject.getString("Description");

                        mDialog = new CheckUpdateDialog(SettingActivity.this, getString(R.string.newest_version_code, serverVersionName),
                                description);
                        mDialog.setButtonListen(SettingActivity.this);

                        mDialog.show();
                    } else {
                        ViewUtils.toast(R.string.newest_version);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ViewUtils.toast(R.string.newest_version);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    @Override
    public void clickCancel() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void clickUpdate() {
//        Toast.makeText(SettingActivity.this, getString(R.string.newest_version), Toast.LENGTH_SHORT).show();
        mDialog.dismiss();
        downloadApk();
    }

    public void onExitLogin(View view) {
        DeviceUtils.logoutUser(SettingActivity.this);
    }

    public void toFeedActivity(View view) {
        Intent intent = new Intent(this, FeedBackActivity.class);
        startActivity(intent);
    }
    //endregion
}
