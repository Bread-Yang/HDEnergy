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
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBoilerDetailBinding;
import com.mdground.hdenergy.databinding.ItemHistoryBoilerFlowBinding;
import com.mdground.hdenergy.models.ProjectWorkFlowrate;
import com.mdground.hdenergy.models.ProjectWorkFuel;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.views.WorkFuelListView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PC on 2016-07-03.
 */

public class HistoryBoilerDetailActivity extends ToolbarActivity<ActivityBoilerDetailBinding> {
    private BolierDetailAdapter mAdapter;
    private ProjectWorkFurnace mProjectWorkFurnace;
    private ArrayList<ProjectWorkFlowrate> mFlowArrayList = new ArrayList<>();
    private ArrayList<ProjectWorkFuel> mFuelArrayList = new ArrayList<>();
    private double mFlowAmount;
    private String mSaleType;
//    private List<ProjectFuelWarehouse> mProjectFuelWarehouseList;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_detail;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mProjectWorkFurnace = bundle.getParcelable(Constants.KEY_BOILERR_PROJECT);
        String title = mProjectWorkFurnace.getFurnaceName();
        String reportName = bundle.getString(Constants.KEY_HISTORY_DATE_NAME);
        mSaleType = bundle.getString(Constants.KEY_SALE_TYPE);
        setTitle(title);
        mDataBinding.tvReportName.setText(reportName);
        mDataBinding.tvSaleType.setText(mSaleType);

        //为热力时，水量布局消失
        if (mSaleType.equals(getString(R.string.heating_power))) {
            mDataBinding.lltWater.setVisibility(View.GONE);
        }

        intiView();
        ArrayList<ProjectWorkFuel> fuels = (ArrayList<ProjectWorkFuel>) mProjectWorkFurnace.getProjectWorkFuelList();
        if (fuels != null) {
            mFuelArrayList.clear();
            mFuelArrayList.addAll(fuels);
        }
        ArrayList<ProjectWorkFlowrate> flowrates = (ArrayList<ProjectWorkFlowrate>) mProjectWorkFurnace.getProjectWorkFlowrateList();
        if (flowrates != null) {
            mFlowArrayList.clear();
            mFlowArrayList.addAll(flowrates);
        }
        if (mFlowArrayList != null) {
            for (int i = 0; i < mFlowArrayList.size(); i++) {
                double flow;
                if (mSaleType.equals(getString(R.string.heating_power))) {
                    flow = (mFlowArrayList.get(i).getEndFlow() - mFlowArrayList.get(i).getBeginFlow()) * 23.8845 / 60;
                } else {
                    flow = (mFlowArrayList.get(i).getEndFlow() - mFlowArrayList.get(i).getBeginFlow()) * 23.8845 / 60;
                }

                mFlowAmount = flow + mFlowAmount;
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BolierDetailAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
        for (int i = 0; i < mFuelArrayList.size(); i++) {
            ProjectWorkFuel projectWorkFuel = mFuelArrayList.get(i);
            WorkFuelListView myTopView = new WorkFuelListView(this, projectWorkFuel, mFlowAmount);
            mDataBinding.lltContent.addView(myTopView);
        }
    }

    private void intiView() {
        mDataBinding.tvWorkingHour.setText(String.valueOf(mProjectWorkFurnace.getWorkingHour()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date ceateDate = format.parse(mProjectWorkFurnace.getCreatedTime());
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formatDate = format1.format(ceateDate);
            mDataBinding.tvDate.setText(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDataBinding.tvElectirc1.setText(mProjectWorkFurnace.getElectricity1() + getString(R.string.electric_unit));
        mDataBinding.tvElectricity2.setText(mProjectWorkFurnace.getElectricity2() + getString(R.string.electric_unit));
        mDataBinding.tvElectricity3.setText(mProjectWorkFurnace.getElectricity3() + getString(R.string.electric_unit));
        mDataBinding.tvElectricitySingleCost.setText(mProjectWorkFurnace.getElectricitySingleCost() + getString(R.string.electric_unit) + getString(R.string.zen_ton));
        mDataBinding.tvWater1.setText(mProjectWorkFurnace.getWater1() + getString(R.string.ton));
        mDataBinding.tvWater2.setText(mProjectWorkFurnace.getWater2() + getString(R.string.ton));
        mDataBinding.tvWater3.setText(mProjectWorkFurnace.getWater3() + getString(R.string.ton));
        mDataBinding.tvWaterSingleCost.setText(mProjectWorkFurnace.getWaterSingleCost() + getString(R.string.ton) + getString(R.string.zen_ton));
        mDataBinding.tvOtherProblem.setText(mProjectWorkFurnace.getDescription());

    }

    @Override
    protected void setListener() {

    }

    private boolean isHeader(int positon) {
        if (positon == 0) {
            return true;
        } else if (positon == mFlowArrayList.size() || positon == (mFlowArrayList.size() + 1)) {
            return true;
        }
        return false;
    }

    //region ADAPTER
    public class BolierDetailAdapter extends RecyclerView.Adapter<BolierDetailAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_boiler_flow, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.viewDataBinding.setPosition(position + 1);
            holder.viewDataBinding.tvBeginFlow.setText(String.valueOf(mFlowArrayList.get(position).getBeginFlow()));
            holder.viewDataBinding.tvEndFlow.setText(String.valueOf(mFlowArrayList.get(position).getEndFlow()));
            double flow;
            if (mSaleType.equals(getString(R.string.heating_power))) {
                flow = (mFlowArrayList.get(position).getEndFlow() - mFlowArrayList.get(position).getBeginFlow()) * 23.8845 / 60;
            } else {
                flow = (mFlowArrayList.get(position).getEndFlow() - mFlowArrayList.get(position).getBeginFlow());
            }
            DecimalFormat df = new DecimalFormat("#####0.00");
            //   mFlowAmount= (int) (flow+mFlowAmount);
            String s = df.format(flow);
            holder.viewDataBinding.tvFlow.setText(s + getString(R.string.ton));
            holder.viewDataBinding.tvAdjustFlow.setText(String.valueOf(mFlowArrayList.get(position).getAdjustFlow()) + getString(R.string.ton));
            holder.viewDataBinding.tvDescription.setText(String.valueOf(mFlowArrayList.get(position).getDescription()));
        }

        @Override
        public int getItemCount() {
            return mFlowArrayList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            ItemHistoryBoilerFlowBinding viewDataBinding;

            public MyViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
