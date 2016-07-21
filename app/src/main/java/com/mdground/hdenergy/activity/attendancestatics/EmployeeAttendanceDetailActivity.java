package com.mdground.hdenergy.activity.attendancestatics;

import android.content.Intent;
import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.attendancereport.AttendanceReportActivity;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityEmployeeAttendanceDetailBinding;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.utils.DateUtils;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class EmployeeAttendanceDetailActivity extends ToolbarActivity<ActivityEmployeeAttendanceDetailBinding> {

    private UserAttendance mUserAttendance;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_employee_attendance_detail;
    }

    @Override
    protected void initData() {
        tvRight.setText(R.string.edit);

        mUserAttendance = getIntent().getParcelableExtra(Constants.KEY_USER_ATTENDANCE);
        mDataBinding.setUserAttendance(mUserAttendance);
        getSupportActionBar().setTitle(mUserAttendance.getUserName());

        long startTime = DateUtils.getDateByServerDateString(mUserAttendance.getBeginTime()).getMillis();
        long endTime = DateUtils.getDateByServerDateString(mUserAttendance.getEndTime()).getMillis();
        mDataBinding.tvManHours.setText(DateUtils.toManHours(endTime - startTime) + "h");
    }

    @Override
    protected void setListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeAttendanceDetailActivity.this, AttendanceReportActivity.class);
                intent.putExtra(Constants.KEY_USER_ATTENDANCE, mUserAttendance);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    //region  ACTION
    public void nextStepAction(View view) {

    }
    //endregion

}
