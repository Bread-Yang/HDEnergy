package hdenergy.mdground.com.hdenergy.activity.homepage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.personalcenter.BasicInformationActivity;
import hdenergy.mdground.com.hdenergy.activity.attendancestatics.HistoryAttendanceStaticsActivity;
import hdenergy.mdground.com.hdenergy.activity.personalcenter.HistoryDataStaticsActivity;
import hdenergy.mdground.com.hdenergy.activity.personalcenter.SettingActivity;
import hdenergy.mdground.com.hdenergy.views.CustomImageButton;


/**
 * Created by PC on 2016-06-24.
 */

public class LeftMenuFragment extends Fragment implements View.OnClickListener {

    private CustomImageButton cibBasicInformation, cibHistoryDate, cibHistoryAttendanceStatics, cibSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        cibBasicInformation = (CustomImageButton) view.findViewById(R.id.cibBasicInformation);
        cibHistoryDate = (CustomImageButton) view.findViewById(R.id.cibHistoryDate);
        cibHistoryAttendanceStatics = (CustomImageButton) view.findViewById(R.id.cibHistoryAttendanceStatics);
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
