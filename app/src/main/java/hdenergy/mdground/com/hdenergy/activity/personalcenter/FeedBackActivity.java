package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.view.View;
import android.widget.EditText;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.databinding.ActivityFeedbackBinding;
import hdenergy.mdground.com.hdenergy.enumobject.restfuls.ResponseCode;
import hdenergy.mdground.com.hdenergy.restfuls.GlobalRestful;
import hdenergy.mdground.com.hdenergy.restfuls.bean.ResponseData;
import hdenergy.mdground.com.hdenergy.utils.ViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-29.
 */

public class FeedBackActivity extends ToolbarActivity<ActivityFeedbackBinding> {
    EditText etSuggest;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        etSuggest = (EditText) findViewById(R.id.etSuggest);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suggest = etSuggest.getText().toString().trim();
                if ("".equals(suggest)) {
                    ViewUtils.toast("请填写内容");
                } else {
                    SaveUserSuggestionRequest(suggest);
                }
            }
        });

    }


    @Override
    protected void setListener() {

    }

    //region SERVER
    public void SaveUserSuggestionRequest(String Suggestion) {
        String UserPhone = MDGroundApplication.mInstance.getLoginUser().getUserPhone();
        GlobalRestful.getInstance().SaveUserSuggestion(Suggestion,
                new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (ResponseCode.isSuccess(response.body())) {
                            ViewUtils.toast("提交成功");
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
