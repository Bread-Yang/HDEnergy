package hdenergy.mdground.com.hdenergy.views;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.adapter.wheelview.BaoWheelAdapter;
import hdenergy.mdground.com.hdenergy.databinding.DialogBaoPickerBinding;

/**
 * Created by yoghourt on 5/25/16.
 */

public class BaoPickerDialog extends Dialog {

    private Context mContext;

    private DialogBaoPickerBinding mDataBinding;

    private List<String> mStringList;

    public BaoPickerDialog(Context context) {
        super(context, R.style.customDialogStyle);
        mContext = context;
    }

    public BaoPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected BaoPickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_bao_picker, null, false);

        setContentView(mDataBinding.getRoot());

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT); // 填充满屏幕的宽度
        window.setWindowAnimations(R.style.action_sheet_animation); // 添加动画
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM; // 使dialog在底部显示
        window.setAttributes(wlp);

        mDataBinding.wheelView.setVisibleItems(7);
        if (mStringList != null) {
            mDataBinding.wheelView.setViewAdapter(new BaoWheelAdapter(mContext, mStringList));
        }
    }

    public void bindWheelViewData(List<String> items) {
        mStringList = items;
        if (mDataBinding != null) {
            mDataBinding.wheelView.setViewAdapter(new BaoWheelAdapter(mContext, mStringList));
        }
    }
}
