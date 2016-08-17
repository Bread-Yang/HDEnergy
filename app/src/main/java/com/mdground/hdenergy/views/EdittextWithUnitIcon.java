package com.mdground.hdenergy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.databinding.ViewEdittextWithUnitBinding;

/**
 * Created by yoghourt on 7/5/16.
 */

public class EdittextWithUnitIcon extends LinearLayout {

    private ViewEdittextWithUnitBinding mDataBinding;

    public EdittextWithUnitIcon(Context context) {
        super(context);
    }

    public EdittextWithUnitIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    public EdittextWithUnitIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.view_edittext_with_unit, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EdittextWithUnitIcon);
        int unitResId = typedArray.getResourceId(R.styleable.EdittextWithUnitIcon_unitResId, 0);
        if (unitResId != 0) {
            mDataBinding.tvUnit.setText(unitResId);
        }

        int iconResId = typedArray.getResourceId(R.styleable.EdittextWithUnitIcon_iconResId, 0);
        if (iconResId != 0) {
            mDataBinding.ivIcon.setImageResource(iconResId);
        }
    }

    public String getTextString() {
        return mDataBinding.etInput.getText().toString();
    }

    public void setText(String text) {
        mDataBinding.etInput.setText(text);
    }

    public ExtendedEditText getEtInput() {
        return mDataBinding.etInput;
    }

    public ImageView getIvIcon() {
        return mDataBinding.ivIcon;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        getEtInput().setOnFocusChangeListener(l);
    }
}
