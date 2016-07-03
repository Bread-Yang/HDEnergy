package hdenergy.mdground.com.hdenergy.activity.datareport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityDataReportBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemBoilerBinding;
import hdenergy.mdground.com.hdenergy.models.ProjectWorkFurnace;
import hdenergy.mdground.com.hdenergy.utils.DateUtils;
import hdenergy.mdground.com.hdenergy.views.BaoPickerDialog;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class DataReportActivity extends ToolbarActivity<ActivityDataReportBinding>
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog mDatePickerDialog;

    private DataReportAdapter mAdapter;

    private BaoPickerDialog mBaoPickerDialog;

    private ArrayList<ProjectWorkFurnace> mBoilerArrayList = new ArrayList<>();

    private ArrayList<String> mProjectStringArrayList = new ArrayList<>();

    private ArrayList<String> mSalesProductArrayList = new ArrayList<>();

    private int mWheelViewChooseResID;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_data_report;
    }

    @Override
    protected void initData() {
        Date previousDate = DateUtils.previousDate(new Date());
        mDataBinding.tvData.setText(DateUtils.getYearMonthDayWithDash(previousDate));

        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());
        mBoilerArrayList.add(new ProjectWorkFurnace());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        mDataBinding.recyclerView.setFocusable(false);

        mAdapter = new DataReportAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        // 项目数据
        mBaoPickerDialog = new BaoPickerDialog(this);
        mProjectStringArrayList = new ArrayList<>();
        mProjectStringArrayList.add("项目1");
        mProjectStringArrayList.add("项目2");
        mProjectStringArrayList.add("项目3");

        // 销售产品数据
        String[] salesProductStrings = getResources().getStringArray(R.array.sale_product_type);
        Collections.addAll(mSalesProductArrayList, salesProductStrings);

    }

    @Override
    protected void setListener() {
        mBaoPickerDialog.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int currentPosition = wheel.getCurrentItem();

                switch (mWheelViewChooseResID) {
                    case R.id.tvProject:
                        mDataBinding.tvProject.setText(mProjectStringArrayList.get(currentPosition));
                        break;
                    case R.id.tvSaleProduct:
                        mDataBinding.tvSaleProduct.setText(mSalesProductArrayList.get(currentPosition));
                        break;
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDataBinding.tvData.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
    }

    //region  ACTION
    public void selectProjectAction(View view) {
        mWheelViewChooseResID = R.id.tvProject;
        mBaoPickerDialog.bindWheelViewData(mProjectStringArrayList);
        mBaoPickerDialog.show();
    }

    public void selectSaleProductAction(View view) {
        mWheelViewChooseResID = R.id.tvSaleProduct;
        mBaoPickerDialog.bindWheelViewData(mSalesProductArrayList);
        mBaoPickerDialog.show();
    }

    public void SelectDataAction(View view) {
        if (mDatePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.show();
    }

    public void nextStepAction(View view) {

    }
    //endregion

    //region ADAPTER
    class DataReportAdapter extends RecyclerView.Adapter<DataReportAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_boiler, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.viewDataBinding.setBoiler(mBoilerArrayList.get(position));

            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DataReportActivity.this, BoilerEditOneActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBoilerArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ItemBoilerBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
