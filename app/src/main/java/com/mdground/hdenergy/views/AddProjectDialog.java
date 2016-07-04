package com.mdground.hdenergy.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mdground.hdenergy.R;

/**
 * Created by PC on 2016-06-29.
 */

public class AddProjectDialog extends Dialog {

    private Context mContext;
    private TextView mTvCancel, tvConfirm;
    private EditText etProjectName;
    private OnClickUpdateListener onClickUpdateListener;

    public interface OnClickUpdateListener {
        void clickCancel();

        void clickUpdate(String project);
    }

    public AddProjectDialog(Context context) {
        super(context, R.style.CheckUpdateDialogStyle);
        mContext = context;

    }

    public AddProjectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_project, null, false);
        mTvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
        etProjectName = (EditText) view.findViewById(R.id.etProjectName);
        setContentView(view);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT); // 填充满屏幕的宽度
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        setButtonClickListe();
    }

    public void setButtonListen(OnClickUpdateListener onClickUpdateListener) {
        this.onClickUpdateListener = onClickUpdateListener;
    }

    public void setButtonClickListe() {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateListener.clickCancel();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String project = etProjectName.getText().toString();
                onClickUpdateListener.clickUpdate(project);
            }
        });
    }
}
