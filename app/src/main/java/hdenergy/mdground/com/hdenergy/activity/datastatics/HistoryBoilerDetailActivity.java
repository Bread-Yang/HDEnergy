package hdenergy.mdground.com.hdenergy.activity.datastatics;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.databinding.ActivityBoilerDetailBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerElectricBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerFlowBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerFuelBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerProjectBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerStockBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerWaterBinding;
import hdenergy.mdground.com.hdenergy.models.Project;
import hdenergy.mdground.com.hdenergy.models.ProjectFuelWarehouse;
import hdenergy.mdground.com.hdenergy.models.ProjectWorkFlowrate;
import hdenergy.mdground.com.hdenergy.models.ProjectWorkFuel;

/**
 * Created by PC on 2016-07-03.
 */

public class HistoryBoilerDetailActivity extends ToolbarActivity<ActivityBoilerDetailBinding> {
    private BolierDetailAdapter mAdapter;

    private ArrayList<Project> mProjectArraylist = new ArrayList<>();
    private ArrayList<ProjectWorkFlowrate> mFlowArrayList = new ArrayList<>();
    private ArrayList<ProjectWorkFuel> mFuelArrayList = new ArrayList<>();
    private List<ProjectFuelWarehouse> mProjectFuelWarehouseList;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_detail;
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        String title=intent.getStringExtra(Constants.KEY_BOILERR_NAME);
        setTitle(title);
        mProjectArraylist.add(new Project());
        mFlowArrayList.add(new ProjectWorkFlowrate());
        mFuelArrayList.add(new ProjectWorkFuel());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BolierDetailAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

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
        private final int PROJECT_VIEW_TYPE = 0x9;
        private final int FUEL_VIEW_TYPE = 0x8;
        private final int FEEDSTROCK_VIEW_TYPE = 0x6;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
            switch (viewType) {

                case PROJECT_VIEW_TYPE:

                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_project, parent, false);
                    break;
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
                case PROJECT_VIEW_TYPE:

                    ItemHistoryBoilerProjectBinding itemHistoryBoilerProjectBinding = (ItemHistoryBoilerProjectBinding) holder.viewDataBinding;

                    break;

                case FLOW_VIEW_TYPE:
                    ItemHistoryBoilerFlowBinding itemHistoryBoilerFlowBinding = (ItemHistoryBoilerFlowBinding) holder.viewDataBinding;
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
            return mProjectArraylist.size() + mFlowArrayList.size() + 2 + mFuelArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            int projectsLength = mProjectArraylist.size();
            int flowLength = mProjectArraylist.size() + mFlowArrayList.size();
            int electricityLength = flowLength + 1;
            int waterLength = electricityLength + 1;
            int fuelLength = waterLength + mFuelArrayList.size();

            if (position < projectsLength) {
                return PROJECT_VIEW_TYPE;
            } else if (position >= projectsLength && position < flowLength) {
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
