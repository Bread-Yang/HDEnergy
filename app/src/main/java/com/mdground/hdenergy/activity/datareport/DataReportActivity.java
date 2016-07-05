package com.mdground.hdenergy.activity.datareport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.databinding.ActivityDataReportBinding;
import com.mdground.hdenergy.databinding.ItemBoilerBinding;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.StringUtil;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class DataReportActivity extends ToolbarActivity<ActivityDataReportBinding>
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog mDatePickerDialog;

    private DataReportAdapter mAdapter;

    private BaoPickerDialog mBaoPickerDialog;

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    private ArrayList<ProjectWorkFurnace> mBoilerArrayList = new ArrayList<>();

    private ArrayList<String> mSalesProductArrayList = new ArrayList<>();

    private int mWheelViewChooseResID, mSelectProjectIndex;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_data_report;
    }

    @Override
    protected void initData() {
        Date previousDate = DateUtils.previousDate(new Date(), 0);
        mDataBinding.tvDate.setText(DateUtils.getYearMonthDayWithDash(previousDate));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        mDataBinding.recyclerView.setFocusable(false);

        mAdapter = new DataReportAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        // 项目数据
        mBaoPickerDialog = new BaoPickerDialog(this);

        // 销售产品数据
        String[] salesProductStrings = getResources().getStringArray(R.array.sale_product_type);
        Collections.addAll(mSalesProductArrayList, salesProductStrings);
        mDataBinding.tvSaleProduct.setText(salesProductStrings[0]);

        getProjectListRequest();

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
                        mSelectProjectIndex = currentPosition;
                        mDataBinding.tvProject.setText(mProjectArrayList.get(currentPosition).getProjectName());
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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.YEAR, year);

        mDataBinding.tvDate.setText(DateUtils.getYearMonthDayWithDash(cal.getTime()));
    }

    //region  ACTION
    public void selectProjectAction(View view) {
        mWheelViewChooseResID = R.id.tvProject;
        ArrayList<String> projectStringArrayList = new ArrayList<>();
        for (Project project : mProjectArrayList) {
            projectStringArrayList.add(project.getProjectName());
        }
        mBaoPickerDialog.bindWheelViewData(projectStringArrayList);
        mBaoPickerDialog.show();
    }

    public void selectSaleProductAction(View view) {
        mWheelViewChooseResID = R.id.tvSaleProduct;
        mBaoPickerDialog.bindWheelViewData(mSalesProductArrayList);
        mBaoPickerDialog.show();
    }

    public void selectDateAction(View view) {
        if (mDatePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            mDatePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.show();
    }

    public void submitAction(View view) {
        ProjectWork projectWork = new ProjectWork();

        projectWork.setUserID(MDGroundApplication.mInstance.getLoginUser().getUserID());
        projectWork.setUserName(MDGroundApplication.mInstance.getLoginUser().getUserName());

        // 项目
        Project project = mProjectArrayList.get(mSelectProjectIndex);
        projectWork.setProjectID(project.getProjectID());
        projectWork.setProjectName(project.getProjectName());

        // 日期
        String date = mDataBinding.tvDate.getText().toString() + " 00:00:00";
        projectWork.setCreatedTime(date);

        //销售产品
        String saleProduct = mDataBinding.tvSaleProduct.getText().toString();
        projectWork.setSaleType(saleProduct);

        // 锅炉

        // 项目费用
        String projectExpenseString = mDataBinding.etuiProjectExpense.getText();
        if (!StringUtil.isEmpty(projectExpenseString)) {
            int projectExpense = Integer.parseInt(projectExpenseString) * 100;
            projectWork.setDailyExpense(projectExpense);
        }

        // 费用明细
        String feeDetail = mDataBinding.etProjectDetail.getText().toString();
        projectWork.setExpenseDetail(feeDetail);

        // 其他问题
        String otherProblem = mDataBinding.etOtherProblem.getText().toString();
        projectWork.setRemark(otherProblem);

        saveProjectWorkRequest(projectWork);
    }
    //endregion

    //region SERVER
    private void getProjectListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                mProjectArrayList = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                });
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void saveProjectWorkRequest(ProjectWork projectWork) {
        GlobalRestful.getInstance().SaveProjectWork(projectWork, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
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
