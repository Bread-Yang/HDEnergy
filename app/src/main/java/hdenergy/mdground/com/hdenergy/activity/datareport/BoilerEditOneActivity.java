package hdenergy.mdground.com.hdenergy.activity.datareport;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityBoilerEditOneBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemBoilerElectricQuantityBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemBoilerFlowBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemBoilerWaterQuantityBinding;
import hdenergy.mdground.com.hdenergy.models.Electricity;
import hdenergy.mdground.com.hdenergy.models.Flow;
import hdenergy.mdground.com.hdenergy.models.Water;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class BoilerEditOneActivity extends ToolbarActivity<ActivityBoilerEditOneBinding> {

    private EditOneAdapter mAdapter;

    private ArrayList<Flow> mFlowArrayList = new ArrayList<>();
    private ArrayList<Electricity> mElectricityArrayList = new ArrayList<>();
    private ArrayList<Water> mWaterArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_edit_one;
    }

    @Override
    protected void initData() {
        mFlowArrayList.add(new Flow());
        mElectricityArrayList.add(new Electricity());
        mWaterArrayList.add(new Water());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EditOneAdapter();
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

    //region  ACTION
    public void nextStepAction(View view) {
        Intent intent = new Intent(this, BoilerEditTwoActivity.class);
        startActivity(intent);
    }
    //endregion

    //region ADAPTER
    class EditOneAdapter extends RecyclerView.Adapter<EditOneAdapter.ViewHolder> {

        private final int FLOW_VIEW_TYPE = 0x11;
        private final int ELECTRICITY_VIEW_TYPE = 0x12;
        private final int WATER_VIEW_TYPE = 0x13;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
            switch (viewType) {
                case FLOW_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_boiler_flow, parent, false);
                    break;
                case ELECTRICITY_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_boiler_electric_quantity, parent, false);
                    break;
                case WATER_VIEW_TYPE:
                    itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_boiler_water_quantity, parent, false);
                    break;
            }
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            ImageView ivAddOrDelete = null;
            switch (getItemViewType(position)) {
                case FLOW_VIEW_TYPE:
                    ItemBoilerFlowBinding itemBoilerFlowBinding = ((ItemBoilerFlowBinding) holder.viewDataBinding);
                    itemBoilerFlowBinding.setFlow(mFlowArrayList.get(position));
                    ivAddOrDelete = itemBoilerFlowBinding.ivAddOrDelete;
                    break;
                case ELECTRICITY_VIEW_TYPE:
                    ItemBoilerElectricQuantityBinding itemBoilerElectricQuantityBinding = ((ItemBoilerElectricQuantityBinding) holder.viewDataBinding);
                    itemBoilerElectricQuantityBinding.setElectricity(mElectricityArrayList.get(position - mFlowArrayList.size()));
                    ivAddOrDelete = itemBoilerElectricQuantityBinding.ivAddOrDelete;
                    break;
                case WATER_VIEW_TYPE:
                    ItemBoilerWaterQuantityBinding itemBoilerWaterQuantityBinding = ((ItemBoilerWaterQuantityBinding) holder.viewDataBinding);
                    itemBoilerWaterQuantityBinding.setWater(mWaterArrayList.get(position - mFlowArrayList.size() - mElectricityArrayList.size()));
                    ivAddOrDelete = itemBoilerWaterQuantityBinding.ivAddOrDelete;
                    break;
            }

            if (isHeader(position)) {
                ivAddOrDelete.setImageResource(R.drawable.icon_addition_pay);

                ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (getItemViewType(position)) {
                            case FLOW_VIEW_TYPE:
                                mFlowArrayList.add(new Flow());
                                break;
                            case ELECTRICITY_VIEW_TYPE:
                                mElectricityArrayList.add(new Electricity());
                                break;
                            case WATER_VIEW_TYPE:
                                mWaterArrayList.add(new Water());
                                break;
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                ivAddOrDelete.setImageResource(R.drawable.icon_reduce_pay);

                ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (getItemViewType(position)) {
                            case FLOW_VIEW_TYPE:
                                mFlowArrayList.remove(position);
                                break;
                            case ELECTRICITY_VIEW_TYPE:
                                mElectricityArrayList.remove(position - mFlowArrayList.size());
                                break;
                            case WATER_VIEW_TYPE:
                                mWaterArrayList.remove(position - mFlowArrayList.size() - mElectricityArrayList.size());
                                break;
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mFlowArrayList.size() + mElectricityArrayList.size() + mWaterArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < mFlowArrayList.size()) {
                return FLOW_VIEW_TYPE;
            } else if (position >= mFlowArrayList.size() && position < (mFlowArrayList.size() + mElectricityArrayList.size())) {
                return ELECTRICITY_VIEW_TYPE;
            } else {
                return WATER_VIEW_TYPE;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewDataBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
