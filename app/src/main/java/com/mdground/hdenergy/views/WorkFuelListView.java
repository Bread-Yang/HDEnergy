package com.mdground.hdenergy.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.databinding.ItemHistoryBoilerWarehouseBinding;
import com.mdground.hdenergy.models.ProjectFuelWarehouse;
import com.mdground.hdenergy.models.ProjectWorkFuel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiongyi on 2016/7/6.
 */
public class WorkFuelListView extends LinearLayout {

    private RecyclerView recyclerView;
    private ProjectWorkFuel mProjectWorkFuel;
    private TextView tvPreviousInventory, tvFuelName, tvCurrentInventory, tvFuelCost, tvAdjustInventory, tvAdjustInventoryExplian, tvFuelUnitConsume;
    private List<ProjectFuelWarehouse> mProjectFuelWareHouseList = new ArrayList<>();
    private MyAdatpter mAdapter;
    private Context context;
    private float mEnterFuelAmount;
    private double mFlowAmount;

    public WorkFuelListView(Context context, ProjectWorkFuel projectWorkFuel, double mFlowAmount) {
        this(context, null, -1, projectWorkFuel, mFlowAmount);
    }

    public WorkFuelListView(Context context, AttributeSet attrs, int defStyleAttr, ProjectWorkFuel projectWorkFuel, double mFlowAmount) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.mFlowAmount = mFlowAmount;
        mProjectWorkFuel = projectWorkFuel;
        mProjectFuelWareHouseList = projectWorkFuel.getProjectFuelWarehouseList();
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_boiler_fuel, this);
        initView(view);
        setDate();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mAdapter = new MyAdatpter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    //设置数据
    private void setDate() {
        float previousInventory = mProjectWorkFuel.getPreviousInventory();
        if (mProjectFuelWareHouseList != null) {
            for (int i = 0; i < mProjectFuelWareHouseList.size(); i++) {
                mEnterFuelAmount = mEnterFuelAmount + mProjectFuelWareHouseList.get(i).getAmount();
            }
        }
        float currentInventory = mProjectWorkFuel.getCurrentInventory();
        float adjustInventory = mProjectWorkFuel.getAdjustInventory();
        float fuelCost = previousInventory + mEnterFuelAmount - currentInventory + adjustInventory;
        double fuelUnitCost = fuelCost * 1000 / mFlowAmount;
        DecimalFormat df = new DecimalFormat("#####0.00");
        String fuelUntiCosts = df.format(fuelUnitCost);
        tvFuelName.setText(mProjectWorkFuel.getFuelName());
        tvPreviousInventory.setText(String.valueOf(previousInventory) + context.getString(R.string.ton));
        tvCurrentInventory.setText(String.valueOf(currentInventory) + context.getString(R.string.ton));
        tvFuelCost.setText(String.valueOf(fuelCost) + context.getString(R.string.kg_unit) + context.getString(R.string.zen_ton));
        if (adjustInventory > 0) {
            tvAdjustInventory.setText("+" + adjustInventory + context.getString(R.string.ton));
        } else {
            tvAdjustInventory.setText(String.valueOf(adjustInventory) + context.getString(R.string.ton));
        }
        tvAdjustInventoryExplian.setText(mProjectWorkFuel.getAdjustReason());
        tvFuelUnitConsume.setText(fuelUntiCosts + context.getString(R.string.kg_unit) + context.getString(R.string.zen_ton));
    }

    private void initView(View view) {
        tvFuelName = (TextView) view.findViewById(R.id.tvFuelName);
        tvPreviousInventory = (TextView) view.findViewById(R.id.tvPreviousInventory);
        tvCurrentInventory = (TextView) view.findViewById(R.id.tvCurrentInventory);
        tvFuelCost = (TextView) view.findViewById(R.id.tvFuelCost);
        tvAdjustInventory = (TextView) view.findViewById(R.id.tvAdjustInventory);
        tvAdjustInventoryExplian = (TextView) view.findViewById(R.id.tvAdjustInventoryExplian);
        tvFuelUnitConsume = (TextView) view.findViewById(R.id.tvFuelUnitConsume);
    }

    public class MyAdatpter extends RecyclerView.Adapter<MyAdatpter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_history_boiler_warehouse, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.itemHistoryBoilerWarehouseBinding.tvSupplier.setText(mProjectFuelWareHouseList.get(position).getSupplier());
            ProjectFuelWarehouse projectFuelWarehouse = mProjectFuelWareHouseList.get(position);
            holder.itemHistoryBoilerWarehouseBinding.setWarehouse(projectFuelWarehouse);
            holder.itemHistoryBoilerWarehouseBinding.tvPosition.setText(String.valueOf(position + 1) + context.getString(R.string.conlon));
        }

        @Override
        public int getItemCount() {
            return mProjectFuelWareHouseList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ItemHistoryBoilerWarehouseBinding itemHistoryBoilerWarehouseBinding;

            public MyViewHolder(View itemView) {
                super(itemView);
                itemHistoryBoilerWarehouseBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
}
