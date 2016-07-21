package com.mdground.hdenergy.activity.attendancestatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityEmployeeAttendanceStaticsBinding;
import com.mdground.hdenergy.databinding.ItemEmployeeAttendanceStaticsBinding;
import com.mdground.hdenergy.enumobject.AttendanceStatus;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.ViewUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 6/29/16.
 */

public class EmployeeAttendanceStaticsActivity extends ToolbarActivity<ActivityEmployeeAttendanceStaticsBinding> {

    private EmployeeAttendanceStaticsAdapter mAdapter;

    private ArrayList<UserAttendance> mAttendanceArrayList = new ArrayList<>();

    private UserAttendance mUserAttendance;

    private String mQueryDate;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_employee_attendance_statics;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        getUserAttendanceListByMonthRequest();
    }

    @Override
    protected void initData() {
        mUserAttendance = getIntent().getParcelableExtra(Constants.KEY_USER_ATTENDANCE);
        mQueryDate = mUserAttendance.getBeginTime();
        showMonthString();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EmployeeAttendanceStaticsAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        getUserAttendanceListByMonthRequest();
    }

    @Override
    protected void setListener() {
        mDataBinding.ivPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQueryDate = DateUtils.previousMonth(mQueryDate);

                showMonthString();
                getUserAttendanceListByMonthRequest();
            }
        });

        mDataBinding.ivNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQueryDate = DateUtils.nextMonth(mQueryDate);

                showMonthString();
                getUserAttendanceListByMonthRequest();
            }
        });
    }

    private void showMonthString() {
        String month = DateUtils.getYearMonthWithChinese(DateUtils.getDateByServerDateString(mQueryDate).toDate());
        mDataBinding.tvMonth.setText(month);
    }

    //region SERVER
    private void getUserAttendanceListByMonthRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUserAttendanceListByMonth(mUserAttendance.getUserID(), mQueryDate,
                new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        ViewUtils.dismiss();
                        mAttendanceArrayList.clear();
                        ArrayList<UserAttendance> tempAttendanceArrayList = response.body().getContent(new TypeToken<ArrayList<UserAttendance>>() {
                        });
                        mAttendanceArrayList.addAll(tempAttendanceArrayList);

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }
    //endregion

    //region ADAPTER
    class EmployeeAttendanceStaticsAdapter extends RecyclerView.Adapter<EmployeeAttendanceStaticsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_employee_attendance_statics, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final UserAttendance userAttendance = mAttendanceArrayList.get(position);

            holder.viewDataBinding.tvAttendanceReason.setText(DateUtils.getYearMonthDayWithDash(userAttendance.getBeginTime()));

            DateTime beginTime = DateUtils.getDateByServerDateString(userAttendance.getBeginTime());
            DateTime endTime = DateUtils.getDateByServerDateString(userAttendance.getEndTime());

            String beginString = DateUtils.toAMOrPM(userAttendance.getBeginTime());
            String endString = DateUtils.toAMOrPM(userAttendance.getEndTime());

            holder.viewDataBinding.tvAttendanceDuration.setText(beginString + " - " + endString);
            holder.viewDataBinding.tvAmountHour.setText(DateUtils.toManHours(endTime.getMillis() - beginTime.getMillis()) + "H");

            AttendanceStatus attendanceStatus = AttendanceStatus.fromValue(userAttendance.getStatus());
            switch (attendanceStatus) {
                case Normal:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_green));
                    break;
                case Business:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_black));
                    break;
                case Leave:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_ffcc00));
                    break;
                case Injury:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_31C967));
                    break;
                case Dispatch:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_E92C0A));
                    break;
                case Shift:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_9793fe));
                    break;
                case NotSubmitted:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_64C5E4));
                    break;
            }

            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EmployeeAttendanceStaticsActivity.this, EmployeeAttendanceDetailActivity.class);
                    intent.putExtra(Constants.KEY_USER_ATTENDANCE, userAttendance);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mAttendanceArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ItemEmployeeAttendanceStaticsBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
