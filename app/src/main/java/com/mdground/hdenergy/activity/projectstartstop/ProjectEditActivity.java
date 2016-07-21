package com.mdground.hdenergy.activity.projectstartstop;

import android.view.View;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityProjectEditBinding;
import com.mdground.hdenergy.enumobject.ProjectStatus;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 6/28/16.
 */

public class ProjectEditActivity extends ToolbarActivity<ActivityProjectEditBinding>
        implements OnDateSetListener {

    private TimePickerDialog mTimePickerDialog;

    private BaoPickerDialog mBaoPickerDialog;

    private Project mProject;

    private ArrayList<String> mProjectStatusArrayList = new ArrayList<>();

    private long mStartTime, mEndTime;

    private int mClickResID;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_project_edit;
    }

    @Override
    protected void initData() {
        mProject = getIntent().getParcelableExtra(Constants.KEY_PROJECT);
        mDataBinding.setProject(mProject);

        hideTimeLayoutDependStatus(ProjectStatus.fromValue(mProject.getProjectStatus()));

        String[] projectStatusStrings = getResources().getStringArray(R.array.project_status);
        Collections.addAll(mProjectStatusArrayList, projectStatusStrings);

        mBaoPickerDialog = new BaoPickerDialog(this);
        mBaoPickerDialog.bindWheelViewData(mProjectStatusArrayList);
        initTimePickerDialog();
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

                mProject.setProjectStatus(currentPosition);
                mDataBinding.tvProjectStatus.setText(mProjectStatusArrayList.get(currentPosition));

                hideTimeLayoutDependStatus(ProjectStatus.fromValue(mProject.getProjectStatus()));
            }
        });
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        switch (mClickResID) {
            case R.id.rltStartTime:
                if (millseconds > mEndTime && mEndTime != 0) {
                    ViewUtils.toast(R.string.stat_work_time_must_before_end_time);
                    return;
                }
                mStartTime = millseconds;
                mDataBinding.tvStartTime.setText(DateUtils.getYearMonthDayHourMinuteSecondWithDash(new Date(millseconds)));
                break;
            case R.id.rltEndTime:
                if (millseconds < mStartTime) {
                    ViewUtils.toast(R.string.end_work_time_must_after_start_time);
                    return;
                }
                mEndTime = millseconds;
                mDataBinding.tvEndTime.setText(DateUtils.getYearMonthDayHourMinuteSecondWithDash(new Date(millseconds)));
                break;
        }
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

    private void hideTimeLayoutDependStatus(ProjectStatus projectStatus) {
        if (projectStatus == ProjectStatus.Normal) {
            mDataBinding.rltStartTime.setVisibility(View.GONE);
            mDataBinding.rltEndTime.setVisibility(View.GONE);
        } else {
            mDataBinding.rltStartTime.setVisibility(View.VISIBLE);
            mDataBinding.rltEndTime.setVisibility(View.VISIBLE);
        }
    }

    //region ACTION
    public void selectProjectStatusAction(View view) {
        mBaoPickerDialog.show();
        mBaoPickerDialog.setCurrentItem(mProject.getProjectStatus());
    }

    public void selectDateAction(View view) {
        mClickResID = view.getId();

        mTimePickerDialog.show(getSupportFragmentManager(), String.valueOf(mClickResID));
    }

    public void submitAction(View view) {
        ProjectStatus projectStatus = ProjectStatus.fromValue(mProject.getProjectStatus());

        String startTime = null;
        String endTime = null;

        if (projectStatus != ProjectStatus.Normal) {
            startTime = mDataBinding.tvStartTime.getText().toString();
            if (StringUtils.isEmpty(startTime)) {
                ViewUtils.toast(R.string.fill_start_time);
                return;
            }

            endTime = mDataBinding.tvEndTime.getText().toString();
            if (StringUtils.isEmpty(startTime)) {
                ViewUtils.toast(R.string.fill_end_time);
                return;
            }
        }

        String remark = mDataBinding.etRemark.getText().toString().trim();
        ViewUtils.loading(this);
        GlobalRestful.getInstance().UpdateProject(mProject.getProjectID(), projectStatus,
                startTime, endTime, remark, new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        ViewUtils.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }

    //endregion
}
