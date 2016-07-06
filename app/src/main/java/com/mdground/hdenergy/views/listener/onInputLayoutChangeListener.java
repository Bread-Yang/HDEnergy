package com.mdground.hdenergy.views.listener;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by yoghourt on 7/6/16.
 */

public abstract class OnInputLayoutChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private View mRootView;
    private int curRectHeight = -1;

    public OnInputLayoutChangeListener(View rootView) {
        this.mRootView = rootView;
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        mRootView.getWindowVisibleDisplayFrame(rect);

        int displayHight = rect.bottom - rect.top;
        int height = mRootView.getHeight();

//        KLog.e("rootView.getHeight() : " + height);
//        KLog.e("bottom : " + rect.bottom);
//        KLog.e("RectHeight:" + displayHight + ",DecorViewHeigt:" + height);
        boolean isHide = (height == rect.bottom);
        if (isHide) {  // 两者相等时,说明软键盘被隐藏了
            onLayoutChange(isHide, displayHight, height);
        }
    }

    public abstract void onLayoutChange(boolean isKeyboardHide, int intputTop, int windowHeight);
}
