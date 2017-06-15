package com.mdground.hdenergy.activity.attendancereport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.Department;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.ProjectCategory;
import com.mdground.hdenergy.models.UserAttendance;
import com.mdground.hdenergy.models.UserContacts;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;

import java.util.ArrayList;
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

    private AlertDialog mAlertDialog;

    private ArrayList<Department> mDepartmentArrayList = new ArrayList<>();

    private ArrayList<Project> mProjectArrayList = new ArrayList<>();

    private ArrayList<UserInfo> mUserInfoArrayList = new ArrayList<>();

    private ArrayList<UserContacts> mLoginUserContactsList = new ArrayList<>();

    private ArrayList<String> mAttendanceStatusArrayList = new ArrayList<>();

    private ArrayList<ProjectCategory> mProjectCategoryArrayList = new ArrayList<>();

    private ArrayList<ProjectCategory> mProjectContentArrayList = new ArrayList<>();

    private long mStartTime, mEndTime;

    private int mSelectDepartment, mSelectProjectIndex, mSelectUserInfoIndex, mSelectAttendanceStatus, mSelectCategoryIndex,
            mSelectContentIndex, mClickResID, mWheelViewChooseResID;

    @Override
    protected void onDestroy() {
        GlobalRestful.getInstance().cancel();
        super.onDestroy();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_attendance_report;
    }

    @Override
    protected void initData() {
        // 初始化对话框
        mAlertDialog = ViewUtils.createAlertDialog(this, getString(R.string.confirm_to_submit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submit();
                    }
                });

        mUserAttendance = getIntent().getParcelableExtra(Constants.KEY_USER_ATTENDANCE);

        if (mUserAttendance == null) {
            mUserAttendance = new UserAttendance();
        }
        getUserContactListRequest();
        getProjectCategoryListRequest(0);

