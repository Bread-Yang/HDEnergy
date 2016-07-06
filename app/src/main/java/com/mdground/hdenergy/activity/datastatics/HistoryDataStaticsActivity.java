package com.mdground.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.databinding.ActivityHistoryDatastaticsBinding;
import com.mdground.hdenergy.databinding.ItemHistoryDatastaticsBinding;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-28.
 */

public class HistoryDataStaticsActivity extends ToolbarActivity<ActivityHistoryDatastaticsBinding>{
   private HistoryDateAdapter mAdapter;
    private ArrayList<String> mArrayList=new ArrayList<>();
    private int  authorityLevel;
    private LinearLayout.LayoutParams layoutParams;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_datastatics;
    }

    @Override
    protected void initData() {
        getDateList();
        GetProjectSummeryListRequest();
        mAdapter=new HistoryDateAdapter();
        layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryDataStaticsActivity.this);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        authorityLevel= MDGroundApplication.mInstance.getLoginUser().getAuthorityLevel();
    }

    @Override
    protected void setListener() {

    }

    //region SERVERN

    public void GetProjectSummeryListRequest(){
        GlobalRestful.getInstance().GetProjectSummeryList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    //endregion

    //region METHOD
    public void getDateList(){
        mArrayList.add(getString(R.string.yongxing));
        mArrayList.add(getString(R.string.app_name));
    }


    //跳转到对应页面的
        public void toHistoryDataListActivity(View view) {
        int position = mDataBinding.recyclerView.getChildAdapterPosition(view);
        String  name = mArrayList.get(position);
        // KLog.e("第几个" + position);
            Intent intent =new Intent(this,HistoryDataListActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
    }
    //endregion

    //region ADAPTER
    public class HistoryDateAdapter extends RecyclerView.Adapter<HistoryDateAdapter.MyViewHolder>{


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(HistoryDataStaticsActivity.this)
                    .inflate(R.layout.item_history_datastatics,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(authorityLevel<3){
                holder.itemHistoryDatastaticsBinding.lltProfit.setVisibility(View.GONE);
                layoutParams.setMargins(0,0,0,0);
                holder.itemHistoryDatastaticsBinding.tvQue.setLayoutParams(layoutParams);
            }
            holder.itemHistoryDatastaticsBinding.tvTitles.setText(mArrayList.get(position));
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
            public  ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;
            public MyViewHolder(final View itemView) {
                super(itemView);
                itemHistoryDatastaticsBinding= DataBindingUtil.bind(itemView);

                itemHistoryDatastaticsBinding.lltItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toHistoryDataListActivity(itemView);
                    }
                });
            }
        }

    }
    //endregion
}
