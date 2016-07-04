package com.mdground.hdenergy.activity.projectstartstop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityProjectStartStopBinding;
import com.mdground.hdenergy.databinding.ItemProjectStartStopBinding;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 6/28/16.
 */

public class ProjectStartStopActivity extends ToolbarActivity<ActivityProjectStartStopBinding> {

    private ProjectStartStopAdapter mAdapter;

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_project_start_stop;
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ProjectStartStopAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        getProjectListRequest();
    }

    @Override
    protected void setListener() {

    }

    //region SERVER
    private void getProjectListRequest() {
        GlobalRestful.getInstance().GetProjectList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ArrayList<Project> projectArrayList = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                });

                mProjectArrayList.addAll(projectArrayList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

    //region ADAPTER
    class ProjectStartStopAdapter extends RecyclerView.Adapter<ProjectStartStopAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_project_start_stop, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProjectStartStopActivity.this, ProjectEditActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProjectArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ItemProjectStartStopBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
