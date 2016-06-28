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

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.attendancereport.AttendanceReportActivity;
import hdenergy.mdground.com.hdenergy.activity.datareport.DataReportActivity;

/**
 * Created by PC on 2016-06-24.
 */

public class RightContextFragment extends Fragment implements View.OnClickListener {
    private View mTitleBar;
    protected Toolbar mToolbar;
    protected TextView tvTitle, tvRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_context, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbars);
        // 左边返回键
        mToolbar.setNavigationIcon(R.drawable.btn_return_public);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // 标题
        tvTitle = ((TextView) mToolbar.findViewById(R.id.tvTitle));
        tvTitle.setText(R.string.home_page);
        view.findViewById(R.id.ivDateReport).setOnClickListener(this);
        view.findViewById(R.id.ivAttendanceReport).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivDateReport: {
                Intent intent = new Intent(getActivity(), DataReportActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.ivAttendanceReport: {
                Intent intent = new Intent(getActivity(), AttendanceReportActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
