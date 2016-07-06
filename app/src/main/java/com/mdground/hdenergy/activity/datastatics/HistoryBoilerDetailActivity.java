package com.mdground.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import com.mdground.hdenergy.databinding.ItemHistoryBoilerElectricBinding;
import com.mdground.hdenergy.databinding.ItemHistoryBoilerFlowBinding;
import com.mdground.hdenergy.databinding.ItemHistoryBoilerFuelBinding;
import com.mdground.hdenergy.databinding.ItemHistoryBoilerStockBinding;
import com.mdground.hdenergy.databinding.ItemHistoryBoilerWaterBinding;
import com.mdground.hdenergy.models.ProjectFuelWarehouse;
import com.mdground.hdenergy.models.ProjectWorkFlowrate;
import com.mdground.hdenergy.models.ProjectWorkFuel;
import com.mdground.hdenergy.models.ProjectWorkFurnace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 2016-07-03.
 */

public class HistoryBoilerDetailActivity extends ToolbarActivity<ActivityBoilerDetailBinding> {
    private BolierDetailAdapter mAdapter;
    private ProjectWorkFurnace mProjectWorkFurnace;
    private ArrayList<ProjectWorkFlowrate> mFlowArrayList = new ArrayList<>();
    private ArrayList<ProjectWorkFuel> mFuelArrayList = new ArrayList<>();
    private List<ProjectFuelWarehouse> mProjectFuelWarehouseList;

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
        String saleType = bundle.getString(Constants.KEY_SALE_TYPE);
        setTitle(title);
        mDataBinding.tvReportName.setText(reportName);
        mDataBinding.tvSaleType.setText(saleType);
        intiView();
        ArrayList<ProjectWorkFlowrate> flowrates = (ArrayList<ProjectWorkFlowrate>) mProjectWorkFurnace.getProjectWorkFlowrateList();
        if (flowrates != null) {
            mFlowArrayList.clear();
            mFlowArrayList.addAll(flowrates);
        }

        mFuelArrayList.add(new ProjectWorkFuel());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BolierDetailAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

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

        private final int FLOW_VIEW_TYPE = 0x11;
        private final int ELECTRICITY_VIEW_TYPE = 0x12;
        private final int WATER_VIEW_TYPE = 0x10;
        private final int FUEL_VIEW_TYPE = 0x8;
        private final int FEEDSTROCK_VIEW_TYPE = 0x6;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
            switch (viewType) {
                case ELECTRICITY_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_electric, parent, false);
                    break;
                case FLOW_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_flow, parent, false);
                    break;

                case WATER_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_water, parent, false);
                    break;
                case FUEL_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_fuel, parent, false);

                    break;
                case FEEDSTROCK_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_stock, parent, false);
                    break;

            }
            MyViewHolder holder = new MyViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case FLOW_VIEW_TYPE:
                    ItemHistoryBoilerFlowBinding itemHistoryBoilerFlowBinding = (ItemHistoryBoilerFlowBinding) holder.viewDataBinding;
                    itemHistoryBoilerFlowBinding.tvBeginFlow.setText(String.valueOf(mFlowArrayList.get(position).getBeginFlow()));
                    itemHistoryBoilerFlowBinding.tvEndFlow.setText(String.valueOf(mFlowArrayList.get(position).getEndFlow()));
                     int flow=mFlowArrayList.get(position).getBeginFlow()-mFlowArrayList.get(position).getEndFlow();
                    itemHistoryBoilerFlowBinding.tvFlow.setText(String.valueOf(flow));
                //    itemHistoryBoilerFlowBinding.
                    break;

                case ELECTRICITY_VIEW_TYPE:
                    ItemHistoryBoilerElectricBinding itemHistoryBoilerElectricBinding = (ItemHistoryBoilerElectricBinding) holder.viewDataBinding;
                    break;

                case WATER_VIEW_TYPE:
                    ItemHistoryBoilerWaterBinding itemHistoryBoilerWaterBinding = (ItemHistoryBoilerWaterBinding) holder.viewDataBinding;

                    break;
                case FUEL_VIEW_TYPE:
                    ItemHistoryBoilerFuelBinding itemHistoryBoilerFuelBinding = (ItemHistoryBoilerFuelBinding) holder.viewDataBinding;

                    break;
                case FEEDSTROCK_VIEW_TYPE:
                    ItemHistoryBoilerStockBinding itemHistoryBoilerStockBinding = (ItemHistoryBoilerStockBinding) holder.viewDataBinding;

                    break;
            }

        }

        @Override
        public int getItemCount() {
            return mFlowArrayList.size() + 2 + mFuelArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
//            int projectsLength = mProjectArraylist.size();
            int flowLength = mFlowArrayList.size();
            int electricityLength = flowLength + 1;
            int waterLength = electricityLength + 1;
            int fuelLength = waterLength + mFuelArrayList.size();

            if (position < flowLength) {
                return FLOW_VIEW_TYPE;
            } else if (position >= flowLength && position < electricityLength) {
                return ELECTRICITY_VIEW_TYPE;
            } else if (position >= electricityLength && position < waterLength) {
                return WATER_VIEW_TYPE;
            } else if (position >= waterLength && position < fuelLength) {
                return FUEL_VIEW_TYPE;
            } else {
                return FEEDSTROCK_VIEW_TYPE;
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ViewDataBinding viewDataBinding;

            public MyViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
