package com.mdground.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.activity.datareport.DataReportActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityHistoryDataDetailsBinding;
import com.mdground.hdenergy.databinding.ItemCheckBoilerBinding;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.itemdecoration.NormalItemDecoration;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PC on 2016-06-28.
 */

public class HistoryDataDetailsActivity extends ToolbarActivity<ActivityHistoryDataDetailsBinding> {

    private DataDetailsAdapter mAdapter;
    private ProjectWork mProjectWork;
    private int mAuthorityLevel;
    private ArrayList<ProjectWorkFurnace> mProjectWorkFurnaceList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_data_details;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mProjectWork = (ProjectWork) bundle.getParcelable(Constants.KEY_HISTORY_DATA_PROJECT);
        ArrayList<ProjectWorkFurnace> FurnaceList = (ArrayList<ProjectWorkFurnace>) mProjectWork.getProjectWorkFurnaceList();
        if (FurnaceList != null) {
            mProjectWorkFurnaceList = FurnaceList;
        }
        setTitle(mProjectWork.getProjectName());
        mAuthorityLevel = MDGroundApplication.sInstance.getLoginUser().getAuthorityLevel();
        if (mAuthorityLevel != 1) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(getString(R.string.edit));
        } else {
            tvRight.setVisibility(View.GONE);
        }
        initView();
        mAdapter = new DataDetailsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.addItemDecoration(new NormalItemDecoration(ViewUtils.dp2px(1)));
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    //初始化界面
    private void initView() {
        mDataBinding.tvReportName.setText(mProjectWork.getUserName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date ceateDate = format.parse(mProjectWork.getReportedTime());
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formatDate = format1.format(ceateDate);
            mDataBinding.tvData.setText(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        float dailyExpense = (float) mProjectWork.getDailyExpense();
        DecimalFormat format2 = new DecimalFormat("0.00");
        String expense = format2.format(dailyExpense);
        mDataBinding.tvSaleProduct.setText(mProjectWork.getSaleType());
        mDataBinding.etProjectDetail.setText(mProjectWork.getExpenseDetail());
        mDataBinding.etProjectExpense.setText(expense);
        mDataBinding.etOtherProblem.setText(mProjectWork.getRemark());

    }

    @Override
    protected void setListener() {
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryDataDetailsActivity.this, DataReportActivity.class);
                intent.putExtra(Constants.KEY_PROJECT, mProjectWork);
                startActivity(intent);
            }
        });
    }

    //region ADAPTER
    class DataDetailsAdapter extends RecyclerView.Adapter<DataDetailsAdapter.ViewHolder> {

        @Override
        public DataDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_check_boiler, parent, false);
            return new DataDetailsAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(DataDetailsAdapter.ViewHolder holder, final int position) {
            holder.itemCheckBoilerBinding.tvBoiler.setText(mProjectWorkFurnaceList.get(position).getFurnaceName());
            holder.itemCheckBoilerBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryDataDetailsActivity.this, HistoryBoilerDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.KEY_HISTORY_DATE_NAME, mProjectWork.getUserName());
                    bundle.putString(Constants.KEY_SALE_TYPE, mProjectWork.getSaleType());
                    bundle.putParcelable(Constants.KEY_BOILERR_PROJECT, mProjectWorkFurnaceList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProjectWorkFurnaceList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ItemCheckBoilerBinding itemCheckBoilerBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                itemCheckBoilerBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
