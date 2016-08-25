package com.mdground.hdenergy.views;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by yoghourt on 8/19/16.
 */
public class PlusAndMinusSignInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String regexStr = "-?\\d+(\\.\\d+)?";

        String inputString = source.toString();

        if (!inputString.matches(regexStr)) {
            return "";
        }
        return null;
    }
}
