package com.mdground.hdenergy.utils;

import android.content.Context;
import android.content.res.Configuration;

import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.enumobject.restfuls.PlatformType;
import com.mdground.hdenergy.restfuls.bean.Device;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class DeviceUtils {

    private static String sFilePath = Tools.getAppPath() + File.separator + "device";

    public static int getDeviceId() {
        if (ToolFileUtils.isExsit(sFilePath)) {
            try {
                String dataStr = ToolFileUtils.readFileByLines(sFilePath);
                return Integer.parseInt(dataStr.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static void setDeviceId(int deviceId) {
        try {
            if (!ToolFileUtils.isExsit(sFilePath)) {
                File file = new File(sFilePath);
                file.createNewFile();
                ToolFileUtils.write(file, String.valueOf(deviceId), System.getProperty("file.encoding"));
            } else {
                ToolFileUtils.write(sFilePath, String.valueOf(deviceId).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断是平板还是手机
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    public static Device getDeviceInfo(Context context) {
        Device device = new Device();
        boolean isPad = DeviceUtils.isPad(context);
        if (isPad) {
            device.setPlatform(PlatformType.ANDROID_PAD.value());
        } else {
            device.setPlatform(PlatformType.ANDROID_PHONE.value());
        }
        // 设置android版本号
        device.setPlatformVersion(android.os.Build.VERSION.RELEASE);
        // 型号
        device.setDeviceModel(android.os.Build.MODEL);

        return device;
    }

    /**
     * 获取平台类型
     *
     * @param context
     * @return
     */
    public static int getPlatformType(Context context) {
        boolean isPad = DeviceUtils.isPad(context);
        if (isPad) {
            return PlatformType.ANDROID_PAD.value();
        } else {
            return PlatformType.ANDROID_PHONE.value();
        }
    }

    public static void logoutUser(Context context) {
        FileUtils.setObject(Constants.KEY_SAVE_LOGIN_USER_INFO, null); // 清空之前的user,保留登录账号
        NavUtils.toLoginActivity(context);
    }
}
