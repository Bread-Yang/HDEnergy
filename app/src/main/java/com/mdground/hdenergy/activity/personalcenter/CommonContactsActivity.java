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
import com.mdground.hdenergy.databinding.ActivityCommonContactBinding;
import com.mdground.hdenergy.databinding.ItemContactsBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.models.UserContacts;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.ViewUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-30.
 */

public class CommonContactsActivity extends ToolbarActivity<ActivityCommonContactBinding> {

    private ContactsAdataper mAdapter;

    ArrayList<UserInfo> mAllUserContactsList = new ArrayList<>();

    ArrayList<UserContacts> mCommonUserContactsList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_common_contact;
    }

    @Override
    protected void initData() {
        GetAllUserListRequest();
        mAdapter = new ContactsAdataper();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void setListener() {

    }


    //进行一个排序
    public void sortAllProject(List<UserContacts> userContacts) {

        for (int i = 0; i < mAllUserContactsList.size(); i++) {

            for (int j = 0; j < userContacts.size(); j++) {

                if (mAllUserContactsList.get(i).getUserID() == userContacts.get(j).getContactUserId()) {

                    UserInfo temp = mAllUserContactsList.get(i);

                    mAllUserContactsList.remove(i);

                    mAllUserContactsList.add(0, temp);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    //region SERVER

    public void GetUserContactListRequest() {
        GlobalRestful.getInstance().GetUserContactList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (ResponseCode.isSuccess(response.body())) {
                    mCommonUserContactsList.clear();
                    ArrayList<UserContacts> userContacts = response.body().getContent(new TypeToken<ArrayList<UserContacts>>() {
                    });
                    mCommonUserContactsList.addAll(userContacts);
                    KLog.e("mCommonUserContactList"+mCommonUserContactsList.size());

                    sortAllProject(mCommonUserContactsList);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    public void GetAllUserListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetAllUserList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                if (ResponseCode.isSuccess(response.body())) {


                    mAllUserContactsList.clear();
                    ArrayList<UserInfo> userInfos = response.body().getContent(new TypeToken<ArrayList<UserInfo>>() {
                    });
                    mAllUserContactsList.addAll(userInfos);
                    mDataBinding.tvContactsAmount.setText(getString(R.string.left_bracket) +
                            mAllUserContactsList.size() + getString(R.string.right_bracket));
                    GetUserContactListRequest();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    public void SaveUserContactListRequset(List<UserContacts> userContactsList) {

        GlobalRestful.getInstance().SaveUserContactList(userContactsList, new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (ResponseCode.isSuccess(response.body())) {

                    GetAllUserListRequest();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

    //region ADAPTER
    public class ContactsAdataper extends RecyclerView.Adapter<ContactsAdataper.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CommonContactsActivity.this).inflate(R.layout.item_contacts, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.itemContactsBinding.tvContacts.setText(mAllUserContactsList.get(position).getUserPhone());
            holder.itemContactsBinding.cbSelector.setChecked(false);
            for (int j = 0; j < mCommonUserContactsList.size(); j++) {
                if (mCommonUserContactsList.get(j).getContactUserId()==mAllUserContactsList.get(position).getUserID()) {
                    holder.itemContactsBinding.cbSelector.setChecked(true);
                    KLog.e("ddd+"+ mAllUserContactsList.get(position).getUserID());
                }
            }
            holder.itemContactsBinding.cbSelector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserContacts userContarcts = new UserContacts();
                    userContarcts.setUserID(MDGroundApplication.mInstance.getLoginUser().getUserID());
                    userContarcts.setContactUserId(mAllUserContactsList.get(position).getUserID());

                    if (holder.itemContactsBinding.cbSelector.isChecked()) {
                        mCommonUserContactsList.add(userContarcts);
                        SaveUserContactListRequset(mCommonUserContactsList);
                    } else {
                        KLog.e("移除项目 ");
                        for (int i = 0; i < mCommonUserContactsList.size(); i++) {
                            if (mCommonUserContactsList.get(i).getContactUserId() == userContarcts.getContactUserId()) {
                                mCommonUserContactsList.remove(i);
                            }
                        }
                        SaveUserContactListRequset(mCommonUserContactsList);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mAllUserContactsList.size();
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
