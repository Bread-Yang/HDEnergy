package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.view.View;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityBasicInformationBinding;

public class BasicInformationActivity extends ToolbarActivity<ActivityBasicInformationBinding> {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_basic_information;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
    //region ACTION
    public void toCommonContactsActivity(View view){
        Intent intent=new Intent(BasicInformationActivity.this,CommonContactsActivity.class);
        startActivity(intent);
    }

    public void toCommonProjectActivity(View view){
        Intent intent=new Intent(BasicInformationActivity.this,CommonProjectActivity.class);
        startActivity(intent);
    }
    //endregion
}
