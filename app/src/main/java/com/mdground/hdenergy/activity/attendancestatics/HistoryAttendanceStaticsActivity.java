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
import android.widget.Toast;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.adapter.DateAdapter;
import com.mdground.hdenergy.databinding.ItemHistoryAttendanceStaticsBinding;
import com.mdground.hdenergy.models.DateModel;
import com.mdground.hdenergy.views.MyTopView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by yoghourt on 6/29/16.
 */

public class HistoryAttendanceStaticsActivity extends Activity {
    private HistoryAttendanceStaticsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MyTopView myTopView;
    private RecyclerView mDateRecyclerView;
    private int year;
    private int month;
    private int day;

    private List<DateModel> mlists=new ArrayList<>();
    private DateAdapter dateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_attendance_statics);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        initData();
        getNowdate();
        getDateData(year,month);
        initEvent();

    }

    private ArrayList<String> mAttendanceArrayList = new ArrayList<>();




    protected void initData() {
        myTopView = (MyTopView) findViewById(R.id.mytopview);
        mDateRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        dateAdapter = new DateAdapter(this,mlists);
        mDateRecyclerView.setAdapter(dateAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDateRecyclerView.setLayoutManager(linearLayoutManager);


        mAttendanceArrayList.add("");
        mAttendanceArrayList.add("");
        mAttendanceArrayList.add("");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new HistoryAttendanceStaticsAdapter();
        mRecyclerView.setAdapter(mAdapter);


    }

    public void getNowdate() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        myTopView.setdatetext("Year " + year + "↓↓↓" );
    }



    public void getDateData(int year,int month) {
        mlists.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int monthtotalday = calendar.getActualMaximum(Calendar.DATE);

        for(int i=1;i<=monthtotalday;i++)
        {
            DateModel dateModel = new DateModel();
            calendar.set(Calendar.DAY_OF_MONTH, i);
            dateModel.setDaynum(i+"");
            switch (calendar.get(Calendar.DAY_OF_WEEK))
            {
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
            mlists.add(dateModel);
        }

    }

    private void initEvent() {

        myTopView.setDate(year,month);

        myTopView.setOnDataSelectListner(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(HistoryAttendanceStaticsActivity.this, "select..." + date, Toast.LENGTH_SHORT).show();
                String[] split = date.split("-");
                year = Integer.parseInt(split[0]);
                month = Integer.parseInt(split[1]);
                day = Integer.parseInt(split[2]);
                getDateData(year,month);
                mDateRecyclerView.scrollToPosition(day-1);
                myTopView.setdatetext("Year " + year + "↓↓↓" );
                myTopView.closeDateDialog();
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
            setItemListener(holder, position);
        }

        @Override
        public int getItemCount() {
            return mAttendanceArrayList.size();
        }

        private void setItemListener(ViewHolder holder, final int position) {
            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryAttendanceStaticsActivity.this, EmployeeAttendanceStaticsActivity.class);
                    startActivity(intent);
                }
            });
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
