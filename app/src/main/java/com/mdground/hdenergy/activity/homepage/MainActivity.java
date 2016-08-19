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

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.advice.ReasonableAdviceActivity;
import com.mdground.hdenergy.activity.attendancereport.AttendanceReportActivity;
import com.mdground.hdenergy.activity.bulletin.BulletinListActivity;
import com.mdground.hdenergy.activity.datareport.DataReportActivity;
import com.mdground.hdenergy.activity.projectstartstop.ProjectStartStopActivity;
import com.mdground.hdenergy.models.Bulletin;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.views.SimpleImageBanner;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ArrayList<Bulletin> mBulletinArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbars);
        // 左边按钮
        mToolbar.setNavigationIcon(R.drawable.nav_icon_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
        mDrawerLayout.setScrimColor(0x00ffffff);
    }

    private void initData() {
        getBulletinListRequest();
    }

    private void setListener() {
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

    public void openMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);
    }

    private void getBulletinListRequest() {
        GlobalRestful.getInstance().GetBulletinList(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                mBulletinArrayList = response.body().getContent(new TypeToken<ArrayList<Bulletin>>() {
                });

                ArrayList<Bulletin> temBulletinArrayList = new ArrayList<>();

                for (int i = 0; i < mBulletinArrayList.size(); i++) {
                    Bulletin bulletin = mBulletinArrayList.get(i);
                    if (i < 5) {
                        temBulletinArrayList.add(bulletin);
                    } else {
                        break;
                    }
                }

                SimpleImageBanner sib = (SimpleImageBanner) findViewById(R.id.simpleImageBanner);
                sib.setSource(temBulletinArrayList).startScroll();                  //获取图片列表并滚动
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

}
