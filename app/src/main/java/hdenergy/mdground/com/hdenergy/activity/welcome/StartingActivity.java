package hdenergy.mdground.com.hdenergy.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.login.LoginActivity;
import hdenergy.mdground.com.hdenergy.application.MDGroundApplication;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.models.UserInfo;
import hdenergy.mdground.com.hdenergy.utils.NavUtils;
import hdenergy.mdground.com.hdenergy.utils.PreferenceUtils;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_starting, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        aa.setFillAfter(true);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                boolean isFirstLaunch = PreferenceUtils.getPrefBoolean(Constants.KEY_IS_FIRST_LAUNCH, true);

                // 跳到引导页
                if (isFirstLaunch) {
                    toGuideActivity();
                } else {
                    UserInfo saveUserInfo = MDGroundApplication.mInstance.getSaveUser();
                    if (saveUserInfo != null) {
                        MDGroundApplication.mInstance.setLoginUserInfo(saveUserInfo);
                        NavUtils.toHomeActivity(StartingActivity.this);
                        finish();
                    } else {
                        toLoginActivity();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void toGuideActivity() {
        Intent intent = new Intent(StartingActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    private void toLoginActivity() {
        Intent intent = new Intent(StartingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
