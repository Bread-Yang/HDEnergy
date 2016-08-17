package com.mdground.hdenergy.activity.homepage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.attendancestatics.HistoryAttendanceStaticsActivity;
import com.mdground.hdenergy.activity.datastatics.HistoryDataStaticsActivity;
import com.mdground.hdenergy.activity.personalcenter.BasicInformationActivity;
import com.mdground.hdenergy.activity.personalcenter.SettingActivity;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.models.UserInfo;
import com.mdground.hdenergy.utils.GlideUtils;
import com.mdground.hdenergy.views.CustomImageButton;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by PC on 2016-06-24.
 */

public class LeftMenuFragment extends Fragment implements View.OnClickListener {

    private CustomImageButton cibBasicInformation, cibHistoryDate, cibHistoryAttendanceStatics, cibSetting;
    private TextView tvUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);

        CircleImageView civAvatar = (CircleImageView) view.findViewById(R.id.civAvatar);

        UserInfo userInfo = MDGroundApplication.sInstance.getLoginUser();
        GlideUtils.loadImageByPhotoSID(civAvatar, userInfo.getPhotoSID(), false);

        cibBasicInformation = (CustomImageButton) view.findViewById(R.id.cibBasicInformation);
        cibHistoryDate = (CustomImageButton) view.findViewById(R.id.cibHistoryDate);
        cibHistoryAttendanceStatics = (CustomImageButton) view.findViewById(R.id.cibHistoryAttendanceStatics);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        String UserName = MDGroundApplication.sInstance.getLoginUser().getUserName();
        tvUserName.setText(UserName);
        cibSetting = (CustomImageButton) view.findViewById(R.id.cibSetting);
        cibSetting.setOnClickListener(this);
        cibHistoryDate.setOnClickListener(this);
        cibHistoryAttendanceStatics.setOnClickListener(this);
        cibBasicInformation.setOnClickListener(this);
        return view;
    }

    //region ACTION
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cibBasicInformation: {
                Intent intent = new Intent(getActivity(), BasicInformationActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.cibHistoryDate: {
                Intent intent = new Intent(getActivity(), HistoryDataStaticsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.cibHistoryAttendanceStatics: {
                Intent intent = new Intent(getActivity(), HistoryAttendanceStaticsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.cibSetting: {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
    //endregion
}
