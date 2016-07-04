package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.view.View;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.activity.login.ForgetPasswordActivity;
import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.databinding.ActivityBasicInformationBinding;
import hdenergy.mdground.com.hdenergy.models.UserInfo;

public class BasicInformationActivity extends ToolbarActivity<ActivityBasicInformationBinding> {
    private UserInfo mUserInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_basic_information;
    }

    @Override
    protected void initData() {
     mUserInfo= MDGroundApplication.mInstance.getLoginUser();
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
        startActivity(intent);
    }
    //endregion
}
