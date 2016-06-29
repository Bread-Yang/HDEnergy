package hdenergy.mdground.com.hdenergy.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.homepage.HomeActivity;

/**
 * Created by PC on 2016-06-24.
 */

public class LoginActivity extends AppCompatActivity{
    EditText mEtAccount;
    EditText mEtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
        mEtAccount= (EditText) findViewById(R.id.cetAccount);
        mEtPassword= (EditText) findViewById(R.id.cetPassword);
    }

    //region ACTION
    public void  forgetPasswordAction(View view){
        Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }

    public void loginAction(View view){
        String phone =mEtAccount.getText().toString();

//        if (StringUtil.isEmpty(phone)) {
//            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if (phone.length() != 11) {
//         //   Toast.makeText(this, R.string.input_corrent_phone, Toast.LENGTH_SHORT).show();
//            return;
//        }

        String password = mEtPassword.getText().toString();

//        if (StringUtil.isEmpty(password)) {
//            Toast.makeText(this, R.string.input_password, Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (password.length() < 6 || password.length() > 16) {
//            Toast.makeText(this, R.string.input_corrent_password, Toast.LENGTH_SHORT).show();
//            return;
//        }
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}