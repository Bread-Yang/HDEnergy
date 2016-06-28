package hdenergy.mdground.com.hdenergy.activity.homepage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.attendancereport.AttendanceReportActivity;
import hdenergy.mdground.com.hdenergy.activity.datareport.DataReportActivity;
import hdenergy.mdground.com.hdenergy.activity.projectstartstop.ProjectStartStopActivity;
import hdenergy.mdground.com.hdenergy.models.BannerItem;
import hdenergy.mdground.com.hdenergy.views.SimpleImageBanner;

/**
 * Created by PC on 2016-06-24.
 */

public class RightContextFragment extends Fragment implements View.OnClickListener {
    private View mTitleBar;
    private Toolbar mToolbar;
    private TextView tvTitle, tvRight;
    private ArrayList<BannerItem> mArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_context, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbars);
        // 左边返回键
        mToolbar.setNavigationIcon(R.drawable.nav_icon_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.mSlidingMenu.toggle();
            }
        });

        // 标题
        tvTitle = ((TextView) mToolbar.findViewById(R.id.tvTitle));
        tvTitle.setText(R.string.app_name);
        SimpleImageBanner sib = (SimpleImageBanner) view.findViewById(R.id.simpleImageBanner);
        geUsertGuides();
        sib.setSource(mArrayList)
                .startScroll();                  //获取图片列表并滚动
        view.findViewById(R.id.lltDateReport).setOnClickListener(this);
        view.findViewById(R.id.lltAttendanceReport).setOnClickListener(this);
        view.findViewById(R.id.lltProjectStartStop).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lltDateReport: {
                Intent intent = new Intent(getActivity(), DataReportActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.lltAttendanceReport: {
                Intent intent = new Intent(getActivity(), AttendanceReportActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.lltProjectStartStop: {
                Intent intent = new Intent(getActivity(), ProjectStartStopActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    //没有网络广播的时候
    public void geUsertGuides() {
        BannerItem bannerItem = new BannerItem();
        bannerItem.setmLoadImage(R.drawable.home_banner);
        mArrayList.add(bannerItem);
        BannerItem bannerItem1 = new BannerItem();
        bannerItem1.setmLoadImage(R.drawable.home_banners);
        mArrayList.add(bannerItem1);
    }

}
