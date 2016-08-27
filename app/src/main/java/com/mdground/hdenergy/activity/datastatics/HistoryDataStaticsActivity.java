package com.mdground.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.mdground.hdenergy.utils.ViewUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-28.
 */

public class HistoryDataStaticsActivity extends ToolbarActivity<ActivityHistoryDataStaticsBinding> {

    private HistoryDateAdapter mAdapter;
    private ArrayList<String> mArrayList = new ArrayList<>();
    private ArrayList<ProjectWork> mProjectList = new ArrayList<>();
    private LinearLayout.LayoutParams mLayoutParams;
    private LinearLayoutManager mLinearLayoutManager;
    private int mAuthorityLevel;
    private int mPageIndex = 0;
    private boolean mIsLoadeMore;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_data_statics;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getProjectSummeryListRequest(0);
    }

    @Override
    protected void initData() {
        getDateList();
        mAdapter = new HistoryDateAdapter();
        mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayoutManager = new LinearLayoutManager(HistoryDataStaticsActivity.this);
        mDataBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mAuthorityLevel = MDGroundApplication.sInstance.getLoginUser().getAuthorityLevel();
    }

    @Override
    protected void setListener() {
        //上拉加载  有问题
        mDataBinding.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount) {
                    if (mIsLoadeMore) {
                        getDateList();
                        //    GetUserMessageListRequset(mPageIndex);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void getDateList() {
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i <= 21; i++) {
            lists.add(getString(R.string.yongxing) + i);
        }
        for (String str : lists) {
            mArrayList.add(str);
        }
    }

    //跳转到对应页面的
    private void toHistoryDataListActivity(View view) {
        int position = mDataBinding.recyclerView.getChildAdapterPosition(view);
        ProjectWork projectWork = mProjectList.get(position);
        Intent intent = new Intent(this, HistoryDataListActivity.class);
        intent.putExtra(Constants.KEY_HISTORY_DATE_PROJECT, projectWork);
        startActivity(intent);
    }

    //region SERVER
    public void getProjectSummeryListRequest(int pageIndex) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectSummeryList(pageIndex, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                if (ResponseCode.isSuccess(response.body())) {
                    ArrayList<ProjectWork> projects = response.body().getContent(new TypeToken<ArrayList<ProjectWork>>() {
                    });

                    mProjectList.clear();
                    if (projects != null) {
                        for (ProjectWork project : projects) {
                            mProjectList.add(project);
                        }
                        if (projects.size() < 20) {
                            mIsLoadeMore = false;
                        } else {
                            mIsLoadeMore = true;
                            mPageIndex++;
                        }

                        mAdapter.notifyDataSetChanged();
                    } else {
                        mIsLoadeMore = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });

    }
    //endregion

    //region ADAPTER
    public class HistoryDateAdapter extends RecyclerView.Adapter<HistoryDateAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HistoryDataStaticsActivity.this)
                    .inflate(R.layout.item_history_datastatics, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (mProjectList.get(position).getDayFuelCost() > mProjectList.get(position).getFuelCost()) {
                holder.itemHistoryDatastaticsBinding.ivWarning.setImageResource(R.drawable.icon_warning);
            }
            if (mAuthorityLevel < 3) {
                holder.itemHistoryDatastaticsBinding.lltProfit.setVisibility(View.GONE);
                mLayoutParams.setMargins(0, 0, 0, 0);
                holder.itemHistoryDatastaticsBinding.tvQue.setLayoutParams(mLayoutParams);
            }

            ProjectWork projectWork = mProjectList.get(position);
            holder.itemHistoryDatastaticsBinding.tvTitles.setText(projectWork.getProjectName());
            holder.itemHistoryDatastaticsBinding.tvProfit.setText(String.valueOf(projectWork.getProfit()));
            holder.itemHistoryDatastaticsBinding.tvQuestion.setText(projectWork.getRemark());

            if (projectWork.getSaleType().equals(getString(R.string.steam))) {
                // 销售产品为蒸汽
                // 标单
                holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(
                        getString(R.string.kg_per_ton_steam, projectWork.getFuelCost()));
                // 单耗
                holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(
                        getString(R.string.kg_per_ton_steam, projectWork.getDayFuelCost()));
                // 电单耗
                holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(
                        getString(R.string.kw_per_ton_steam, projectWork.getDayElectricityCost()));
                // 水单耗
                holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(
                        getString(R.string.ton_per_ton_steam, projectWork.getDayWaterCost()));
            } else {
                // 销售产品为热力
                // 标单
                holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(
                        getString(R.string.kg_per_ton, projectWork.getFuelCost()));
                // 单耗
                holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(
                        getString(R.string.kg_per_ton, projectWork.getDayFuelCost()));
                // 电单耗
                holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(
                        getString(R.string.kw_per_ton, projectWork.getDayElectricityCost()));
                // 水单耗
                holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(
                        getString(R.string.ton_per_ton, projectWork.getDayWaterCost()));
            }
        }

        @Override
        public int getItemCount() {
            return mProjectList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;

            public MyViewHolder(final View itemView) {
                super(itemView);
                itemHistoryDatastaticsBinding = DataBindingUtil.bind(itemView);

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
