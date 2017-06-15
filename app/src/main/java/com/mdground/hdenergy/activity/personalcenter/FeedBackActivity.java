package com.mdground.hdenergy.activity.personalcenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.databinding.ActivityFeedbackBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.ViewUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RobinYang on 2016-06-29.
 */

public class FeedBackActivity extends ToolbarActivity<ActivityFeedbackBinding> {

    private EditText etSuggest;

    private AlertDialog mAlertDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        etSuggest = (EditText) findViewById(R.id.etSuggest);
        tvRight.setVisibility(View.VISIBLE);
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
                        String suggest = etSuggest.getText().toString().trim();

                        saveUserSuggestionRequest(suggest);
                    }
                });
    }

    @Override
    protected void setListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suggest = etSuggest.getText().toString().trim();
                if ("".equals(suggest)) {
                    ViewUtils.toast(R.string.input_something);
                } else {
                    mAlertDialog.show();
                }
            }
        });
    }

    //region SERVER
    public void saveUserSuggestionRequest(String Suggestion) {
        String UserPhone = MDGroundApplication.sInstance.getLoginUser().getUserPhone();
        GlobalRestful.getInstance().SaveUserSuggestion(Suggestion,
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
