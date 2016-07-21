package com.mdground.hdenergy.activity.attendancereport;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityAttendanceReportBinding;
import com.mdground.hdenergy.models.Department;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.ProjectCategory;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 2016-06-28.
 */
public class AttendanceReportActivity extends ToolbarActivity<ActivityAttendanceReportBinding>
        implements OnDateSetListener {

    private TimePickerDialog mTimePickerDialog;

    private BaoPickerDialog mBaoPickerDialog;

    private UserAttendance mUserAttendance;

    private ArrayList<Department> mDepartmentArrayList = new ArrayList<>();

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    private ArrayList<UserInfo> mUserInfoArrayList = new ArrayList<>();

    private ArrayList<String> mAttendanceStatusArrayList = new ArrayList<>();

    private ArrayList<ProjectCategory> mProjectCategoryArrayList = new ArrayList<>();

    private ArrayList<ProjectCategory> mProjectContentArrayList = new ArrayList<>();

    private long mStartTime, mEndTime;

    private int mSelectDepartment, mSelectProjectIndex, mSelectUserInfoIndex, mSelectAttendanceStatus, mSelectCategoryIndex,
            mSelectContentIndex, mClickResID, mWheelViewChooseResID;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_attendance_report;
    }

    @Override
    protected void initData() {
        mUserAttendance = getIntent().getParcelableExtra(Constants.KEY_USER_ATTENDANCE);

        if (mUserAttendance == null) {
            mUserAttendance = new UserAttendance();
        } else {

        }

        getDepartmentListRequest();
        getProjectCategoryListRequest(0);

        initTimePickerDialog();
        mBaoPickerDialog = new BaoPickerDialog(this);

        // 默认上班时间,早上9点
        Date previousStartWorkDate = DateUtils.previousDate(new Date(), 9);
        mStartTime = previousStartWorkDate.getTime();
        mDataBinding.tvStartTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(previousStartWorkDate));

        // 默认下班时间,下午17点
        Date previousEndWorkDate = DateUtils.previousDate(new Date(), 17);
        mEndTime = previousEndWorkDate.getTime();
        mDataBinding.tvEndTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(previousEndWorkDate));

        calculateManHours();

        // 上班状态数据
        String[] attendanceStatusStrings = getResources().getStringArray(R.array.attendance_status_array);
        Collections.addAll(mAttendanceStatusArrayList, attendanceStatusStrings);

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
                    case R.id.tvDepartment:
                        mSelectDepartment = currentPosition;
                        hideRateLayout(currentPosition != 1); // 当部门为工程部门时，不显示打分栏
                        mDataBinding.tvDepartment.setText(mDepartmentArrayList.get(currentPosition).getDepartmentName());

                        mSelectUserInfoIndex = 0;
                        getUserListByDepartmentRequest();
                        break;
                    case R.id.tvProject:
                        mSelectProjectIndex = currentPosition;
                        mDataBinding.tvProject.setText(mProjectArrayList.get(currentPosition).getProjectName());
                        break;
                    case R.id.tvName:
                        mSelectUserInfoIndex = currentPosition;
                        mDataBinding.tvName.setText(mUserInfoArrayList.get(currentPosition).getUserName());
                        break;
                    case R.id.tvAttendanceStatus:
                        mSelectAttendanceStatus = currentPosition;
                        mDataBinding.tvAttendanceStatus.setText(mAttendanceStatusArrayList.get(currentPosition));
                        break;
                    case R.id.tvCategory:
                        mSelectCategoryIndex = currentPosition;
                        mDataBinding.tvCategory.setText(mProjectCategoryArrayList.get(currentPosition).getCategoryName());

                        mSelectContentIndex = 0;
                        getProjectCategoryListRequest(mProjectCategoryArrayList.get(currentPosition).getCategoryID());
                        break;
                    case R.id.tvContent:
                        mSelectContentIndex = currentPosition;
                        mDataBinding.tvContent.setText(mProjectContentArrayList.get(currentPosition).getCategoryName());
                        break;
                }
            }
        });
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        switch (mClickResID) {
            case R.id.rltStartTime:
                if (millseconds > mEndTime) {
                    ViewUtils.toast(R.string.stat_work_time_must_before_end_time);
                    return;
                }
                mStartTime = millseconds;
                mDataBinding.tvStartTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(new Date(millseconds)));
                break;
            case R.id.rltEndTime:
                if (millseconds < mStartTime) {
                    ViewUtils.toast(R.string.end_work_time_must_after_start_time);
                    return;
                }
                mEndTime = millseconds;
                mDataBinding.tvEndTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(new Date(millseconds)));
                break;
        }
        calculateManHours();
    }

    private void initTimePickerDialog() {
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.confirm))
                .setTitleStringId(getString(R.string.choose_time))
                .setYearText(getString(R.string.year))
                .setMonthText(getString(R.string.month))
                .setDayText(getString(R.string.day))
                .setHourText(getString(R.string.hour))
                .setMinuteText(getString(R.string.minute))
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
    }

    // 选择工程公司则无打分的选项
    private void hideRateLayout(boolean isShow) {
        mDataBinding.tvRateTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mDataBinding.rltRate.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    // 计算工时
    private void calculateManHours() {
        mDataBinding.tvManHours.setText(DateUtils.toManHours(mEndTime - mStartTime));
    }

    //region ACTION
    public void selectDepartmentAction(View view) {
        mWheelViewChooseResID = R.id.tvDepartment;
        ArrayList<String> departmentStringArrayList = new ArrayList<>();
        for (Department department : mDepartmentArrayList) {
            departmentStringArrayList.add(department.getDepartmentName());
        }
        mBaoPickerDialog.bindWheelViewData(departmentStringArrayList);
        mBaoPickerDialog.setCurrentItem(mSelectDepartment);
        mBaoPickerDialog.show();
    }

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

    public void selectUserInfoAction(View view) {
        mWheelViewChooseResID = R.id.tvName;
        ArrayList<String> userNameStringArrayList = new ArrayList<>();
        for (UserInfo userInfo : mUserInfoArrayList) {
            userNameStringArrayList.add(userInfo.getUserName());
        }
        mBaoPickerDialog.bindWheelViewData(userNameStringArrayList);
        mBaoPickerDialog.setCurrentItem(mSelectUserInfoIndex);
        mBaoPickerDialog.show();
    }

    public void selectAttendanceStatusAction(View view) {
        mWheelViewChooseResID = R.id.tvAttendanceStatus;
        mBaoPickerDialog.bindWheelViewData(mAttendanceStatusArrayList);
        mBaoPickerDialog.setCurrentItem(mSelectAttendanceStatus);
        mBaoPickerDialog.show();
    }

    public void selectDateAction(View view) {
        mClickResID = view.getId();

        mTimePickerDialog.show(getSupportFragmentManager(), String.valueOf(mClickResID));
    }

    public void selectCategoryAction(View view) {
        mWheelViewChooseResID = R.id.tvCategory;
        ArrayList<String> categoryStringArrayList = new ArrayList<>();
        for (ProjectCategory projectCategory : mProjectCategoryArrayList) {
            categoryStringArrayList.add(projectCategory.getCategoryName());
        }
        mBaoPickerDialog.bindWheelViewData(categoryStringArrayList);
        mBaoPickerDialog.setCurrentItem(mSelectCategoryIndex);
        mBaoPickerDialog.show();
    }

    public void selectContentAction(View view) {
        mWheelViewChooseResID = R.id.tvContent;
        ArrayList<String> contentStringArrayList = new ArrayList<>();
        for (ProjectCategory projectContent : mProjectContentArrayList) {
            contentStringArrayList.add(projectContent.getCategoryName());
        }
        mBaoPickerDialog.bindWheelViewData(contentStringArrayList);
        mBaoPickerDialog.setCurrentItem(mSelectContentIndex);
        mBaoPickerDialog.show();
    }

    public void submitAction(View view) {
        UserAttendance userAttendance = mUserAttendance;

        userAttendance.setReportUserID(MDGroundApplication.sInstance.getLoginUser().getUserID());
        userAttendance.setReportUserName(MDGroundApplication.sInstance.getLoginUser().getUserName());

        // 部门
        String department = mDataBinding.tvDepartment.getText().toString();
        userAttendance.setDepartment(department);

        // 项目
        if (mProjectArrayList.size() > 0) {
            Project project = mProjectArrayList.get(mSelectProjectIndex);
            userAttendance.setProjectID(project.getProjectID());
        }

        // 姓名
        if (mUserInfoArrayList.size() > 0) {
            UserInfo userinfo = mUserInfoArrayList.get(mSelectUserInfoIndex);
            userAttendance.setUserID(userinfo.getUserID());
            userAttendance.setUserName(userinfo.getUserName());
        }

        // 基本信息为必填项
        if (StringUtils.isEmpty(department) || mProjectArrayList.size() == 0 || mUserInfoArrayList.size() == 0) {
            ViewUtils.toast(R.string.fill_base_info);
            return;
        }

        // 上班状态
        userAttendance.setStatus(mSelectAttendanceStatus);

        // 上班时间
        String startWorkTime = mDataBinding.tvStartTime.getText().toString() + ":00";
        userAttendance.setBeginTime(startWorkTime);

        // 下班时间
        String endWorkTime = mDataBinding.tvEndTime.getText().toString() + ":00";
        userAttendance.setEndTime(endWorkTime);

        // 加班时间
        String overTimeString = mDataBinding.etOverTimeHour.getText().toString();
        if (!StringUtils.isEmpty(overTimeString)) {
            int overTime = Integer.parseInt(overTimeString);
            userAttendance.setOverTime(overTime);
        }

        // 加班事由
        String overTimeReason = mDataBinding.etOverTimeReason.getText().toString();
        userAttendance.setOverTimeReason(overTimeReason);

        // 类别
        if (mProjectCategoryArrayList.size() > 0) {
            ProjectCategory projectCategory = mProjectCategoryArrayList.get(mSelectCategoryIndex);
            userAttendance.setCategoryID1(projectCategory.getCategoryID());
            userAttendance.setCategoryName1(projectCategory.getCategoryName());
        }

        // 工作内容
        if (mProjectContentArrayList.size() > 0) {
            ProjectCategory projectContent = mProjectContentArrayList.get(mSelectContentIndex);
            userAttendance.setCategoryID2(projectContent.getCategoryID());
            userAttendance.setCategoryName2(projectContent.getCategoryName());
        }

        if (mProjectCategoryArrayList.size() == 0 || mProjectContentArrayList.size() == 0) {
            ViewUtils.toast(R.string.fill_category_info);
            return;
        }

        // 出差地点
        String businessTripLocation = mDataBinding.etBusinessTripLocation.getText().toString();
        userAttendance.setBusinessAddress(businessTripLocation);

        // 交通费
        String transporatationFareString = mDataBinding.etuiTransportationFare.getText();
        if (!StringUtils.isEmpty(transporatationFareString)) {
            int transporatationFare = Integer.parseInt(transporatationFareString);
            userAttendance.setTransportation(transporatationFare);
        }

        // 交通耗时
        String transportationTimeConsumingString = mDataBinding.etuiTransportationTimeconsuming.getText();
        if (!StringUtils.isEmpty(transportationTimeConsumingString)) {
            int transportationTimeConsuming = Integer.parseInt(transportationTimeConsumingString);
            userAttendance.setTrafficTime(transportationTimeConsuming);
        }

        // 住宿费
        String accommodationFeeString = mDataBinding.etuiAccommodationFee.getText();
        if (!StringUtils.isEmpty(accommodationFeeString)) {
            int accommodationFee = Integer.parseInt(accommodationFeeString);
            userAttendance.setAccommodationFee(accommodationFee);
        }

        // 其他问题
        String otherProblem = mDataBinding.etOtherProblem.getText().toString();
        userAttendance.setRemark(otherProblem);

        // 打分
        String scoreString = mDataBinding.etScore.getText().toString();
        if (!StringUtils.isEmpty(scoreString)) {
            int score = Integer.parseInt(scoreString);
            userAttendance.setScore(score);
        }

        saveUserAttendanceRequest(userAttendance);
    }
    //endregion

    //region SERVER
    private void getDepartmentListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetDepartmentList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mDepartmentArrayList = response.body().getContent(new TypeToken<ArrayList<Department>>() {
                });
                mDataBinding.tvDepartment.setText(mDepartmentArrayList.get(0).getDepartmentName());

                getProjectListRequest();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getProjectListRequest() {
        GlobalRestful.getInstance().GetProjectList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mProjectArrayList = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                });
                mDataBinding.tvProject.setText(mProjectArrayList.get(0).getProjectName());

                getUserListByDepartmentRequest();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getUserListByDepartmentRequest() {
        String department = mDataBinding.tvDepartment.getText().toString();

        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUserListByDepartment(department, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                mUserInfoArrayList = response.body().getContent(new TypeToken<ArrayList<UserInfo>>() {
                });

                if (mUserInfoArrayList.size() > 0) {
                    mDataBinding.tvName.setText(mUserInfoArrayList.get(0).getUserName());
                } else {
                    mDataBinding.tvName.setText("");
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getProjectCategoryListRequest(final int parentID) {
        GlobalRestful.getInstance().GetProjectCategoryList(parentID, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (parentID == 0) {
                    mProjectCategoryArrayList = response.body().getContent(new TypeToken<ArrayList<ProjectCategory>>() {
                    });

                    if (mProjectCategoryArrayList.size() > 0) {
                        ProjectCategory firstCategory = mProjectCategoryArrayList.get(0);
                        mDataBinding.tvCategory.setText(firstCategory.getCategoryName());
                        getProjectCategoryListRequest(mProjectCategoryArrayList.get(0).getCategoryID());
                    }
                } else {
                    mProjectContentArrayList = response.body().getContent(new TypeToken<ArrayList<ProjectCategory>>() {
                    });
                    if (mProjectContentArrayList.size() > 0) {
                        ProjectCategory firstContent = mProjectContentArrayList.get(0);
                        mDataBinding.tvContent.setText(firstContent.getCategoryName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void saveUserAttendanceRequest(UserAttendance userAttendance) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().SaveUserAttendance(userAttendance, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

}
