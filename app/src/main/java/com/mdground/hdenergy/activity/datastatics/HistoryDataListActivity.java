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
import com.mdground.hdenergy.databinding.ActivityHistoryDataStaticsBinding;
import com.mdground.hdenergy.databinding.ItemHistoryDatastaticsBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.BigDecimalUtil;
import com.mdground.hdenergy.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RobinYang on 2016-06-29.
 */

public class HistoryDataListActivity extends ToolbarActivity<ActivityHistoryDataStaticsBinding> {

    private HistoryDateListAdapter mAdapter;
    private List<ProjectWork> mProjectWorkList = new ArrayList<>();
    private ProjectWork mProjectWork;
    private int mPageIndex = 0;
    private boolean mIsLoadeMore = false;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_data_statics;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getProjectWorkListRequest(mProjectWork.getProjectID(), mPageIndex);
    }

    @Override
    protected void initData() {
        mProjectWork = getIntent().getParcelableExtra(Constants.KEY_HISTORY_DATE_PROJECT);

        setTitle(mProjectWork.getProjectName());

        mAdapter = new HistoryDateListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryDataListActivity.this);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    private void correspondingDetails(View view) {
        int postion = mDataBinding.recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, HistoryDataDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_HISTORY_DATA_PROJECT, mProjectWorkList.get(postion));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //region SERVER
    private void getProjectWorkListRequest(int ProjectID, int PageIndex) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectWorkList(ProjectID, PageIndex, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                if (ResponseCode.isSuccess(response.body())) {
                    String ss = response.body().getContent();
                    ArrayList<ProjectWork> tempList = response.body().getContent(new TypeToken<ArrayList<ProjectWork>>() {
                    });

                    mProjectWorkList.clear();
                    if (tempList != null) {
                        for (ProjectWork projectWork : tempList) {
                            if (projectWork.getUserID() == MDGroundApplication.sInstance.getLoginUser().getUserID()) {
                                mProjectWorkList.add(projectWork);
                            }
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

    //region ADAPTER
    private class HistoryDateListAdapter extends RecyclerView.Adapter<HistoryDateListAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HistoryDataListActivity.this)
                    .inflate(R.layout.item_history_datastatics, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            ProjectWork projectWork = mProjectWorkList.get(position);

            int authorityLevel = MDGroundApplication.sInstance.getLoginUser().getAuthorityLevel();
            if (authorityLevel != 3) {
                holder.itemHistoryDatastaticsBinding.lltProfit.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                holder.itemHistoryDatastaticsBinding.tvQue.setLayoutParams(layoutParams);
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ceateDate = format.parse(projectWork.getReportedTime());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
                String formatDate = format1.format(ceateDate);
                holder.itemHistoryDatastaticsBinding.tvTitles.setText(formatDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mProjectWork.getFuelCost() < projectWork.getDayFuelCost()) {
                holder.itemHistoryDatastaticsBinding.ivWarning.setImageResource(R.drawable.icon_warning);
            } else {
                holder.itemHistoryDatastaticsBinding.ivWarning.setImageResource(R.drawable.icon_normal);
            }

            if (projectWork.getSaleType().equals(getString(R.string.steam))) {
                // 销售产品为蒸汽
                // 标单
                holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(
                        getString(R.string.kg_per_ton_steam, BigDecimalUtil.keepTwoDecimalPlaces(mProjectWork.getFuelCost())));
                // 单耗
                holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(
                        getString(R.string.kg_per_ton_steam, BigDecimalUtil.keepTwoDecimalPlaces(projectWork.getDayFuelCost())));
                // 电单耗
                holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(
                        getString(R.string.kw_per_ton_steam, BigDecimalUtil.keepTwoDecimalPlaces(projectWork.getDayElectricityCost())));
                // 水单耗
                holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(
                        getString(R.string.ton_per_ton_steam, BigDecimalUtil.keepTwoDecimalPlaces(projectWork.getDayWaterCost())));
            } else {
                // 销售产品为热力
                // 标单
                holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(
                        getString(R.string.kg_per_ton, BigDecimalUtil.keepTwoDecimalPlaces(mProjectWork.getFuelCost())));
                // 单耗
                holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(
                        getString(R.string.kg_per_ton, BigDecimalUtil.keepTwoDecimalPlaces(projectWork.getDayFuelCost())));
                // 电单耗
                holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(
                        getString(R.string.kw_per_ton, BigDecimalUtil.keepTwoDecimalPlaces(projectWork.getDayElectricityCost())));
                // 水单耗
                holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(
                        getString(R.string.ton_per_ton, BigDecimalUtil.keepTwoDecimalPlaces(projectWork.getDayWaterCost())));
            }

            // 利润
            holder.itemHistoryDatastaticsBinding.tvProfit.setText(String.valueOf(projectWork.getProfit()));
            if (MDGroundApplication.sInstance.getLoginUser().getAuthorityLevel() != 3) {
                holder.itemHistoryDatastaticsBinding.tvProfit.setVisibility(View.GONE);
            }

            // 问题
            holder.itemHistoryDatastaticsBinding.tvQuestion.setText(projectWork.getRemark());
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
