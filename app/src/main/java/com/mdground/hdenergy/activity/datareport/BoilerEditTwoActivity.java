package com.mdground.hdenergy.activity.datareport;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityBoilerEditTwoBinding;
import com.mdground.hdenergy.databinding.ItemBoilerFuelBinding;
import com.mdground.hdenergy.models.ProjectFuelWarehouse;
import com.mdground.hdenergy.models.ProjectWorkFuel;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class BoilerEditTwoActivity extends ToolbarActivity<ActivityBoilerEditTwoBinding> {

    private EditTwoAdapter mAdapter;

    private ArrayList<ProjectWorkFuel> mFuelArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_edit_two;
    }

    @Override
    protected void initData() {
        ProjectWorkFuel fuel = new ProjectWorkFuel();
        ProjectFuelWarehouse fuelWarehouse = new ProjectFuelWarehouse();
        ArrayList<ProjectFuelWarehouse> feedstockArrayList = new ArrayList<>();
        feedstockArrayList.add(fuelWarehouse);
        fuel.setProjectFuelWarehouseList(feedstockArrayList);

        mFuelArrayList.add(fuel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EditTwoAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    //region  ACTION
    public void nextStepAction(View view) {

    }
    //endregion

    //region ADAPTER
    class EditTwoAdapter extends RecyclerView.Adapter<EditTwoAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_boiler_fuel, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.viewDataBinding.setFuel(mFuelArrayList.get(position));

            if (position == 0) {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.add);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFuelArrayList.add(new ProjectWorkFuel());

                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.delete);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFuelArrayList.remove(position);

                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mFuelArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ItemBoilerFuelBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
