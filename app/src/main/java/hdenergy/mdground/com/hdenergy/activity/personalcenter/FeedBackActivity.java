package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.view.View;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityFeedbackBinding;

/**
 * Created by PC on 2016-06-29.
 */

public class FeedBackActivity extends ToolbarActivity<ActivityFeedbackBinding> {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void setListener() {

    }
}
