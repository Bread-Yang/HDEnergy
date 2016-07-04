package com.mdground.hdenergy.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

import com.mdground.hdenergy.activity.homepage.MainActivity;
import com.mdground.hdenergy.activity.login.LoginActivity;

/**
 * Created by yoghourt on 7/3/16.
 */

public class NavUtils {

    public static void toLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void toHomeActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
