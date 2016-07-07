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
import com.mdground.hdenergy.databinding.ActivityHistoryDatastaticsBinding;
import com.mdground.hdenergy.databinding.ItemHistoryDatastaticsBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.ViewUtils;
import com.socks.library.KLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-28.
 */

public class HistoryDataStaticsActivity extends ToolbarActivity<ActivityHistoryDatastaticsBinding> {
    private HistoryDateAdapter mAdapter;
    private ArrayList<String> mArrayList = new ArrayList<>();
    private ArrayList<ProjectWork> mProjectList = new ArrayList<>();
    private int authorityLevel;
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayoutManager mLinearLayoutManager;
    int mPageIndex = 0;
    private boolean mIsLoadeMore;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_datastatics;
    }

    @Override
    protected void initData() {
        getDateList();
        GetProjectSummeryListRequest(0);
        mAdapter = new HistoryDateAdapter();
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayoutManager = new LinearLayoutManager(HistoryDataStaticsActivity.this);
        mDataBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        authorityLevel = MDGroundApplication.mInstance.getLoginUser().getAuthorityLevel();
    }

    @Override
    protected void setListener() {
        //上啦加载  有问题
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

    //region SERVERN

    public void GetProjectSummeryListRequest(int pageIndex) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectSummeryList(pageIndex, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                if (ResponseCode.isSuccess(response.body())) {
                    ArrayList<ProjectWork> projects = response.body().getContent(new TypeToken<ArrayList<ProjectWork>>() {
                    });
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

    //region METHOD
    public void getDateList() {
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i <= 21; i++) {
            lists.add(getString(R.string.yongxing) + i);
        }
        for (String str : lists) {
            mArrayList.add(str);
        }

    }


    //跳转到对应页面的
    public void toHistoryDataListActivity(View view) {
        int position = mDataBinding.recyclerView.getChildAdapterPosition(view);
     //   String name = mArrayList.get(position);
        int projectID=mProjectList.get(position).getProjectID();
        KLog.e("projectID---"+projectID);
        String projectName=mProjectList.get(position).getProjectName();
        Intent intent = new Intent(this, HistoryDataListActivity.class);
        intent.putExtra(Constants.KEY_HISTORY_DATE_NAME, projectName);
        intent.putExtra(Constants.KEY_HISTORY_DATE_PROJECT_ID,projectID);
        startActivity(intent);
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
            if (authorityLevel < 3) {
                holder.itemHistoryDatastaticsBinding.lltProfit.setVisibility(View.GONE);
                layoutParams.setMargins(0, 0, 0, 0);
                holder.itemHistoryDatastaticsBinding.tvQue.setLayoutParams(layoutParams);
            }
            holder.itemHistoryDatastaticsBinding.tvTitles.setText(mProjectList.get(position).getProjectName());
            holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(String.valueOf(mProjectList.get(position).getFuelCost()));
            holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(mProjectList.get(position).getDayElectricityCost());
            holder.itemHistoryDatastaticsBinding.tvProfit.setText(mProjectList.get(position).getExpenseDetail());
            holder.itemHistoryDatastaticsBinding.tvQuestion.setText(mProjectList.get(position).getRemark());
            holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(mProjectList.get(position).getDayWaterCost());
            holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(mProjectList.get(position).getDayFuelCost());

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