//        initTimePickerDialog();
        mBaoPickerDialog = new BaoPickerDialog(this);

        // 上班状态数据
        String[] attendanceStatusStrings = getResources().getStringArray(R.array.attendance_status_array);

        // 最后的"未提交"状态不用显示出来
        for (int i = 0; i < attendanceStatusStrings.length - 1; i++) {
            mAttendanceStatusArrayList.add(attendanceStatusStrings[i]);
        }

        // 工作状态
        mSelectAttendanceStatus = mUserAttendance.getStatus();
        mDataBinding.tvAttendanceStatus.setText(mAttendanceStatusArrayList.get(mUserAttendance.getStatus()));

        // 上班时间
        String startTime = mUserAttendance.getBeginTime();
        if (StringUtils.isEmpty(startTime)) {
            // 默认上班时间,早上9点
            Date previousStartWorkDate = DateUtils.previousDate(new Date(), 9);
            mStartTime = previousStartWorkDate.getTime();
            mDataBinding.tvStartTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(previousStartWorkDate));
        } else {
            mStartTime = DateUtils.getDateByServerDateString(startTime).getMillis();
            mDataBinding.tvStartTime.setText(startTime.substring(0, startTime.length() - 3));
        }

        // 下班时间
        String endTime = mUserAttendance.getEndTime();
        if (StringUtils.isEmpty(endTime)) {
            // 默认下班时间,下午17点
            Date previousEndWorkDate = DateUtils.previousDate(new Date(), 17);
            mEndTime = previousEndWorkDate.getTime();
            mDataBinding.tvEndTime.setText(DateUtils.getYearMonthDayHourMinuteWithDash(previousEndWorkDate));
        } else {
            mEndTime = DateUtils.getDateByServerDateString(endTime).getMillis();
            mDataBinding.tvEndTime.setText(endTime.substring(0, endTime.length() - 3));
        }

        // 工时
        calculateManHours();

        // 加班时间
        mDataBinding.etOverTimeHour.setText(String.valueOf(mUserAttendance.getOverTime()));

        // 加班事由
        mDataBinding.etOverTimeReason.setText(String.valueOf(mUserAttendance.getOverTimeReason()));

        // 出差地点
        mDataBinding.etBusinessTripLocation.setText(mUserAttendance.getBusinessAddress());

        // 交通费
        mDataBinding.etuiTransportationFare.setText(String.valueOf(mUserAttendance.getTransportation()));

        // 交通耗时
        mDataBinding.etuiTransportationTimeconsuming.setText(String.valueOf(mUserAttendance.getTrafficTime()));

        // 住宿费
        mDataBinding.etuiAccommodationFee.setText(String.valueOf(mUserAttendance.getAccommodationFee()));

        // 其他问题
        mDataBinding.etOtherProblem.setText(mUserAttendance.getRemark());

        // 打分
        mDataBinding.etScore.setText(String.valueOf(mUserAttendance.getScore()));
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

                        if (mDepartmentArrayList.size() > 0) {
                            mDataBinding.tvDepartment.setText(mDepartmentArrayList.get(currentPosition).getDepartmentName());

                            mSelectUserInfoIndex = 0;
                            getUserListByDepartmentRequest();
                        }
                        break;
                    case R.id.tvProject:
                        mSelectProjectIndex = currentPosition;

                        if (mProjectArrayList.size() > 0) {
                            mDataBinding.tvProject.setText(mProjectArrayList.get(currentPosition).getProjectName());
                        }
                        break;
                    case R.id.tvName:
                        mSelectUserInfoIndex = currentPosition;
                        if (mUserInfoArrayList.size() > 0) {
                            mDataBinding.tvName.setText(mUserInfoArrayList.get(currentPosition).getUserName());
                        }
                        break;
                    case R.id.tvAttendanceStatus:
                        mSelectAttendanceStatus = currentPosition;
                        mDataBinding.tvAttendanceStatus.setText(mAttendanceStatusArrayList.get(currentPosition));
                        break;
                    case R.id.tvCategory:
                        mSelectCategoryIndex = currentPosition;

                        if (mProjectCategoryArrayList.size() > 0) {
                            mDataBinding.tvCategory.setText(mProjectCategoryArrayList.get(currentPosition).getCategoryName());

                            mSelectContentIndex = 0;
                            getProjectCategoryListRequest(mProjectCategoryArrayList.get(currentPosition).getCategoryID());
                        }
                        break;
                    case R.id.tvContent:
                        mSelectContentIndex = currentPosition;

                        if (mProjectContentArrayList.size() > 0) {
                            mDataBinding.tvContent.setText(mProjectContentArrayList.get(currentPosition).getCategoryName());
                        }
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

    private void initTimePickerDialog(long currentTime) {
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
                .setCurrentMillseconds(currentTime)
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

    private void submit() {
        UserAttendance userAttendance = mUserAttendance;

        userAttendance.setReportUserID(MDGroundApplication.sInstance.getLoginUser().getUserID());
        userAttendance.setReportUserName(MDGroundApplication.sInstance.getLoginUser().getUserName());

        // 部门
        String department = mDataBinding.tvDepartment.getText().toString();
        userAttendance.setDepartment(department);
        userAttendance.setDepartmentID(mDepartmentArrayList.get(mSelectDepartment).getDepartmentID());

        // 项目
        if (mProjectArrayList.size() > 0) {
            Project project = mProjectArrayList.get(mSelectProjectIndex);
            userAttendance.setProjectID(project.getProjectID());
            userAttendance.setProjectName(project.getProjectName());
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
        String transporatationFareString = mDataBinding.etuiTransportationFare.getTextString();
        if (!StringUtils.isEmpty(transporatationFareString)) {
            float transporatationFare = Float.parseFloat(transporatationFareString);
            userAttendance.setTransportation(transporatationFare);
        }

        // 交通耗时
        String transportationTimeConsumingString = mDataBinding.etuiTransportationTimeconsuming.getTextString();
        if (!StringUtils.isEmpty(transportationTimeConsumingString)) {
            int transportationTimeConsuming = Integer.parseInt(transportationTimeConsumingString);
            userAttendance.setTrafficTime(transportationTimeConsuming);
        }

        // 住宿费
        String accommodationFeeString = mDataBinding.etuiAccommodationFee.getTextString();
        if (!StringUtils.isEmpty(accommodationFeeString)) {
            float accommodationFee = Float.parseFloat(accommodationFeeString);
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

        if (mClickResID == R.id.rltStartTime) {
            initTimePickerDialog(mStartTime);
        } else {
            initTimePickerDialog(mEndTime);
        }

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
        mAlertDialog.show();
    }
    //endregion

    //region SERVER
    public void getUserContactListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUserContactList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (ResponseCode.isSuccess(response.body())) {
                    mLoginUserContactsList.clear();
                    ArrayList<UserContacts> userContacts = response.body().getContent(new TypeToken<ArrayList<UserContacts>>() {
                    });
                    mLoginUserContactsList.addAll(userContacts);

                    getDepartmentListRequest();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getDepartmentListRequest() {
        GlobalRestful.getInstance().GetDepartmentList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mDepartmentArrayList = response.body().getContent(new TypeToken<ArrayList<Department>>() {
                });

                UserInfo userInfo = MDGroundApplication.sInstance.getLoginUser();

                String userDepartmentName = userInfo.getDepartment();
                String oldDepartment = mUserAttendance.getDepartment();

                if (StringUtils.isEmpty(oldDepartment)) {
                    oldDepartment = userDepartmentName;
                }

                for (int i = 0; i < mDepartmentArrayList.size(); i++) {
                    Department department = mDepartmentArrayList.get(i);

                    if (department.getDepartmentName().equals(oldDepartment)) {
                        mSelectDepartment = i;
                        mDataBinding.tvDepartment.setText(department.getDepartmentName());
                        break;
                    }
                }

                if (StringUtils.isEmpty(mDataBinding.tvDepartment.getText().toString())
                        && mDepartmentArrayList.size() > 0) {
                    mDataBinding.tvDepartment.setText(mDepartmentArrayList.get(0).getDepartmentName());
                }

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

                int projectID = mUserAttendance.getProjectID();

                for (int i = 0; i < mProjectArrayList.size(); i++) {
                    if (mProjectArrayList.get(i).getProjectID() == projectID) {
                        mSelectProjectIndex = i;
                        break;
                    }
                }

                mDataBinding.tvProject.setText(mProjectArrayList.get(mSelectProjectIndex).getProjectName());

                getUserListByDepartmentRequest();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getUserListByDepartmentRequest() {
        final String department = mDataBinding.tvDepartment.getText().toString();

        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUserListByDepartment(department, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();


                ArrayList<UserInfo> tempUserInfoArrayList = response.body().getContent(new TypeToken<ArrayList<UserInfo>>() {
                });

                mUserInfoArrayList.clear();

                UserInfo loginUserInfo = MDGroundApplication.sInstance.getLoginUser();

                if (department.equals(loginUserInfo.getDepartment())) {
                    mUserInfoArrayList.add(loginUserInfo);
                }

//                for (UserInfo userinfo : tempUserInfoArrayList) {
//                    for (UserContacts userContacts : mLoginUserContactsList) {
//                        if (userinfo.getUserID() == userContacts.getContactUserId()) {
//                            mUserInfoArrayList.add(userinfo);
//                            break;
//                        }
//                    }
//                }

                // 改为所有人能看到所有人
                for (UserInfo userinfo : tempUserInfoArrayList) {
                    mUserInfoArrayList.add(userinfo);
                }

                mDataBinding.tvName.setText("");

                // 如果是登录者的部门,则默认选中登录者
                if (department.equals(loginUserInfo.getDepartment())) {
                    // 把登陆者加进去
                    for (int i = 0; i < mUserInfoArrayList.size(); i++) {
                        UserInfo userInfo = mUserInfoArrayList.get(i);

                        if (userInfo.getUserID() == loginUserInfo.getUserID()) {
                            mSelectUserInfoIndex = i;
                            mDataBinding.tvName.setText(userInfo.getUserName());
                            break;
                        }
                    }
                }

                if (StringUtils.isEmpty(mDataBinding.tvName.getText().toString())
                        && mUserInfoArrayList.size() > 0) {
                    mDataBinding.tvName.setText(mUserInfoArrayList.get(0).getUserName());
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
                        ProjectCategory setCategory = mProjectCategoryArrayList.get(0);

                        int categoryId = mUserAttendance.getCategoryID1();

                        for (int i = 0; i < mProjectCategoryArrayList.size(); i++) {
                            if (categoryId == mProjectCategoryArrayList.get(i).getCategoryID()) {
                                mSelectCategoryIndex = i;
                                setCategory = mProjectCategoryArrayList.get(i);
                                break;
                            }
                        }

                        mDataBinding.tvCategory.setText(setCategory.getCategoryName());
                        getProjectCategoryListRequest(setCategory.getCategoryID());
                    }
                } else {
                    mProjectContentArrayList = response.body().getContent(new TypeToken<ArrayList<ProjectCategory>>() {
                    });
                    if (mProjectContentArrayList.size() > 0) {
                        ProjectCategory setContent = mProjectContentArrayList.get(0);

                        int contentId = mUserAttendance.getCategoryID2();

                        for (int i = 0; i < mProjectContentArrayList.size(); i++) {
                            if (contentId == mProjectContentArrayList.get(i).getCategoryID()) {
                                mSelectContentIndex = i;
                                setContent = mProjectContentArrayList.get(i);
                                break;
                            }
                        }

                        mDataBinding.tvContent.setText(setContent.getCategoryName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void saveUserAttendanceRequest(final UserAttendance userAttendance) {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().SaveUserAttendance(userAttendance, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();

                if (ResponseCode.isSuccess(response.body())) {
                    ViewUtils.toast(R.string.submit_success);

                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_USER_ATTENDANCE, userAttendance);
                    setResult(RESULT_OK, intent);
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

}
