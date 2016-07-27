package com.mdground.hdenergy.activity.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityForgetPasswordBinding;
import com.mdground.hdenergy.enumobject.restfuls.ResponseCode;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.DeviceUtils;
import com.mdground.hdenergy.utils.MD5Utils;
import com.mdground.hdenergy.utils.NavUtils;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.socks.library.KLog;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-06-24.
 */
public class ForgetPasswordActivity extends ToolbarActivity<ActivityForgetPasswordBinding> {

    private boolean mIsResetPassword;

    private EventHandler mEventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            ViewUtils.dismiss();
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 提交验证码成功
                    changePswRequest();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功,开始倒计时
                    KLog.e("获取验证码成功,开始倒计时");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                try {
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    JSONObject object = new JSONObject(throwable.getMessage());
                    final String des = object.optString("detail");//错误描述
                    int status = object.optInt("status");//错误代码
                    if (status > 0 && !TextUtils.isEmpty(des)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                ViewUtils.toast(des);
                                ViewUtils.toast(getString(R.string.error_auth_code));
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    //do something
                }
            }
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mIsResetPassword = intent.getBooleanExtra(Constants.KEY_RESET_PASSWORD, false);
            if (mIsResetPassword) {
                setTitle(getString(R.string.change_password));
            }
        }
        SMSSDK.registerEventHandler(mEventHandler);
    }

    @Override
    protected void setListener() {
    }

    //region  ACTION
    public void getCaptchaAction(View view) {
        String phone = mDataBinding.cetAccount.getText().toString();

        if (StringUtils.isEmpty(phone)) {
            ViewUtils.toast(R.string.input_phone);
            return;
        }

        if (phone.length() != 11) {
            ViewUtils.toast(R.string.input_corrent_phone);
            return;
        }

        mDataBinding.tvAcquireCaptcha.setClickable(false);

        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDataBinding.tvAcquireCaptcha.setText(getString(R.string.after_second_acquire_again, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mDataBinding.tvAcquireCaptcha.setClickable(true);
                mDataBinding.tvAcquireCaptcha.setText(R.string.acquire_again);
            }
        };
        countDownTimer.start();

        SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
            /**
             * 此方法在发送验证短信前被调用，传入参数为接收者号码返回true表示此号码无须实际接收短信
             */
            @Override
            public boolean onSendMessage(String country, String phone) {
                // 可添加测试白名单
                /*
                 * if (phone.equals("18576627750")) { return true; }
				 */
                return false;
            }
        });
    }

    public void resetPasswordAction(View view) {
        String phone = mDataBinding.cetAccount.getText().toString();

        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.input_phone, Toast.LENGTH_SHORT).show();
            return;

        }
        if (phone.length() != 11) {
            Toast.makeText(this, R.string.input_corrent_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        String captcha = mDataBinding.cetCaptcha.getText().toString();
        if (StringUtils.isEmpty(captcha)) {
            Toast.makeText(this, R.string.input_captcha, Toast.LENGTH_SHORT).show();
            return;
        }

        String password = mDataBinding.cetPassword.getText().toString();
        if (StringUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.input_password, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6 || password.length() > 16) {
            Toast.makeText(this, R.string.input_corrent_password, Toast.LENGTH_SHORT).show();
            return;
        }

        ViewUtils.loading(this);
        SMSSDK.submitVerificationCode("86", phone, captcha);
    }
    //endregion

    //region SERVER
    private void changePswRequest() {
        String phone = mDataBinding.cetAccount.getText().toString();
        String password = mDataBinding.cetPassword.getText().toString();

        GlobalRestful.getInstance()
                .ChangeUserPassword(phone, MD5Utils.MD5(password), new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.body().getCode() == ResponseCode.Normal.getValue()) {
                            ViewUtils.toast(R.string.change_password_success);

                            if (mIsResetPassword) {
                                DeviceUtils.logoutUser();
                                NavUtils.toLoginActivity(ForgetPasswordActivity.this);
                            } else {
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }
    //endregion
}
