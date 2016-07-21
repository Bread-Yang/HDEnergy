package com.mdground.hdenergy.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdground.hdenergy.R;

import org.joda.time.DateTime;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by computer on 2016/7/6.
 */
public class MyTopView extends LinearLayout {

    private ImageView ivBack;
    private TextView tvDate;
    private View DateView;
    private DatePicker datePicker;
    private AlertDialog alertDialog;

    public MyTopView(Context context) {
        this(context, null);
    }

    public MyTopView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_top, this);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvDate = (TextView) findViewById(R.id.tvDate);

        DateView = View.inflate(getContext(), R.layout.dateview, null);
        datePicker = (DatePicker) DateView.findViewById(R.id.date);
        datePicker.setMode(DPMode.SINGLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DateTime dateTime = new DateTime();
        datePicker.setDate(dateTime.getYear(), dateTime.getMonthOfYear());
        builder.setView(DateView);
        alertDialog = builder.create();
        initOnclick();
    }

    private void initOnclick() {
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });

        tvDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        alertDialog.show();
    }

    public void setDate(int year, int month) {
        datePicker.setDate(year, month);
    }

    public void setOnDataSelectListner(DatePicker.OnDatePickedListener onDataSelectListner) {
        datePicker.setOnDatePickedListener(onDataSelectListner);
    }

    public void closeDateDialog() {
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
    }

    public void setDateText(String text) {
        tvDate.setText(text);
    }
}
