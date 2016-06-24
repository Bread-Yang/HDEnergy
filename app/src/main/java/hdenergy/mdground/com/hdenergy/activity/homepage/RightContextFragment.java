package hdenergy.mdground.com.hdenergy.activity.homepage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.databinding.FragmentRightContextBinding;

/**
 * Created by PC on 2016-06-24.
 */

public class RightContextFragment extends Fragment {
    private FragmentRightContextBinding mFragmentRightContextBinding;
    private View mTitleBar;
    protected Toolbar mToolbar;
    protected TextView tvTitle, tvRight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_right_context,container,false);
        mTitleBar=view.findViewById(R.id.toolBar);

        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);

        // 左边返回键
        mToolbar.setNavigationIcon(R.drawable.btn_return_public);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // 标题
     //   String title = (String) getSupportActionBar().getTitle();
        tvTitle = ((TextView) mToolbar.findViewById(R.id.tvTitle));
        tvTitle.setText(R.string.homePage);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewStub viewStub= (ViewStub) view.findViewById(R.id.viewStubContent);
        viewStub.setLayoutResource(R.layout.fragment_home_content);
        viewStub.inflate();
        return view;
    }

//public ActionBarContextView getSupportActionBar() {
//        return supportActionBar;
//    }
}
