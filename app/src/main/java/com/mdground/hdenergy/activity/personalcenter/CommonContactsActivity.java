package com.mdground.hdenergy.activity.personalcenter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.databinding.ActivityCommonContactBinding;
import com.mdground.hdenergy.databinding.ItemContactsBinding;
import com.mdground.hdenergy.views.AddProjectDialog;

import java.util.ArrayList;

/**
 * Created by PC on 2016-06-30.
 */

public class CommonContactsActivity extends ToolbarActivity<ActivityCommonContactBinding> implements AddProjectDialog.OnClickUpdateListener{
    private ArrayList<String> mArrayList=new ArrayList<>();
    private ContactsAdataper mAdapter;
    private AddProjectDialog mDialog;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_common_contact;
    }

    @Override
    protected void initData() {
        getContactsList();
        mDialog=new AddProjectDialog(this);
        mAdapter=new ContactsAdataper();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.tvContactsAmount.setText(getString(R.string.left_bracket)+mArrayList.size()+getString(R.string.right_bracket));
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (MDGroundApplication.mInstance.getLoginUser().getUserRole() == 0) {
            mDataBinding.lltAdd.setVisibility(View.GONE);
        }
    }
    @Override
    protected void setListener() {

    }
    public void showAddDialog(View view){
        mDialog.show();
    }
    public void getContactsList(){
//        for(int i=0;i<5;i++){
            mArrayList.add(getString(R.string.yongxing));
            mArrayList.add(getString(R.string.app_name));
//        }
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickCancel() {
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void clickUpdate(String project) {

    }

    //region ADAPTER
    public class ContactsAdataper extends RecyclerView.Adapter<ContactsAdataper.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(CommonContactsActivity.this).inflate(R.layout.item_contacts,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.itemContactsBinding.tvContacts.setText(mArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public ItemContactsBinding itemContactsBinding;
            public MyViewHolder(View itemView) {
                super(itemView);
                itemContactsBinding= DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
