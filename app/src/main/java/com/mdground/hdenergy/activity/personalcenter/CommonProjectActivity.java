package com.mdground.hdenergy.activity.personalcenter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.databinding.ActivityCommonProjectBinding;
import com.mdground.hdenergy.databinding.ItemContactsBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.Project;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.models.UserProject;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DateUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.AddProjectDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RobinYang on 2016-06-30.
 */

public class CommonProjectActivity extends ToolbarActivity<ActivityCommonProjectBinding> implements AddProjectDialog.OnClickUpdateListener {

    private ArrayList<Project> mAllProjectList = new ArrayList<>();
    private ArrayList<UserProject> mUserProjectList = new ArrayList<>();
    private CommonProjectAdapter mAdapter;
    private AddProjectDialog mDialog;
    private int mUserID;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_common_project;
    }

    @Override
    protected void initData() {
        mUserID = MDGroundApplication.sInstance.getLoginUser().getUserID();
        mDialog = new AddProjectDialog(this);
        mAdapter = new CommonProjectAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);

        mDialog.setButtonListen(this);

        getUserInfoRequest();
    }

    @Override
    protected void setListener() {

    }

    //进行一个排序
    private void sortAllProject(List<UserProject> userProjects) {
        for (int i = 0; i < mAllProjectList.size(); i++) {
            for (int j = 0; j < userProjects.size(); j++) {

                if (mAllProjectList.get(i).getProjectID() == userProjects.get(j).getProjectID()) {

                    Project temp = mAllProjectList.get(i);

                    mAllProjectList.remove(i);

                    mAllProjectList.add(0, temp);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //region SERVER
    private void getUserInfoRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetUserInfo(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                UserInfo userInfo = response.body().getContent(UserInfo.class);
                MDGroundApplication.sInstance.updateLoginUserInfo(userInfo);

                if (userInfo.getAuthorityLevel() == 1) {
                    mDataBinding.lltAdd.setVisibility(View.GONE);
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
                if (ResponseCode.isSuccess(response.body())) {

                    mAllProjectList.clear();
                    ArrayList<Project> tempList = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                    });
                    mAllProjectList.addAll(tempList);
                    mDataBinding.tvContactsAmount.setText(getString(R.string.left_bracket)
                            + mAllProjectList.size() + getString(R.string.right_bracket));
                    getUserProjectListRequest();
                    ViewUtils.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void getUserProjectListRequest() {
        GlobalRestful.getInstance().GetUserProjectList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (ResponseCode.isSuccess(response.body())) {
                    mUserProjectList.clear();
                    ArrayList<Project> Projects = response.body().getContent(new TypeToken<ArrayList<Project>>() {
                    });
                    ArrayList<UserProject> userProjectList = new ArrayList<UserProject>();
                    for (Project project : Projects) {
                        UserProject userProject1 = new UserProject();
                        userProject1.setUserID(mUserID);
                        userProject1.setProjectID(project.getProjectID());
                        userProjectList.add(userProject1);
                    }
                    mUserProjectList.addAll(userProjectList);
                    sortAllProject(mUserProjectList);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });

    }

    private void saveProjectResqust(Project project) {
        GlobalRestful.getInstance().SaveProject(project, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (ResponseCode.isSuccess(response.body())) {
                    mDialog.dismiss();
                    getProjectListRequest();
                } else {
                    ViewUtils.toast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void saveUserProjectListRequest(final List<UserProject> userProjects) {
        GlobalRestful.getInstance().SaveUserProjectList(userProjects, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (ResponseCode.isSuccess(response.body())) {

                    getProjectListRequest();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

    //region ACTION
    @Override
    public void clickCancel() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void clickUpdate(String projectName) {
        if (!"".equals(projectName)) {
            for (int i = 0; i < mAllProjectList.size(); i++) {
                if (mAllProjectList.get(i).getProjectName().equals(projectName)) {
                    ViewUtils.toast("项目已经存在");
                    return;
                }

            }
            Project project = new Project();
            project.setProjectName(projectName);
            Date date = new Date(System.currentTimeMillis());
            String creteDate = DateUtils.getServerDateStringByDate(date);
            project.setCreatedTime(creteDate);
            saveProjectResqust(project);

        } else {
            ViewUtils.toast(getString(R.string.project_no_empt));
        }
    }

    public void showAddDialog(View view) {
        mDialog.show();
    }
    //endregion

    //region ADAPTER
    public class CommonProjectAdapter extends RecyclerView.Adapter<CommonProjectAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CommonProjectActivity.this).inflate(R.layout.item_contacts, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.itemContactsBinding.tvContacts.setText(mAllProjectList.get(position).getProjectName());
            holder.itemContactsBinding.cbSelector.setChecked(false);
            for (int j = 0; j < mUserProjectList.size(); j++) {
                if (mAllProjectList.get(position).getProjectID() == mUserProjectList.get(j).getProjectID()) {
                    holder.itemContactsBinding.cbSelector.setChecked(true);
                }
            }

            holder.itemContactsBinding.cbSelector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserProject userProject = new UserProject();
                    userProject.setUserID(mUserID);
                    userProject.setProjectID(mAllProjectList.get(position).getProjectID());
                    if (holder.itemContactsBinding.cbSelector.isChecked()) {
                        mUserProjectList.add(userProject);
                        saveUserProjectListRequest(mUserProjectList);
                    } else {
                        for (int i = 0; i < mUserProjectList.size(); i++) {
                            if (mUserProjectList.get(i).getProjectID() == userProject.getProjectID()) {
                                mUserProjectList.remove(i);
                            }
                        }

                        saveUserProjectListRequest(mUserProjectList);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mAllProjectList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ItemContactsBinding itemContactsBinding;

            public MyViewHolder(View itemView) {
                super(itemView);
                itemContactsBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
