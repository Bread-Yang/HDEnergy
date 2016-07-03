package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityBoilerDetailBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerElectricBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerFlowBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerFuelBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerProjectBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerStockBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryBoilerWaterBinding;
import hdenergy.mdground.com.hdenergy.models.Electricity;
import hdenergy.mdground.com.hdenergy.models.Feedstock;
import hdenergy.mdground.com.hdenergy.models.Flow;
import hdenergy.mdground.com.hdenergy.models.Fuel;
import hdenergy.mdground.com.hdenergy.models.Project;
import hdenergy.mdground.com.hdenergy.models.Water;

/**
 * Created by PC on 2016-07-03.
 */

public class HistoryBoilerDetailActivity extends ToolbarActivity<ActivityBoilerDetailBinding> {
    private  BolierDetailAdapter mAdapter;

    private ArrayList<Project> mProjectArraylist = new ArrayList<>();
    private ArrayList<Flow> mFlowArrayList = new ArrayList<>();
    private ArrayList<Electricity> mElectricityArrayList = new ArrayList<>();
    private ArrayList<Water> mWaterArrayList = new ArrayList<>();
    private ArrayList<Fuel> mFuelArrayList = new ArrayList<>();
    private ArrayList<Feedstock> mFeedstockArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {

        return R.layout.activity_boiler_detail;
    }

    @Override
    protected void initData() {
        mProjectArraylist.add(new Project());
        mFlowArrayList.add(new Flow());
        mElectricityArrayList.add(new Electricity());
        mWaterArrayList.add(new Water());
        mFuelArrayList.add(new Fuel());
        mFeedstockArrayList.add(new Feedstock());

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
        } else if (positon == mFlowArrayList.size() || positon == (mFlowArrayList.size() + mElectricityArrayList.size())) {
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

                    itemView=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_project,parent,false);
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
                case  FUEL_VIEW_TYPE:
                    itemView=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_fuel,parent,false);

                    break;
                case FEEDSTROCK_VIEW_TYPE:
                    itemView=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_history_boiler_stock,parent,false);
                    break;

            }
            MyViewHolder holder = new MyViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
           switch (getItemViewType(position)){
                case PROJECT_VIEW_TYPE:

                    ItemHistoryBoilerProjectBinding itemHistoryBoilerProjectBinding= (ItemHistoryBoilerProjectBinding) holder.viewDataBinding;

                   break;

               case FLOW_VIEW_TYPE:
                   ItemHistoryBoilerFlowBinding itemHistoryBoilerFlowBinding= (ItemHistoryBoilerFlowBinding) holder.viewDataBinding;
                   break;

               case ELECTRICITY_VIEW_TYPE:
                   ItemHistoryBoilerElectricBinding itemHistoryBoilerElectricBinding= (ItemHistoryBoilerElectricBinding) holder.viewDataBinding;
                   break;

               case WATER_VIEW_TYPE:
                   ItemHistoryBoilerWaterBinding itemHistoryBoilerWaterBinding= (ItemHistoryBoilerWaterBinding) holder.viewDataBinding;

                   break;
               case FUEL_VIEW_TYPE:
                   ItemHistoryBoilerFuelBinding itemHistoryBoilerFuelBinding= (ItemHistoryBoilerFuelBinding) holder.viewDataBinding;

                   break;
               case FEEDSTROCK_VIEW_TYPE:
                   ItemHistoryBoilerStockBinding itemHistoryBoilerStockBinding= (ItemHistoryBoilerStockBinding) holder.viewDataBinding;

                   break;
           }

        }

        @Override
        public int getItemCount() {
            return mProjectArraylist.size()+mFlowArrayList.size()+mElectricityArrayList.size()
                    +mWaterArrayList.size()+mFuelArrayList.size()+ mFeedstockArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            int projectsLength = mProjectArraylist.size();
            int flowLength = mProjectArraylist.size() + mFlowArrayList.size();
            int electricityLength = flowLength+ mElectricityArrayList.size();
            int waterLength=electricityLength+mWaterArrayList.size();
            int fuelLength=waterLength+mFuelArrayList.size();
            int fuelAmountLength=fuelLength+ mFeedstockArrayList.size();

            if (position < projectsLength) {
                return PROJECT_VIEW_TYPE;
            } else if (position >= projectsLength && position < flowLength) {
                return FLOW_VIEW_TYPE;
            } else if (position >=flowLength&&position<electricityLength){
                return  ELECTRICITY_VIEW_TYPE;
            }else if(position>=electricityLength&&position<waterLength){
                return WATER_VIEW_TYPE;
            }else if(position>=waterLength&&position<fuelLength){
                return  FUEL_VIEW_TYPE;
            }else{
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
