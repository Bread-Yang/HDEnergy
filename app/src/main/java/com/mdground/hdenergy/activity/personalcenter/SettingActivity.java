package com.mdground.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivitySettingBinding;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
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
    }

    @Override
    protected void setListener() {

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

                    float serverVersionCode = jsonObject.getLong("Version");

                    int currentVersionCode = StringUtils.getVersionCode();

                    if (serverVersionCode > currentVersionCode) {
                        String description = jsonObject.getString("Description");

                        mDialog = new CheckUpdateDialog(SettingActivity.this, getString(R.string.newest_version_code, String.valueOf(serverVersionCode)),
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
//        mDialog.dismiss();
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
