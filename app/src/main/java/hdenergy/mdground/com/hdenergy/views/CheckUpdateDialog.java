package hdenergy.mdground.com.hdenergy.views;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.databinding.DialogUpdatePromptBinding;

/**
 * Created by PC on 2016-06-29.
 */

public class CheckUpdateDialog extends Dialog {
    private Context mContext;
    private TextView mTvCancel,mTvUpdate;
    private DialogUpdatePromptBinding mDataBinding;
    private OnClickUpdateListener onClickUpdateListener;
    public interface  OnClickUpdateListener{
        void clickCancel();
        void clickUpdate();
    }
    public CheckUpdateDialog(Context context) {
        super(context, R.style.CheckUpdateDialogStyle);
        mContext=context;

    }

    public CheckUpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_prompt,null,false);
        mTvCancel= (TextView) view.findViewById(R.id.tvCancel);
        mTvUpdate= (TextView) view.findViewById(R.id.tvUpdate);
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_update_prompt, null, false);
        setContentView(view);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT); // 填充满屏幕的宽度
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER; // 使dialog在底部显示
        window.setAttributes(wlp);
        setButtonClickListe();
    }
    public void setButtonListen(OnClickUpdateListener onClickUpdateListener ){
        this.onClickUpdateListener=onClickUpdateListener;
    }
    public void setButtonClickListe(){
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateListener.clickCancel();
            }
        });
        mTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateListener.clickUpdate();
            }
        });
    }
}
