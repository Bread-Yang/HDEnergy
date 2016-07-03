package hdenergy.mdground.com.hdenergy.activity.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.flyco.banner.anim.select.ZoomInEnter;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.adapter.SimpleGuideBanner;
import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.utils.NavUtils;
import hdenergy.mdground.com.hdenergy.utils.PreferenceUtils;

/**
 * Created by yoghourt on 6/20/16.
 */

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        PreferenceUtils.setPrefBoolean(Constants.KEY_IS_FIRST_LAUNCH, false);

        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.guide_1);
        imageList.add(R.drawable.guide_2);
        imageList.add(R.drawable.guide_3);

        SimpleGuideBanner sgb = (SimpleGuideBanner) findViewById(R.id.sgb);

        sgb.setIndicatorWidth(6)
                .setIndicatorHeight(6)
                .setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
//                .setTransformerClass(transformerClass)
                .barPadding(0, 10, 0, 10)
                .setSource(imageList)
                .startScroll();

        sgb.setOnJumpClickListener(new SimpleGuideBanner.OnJumpClickListener() {
            @Override
            public void onJumpClick() {
                NavUtils.toLoginActivity(GuideActivity.this);
            }
        });
    }
}
