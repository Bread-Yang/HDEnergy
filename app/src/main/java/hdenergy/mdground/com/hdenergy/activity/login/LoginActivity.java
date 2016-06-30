package hdenergy.mdground.com.hdenergy.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.homepage.HomeActivity;
import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.models.User;
import hdenergy.mdground.com.hdenergy.utils.DeviceUtil;
import hdenergy.mdground.com.hdenergy.utils.FileUtils;
import hdenergy.mdground.com.hdenergy.utils.StringUtil;
import hdenergy.mdground.com.hdenergy.utils.ViewUtils;

/**
 * Created by PC on 2016-06-24.
 */

public class LoginActivity extends AppCompatActivity{
    private EditText mEtAccount;
    private EditText mEtPassword;
    private CheckBox cbAutoLogin;
    private String isFromExit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent  intent=getIntent();
        isFromExit=intent.getStringExtra(Constants.KEY_IS_EXIT_LOGIN);
        if(intent!=null){
            if(isFromExit==null){
                if (MDGroundApplication.mInstance.getLoginUser() != null) {
                    Intent intent1=new Intent(this, HomeActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        }
        init();
    }


    private void init() {
        mEtAccount= (EditText) findViewById(R.id.cetAccount);
        mEtPassword= (EditText) findViewById(R.id.cetPassword);
        cbAutoLogin= (CheckBox) findViewById(R.id.cbAutoLogin);
    }

    //region ACTION
    public void  forgetPasswordAction(View view){
        Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }
    public void loginAction(View view){
        String phone =mEtAccount.getText().toString();

        if (StringUtil.isEmpty(phone)) {
         //   Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            ViewUtils.toast(getString(R.string.please_input_account));
            return;

        }

        String password = mEtPassword.getText().toString();

        if (StringUtil.isEmpty(password)) {
             ViewUtils.toast(getString(R.string.input_password));
            return;
        }

//        if (password.length() < 6 || password.length() > 16) {
//            Toast.makeText(this, R.string.input_corrent_password, Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(cbAutoLogin.isChecked()){
            User user=new User();
            user.setUserName(phone);
            user.setPassword(password);
            saveUserAndToMainActivity(user);
            Intent intent=new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            MDGroundApplication.mInstance.setLoginUser(null);
            Intent intent=new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void toHomeActivity(){

    }
    //endgion
    private void saveUserAndToMainActivity(User user) {
        MDGroundApplication.mInstance.setLoginUser(user);
//        if (cbAutoLogin.isChecked()) {
            FileUtils.setObject(Constants.KEY_ALREADY_LOGIN_USER, user);
            DeviceUtil.setDeviceId(user.getDeviceID());
        }
   // }

//    if (MDGroundApplication.mInstance.getLoginUser() != null) {
//
////                    loginRequest(MDGroundApplication.mLoginUser);
//        NavUtils.toMainActivity(StartingActivity.this);
//        finish();

}