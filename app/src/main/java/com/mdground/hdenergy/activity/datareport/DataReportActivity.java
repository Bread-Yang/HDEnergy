package com.mdground.hdenergy.activity.datareport;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityDataReportBinding;
import com.mdground.hdenergy.databinding.ItemBoilerBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.ProjectWork;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.StringUtils;
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

    private AlertDialog mAlertDialog;

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    private ArrayList<ProjectWorkFurnace> mProjectWorkFurnaceArrayList = new ArrayList<>();

    private ArrayList<String> mSalesProductArrayList = new ArrayList<>();

    private int mWheelViewChooseResID, mSelectProjectIndex;

    private boolean mIsHeatProduct; // 销售产品是否是"热力"

    private ProjectWork mProjectWork;

    private boolean mIsNewProjectWork = true;

    private float mLastEndFlow;

    private float mPreviousInventory;

    private String mLastReportTime;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_data_report;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mProjectWork = intent.getParcelableExtra(Constants.KEY_PROJECT);
        if (mProjectWork != null) {
            mIsNewProjectWork = false;
            mLastReportTime = mProjectWork.getReportedTime();

            List<ProjectWorkFurnace> furnaceList = mProjectWork.getProjectWorkFurnaceList();
            if (furnaceList != null) {
                mProjectWorkFurnaceArrayList.clear();
                mProjectWorkFurnaceArrayList.addAll(furnaceList);
            }
        } else {
            mProjectWork = new ProjectWork();
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
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ceateDate = format.parse(mProjectWork.getReportedTime());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String formatDate = format1.format(ceateDate);
                mDataBinding.tvDate.setText(formatDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String saleType = mProjectWork.getSaleType();
            mDataBinding.tvSaleProduct.setText(saleType);
            mIsHeatProduct = getString(R.string.heating_power).equals(saleType);
            mDataBinding.etuiProjectExpense.setText(String.valueOf(mProjectWork.getDailyExpense()));
            mDataBinding.etProjectDetail.setText(mProjectWork.getExpenseDetail());
            mDataBinding.etOtherProblem.setText(mProjectWork.getRemark());
        }

        // 初始化对话框
        mAlertDialog = ViewUtils.createAlertDialog(this, getString(R.string.confirm_to_submit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveProjectWorkRequest(mProjectWork);
                    }
                });
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
                        Project project = mProjectArrayList.get(currentPosition);
                        setProjectInfoAfterSelectProject(project);
                        getProjectFurnaceList(project.getProjectID());
                        break;

                    case tvSaleProduct:
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

    private void setProjectInfoAfterSelectProject(Project project) {
        mDataBinding.tvProject.setText(project.getProjectName());
        String saleType = project.getSaleType();
        mDataBinding.tvSaleProduct.setText(saleType);
        mIsHeatProduct = getString(R.string.heating_power).equals(saleType);
    }

    //region  ACTION
    public void selectProjectAction(View view) {
        mWheelViewChooseResID = R.id.tvProject;
        ArrayList<String> projectStringArrayList = new ArrayList<>();
        for (Project project : mProjectArrayList) {
            projectStringArrayList.add(project.getProjectName());
        }
        mBaoPickerDialog.bindWheelViewData(projectStringArrayList);
        mBaoPickerDialog.setCurrentItem(mSelectProjectIndex);
        mBaoPickerDialog.show();
    }

    public void selectSaleProductAction(View view) {
        mWheelViewChooseResID = tvSaleProduct;
        mBaoPickerDialog.bindWheelViewData(mSalesProductArrayList);
        mBaoPickerDialog.show();
    }

    public void selectDateAction(View view) {
        if (mDatePickerDialog == null) {
            Date previousDate = DateUtils.previousDate(new Date(), 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(previousDate);
            mDatePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.show();
    }

    public void submitAction(View view) {
        //项目名称
        String projectName = mDataBinding.tvProject.getText().toString();
        if (StringUtils.isEmpty(projectName)) {
            ViewUtils.toast(R.string.fill_project_info);
            return;
        }

        // 日期
        String reportTime = mDataBinding.tvDate.getText().toString() + " 00:00:00";

        // 编辑时不能选择已提交过的日期
//        if (!mIsNewProjectWork) {
//            if (mLastReportTime.equals(reportTime)) {
//                ViewUtils.toast(R.string.edit_mode_choose_different_report_time);
//                return ;
//            }
//        }

        // 销售产品
        String saleProduct = mDataBinding.tvSaleProduct.getText().toString();

        // 锅炉, 改为锅炉不作为必填项
//        for (ProjectWorkFurnace item : mProjectWorkFurnaceArrayList) {
//            if (item.getProjectWorkFlowrateList() == null) {
//                ViewUtils.toast(R.string.fill_boiler_info);
//                return;
//            }
//        }

        // 项目费用
        String projectExpenseString = mDataBinding.etuiProjectExpense.getTextString();
        float projectExpense = 0;
        if (!StringUtils.isEmpty(projectExpenseString)) {
            projectExpense = Float.parseFloat(projectExpenseString);
        } else {
//            ViewUtils.toast(getString(R.string.fill_cost_info));
//            return;
        }

        // 费用明细
        String feeDetail = mDataBinding.etProjectDetail.getText().toString();
        if (StringUtils.isEmpty(feeDetail)) {
//            ViewUtils.toast(getString(R.string.fill_cost_info));
//            return;
            feeDetail = "";
        }

        // 其他问题
        String otherProblem = mDataBinding.etOtherProblem.getText().toString();

        if (mIsNewProjectWork) {
            mProjectWork.setUserID(MDGroundApplication.sInstance.getLoginUser().getUserID());
            mProjectWork.setUserName(MDGroundApplication.sInstance.getLoginUser().getUserName());
            // 项目
            Project project = mProjectArrayList.get(mSelectProjectIndex);
            mProjectWork.setProjectID(project.getProjectID());
        }

        mProjectWork.setProjectName(projectName);
        mProjectWork.setReportedTime(reportTime);
        mProjectWork.setCreatedTime(DateUtils.getServerDateStringByDate(new Date()));
        mProjectWork.setSaleType(saleProduct);
        // 锅炉
        if (mProjectWorkFurnaceArrayList.size() <= 0) {
            ViewUtils.toast(getString(R.string.params_not_full_prompt));
            return;
        }
        mProjectWork.setProjectWorkFurnaceList(mProjectWorkFurnaceArrayList);
        mProjectWork.setDailyExpense(projectExpense);
        mProjectWork.setExpenseDetail(feeDetail);
        mProjectWork.setRemark(otherProblem);

        mAlertDialog.show();
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

                        setProjectInfoAfterSelectProject(mProjectArrayList.get(0));
                    } else {
                        mDataBinding.tvProject.setText(mProjectWork.getProjectName());
                    }
                }

                ViewUtils.dismiss();
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
                ArrayList<ProjectWorkFurnace> tempProjectWorkFurnaceArrayList = response.body().getContent(new TypeToken<ArrayList<ProjectWorkFurnace>>() {
                });

                mProjectWorkFurnaceArrayList.clear();
                if (tempProjectWorkFurnaceArrayList != null) {
                    mProjectWorkFurnaceArrayList.addAll(tempProjectWorkFurnaceArrayList);
                }

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

    private void getProjectLastEndFlowRequest(final ProjectWorkFurnace projectWorkFurnace) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectLastEndFlow(
                projectWorkFurnace.getProjectID(),
                projectWorkFurnace.getFurnaceID(),
                new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (!StringUtils.isEmpty(response.body().getContent())) {
                    mLastEndFlow = Float.valueOf(response.body().getContent());
                } else {
                    mLastEndFlow = 0;
                }

                getProjectFuelPreviousInventoryRequest(projectWorkFurnace);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }


    private void getProjectFuelPreviousInventoryRequest(final ProjectWorkFurnace projectWorkFurnace) {
        GlobalRestful.getInstance().GetProjectFuelPreviousInventory(
                projectWorkFurnace.getProjectID(),
                projectWorkFurnace.getFurnaceID(),
                new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (!StringUtils.isEmpty(response.body().getContent())) {
                    mPreviousInventory = Float.valueOf(response.body().getContent());
                } else {
                    mPreviousInventory = 0;
                }

                ViewUtils.dismiss();

                Intent intent = new Intent(DataReportActivity.this, BoilerEditOneActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_WORK_FURNACE, projectWorkFurnace);
                intent.putExtra(Constants.KEY_IS_HEAT_SALE_PRODUCT, mIsHeatProduct);
                intent.putExtra(Constants.KEY_LAST_END_FLOW, mLastEndFlow);
                intent.putExtra(Constants.KEY_PREVIOUS_INVENTORY, mPreviousInventory);
                startActivityForResult(intent, 0);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void saveProjectWorkRequest(ProjectWork projectWork) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().SaveProjectWork(projectWork, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                if (ResponseCode.isSuccess(response.body())) {
                    ViewUtils.toast(R.string.submit_success);

                    if (!mIsNewProjectWork) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.KEY_PROJECT, mProjectWork);
                        setResult(RESULT_OK, intent);
                    }
                    finish();
                } else {
                    ViewUtils.toast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                ViewUtils.dismiss();
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
                    getProjectLastEndFlowRequest(projectWorkFurnace);
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
