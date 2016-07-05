package com.mdground.hdenergy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdground.hdenergy.R;

/**
 * Created by yoghourt on 7/5/16.
 */

public class EdittextWithUnitIcon extends LinearLayout {

    private EditText etInput;

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
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflateLayout = mInflater.inflate(R.layout.view_edittext_with_unit_icon, null);
        this.addView(inflateLayout);

        etInput = (EditText) inflateLayout.findViewById(R.id.etInput);

        TextView tvUnit = (TextView) inflateLayout.findViewById(R.id.tvUnit);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EdittextWithUnitIcon);
        int unitResId = typedArray.getResourceId(R.styleable.EdittextWithUnitIcon_unitResId, 0);
        if (unitResId != 0) {
            tvUnit.setText(unitResId);
        }

        ImageView ivIcon = (ImageView) inflateLayout.findViewById(R.id.ivIcon);
        int iconResId = typedArray.getResourceId(R.styleable.EdittextWithUnitIcon_iconResId, 0);
        if (iconResId != 0) {
            ivIcon.setImageResource(iconResId);
        }
    }

    public String getText() {
        return etInput.getText().toString();
    }

    public void setText(String text) {
        etInput.setText(text);
    }
}
