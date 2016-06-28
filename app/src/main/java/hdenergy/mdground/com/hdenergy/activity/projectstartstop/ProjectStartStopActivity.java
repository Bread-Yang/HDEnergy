package hdenergy.mdground.com.hdenergy.activity.projectstartstop;

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
import hdenergy.mdground.com.hdenergy.databinding.ActivityProjectStartStopBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemProjectStartStopBinding;
import hdenergy.mdground.com.hdenergy.models.Project;

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
        mProjectArrayList.add(new Project());
        mProjectArrayList.add(new Project());
        mProjectArrayList.add(new Project());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ProjectStartStopAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

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
