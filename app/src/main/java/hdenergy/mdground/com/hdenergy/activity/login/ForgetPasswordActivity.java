package hdenergy.mdground.com.hdenergy.activity.login;

import android.content.Intent;
import android.view.View;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityForgetPasswordBinding;
import hdenergy.mdground.com.hdenergy.utils.StringUtil;
import hdenergy.mdground.com.hdenergy.utils.ViewUtils;

/**
 * Created by PC on 2016-06-24.
 */

public class ForgetPasswordActivity extends ToolbarActivity<ActivityForgetPasswordBinding> {
    private String mAccount;
    private String mPassword;
    private String mVerfify;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
     mDataBinding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             mAccount=mDataBinding.cetAccount.getText().toString();
             mPassword=mDataBinding.cetPassword.getText().toString();
             mVerfify=mDataBinding.cetVerify.getText().toString();
             if(StringUtil.isEmpty(mAccount)){
                 ViewUtils.toast(getString(R.string.input_phone));
                 return;
             }
              if(StringUtil.isEmpty(mPassword)){
                  ViewUtils.toast(getString(R.string.input_password));
                  return;
              }
             if(StringUtil.isEmpty(mVerfify)){
                 ViewUtils.toast(getString(R.string.input_verify));
                 return;
             }
             Intent  intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
             startActivity(intent);
         }
     });


    }
    //region  ACTION
    public void resetPasswordAction(View view){

    }
    //endregion
}
