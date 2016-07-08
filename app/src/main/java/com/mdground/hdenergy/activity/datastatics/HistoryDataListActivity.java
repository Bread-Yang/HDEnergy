package com.mdground.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityHistoryDatastaticsBinding;
import com.mdground.hdenergy.databinding.ItemHistoryDatastaticsBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-29.
 */

public class HistoryDataListActivity extends ToolbarActivity<ActivityHistoryDatastaticsBinding> {
    private HistoryDateListAdapter mAdapter;
    private ArrayList<String> mArrayList = new ArrayList<>();
    private int mPageIndex = 0;
    private int authorityLevel;
    int mProjectID;
    private List<ProjectWork> mProjectWorkList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsLoadeMore = false;
    private LinearLayout.LayoutParams layoutParams;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_datastatics;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.KEY_HISTORY_DATE_NAME);
        mProjectID=intent.getIntExtra(Constants.KEY_HISTORY_DATE_PROJECT_ID,0);
          getDateList();
        setTitle(title);
        GetProjectWorkList(mProjectID, mPageIndex);
        authorityLevel = MDGroundApplication.mInstance.getLoginUser().getAuthorityLevel();
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mAdapter = new HistoryDateListAdapter();
        mLinearLayoutManager = new LinearLayoutManager(HistoryDataListActivity.this);
        mDataBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {

    }

    public void correspondingDetails(View view) {
        int postion = mDataBinding.recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, HistoryDataDetailsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constants.KEY_HISTORY_DATA_PROJECT,mProjectWorkList.get(postion));
        intent.putExtras(bundle);
        startActivity(intent);

    }

    //region SERVER
    public void GetProjectWorkList(int ProjectID, int PageIndex) {
        GlobalRestful.getInstance().GetProjectWorkList(ProjectID, PageIndex, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (ResponseCode.isSuccess(response.body())) {
                    String ss=response.body().getContent();
                    ArrayList<ProjectWork> tempList = response.body().getContent(new TypeToken<ArrayList<ProjectWork>>() {
                    });

                    if (tempList != null) {
                        for (ProjectWork projectWork : tempList) {
                            mProjectWorkList.add(projectWork);
                        }
                    }
                    if (tempList != null) {
                        if (tempList.size() < 20) {
                            mIsLoadeMore = false;
                        } else {
                            mIsLoadeMore = true;
                            mPageIndex++;
                        }
                    } else {
                        mIsLoadeMore = false;
                    }
                    mAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

    //region METHOD
    //先别删还要用来调下来刷新
    public void getDateList() {
        mArrayList.add(getString(R.string.yongxing));
        mArrayList.add(getString(R.string.app_name));
    }


    //endregion

    //region ADAPTER
    public class HistoryDateListAdapter extends RecyclerView.Adapter<HistoryDateListAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HistoryDataListActivity.this)
                    .inflate(R.layout.item_history_datastatics, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            if (authorityLevel!=3) {
                holder.itemHistoryDatastaticsBinding.lltProfit.setVisibility(View.GONE);
                layoutParams.setMargins(0, 0, 0, 0);
                holder.itemHistoryDatastaticsBinding.tvQue.setLayoutParams(layoutParams);
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ceateDate = format.parse(mProjectWorkList.get(position).getCreatedTime());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
                String formatDate = format1.format(ceateDate);
                holder.itemHistoryDatastaticsBinding.tvTitles.setText(formatDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(Integer.valueOf(mProjectWorkList.get(position).getDayFuelCost())>mProjectWorkList.get(position).getFuelCost()) {
                holder.itemHistoryDatastaticsBinding.ivWarning.setImageResource(R.drawable.icon_warning);
            }
            holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(String.valueOf(mProjectWorkList.get(position).getFuelCost()));
            holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(mProjectWorkList.get(position).getDayFuelCost());
            holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(mProjectWorkList.get(position).getDayElectricityCost());
            holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(mProjectWorkList.get(position).getDayWaterCost());
            if (MDGroundApplication.mInstance.getLoginUser().getAuthorityLevel() != 3) {
                holder.itemHistoryDatastaticsBinding.tvProfit.setVisibility(View.GONE);
            } else {
            }
        }

        @Override
        public int getItemCount() {
            return mProjectWorkList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;
            public MyViewHolder(final View itemView) {
                super(itemView);
                itemHistoryDatastaticsBinding = DataBindingUtil.bind(itemView);
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


}
