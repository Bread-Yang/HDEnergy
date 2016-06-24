package hdenergy.mdground.com.hdenergy.activity.homepage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hdenergy.mdground.com.hdenergy.R;


/**
 * Created by PC on 2016-06-24.
 */

public class LeftMenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_left_menu,container,false);
    }
}
