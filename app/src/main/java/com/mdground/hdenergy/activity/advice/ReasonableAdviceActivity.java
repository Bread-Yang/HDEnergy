package com.mdground.hdenergy.activity.advice;

import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityReasonableAdviceBinding;

/**
 * Created by yoghourt on 6/29/16.
 */
public class ReasonableAdviceActivity extends ToolbarActivity<ActivityReasonableAdviceBinding> {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_reasonable_advice;
    }

    @Override
    protected void initData() {
        tvRight.setText(R.string.submit);
    }

    @Override
    protected void setListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
