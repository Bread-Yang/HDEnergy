package com.mdground.hdenergy.activity.bulletin;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBulletinDetailBinding;
import com.mdground.hdenergy.models.Bulletin;

/**
 * Created by yoghourt on 6/28/16.
 */
public class BulletinDetailActivity extends ToolbarActivity<ActivityBulletinDetailBinding> {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bulletin_detail;
    }

    @Override
    protected void initData() {
        Bulletin bulletin = getIntent().getParcelableExtra(Constants.KEY_BULLETIN);

        mDataBinding.tvContent.setText(bulletin.getRemark());
    }

    @Override
    protected void setListener() {

    }

}
