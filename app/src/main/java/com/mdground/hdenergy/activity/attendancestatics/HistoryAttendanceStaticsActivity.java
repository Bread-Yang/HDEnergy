package com.mdground.hdenergy.activity.attendancestatics;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.adapter.DateAdapter;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityHistoryAttendanceStaticsBinding;
import com.mdground.hdenergy.databinding.ItemHistoryAttendanceStaticsBinding;
import com.mdground.hdenergy.enumobject.AttendanceStatus;
import com.mdground.hdenergy.models.DateModel;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.GlideUtils;
import com.mdground.hdenergy.utils.ViewUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 6/29/16.
 */

public class HistoryAttendanceStaticsActivity extends Activity
        implements DatePickerDialog.OnDateSetListener {

    private ActivityHistoryAttendanceStaticsBinding mDataBinding;

    private DatePickerDialog mDatePickerDialog;

    private HistoryAttendanceStaticsAdapter mAdapter;

    private ArrayList<UserAttendance> mAllAttendanceArrayList = new ArrayList<>();
    private ArrayList<UserAttendance> mShowAttendanceArrayList = new ArrayList<>();

    private List<DateModel> modelArrayList = new ArrayList<>();
    private DateAdapter mDateAdapter;

    private AttendanceStatus mClickAttendanceStatus = AttendanceStatus.All;

    private int mAllUserCount;
    private String mQueryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_history_attendance_statics);

        initData();
        setListenr();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAllUserListRequest();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        getDateData(year, monthOfYear + 1, dayOfMonth);
    }

    private void initData() {
        {
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
            horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mDataBinding.dateRecyclerView.setLayoutManager(horizontalLayoutManager);

            mDateAdapter = new DateAdapter(this, modelArrayList);
            mDataBinding.dateRecyclerView.setAdapter(mDateAdapter);
        }

        {
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this);
            verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mDataBinding.recyclerView.setLayoutManager(verticalLayoutManager);

            mAdapter = new HistoryAttendanceStaticsAdapter();
            mDataBinding.recyclerView.setAdapter(mAdapter);
        }

        {
            Calendar calendar = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        DateTime dateTime = new DateTime();
        getDateData(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
    }

    private void setListenr() {
        mDataBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDataBinding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialog.show();
            }
        });

        mDateAdapter.setOnDateClickListener(new DateAdapter.OnDateClickListener() {
            @Override
            public void onDateClick(DateModel dateModel) {
                DateTime dateTime = new DateTime()
                        .withYear(dateModel.getYear())
                        .withMonthOfYear(dateModel.getMonth())
                        .withDayOfMonth(dateModel.getDay());
                mQueryDate = DateUtils.getServerDateStringByDate(dateTime.toDate());

                mClickAttendanceStatus = AttendanceStatus.All;
                getUserAttendanceByDateRequest();
            }
        });
    }

    private void getDateData(int year, int month, int day) {
        mDataBinding.tvDate.setText(getString(R.string.year_month, year, month));
        modelArrayList.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int monthTotalDay = calendar.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= monthTotalDay; i++) {
            DateModel dateModel = new DateModel();
            dateModel.setYear(year);
            dateModel.setMonth(month);
            dateModel.setDay(i);

            calendar.set(Calendar.DAY_OF_MONTH, i);
            dateModel.setDaynum(String.valueOf(i));
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    dateModel.setWeeknum("Sun");
                    break;
                case 2:
                    dateModel.setWeeknum("Mon");
                    break;
                case 3:
                    dateModel.setWeeknum("Tus");
                    break;
                case 4:
                    dateModel.setWeeknum("Wed");
                    break;
                case 5:
                    dateModel.setWeeknum("Thu");
                    break;
                case 6:
                    dateModel.setWeeknum("Fri");
                    break;
                case 7:
                    dateModel.setWeeknum("Sat");
                    break;
            }
            modelArrayList.add(dateModel);
        }

        mDateAdapter.publicHighlightPosition = day - 1;
        mDateAdapter.notifyDataSetChanged();
        mDataBinding.dateRecyclerView.scrollToPosition(day - 1);

        mQueryDate = DateUtils.getServerDateStringByYearMonthDay(year, month, day);
        getUserAttendanceByDateRequest();
    }

    private void refreshRecyclerViewByAttendanceStatus() {
        mShowAttendanceArrayList.clear();

        if (mClickAttendanceStatus == AttendanceStatus.All) {
             mShowAttendanceArrayList.addAll(mAllAttendanceArrayList);
        } else {
            for (UserAttendance userAttendance : mAllAttendanceArrayList) {
                AttendanceStatus attendanceStatus = AttendanceStatus.fromValue(userAttendance.getStatus());

                if (attendanceStatus == mClickAttendanceStatus) {
                    mShowAttendanceArrayList.add(userAttendance);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    //region SERVER
    private void getAllUserListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetAllUserList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ArrayList<UserInfo> userInfoArrayList = response.body().getContent(new TypeToken<ArrayList<UserInfo>>() {
                });

                mAllUserCount = userInfoArrayList.size();

                getUserAttendanceByDateRequest();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getUserAttendanceByDateRequest() {
        GlobalRestful.getInstance().GetAllUserAttendanceByDate(mQueryDate,
                new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        ViewUtils.dismiss();
                        mAllAttendanceArrayList.clear();

                        ArrayList<UserAttendance> tempAttendanceArrayList = response.body().getContent(new TypeToken<ArrayList<UserAttendance>>() {
                        });
                        mAllAttendanceArrayList.addAll(tempAttendanceArrayList);

                        refreshRecyclerViewByAttendanceStatus();

                        // "未提交"人数
                        int notSubmitCount = mAllUserCount - mAllAttendanceArrayList.size();
                        mDataBinding.tvNotSubmittedCount.setText(String.valueOf(notSubmitCount));

                        int normalStatusCount = 0;
                        int businessStatusCount = 0;
                        int leaveStatusCount = 0;
                        int injuryStatusCount = 0;
                        int dispatchStatusCount = 0;
                        int shiftStatusCount = 0;
                        int notSubmitStatusCount = 0;

                        for (UserAttendance userAttendance : mAllAttendanceArrayList) {
                            AttendanceStatus attendanceStatus = AttendanceStatus.fromValue(userAttendance.getStatus());

                            switch (attendanceStatus) {
                                case Normal:
                                    normalStatusCount++;
                                    break;
                                case Business:
                                    businessStatusCount++;
                                    break;
                                case Leave:
                                    leaveStatusCount++;
                                    break;
                                case Injury:
                                    injuryStatusCount++;
                                    break;
                                case Dispatch:
                                    dispatchStatusCount++;
                                    break;
                                case Shift:
                                    shiftStatusCount++;
                                    break;
                                case NotSubmitted:
                                    notSubmitStatusCount++;
                                    break;
                            }
                        }

                        mDataBinding.tvNormalCount.setText(String.valueOf(normalStatusCount));
                        mDataBinding.tvBusinessCount.setText(String.valueOf(businessStatusCount));
                        mDataBinding.tvLeaveCount.setText(String.valueOf(leaveStatusCount));
                        mDataBinding.tvInjuryCount.setText(String.valueOf(injuryStatusCount));
                        mDataBinding.tvDispatchCount.setText(String.valueOf(dispatchStatusCount));
                        mDataBinding.tvShiftCount.setText(String.valueOf(shiftStatusCount));
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }
    //endregion

    //region ACTION
    public void switchStatusLayoutAction(View view) {
        if (mDataBinding.lltStatusPage1.getVisibility() == View.VISIBLE) {
            mDataBinding.lltStatusPage1.setVisibility(View.INVISIBLE);
            mDataBinding.lltStatusPage2.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.lltStatusPage1.setVisibility(View.VISIBLE);
            mDataBinding.lltStatusPage2.setVisibility(View.INVISIBLE);
        }
    }

    public void normalStatusClickAction(View view) {
        mClickAttendanceStatus = AttendanceStatus.Normal;

        refreshRecyclerViewByAttendanceStatus();
    }

    public void businessStatusClickAction(View view) {
        mClickAttendanceStatus = AttendanceStatus.Business;

        refreshRecyclerViewByAttendanceStatus();
    }

    public void leaveStatusClickAction(View view) {
        mClickAttendanceStatus = AttendanceStatus.Leave;

        refreshRecyclerViewByAttendanceStatus();
    }

    public void injuryStatusClickAction(View view) {
        mClickAttendanceStatus = AttendanceStatus.Injury;

        refreshRecyclerViewByAttendanceStatus();
    }

    public void dispatchStatusClickAction(View view) {
        mClickAttendanceStatus = AttendanceStatus.Dispatch;

        refreshRecyclerViewByAttendanceStatus();
    }

    public void shiftStatusClickAction(View view) {
        mClickAttendanceStatus = AttendanceStatus.Shift;

        refreshRecyclerViewByAttendanceStatus();
    }
    //endregion

    //region ADAPTER
    class HistoryAttendanceStaticsAdapter extends RecyclerView.Adapter<HistoryAttendanceStaticsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_attendance_statics, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final UserAttendance userAttendance = mShowAttendanceArrayList.get(position);

            holder.viewDataBinding.tvAttendanceReason.setText(userAttendance.getUserName());

            DateTime beginTime = DateUtils.getDateByServerDateString(userAttendance.getBeginTime());
            DateTime endTime = DateUtils.getDateByServerDateString(userAttendance.getEndTime());

            String beginString = DateUtils.toAMOrPM(userAttendance.getBeginTime());
            String endString = DateUtils.toAMOrPM(userAttendance.getEndTime());

            holder.viewDataBinding.tvAttendanceDuration.setText(beginString + " - " + endString);
            holder.viewDataBinding.tvAmountHour.setText(DateUtils.toManHours(endTime.getMillis() - beginTime.getMillis()) + "H");

            AttendanceStatus attendanceStatus = AttendanceStatus.fromValue(userAttendance.getStatus());
            switch (attendanceStatus) {
                case Normal:
                    holder.viewDataBinding.viewStatus.setBackgroundColor(getResources().getColor(R.color.color_00943F));
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

            // 头像
            GlideUtils.loadImageByPhotoSID(holder.viewDataBinding.civAvatar, userAttendance.getPhotoSID(), false);

            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryAttendanceStaticsActivity.this, EmployeeAttendanceStaticsActivity.class);
                    intent.putExtra(Constants.KEY_USER_ATTENDANCE, userAttendance);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mShowAttendanceArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ItemHistoryAttendanceStaticsBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
