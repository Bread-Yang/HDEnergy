package com.mdground.hdenergy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.models.DateModel;

import java.util.List;


public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {

    private Context mContext;
    private List<DateModel> mModelList;
    public int publicHighlightPosition;
    private OnDateClickListener onDateClickListener;

    public interface OnDateClickListener {
        void onDateClick(DateModel dateModel);
    }

    public DateAdapter(Context context, List<DateModel> modelList) {
        this.mModelList = modelList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dateview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DateModel dateModel = mModelList.get(position);
        holder.tvWeekday.setText(dateModel.getWeeknum());
        holder.tvDay.setText(dateModel.getDaynum());

        if (publicHighlightPosition == position) {
            holder.lltRoot.setBackgroundColor(mContext.getResources().getColor(R.color.color_31C967));
            holder.tvWeekday.setTextColor(mContext.getResources().getColor(R.color.color_white));
            holder.tvDay.setTextColor(mContext.getResources().getColor(R.color.color_white));
        } else {
            holder.lltRoot.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
            holder.tvWeekday.setTextColor(mContext.getResources().getColor(R.color.color_666666));
            holder.tvDay.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }

        holder.lltRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int lastClickPosition = publicHighlightPosition;
                publicHighlightPosition = position;
                notifyItemChanged(lastClickPosition);
                notifyItemChanged(publicHighlightPosition);

                if (onDateClickListener != null) {
                    onDateClickListener.onDateClick(dateModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvWeekday;
        LinearLayout lltRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            lltRoot = (LinearLayout) itemView.findViewById(R.id.lltRoot);
            tvWeekday = (TextView) itemView.findViewById(R.id.tvWeekday);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
        }
    }

    public OnDateClickListener getOnDateClickListener() {
        return onDateClickListener;
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }
}
