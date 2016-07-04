package com.mdground.hdenergy.activity.datareport;

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

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityBoilerEditOneBinding;
import com.mdground.hdenergy.databinding.ItemBoilerElectricQuantityBinding;
import com.mdground.hdenergy.databinding.ItemBoilerFlowBinding;
import com.mdground.hdenergy.databinding.ItemBoilerWaterQuantityBinding;
import com.mdground.hdenergy.models.ProjectWorkFlowrate;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class BoilerEditOneActivity extends ToolbarActivity<ActivityBoilerEditOneBinding> {

    private EditOneAdapter mAdapter;

    private ArrayList<ProjectWorkFlowrate> mFlowArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_edit_one;
    }

    @Override
    protected void initData() {
        mFlowArrayList.add(new ProjectWorkFlowrate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        mDataBinding.recyclerView.setFocusable(false);

        mAdapter = new EditOneAdapter();
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
                case FLOW_VIEW_TYPE: {
                    ItemBoilerFlowBinding itemBoilerFlowBinding = ((ItemBoilerFlowBinding) holder.viewDataBinding);
                    itemBoilerFlowBinding.setFlow(mFlowArrayList.get(position));
                    ivAddOrDelete = itemBoilerFlowBinding.ivAddOrDelete;

                    if (isHeader(position)) {
                        ivAddOrDelete.setImageResource(R.drawable.add);

                        ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mFlowArrayList.add(new ProjectWorkFlowrate());
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        ivAddOrDelete.setImageResource(R.drawable.delete);

                        ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mFlowArrayList.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    break;
                }

                case ELECTRICITY_VIEW_TYPE: {
                    final ItemBoilerElectricQuantityBinding itemBoilerElectricQuantityBinding = ((ItemBoilerElectricQuantityBinding) holder.viewDataBinding);
                    ivAddOrDelete = itemBoilerElectricQuantityBinding.ivAddOrDelete;

                    ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemBoilerElectricQuantityBinding.rltElectricQuantityTwo.getVisibility()
                                    == View.GONE) {
                                itemBoilerElectricQuantityBinding.rltElectricQuantityTwo.setVisibility(View.VISIBLE);
                            } else if (itemBoilerElectricQuantityBinding.rltElectricQuantityThree.getVisibility()
                                    == View.GONE) {
                                itemBoilerElectricQuantityBinding.rltElectricQuantityThree.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    itemBoilerElectricQuantityBinding.ivDeleteTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemBoilerElectricQuantityBinding.rltElectricQuantityTwo.setVisibility(View.GONE);
                        }
                    });

                    itemBoilerElectricQuantityBinding.ivDeleteThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemBoilerElectricQuantityBinding.rltElectricQuantityThree.setVisibility(View.GONE);
                        }
                    });

                    break;
                }

                case WATER_VIEW_TYPE: {
                    final ItemBoilerWaterQuantityBinding itemBoilerWaterQuantityBinding = ((ItemBoilerWaterQuantityBinding) holder.viewDataBinding);
                    ivAddOrDelete = itemBoilerWaterQuantityBinding.ivAddOrDelete;

                    ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemBoilerWaterQuantityBinding.rltWaterQuantityTwo.getVisibility()
                                    == View.GONE) {
                                itemBoilerWaterQuantityBinding.rltWaterQuantityTwo.setVisibility(View.VISIBLE);
                            } else if (itemBoilerWaterQuantityBinding.rltWaterQuantityThree.getVisibility()
                                    == View.GONE) {
                                itemBoilerWaterQuantityBinding.rltWaterQuantityThree.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    itemBoilerWaterQuantityBinding.ivDeleteTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemBoilerWaterQuantityBinding.rltWaterQuantityTwo.setVisibility(View.GONE);
                        }
                    });

                    itemBoilerWaterQuantityBinding.ivDeleteThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemBoilerWaterQuantityBinding.rltWaterQuantityThree.setVisibility(View.GONE);
                        }
                    });
                    break;
                }
            }
        }

        @Override
        public int getItemCount() {
            return mFlowArrayList.size() + 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < mFlowArrayList.size()) {
                return FLOW_VIEW_TYPE;
            } else if (position == mFlowArrayList.size() + 1) {
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
