package com.mdground.hdenergy.activity.bulletin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBulletinListBinding;
import com.mdground.hdenergy.databinding.ItemBulletinBinding;
import com.mdground.hdenergy.models.Bulletin;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BulletinListAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        getBulletinListRequest();
    }

    @Override
    protected void setListener() {

    }

    private void getBulletinListRequest() {
        GlobalRestful.getInstance().GetBulletinList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mBulletinArrayList = response.body().getContent(new TypeToken<ArrayList<Bulletin>>() {
                });

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
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
            final Bulletin bulletin = mBulletinArrayList.get(position);

            holder.viewDataBinding.setBulletin(bulletin);

            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BulletinListActivity.this, BulletinDetailActivity.class);
                    intent.putExtra(Constants.KEY_BULLETIN, bulletin);
                    startActivity(intent);
                }
            });
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
