package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityHistoryDataDetailsBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemCheckBoilerBinding;
import hdenergy.mdground.com.hdenergy.models.ProjectWorkFurnace;

/**
 * Created by PC on 2016-06-28.
 */

public class HistoryDataDetailsActivity extends ToolbarActivity<ActivityHistoryDataDetailsBinding> {

    private ArrayList<ProjectWorkFurnace> mProjectWorkFurnaceArrayList = new ArrayList<>();
    private DataDetailsAdapter mAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_data_details;
    }

    @Override
    protected void initData() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.edit));
        ProjectWorkFurnace boiler1 = new ProjectWorkFurnace();
        boiler1.setFurnaceName("锅炉A");
        ProjectWorkFurnace boiler2 = new ProjectWorkFurnace();
        boiler2.setFurnaceName("锅炉B");
        mProjectWorkFurnaceArrayList.add(boiler1);
        mProjectWorkFurnaceArrayList.add(boiler2);
        mAdapter = new DataDetailsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {

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
            holder.itemCheckBoilerBinding.tvBoiler.setText(mProjectWorkFurnaceArrayList.get(position).getFurnaceName());
            holder.itemCheckBoilerBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryDataDetailsActivity.this, HistoryBoilerDetailActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProjectWorkFurnaceArrayList.size();
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
