package com.mdground.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.view.View;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.activity.login.ForgetPasswordActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBasicInformationBinding;
import com.mdground.hdenergy.models.UserInfo;

public class BasicInformationActivity extends ToolbarActivity<ActivityBasicInformationBinding> {
    private UserInfo mUserInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_basic_information;
    }

    @Override
    protected void initData() {
     mUserInfo= MDGroundApplication.sInstance.getLoginUser();
        String userName=mUserInfo.getUserName();
        String userPhone=mUserInfo.getUserPhone();
        String userDepartment=mUserInfo.getDepartment();
        mDataBinding.tvUserName.setText(userName);
        mDataBinding.tvUserPhone.setText(userPhone);
        mDataBinding.tvUserDepartment.setText(userDepartment);
    }

    @Override
    protected void setListener() {

    }
    //region ACTION
    public void toCommonContactsActivity(View view){
        Intent intent=new Intent(BasicInformationActivity.this,CommonContactsActivity.class);
        startActivity(intent);
    }

    public void toCommonProjectActivity(View view){
        Intent intent=new Intent(BasicInformationActivity.this,CommonProjectActivity.class);
        startActivity(intent);
    }
    public void toRestPassword(View view){
        Intent intent=new Intent(BasicInformationActivity.this, ForgetPasswordActivity.class);
        intent.putExtra(Constants.KEY_RESET_PASSWORD,Constants.KEY_RESET_PASSWORD);
        startActivity(intent);
    }
    //endregion
}
