package com.mdground.hdenergy.activity.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.views.listener.OnInputLayoutChangeListener;

import static com.mdground.hdenergy.R.id.toolbar;

public abstract class ToolbarActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected LinearLayout lltRoot;
    protected TextView tvTitle, tvRight;
    protected T mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toolbar);

        lltRoot = (LinearLayout) findViewById(R.id.lltRoot);

        mToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(mToolbar);

        // 左边返回键
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 标题
//        String title = (String) getSupportActionBar().getTitle();
//        tvTitle = ((TextView) mToolbar.findViewById(R.id.tvTitle));
//        tvTitle.setText(title);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitleTextColor(Color.WHITE);

        // 右边按钮
        tvRight = (TextView) mToolbar.findViewById(R.id.tvRight);

        // data binding
        ViewStub viewStubContent = (ViewStub) findViewById(R.id.viewStubContent);
        if (getContentLayout() != 0) {
            viewStubContent.setOnInflateListener(new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub stub, View inflated) {
                    mDataBinding = DataBindingUtil.bind(inflated);
                }
            });

            viewStubContent.setLayoutResource(getContentLayout());
            viewStubContent.inflate();
        }
        setOnInputLayoutChangeListener();

        initData();
        setListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lltRoot.requestFocus();  // 隐藏键盘的时候, 去除所有EditText的focus
    }

    private void setOnInputLayoutChangeListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new OnInputLayoutChangeListener(getWindow().getDecorView()) {

            @Override
            public void onLayoutChange(boolean isKeyboardHide, int intputTop, int windowHeight) {
                lltRoot.requestFocus();  // 隐藏键盘的时候, 去除所有EditText的focus
            }
        });
    }

    protected abstract int getContentLayout();

    protected abstract void initData();

    protected abstract void setListener();
}
