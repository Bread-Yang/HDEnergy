package com.mdground.hdenergy.activity.advice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.databinding.ActivityReasonableAdviceBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 6/29/16.
 */
public class ReasonableAdviceActivity extends ToolbarActivity<ActivityReasonableAdviceBinding> {

    private AlertDialog mAlertDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_reasonable_advice;
    }

    @Override
    protected void initData() {
        tvRight.setText(R.string.submit);

        // 初始化对话框
        mAlertDialog = ViewUtils.createAlertDialog(this, getString(R.string.confirm_to_submit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String suggestion = mDataBinding.etSuggestion.getText().toString().trim();
                        saveUserSuggestionRequest(suggestion);
                    }
                });
    }

    @Override
    protected void setListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suggestion = mDataBinding.etSuggestion.getText().toString().trim();
                if (StringUtils.isEmpty(suggestion)) {
                    ViewUtils.toast(R.string.can_not_empty);
                    return;
                }
                mAlertDialog.show();
            }
        });
    }

    //region SERVER
    public void saveUserSuggestionRequest(String suggestion) {
        String userPhone = MDGroundApplication.sInstance.getLoginUser().getUserPhone();
        GlobalRestful.getInstance().SaveUserSuggestion(suggestion,
                new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (ResponseCode.isSuccess(response.body())) {
                            ViewUtils.toast(R.string.submit_success);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }
    //endregion
}
