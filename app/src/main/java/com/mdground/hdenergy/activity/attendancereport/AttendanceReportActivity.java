package com.mdground.hdenergy.activity.attendancereport;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityAttendanceReportBinding;
import com.mdground.hdenergy.models.Department;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 2016-06-28.
 */
public class AttendanceReportActivity extends ToolbarActivity<ActivityAttendanceReportBinding>
        implements OnDateSetListener {

    private TimePickerDialog mTimePickerDialog;

    private BaoPickerDialog mBaoPickerDialog;

    private ArrayList<Department> mDepartmentArrayList = new ArrayList<>();

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    private ArrayList<UserInfo> mUserInfoArrayList = new ArrayList<>();

    private ArrayList<String> mAttendanceStatusArrayList = new ArrayList<>();

    private long mStartTime, mEndTime;

    private int mClickResID;

    private int mWheelViewChooseResID;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_attendance_report;
    }

    @Override
    protected void initData() {
        getDepartmentListRequest();

        initTimePickerDialog();
        mBaoPickerDialog = new BaoPickerDialog(this);

        // 默认上班时间,早上9点
        Date previousStartWorkDate = DateUtils.previousDate(new Date(), 9);
        mStartTime = previousStartWorkDate.getTime();
        mDataBinding.tvStartTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(previousStartWorkDate));

        // 默认下班时间,下午17点
        Date previousEndWorkDate = DateUtils.previousDate(new Date(), 17);
        mEndTime = previousEndWorkDate.getTime();
        mDataBinding.tvEndTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(previousEndWorkDate));

        calculateManHours();

        // 上班状态数据
        String[] attendanceStatusStrings = getResources().getStringArray(R.array.attendance_status_array);
        Collections.addAll(mAttendanceStatusArrayList, attendanceStatusStrings);

    }

    @Override
    protected void setListener() {
        mBaoPickerDialog.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int currentPosition = wheel.getCurrentItem();

                switch (mWheelViewChooseResID) {
                    case R.id.tvDepartment:
                        mDataBinding.tvDepartment.setText(mDepartmentArrayList.get(currentPosition).getDepartmentName());
                        break;
                    case R.id.tvProject:
                        mDataBinding.tvProject.setText(mProjectArrayList.get(currentPosition).getProjectName());
                        break;
                    case R.id.tvName:
                        mDataBinding.tvName.setText(mUserInfoArrayList.get(currentPosition).getUserName());
                        break;
                    case R.id.tvAttendanceStatus:
                        mDataBinding.tvAttendanceStatus.setText(mAttendanceStatusArrayList.get(currentPosition));
                        break;
                }
            }
        });

    }

    private void initTimePickerDialog() {
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.confirm))
                .setTitleStringId(getString(R.string.choose_time))
                .setYearText(getString(R.string.year))
                .setMonthText(getString(R.string.month))
                .setDayText(getString(R.string.day))
                .setHourText(getString(R.string.hour))
                .setMinuteText(getString(R.string.minute))
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
    }

    // 选择工程公司则无打分的选项
    private void hideRateLayout(boolean isShow) {
        mDataBinding.tvRateTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mDataBinding.rltRate.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    // 计算工时
    private void calculateManHours() {
        mDataBinding.tvManHours.setText(DateUtils.toHour(mEndTime - mStartTime));
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        switch (mClickResID) {
            case R.id.rltStartTime:
                if (millseconds > mEndTime) {
                    ViewUtils.toast(R.string.stat_work_time_must_before_end_time);
                    return;
                }
                mStartTime = millseconds;
                mDataBinding.tvStartTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(new Date(millseconds)));
                break;
            case R.id.rltEndTime:
                if (millseconds < mStartTime) {
                    ViewUtils.toast(R.string.end_work_time_must_after_start_time);
                    return;
                }
                mEndTime = millseconds;
                mDataBinding.tvEndTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(new Date(millseconds)));
                break;
        }
        calculateManHours();
    }

    //region ACTION
    public void selectDepartmentAction(View view) {
        mWheelViewChooseResID = R.id.tvDepartment;
        ArrayList<String> departmentStringArrayList = new ArrayList<>();
        for (Department department : mDepartmentArrayList) {
            departmentStringArrayList.add(department.getDepartmentName());
        }
        mBaoPickerDialog.bindWheelViewData(departmentStringArrayList);
        mBaoPickerDialog.show();
    }

    public void selectProjectAction(View view) {
        mWheelViewChooseResID = R.id.tvProject;
        ArrayList<String> projectStringArrayList = new ArrayList<>();
        for (Project project : mProjectArrayList) {
            projectStringArrayList.add(project.getProjectName());
        }
        mBaoPickerDialog.bindWheelViewData(projectStringArrayList);
        mBaoPickerDialog.show();
    }

    public void selectUserInfoAction(View view) {
        mWheelViewChooseResID = R.id.tvName;
        ArrayList<String> userNameStringArrayList = new ArrayList<>();
        for (UserInfo userInfo : mUserInfoArrayList) {
            userNameStringArrayList.add(userInfo.getUserName());
        }
        mBaoPickerDialog.bindWheelViewData(userNameStringArrayList);
        mBaoPickerDialog.show();
    }

    public void selectAttendanceStatusAction(View view) {
        mWheelViewChooseResID = R.id.tvAttendanceStatus;
        mBaoPickerDialog.bindWheelViewData(mAttendanceStatusArrayList);
        mBaoPickerDialog.show();
    }

    public void SelectDateAction(View view) {
        mClickResID = view.getId();

        mTimePickerDialog.show(getSupportFragmentManager(), String.valueOf(mClickResID));
    }

    public void nextStepAction(View view) {

    }
    //endregion

    //region SERVER
    private void getDepartmentListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetDepartmentList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                mDepartmentArrayList = response.body().getContent(new TypeToken<ArrayList<Department>>() {
                });
                mDataBinding.tvDepartment.setText(mDepartmentArrayList.get(0).getDepartmentName());

                getProjectListRequest();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getProjectListRequest() {
        GlobalRestful.getInstance().GetProjectList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mProjectArrayList = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                });
                mDataBinding.tvProject.setText(mProjectArrayList.get(0).getProjectName());

                getUserListByDepartmentRequest();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getUserListByDepartmentRequest() {
        String department = mDataBinding.tvDepartment.getText().toString();

        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUserListByDepartment(department, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                mUserInfoArrayList = response.body().getContent(new TypeToken<ArrayList<UserInfo>>() {
                });
                mDataBinding.tvName.setText(mUserInfoArrayList.get(0).getUserName());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

}
