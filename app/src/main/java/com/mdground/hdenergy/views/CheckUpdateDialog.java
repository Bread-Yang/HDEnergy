package com.mdground.hdenergy.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mdground.hdenergy.R;

/**
 * Created by PC on 2016-06-29.
 */

public class CheckUpdateDialog extends Dialog {

    private TextView tvCancel, tvUpdate;
    public TextView tvCurrentVersion, tvNewestProperty;
    private OnClickUpdateListener onClickUpdateListener;
    private String mFirstContent, mSecondContent;

    public interface OnClickUpdateListener {
        void clickCancel();

        void clickUpdate();
    }

    public CheckUpdateDialog(Context context, String firstContent, String secondContent) {
        super(context, R.style.CheckUpdateDialogStyle);
        mFirstContent = firstContent;
        mSecondContent = secondContent;
    }

    public CheckUpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_prompt, null, false);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvUpdate = (TextView) view.findViewById(R.id.tvUpdate);
        tvCurrentVersion = (TextView) view.findViewById(R.id.tvCurrentVersion);
        tvCurrentVersion.setText(mFirstContent);
        tvNewestProperty = (TextView) view.findViewById(R.id.tvNewestProperty);
        tvNewestProperty.setText(mSecondContent);
        setContentView(view);

//        Window window = getWindow();
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        window.setAttributes(wlp);
        setButtonClickListe();
    }

    public void setButtonListen(OnClickUpdateListener onClickUpdateListener) {
        this.onClickUpdateListener = onClickUpdateListener;
    }

    public void setButtonClickListe() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateListener.clickCancel();
            }
        });
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateListener.clickUpdate();
            }
        });
    }
}
