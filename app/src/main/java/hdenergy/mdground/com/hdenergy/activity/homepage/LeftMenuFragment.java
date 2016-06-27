package hdenergy.mdground.com.hdenergy.activity.homepage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.personalcenter.BasicInformationActivity;
import hdenergy.mdground.com.hdenergy.views.CustomImageButton;


/**
 * Created by PC on 2016-06-24.
 */

public class LeftMenuFragment extends Fragment implements View.OnClickListener{
  CustomImageButton mBasicInformation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_left_menu,container,false);
        mBasicInformation= (CustomImageButton) view.findViewById(R.id.cibBasicInformation);
        mBasicInformation.setOnClickListener(this);
        return view;
    }

//region ACTION
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cibBasicInformation:
                Intent intent=new Intent(getActivity(), BasicInformationActivity.class);
                startActivity(intent);
                break;

        }
    }
    //endregion
}
