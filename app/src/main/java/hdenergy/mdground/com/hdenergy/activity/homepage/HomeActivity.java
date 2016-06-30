package hdenergy.mdground.com.hdenergy.activity.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.views.SlidingMenu;


/**
 * Created by PC on 2016-06-06.
 */
public class HomeActivity extends FragmentActivity {
   public static SlidingMenu mSlidingMenu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mSlidingMenu= (SlidingMenu) findViewById(R.id.slidingMenu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlidingMenu.scrollTo(mSlidingMenu.menuWidth,0);
        mSlidingMenu.isOpen=false;
    }
}
