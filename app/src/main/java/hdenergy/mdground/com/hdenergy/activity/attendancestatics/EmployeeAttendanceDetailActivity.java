package hdenergy.mdground.com.hdenergy.activity.attendancestatics;

import android.content.Intent;
import android.view.View;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.attendancereport.AttendanceReportActivity;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityEmployeeAttendanceDetailBinding;

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
