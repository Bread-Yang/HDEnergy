package com.mdground.hdenergy.activity.bulletin;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityBulletinListBinding;
import com.mdground.hdenergy.databinding.ItemBulletinBinding;
import com.mdground.hdenergy.models.Bulletin;

/**
 * Created by yoghourt on 6/28/16.
 */
public class BulletinListActivity extends ToolbarActivity<ActivityBulletinListBinding> {

    private BulletinListAdapter mAdapter;

    private ArrayList<Bulletin> mBulletinArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bulletin_list;
    }

    @Override
    protected void initData() {
        mBulletinArrayList.add(new Bulletin());
        mBulletinArrayList.add(new Bulletin());
        mBulletinArrayList.add(new Bulletin());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BulletinListAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    //region ADAPTER
    class BulletinListAdapter extends RecyclerView.Adapter<BulletinListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bulletin, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mBulletinArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ItemBulletinBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
