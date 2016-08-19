package com.mdground.hdenergy.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.application.MDGroundApplication;

/**
 * Created by yoghourt on 5/6/16.
 */
public class StringUtils {

    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string) || TextUtils.isEmpty(string) || "[]".equals(string) || "null".equals(string) || "[null]".equals(string)) {
            return true;
        }
        return false;
    }

    public static String toYuanWithInteger(int amount) {
        return String.valueOf(amount);
    }

    public static String toYuanWithoutUnit(float amount) {
        return String.format("%.02f", amount);
    }

    public static String toYuanWithUnit(float amount) {
        return String.format("%.02f", amount) + "元";
    }

//    public static String getCompleteAddress(DeliveryAddress deliveryAddress) {
//        Location provinceLocation = MDGroundApplication.mDaoSession.getLocationDao().load(deliveryAddress.getProvinceID());
//        Location cityLocation = MDGroundApplication.mDaoSession.getLocationDao().load(deliveryAddress.getCityID());
//        Location countyLocation = MDGroundApplication.mDaoSession.getLocationDao().load(deliveryAddress.getDistrictID());
//
//        String province = "";
//        if (provinceLocation != null) {
//            province = provinceLocation.getLocationName();
//        }
//
//        String city = "";
//        if (provinceLocation != null) {
//            city = cityLocation.getLocationName();
//        }
//
//        String county = "";
//        if (countyLocation != null) {
//            county = countyLocation.getLocationName();
//        }
//
//        String completeAddress = province + city + county + deliveryAddress.getStreet();
//
//        return completeAddress;
//    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = MDGroundApplication.sInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(MDGroundApplication.sInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T getInstanceByJsonString(String jsonString, TypeToken<T> type) {
        if (type == null) {
            return null;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        if (jsonString == null) {
            return null;
        } else {
            return gson.fromJson(jsonString, type.getType());
        }
    }

    public static <T> T getInstanceByJsonString(String jsonString, Class<? extends T> clazz) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        if (jsonString == null) {
            return null;
        } else {
            return gson.fromJson(jsonString, clazz);
        }
    }

    public static String getProjectStatus(int projectStatus) {
        String[] projectStatusStrings = MDGroundApplication.sInstance.getResources().getStringArray(R.array.project_status);
        if (projectStatus < projectStatusStrings.length) {
            return projectStatusStrings[projectStatus];
        }
        return "";
    }

    public static int convertStringToInt(String convertString) {
        int returnInt = 0;
        if (!StringUtils.isEmpty(convertString)) {
            returnInt = Integer.parseInt(convertString);
        }
        return returnInt;
    }

    public static float convertStringToFloat(String convertString) {
        String regexStr = "-?\\d+(\\.\\d+)?";
        float returnInt = 0;
        if (!StringUtils.isEmpty(convertString)) {
            if(convertString.matches(regexStr)) {
                returnInt = Float.parseFloat(convertString);
            }
        }
        return returnInt;
    }
}
