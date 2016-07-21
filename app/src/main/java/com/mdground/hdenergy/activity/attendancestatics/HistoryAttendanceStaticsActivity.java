package com.mdground.hdenergy.activity.attendancestatics;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.adapter.DateAdapter;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ItemHistoryAttendanceStaticsBinding;
import com.mdground.hdenergy.enumobject.AttendanceStatus;
import com.mdground.hdenergy.models.DateModel;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.MyTopView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.aigestudio.datepicker.views.DatePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 6/29/16.
 */

public class HistoryAttendanceStaticsActivity extends Activity {

    private HistoryAttendanceStaticsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MyTopView myTopView;
    private RecyclerView mDateRecyclerView;
    private ArrayList<UserAttendance> mAttendanceArrayList = new ArrayList<>();
    private List<DateModel> modelArrayList = new ArrayList<>();
    private DateAdapter mDateAdapter;
    private String mQueryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_attendance_statics);

        initData();
        setListenr();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserAttendanceByDateRequest();
    }

    private void initData() {
        myTopView = (MyTopView) findViewById(R.id.mytopview);

        {
            mDateRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
            horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mDateRecyclerView.setLayoutManager(horizontalLayoutManager);

            mDateAdapter = new DateAdapter(this, modelArrayList);
            mDateRecyclerView.setAdapter(mDateAdapter);
        }

        {
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this);
            verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(verticalLayoutManager);

            mAdapter = new HistoryAttendanceStaticsAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }

//        myTopView.setDateText(DateUtils.getYearMonthWithChinese(new Date()));
//        getUserAttendanceByDateRequest(DateUtils.getServerDateStringByDate(new Date()));
        DateTime dateTime = new DateTime();
        getDateData(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
    }

    private void setListenr() {
        myTopView.setOnDataSelectListner(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                String[] split = date.split("-");
                int year = Integer.parseInt(split[0]);
                int month = Integer.parseInt(split[1]);
                int day = Integer.parseInt(split[2]);
                getDateData(year, month, day);
                myTopView.closeDateDialog();
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
                getUserAttendanceByDateRequest();
            }
        });
    }

    private void getDateData(int year, int month, int day) {
        myTopView.setDateText(getString(R.string.year_month, year, month));
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
        mDateRecyclerView.scrollToPosition(day - 1);

        mQueryDate = DateUtils.getServerDateStringByYearMonthDay(year, month, day);
        getUserAttendanceByDateRequest();
    }

    private void getUserAttendanceByDateRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetAllUserAttendanceByDate(mQueryDate,
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
            final UserAttendance userAttendance = mAttendanceArrayList.get(position);

            holder.viewDataBinding.tvAttendanceReason.setText(userAttendance.getCategoryName2());

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
                    Intent intent = new Intent(HistoryAttendanceStaticsActivity.this, EmployeeAttendanceStaticsActivity.class);
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

            private ItemHistoryAttendanceStaticsBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
