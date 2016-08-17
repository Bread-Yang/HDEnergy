package com.mdground.hdenergy.views;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by yoghourt on 8/16/16.
 */
public class ExtendedEditText extends EditText {

    private TextWatcher mWatcher;

    public ExtendedEditText(Context ctx) {
        super(ctx);
    }

    public ExtendedEditText(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    public ExtendedEditText(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (mWatcher != null) {
            removeTextChangedListener(mWatcher);
            mWatcher = null;
        }
        mWatcher = watcher;

        super.addTextChangedListener(watcher);
    }

    public void setOnlyOneTextWatcher(TextWatcher watcher) {
        if (mWatcher != null) {
            removeTextChangedListener(mWatcher);
            mWatcher = null;
        }
        mWatcher = watcher;

        addTextChangedListener(watcher);
    }
}