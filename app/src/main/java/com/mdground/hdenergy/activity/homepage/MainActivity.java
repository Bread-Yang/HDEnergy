package com.mdground.hdenergy.activity.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.advice.ReasonableAdviceActivity;
import com.mdground.hdenergy.activity.attendancereport.AttendanceReportActivity;
import com.mdground.hdenergy.activity.bulletin.BulletinListActivity;
import com.mdground.hdenergy.activity.datareport.DataReportActivity;
import com.mdground.hdenergy.activity.projectstartstop.ProjectStartStopActivity;
import com.mdground.hdenergy.models.BannerItem;
import com.mdground.hdenergy.views.SimpleImageBanner;

/**
 * http://blog.csdn.net/lmj623565791/article/details/41531475
 *
 * @author zhy
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private View mTitleBar;
    private Toolbar mToolbar;
    private TextView tvTitle, tvRight;
    private LinearLayout lltDateReport, lltAttendanceReport;
    private ArrayList<BannerItem> mArrayList = new ArrayList<>();
    public static SimpleImageBanner simpleImageBanner;
    private DrawerLayout mDrawerLayout;
    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbars);
        // 左边返回键
        mToolbar.setNavigationIcon(R.drawable.nav_icon_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMenu();
            }
        });

        // 标题
        tvTitle = ((TextView) mToolbar.findViewById(R.id.tvTitle));
        tvTitle.setText(R.string.app_name);
        SimpleImageBanner sib = (SimpleImageBanner) findViewById(R.id.simpleImageBanner);
        getUsertGuides();
        sib.setSource(mArrayList)
                .startScroll();                  //获取图片列表并滚动
        findViewById(R.id.tvRight).setOnClickListener(this);
        simpleImageBanner = (SimpleImageBanner) findViewById(R.id.simpleImageBanner);
        lltDateReport = (LinearLayout) findViewById(R.id.lltDateReport);
        lltDateReport.setOnClickListener(this);
        lltAttendanceReport = (LinearLayout) findViewById(R.id.lltAttendanceReport);
        lltAttendanceReport.setOnClickListener(this);
        findViewById(R.id.lltProjectStartStop).setOnClickListener(this);
        findViewById(R.id.lltReasonableAdvice).setOnClickListener(this);

        initView();
        initEvents();

    }

    public void OpenMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);
    }




    private void initEvents() {
        mDrawerLayout.setDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
        mDrawerLayout.setScrimColor(0x00ffffff);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRight: {
                Intent intent = new Intent(this, BulletinListActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.lltDateReport: {

                Intent intent = new Intent(this, DataReportActivity.class);
                startActivity(intent);


                break;
            }

            case R.id.lltAttendanceReport: {
                Intent intent = new Intent(this, AttendanceReportActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.lltProjectStartStop: {
                Intent intent = new Intent(this, ProjectStartStopActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.lltReasonableAdvice: {
                Intent intent = new Intent(this, ReasonableAdviceActivity.class);
                startActivity(intent);
                break;
            }

        }
    }

    //没有网络广告的时候
    public void getUsertGuides() {
        BannerItem bannerItem = new BannerItem();
        bannerItem.setmLoadImage(R.drawable.home_banner);
        mArrayList.add(bannerItem);
        BannerItem bannerItem1 = new BannerItem();
        bannerItem1.setmLoadImage(R.drawable.home_banners);
        mArrayList.add(bannerItem1);
    }
}
