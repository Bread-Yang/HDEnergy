package hdenergy.mdground.com.hdenergy.activity.attendancestatics;

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
import hdenergy.mdground.com.hdenergy.databinding.ActivityEmployeeAttendanceStaticsBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemEmployeeAttendanceStaticsBinding;

/**
 * Created by yoghourt on 6/29/16.
 */

public class EmployeeAttendanceStaticsActivity extends ToolbarActivity<ActivityEmployeeAttendanceStaticsBinding> {

    private EmployeeAttendanceStaticsAdapter mAdapter;

    private ArrayList<String> mAttendanceArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_employee_attendance_statics;
    }

    @Override
    protected void initData() {
        mAttendanceArrayList.add("");
        mAttendanceArrayList.add("");
        mAttendanceArrayList.add("");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EmployeeAttendanceStaticsAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    //region ADAPTER
    class EmployeeAttendanceStaticsAdapter extends RecyclerView.Adapter<EmployeeAttendanceStaticsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_employee_attendance_statics, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            setItemListener(holder, position);
        }

        @Override
        public int getItemCount() {
            return mAttendanceArrayList.size();
        }

        private void setItemListener(ViewHolder holder, final int position) {
            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EmployeeAttendanceStaticsActivity.this, EmployeeAttendanceDetailActivity.class);
                    startActivity(intent);
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ItemEmployeeAttendanceStaticsBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
