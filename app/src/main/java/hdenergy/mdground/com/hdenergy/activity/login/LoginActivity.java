package hdenergy.mdground.com.hdenergy.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.homepage.MainActivity;
import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.enumobject.restfuls.ResponseCode;
import hdenergy.mdground.com.hdenergy.models.UserInfo;
import hdenergy.mdground.com.hdenergy.restfuls.GlobalRestful;
import hdenergy.mdground.com.hdenergy.restfuls.bean.ResponseData;
import hdenergy.mdground.com.hdenergy.utils.DeviceUtil;
import hdenergy.mdground.com.hdenergy.utils.FileUtils;
import hdenergy.mdground.com.hdenergy.utils.MD5Util;
import hdenergy.mdground.com.hdenergy.utils.StringUtil;
import hdenergy.mdground.com.hdenergy.utils.ViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-24.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etAccount;
    private EditText etPassword;
    private CheckBox cbAutoLogin;
    private String mIsFromExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        etAccount = (EditText) findViewById(R.id.cetAccount);
        etPassword = (EditText) findViewById(R.id.cetPassword);
        cbAutoLogin = (CheckBox) findViewById(R.id.cbAutoLogin);
    }

    private void saveUserAndToMainActivity(UserInfo userInfo) {
        FileUtils.setObject(Constants.KEY_SAVE_LOGIN_USER_INFO, userInfo);
        DeviceUtil.setDeviceId(userInfo.getDeviceID());
    }

    //region ACTION
    public void forgetPasswordAction(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    public void loginAction(View view) {
        String phone = etAccount.getText().toString();

        if (StringUtil.isEmpty(phone)) {
            ViewUtils.toast(getString(R.string.please_input_account));
            return;
        }

        String password = etPassword.getText().toString();

        if (StringUtil.isEmpty(password)) {
            ViewUtils.toast(getString(R.string.input_password));
            return;
        }

//        if (password.length() < 6 || password.length() > 16) {
//            Toast.makeText(this, R.string.input_corrent_password, Toast.LENGTH_SHORT).show();
//            return;
//        }
        ViewUtils.loading(this);
        GlobalRestful.getInstance().LoginUser(phone, MD5Util.MD5(password), new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                if (ResponseCode.isSuccess(response.body())) {
                    UserInfo userInfo = response.body().getContent(UserInfo.class);

                    if (cbAutoLogin.isChecked()) {
                        saveUserAndToMainActivity(userInfo);
                    }

                    MDGroundApplication.mInstance.setLoginUserInfo(userInfo);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ViewUtils.toast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion
}