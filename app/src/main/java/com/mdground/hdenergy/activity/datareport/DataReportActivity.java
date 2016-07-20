package com.mdground.hdenergy.activity.datareport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.mdground.hdenergy.constants.Constants;
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
import com.mdground.hdenergy.views.itemdecoration.NormalItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mdground.hdenergy.R.id.tvSaleProduct;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class DataReportActivity extends ToolbarActivity<ActivityDataReportBinding>
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog mDatePickerDialog;

    private DataReportAdapter mAdapter;

    private BaoPickerDialog mBaoPickerDialog;

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    private ArrayList<ProjectWorkFurnace> mProjectWorkFurnaceArrayList = new ArrayList<>();

    private ArrayList<String> mSalesProductArrayList = new ArrayList<>();

    private int mWheelViewChooseResID, mSelectProjectIndex;

    private boolean mIsHeatProduct; // 销售产品是否是"热力"

    private ProjectWork mProjectWork;

    private boolean mIsNewProjectWork = true;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_data_report;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mProjectWork = bundle.getParcelable(Constants.KEY_PROJECT);
                if (mProjectWork != null) {
                    mIsNewProjectWork = false;
                    List<ProjectWorkFurnace> furnaceList = (ArrayList<ProjectWorkFurnace>) mProjectWork.getProjectWorkFurnaceList();
                    if (furnaceList != null) {
                        mProjectWorkFurnaceArrayList.clear();
                        mProjectWorkFurnaceArrayList.addAll(furnaceList);
                    }
                }
            }
        }

        Date previousDate = DateUtils.previousDate(new Date(), 0);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mDataBinding.recyclerView.addItemDecoration(new NormalItemDecoration(ViewUtils.dp2px(1)));
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        mDataBinding.recyclerView.setFocusable(false);

        mAdapter = new DataReportAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        // 项目数据
        mBaoPickerDialog = new BaoPickerDialog(this);

        // 销售产品数据
        String[] salesProductStrings = getResources().getStringArray(R.array.sale_product_type);
        Collections.addAll(mSalesProductArrayList, salesProductStrings);

        getUserProjectListRequest();
        if (mIsNewProjectWork) {
            mDataBinding.tvDate.setText(DateUtils.getYearMonthDayWithDash(previousDate));
            mDataBinding.tvSaleProduct.setText(salesProductStrings[0]);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ceateDate = format.parse(mProjectWork.getCreatedTime());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String formatDate = format1.format(ceateDate);
                mDataBinding.tvDate.setText(formatDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mDataBinding.tvSaleProduct.setText(mProjectWork.getSaleType());
            mDataBinding.etuiProjectExpense.setText(String.valueOf(mProjectWork.getDailyExpense()));
            mDataBinding.etProjectDetail.setText(mProjectWork.getExpenseDetail());
            mDataBinding.etOtherProblem.setText(mProjectWork.getRemark());
        }

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

                        if (mIsNewProjectWork) {

                            mSelectProjectIndex = currentPosition;
                            Project project = mProjectArrayList.get(currentPosition);
                            mDataBinding.tvProject.setText(project.getProjectName());
                            getProjectFurnaceList(project.getProjectID());

                        } else {
                            Project project = mProjectArrayList.get(currentPosition);
                            mDataBinding.tvProject.setText(project.getProjectName());
                        }

                        break;
                    case tvSaleProduct:

                        mIsHeatProduct = (currentPosition == 1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                ProjectWorkFurnace projectWorkFurnace = data.getParcelableExtra(Constants.KEY_PROJECT_WORK_FURNACE);

                for (int i = 0; i < mProjectWorkFurnaceArrayList.size(); i++) {
                    ProjectWorkFurnace originalFurnace = mProjectWorkFurnaceArrayList.get(i);

                    if (originalFurnace.getFurnaceID() == projectWorkFurnace.getFurnaceID()) {
                        mProjectWorkFurnaceArrayList.set(i, projectWorkFurnace);
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
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
        mWheelViewChooseResID = tvSaleProduct;
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

        //项目名称
        String projectName = mDataBinding.tvProject.getText().toString();
        if (StringUtil.isEmpty(projectName)) {
            ViewUtils.toast(R.string.fill_project_info);
            return;
        }

        // 日期
        String date = mDataBinding.tvDate.getText().toString() + " 00:00:00";

        // 销售产品
        String saleProduct = mDataBinding.tvSaleProduct.getText().toString();

        // 锅炉
        for (ProjectWorkFurnace item : mProjectWorkFurnaceArrayList) {
            if (item.getProjectWorkFlowrateList() == null) {
                ViewUtils.toast(R.string.fill_boiler_info);
                return;
            }
        }

        // 项目费用
        String projectExpenseString = mDataBinding.etuiProjectExpense.getText();
        int projectExpense = 0;
        if (!StringUtil.isEmpty(projectExpenseString)) {
            projectExpense = Integer.parseInt(projectExpenseString) * 100;
        } else {
            ViewUtils.toast(getString(R.string.fill_cost_info));
            return;
        }

        // 费用明细
        String feeDetail = mDataBinding.etProjectDetail.getText().toString();
        if (StringUtil.isEmpty(feeDetail)) {
            ViewUtils.toast(getString(R.string.fill_cost_info));
            return;
        }

        // 其他问题
        String otherProblem = mDataBinding.etOtherProblem.getText().toString();

        if (mIsNewProjectWork) {
            ProjectWork projectWork = new ProjectWork();
            projectWork.setUserID(MDGroundApplication.sInstance.getLoginUser().getUserID());
            projectWork.setUserName(MDGroundApplication.sInstance.getLoginUser().getUserName());
            // 项目
            Project project = mProjectArrayList.get(mSelectProjectIndex);
            projectWork.setProjectID(project.getProjectID());
            projectWork.setProjectName(projectName);
            projectWork.setCreatedTime(date);
            projectWork.setSaleType(saleProduct);
            // 锅炉
            if (mProjectWorkFurnaceArrayList.size() <= 0) {
                ViewUtils.toast(getString(R.string.params_not_full_prompt));
                return;
            }
            projectWork.setProjectWorkFurnaceList(mProjectWorkFurnaceArrayList);

            projectWork.setDailyExpense(projectExpense);
            projectWork.setExpenseDetail(feeDetail);
            projectWork.setRemark(otherProblem);
            saveProjectWorkRequest(projectWork);
        } else {

            mProjectWork.setProjectName(projectName);
            mProjectWork.setCreatedTime(date);
            mProjectWork.setSaleType(saleProduct);

            if (mProjectWorkFurnaceArrayList.size() <= 0) {
                ViewUtils.toast(getString(R.string.params_not_full_prompt));
                return;
            }

            mProjectWork.setProjectWorkFurnaceList(mProjectWorkFurnaceArrayList);
            mProjectWork.setDailyExpense(projectExpense);
            mProjectWork.setExpenseDetail(feeDetail);
            mProjectWork.setRemark(otherProblem);
            saveProjectWorkRequest(mProjectWork);
        }
    }
    //endregion

    //region SERVER
    private void getUserProjectListRequest() {
        GlobalRestful.getInstance().GetUserProjectList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mProjectArrayList = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                });

                if (mProjectArrayList.size() > 0) {

                    if (mIsNewProjectWork) {
                        getProjectFurnaceList(mProjectArrayList.get(0).getProjectID());
                        mDataBinding.tvProject.setText(mProjectArrayList.get(0).getProjectName());
                    } else {
                        mDataBinding.tvProject.setText(mProjectWork.getProjectName());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getProjectFurnaceList(final int projectID) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectFurnaceList(projectID, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                mProjectWorkFurnaceArrayList = response.body().getContent(new TypeToken<ArrayList<ProjectWorkFurnace>>() {
                });

                for (ProjectWorkFurnace projectWorkFurnace : mProjectWorkFurnaceArrayList) {
                    projectWorkFurnace.setProjectID(projectID);
                }

                mAdapter.notifyDataSetChanged();
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
                ViewUtils.toast(R.string.submit_success);
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

            final ProjectWorkFurnace projectWorkFurnace = mProjectWorkFurnaceArrayList.get(position);

            holder.viewDataBinding.setBoiler(projectWorkFurnace);
            if (projectWorkFurnace.getProjectWorkFlowrateList() != null) {
                holder.viewDataBinding.tvInputStatus.setText(R.string.already_input);
            } else {
                holder.viewDataBinding.tvInputStatus.setText(R.string.please_input);
            }

            holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DataReportActivity.this, BoilerEditOneActivity.class);
                    intent.putExtra(Constants.KEY_PROJECT_WORK_FURNACE, projectWorkFurnace);
                    intent.putExtra(Constants.KEY_IS_HEAT_SALE_PRODUCT, mIsHeatProduct);
                    startActivityForResult(intent, 0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProjectWorkFurnaceArrayList.size();
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
