package com.mdground.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityHistoryDatastaticsBinding;
import com.mdground.hdenergy.databinding.ItemHistoryDatastaticsBinding;

/**
 * Created by PC on 2016-06-29.
 */

public class HistoryDataListActivity extends ToolbarActivity<ActivityHistoryDatastaticsBinding>{
    private HistoryDateAdapter mAdapter;
    private ArrayList<String> mArrayList=new ArrayList<>();
    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_datastatics;
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        String title=intent.getStringExtra("name");
       getDateList();
        setTitle(title);
        mAdapter=new HistoryDateAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryDataListActivity.this);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {

    }

    public void correspondingDetails(View view){
     int postion=mDataBinding.recyclerView.getChildAdapterPosition(view);
        Intent intent=new Intent(this,HistoryDataDetailsActivity.class);
        startActivity(intent);

    }


    //region ADAPTER
    public class HistoryDateAdapter extends RecyclerView.Adapter<HistoryDateAdapter.MyViewHolder>{


        @Override
        public HistoryDateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(HistoryDataListActivity.this)
                    .inflate(R.layout.item_history_datastatics,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.itemHistoryDatastaticsBinding.tvTitles.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvProfit.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvQuestion.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(mArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            //           public ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;
            public ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;
            public MyViewHolder(final View itemView) {
                super(itemView);
                itemHistoryDatastaticsBinding= DataBindingUtil.bind(itemView);
                itemHistoryDatastaticsBinding.lltItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correspondingDetails(itemView);
                    }
                });
            }
        }

    }
    //endregion

    //region METHOD
    public void getDateList(){
        mArrayList.add(getString(R.string.yongxing));
        mArrayList.add(getString(R.string.app_name));
    }


    //endregion
}
