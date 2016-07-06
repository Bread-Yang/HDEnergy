package com.mdground.hdenergy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.models.DateModel;

import java.util.List;


/**
 * Created by wistbean on 2016/7/6.
 */
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder>{

    private Context context;
    private List<DateModel> modelList;
    public DateAdapter(Context context, List<DateModel> modelList) {

        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_dateview,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_week.setText(modelList.get(position).getWeeknum());
        holder.tv_day.setText(modelList.get(position).getDaynum());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_day,tv_week,tv_point;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_day = (TextView) itemView.findViewById(R.id.tv_daynum);
            tv_week = (TextView) itemView.findViewById(R.id.tv_weeknum);
            tv_point = (TextView) itemView.findViewById(R.id.point);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);
        }
    }




}
