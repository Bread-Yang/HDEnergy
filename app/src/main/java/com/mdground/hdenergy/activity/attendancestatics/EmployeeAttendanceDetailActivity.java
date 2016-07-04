package com.mdground.hdenergy.activity.attendancestatics;

import android.content.Intent;
import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.attendancereport.AttendanceReportActivity;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityEmployeeAttendanceDetailBinding;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class EmployeeAttendanceDetailActivity extends ToolbarActivity<ActivityEmployeeAttendanceDetailBinding> {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_employee_attendance_detail;
    }

    @Override
    protected void initData() {
        tvRight.setText(R.string.edit);
    }

    @Override
    protected void setListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeAttendanceDetailActivity.this, AttendanceReportActivity.class);
                startActivity(intent);
            }
        });
    }

    //region  ACTION
    public void nextStepAction(View view) {

    }
    //endregion

}
