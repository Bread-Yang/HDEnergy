package hdenergy.mdground.com.hdenergy.activity.attendancereport;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityAttendanceReportBinding;

/**
 * Created by yoghourt on 2016-06-28.
 */
public class AttendanceReportActivity extends ToolbarActivity<ActivityAttendanceReportBinding>
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog mBirthdayDatePickerDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_attendance_report;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }

    // 选择工程公司则无打分的选项
    private void hideRateLayout(boolean isShow) {
        mDataBinding.tvRateTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mDataBinding.rltRate.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }

    //region  ACTION
    public void SelectDataAction(View view) {
        if (mBirthdayDatePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            mBirthdayDatePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        mBirthdayDatePickerDialog.show();
    }

    public void nextStepAction(View view) {

    }
    //endregion
}
