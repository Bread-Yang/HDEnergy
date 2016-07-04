package com.mdground.hdenergy.activity.projectstartstop;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityProjectEditBinding;
import com.mdground.hdenergy.views.BaoPickerDialog;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by yoghourt on 6/28/16.
 */

public class ProjectEditActivity extends ToolbarActivity<ActivityProjectEditBinding>
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog mDatePickerDialog;

    private BaoPickerDialog mBaoPickerDialog;

    private ArrayList<String> mProjectStatusArrayList = new ArrayList<>();

    private int mClickResID;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_project_edit;
    }

    @Override
    protected void initData() {
        String[] projectStatusStrings = getResources().getStringArray(R.array.project_status);
        Collections.addAll(mProjectStatusArrayList, projectStatusStrings);

        mBaoPickerDialog = new BaoPickerDialog(this);
        mBaoPickerDialog.bindWheelViewData(mProjectStatusArrayList);
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

                mDataBinding.tvProjectStatus.setText(mProjectStatusArrayList.get(currentPosition));
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        switch (mClickResID) {
            case R.id.rltStartTime:
                mDataBinding.tvStartTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                break;
            case R.id.rltEndTime:
                mDataBinding.tvEndTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                break;
        }
    }

    //region ACTION
    public void selectProjectStatusAction(View view) {
        mBaoPickerDialog.show();
    }

    public void SelectDateAction(View view) {
        mClickResID = view.getId();
        if (mDatePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.show();
    }
    //endregion
}
