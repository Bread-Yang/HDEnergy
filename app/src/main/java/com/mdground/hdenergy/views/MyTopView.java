package com.mdground.hdenergy.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdground.hdenergy.R;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by computer on 2016/7/6.
 */
public class MyTopView extends LinearLayout {

    private TextView tv_back;
    private TextView tv_date;
    private View DateView;
    private DatePicker datePicker;
    private AlertDialog alertDialog;


    public MyTopView(Context context) {
        this(context,null);
    }

    public MyTopView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MyTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.toplayout,this);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_date = (TextView) findViewById(R.id.tv_date);

        DateView = View.inflate(getContext(),R.layout.dateview,null);
        datePicker = (DatePicker) DateView.findViewById(R.id.date);
        datePicker.setMode(DPMode.SINGLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        datePicker.setDate(2015,6);
        builder.setView(DateView);
        alertDialog = builder.create();
        initonclick();
    }


    private void initonclick() {
        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });

        tv_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                showdateDialog();
            }


        });

    }

    private void showdateDialog() {
        alertDialog.show();
    }

    public void setDate(int year,int month)
    {
        datePicker.setDate(year,month);
    }

    public void setOnDataSelectListner(DatePicker.OnDatePickedListener onDataSelectListner)
    {
        datePicker.setOnDatePickedListener(onDataSelectListner);
    }



    public void closeDateDialog()
    {
        if (alertDialog.isShowing())
        {
            alertDialog.cancel();
        }
    }


    public void setdatetext(String text)
    {
        tv_date.setText(text);
    }

    public void setbacktext(String text)
    {
        tv_back.setText(text);
    }






}
