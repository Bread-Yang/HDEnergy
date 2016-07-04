package com.mdground.hdenergy.activity.personalcenter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.databinding.ActivityCommonProjectBinding;
import com.mdground.hdenergy.databinding.ItemContactsBinding;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.AddProjectDialog;

/**
 * Created by PC on 2016-06-30.
 */

public class CommonProjectActivity extends ToolbarActivity<ActivityCommonProjectBinding> implements AddProjectDialog.OnClickUpdateListener {
    private ArrayList<String> mArrayList=new ArrayList<>();
    private CommonProjectAdapter mAdapter;
    private AddProjectDialog mDialog;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_common_project;
    }

    @Override
    protected void initData() {
        mDialog=new AddProjectDialog(this);
        getContactsList();
        mAdapter=new CommonProjectAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.tvContactsAmount.setText(getString(R.string.left_bracket)+mArrayList.size()+getString(R.string.right_bracket));
        mDialog.setButtonListen(this);
    }

    @Override
    protected void setListener() {

    }
    public void getContactsList(){
       for(int i=0;i<1;i++){
        mArrayList.add(getString(R.string.yongxing));
        mArrayList.add(getString(R.string.app_name));
       }
    }
    //region ACTION
    @Override
    public void clickCancel() {
        if(mDialog==null){
            mDialog.dismiss();
        }
    }

    @Override
    public void clickUpdate(String project) {
        if(!"".equals(project)){
        mArrayList.add(project);
        mDialog.dismiss();
        mAdapter.notifyDataSetChanged();
            mDataBinding.tvContactsAmount.setText(getString(R.string.left_bracket)
                    +mArrayList.size()+getString(R.string.right_bracket));
        }else{
            ViewUtils.toast(getString(R.string.project_no_empt));
        }
    }

    public void showAddDialog(View view){
        mDialog.show();
    }
    //endregion

    //region ADAPTER
    public class CommonProjectAdapter extends RecyclerView.Adapter<CommonProjectAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(CommonProjectActivity.this).inflate(R.layout.item_contacts,parent,false);
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
