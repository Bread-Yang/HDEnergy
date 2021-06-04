package com.mdground.hdenergy.restfuls;

import android.graphics.Bitmap;
import android.util.Base64;

import com.google.gson.JsonObject;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.enumobject.restfuls.BusinessType;
import com.mdground.hdenergy.models.MDImage;
import com.mdground.hdenergy.restfuls.bean.ResponseData;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by yoghourt on 5/6/16.
 */
public class FileRestful extends BaseRestful {

    public interface OnUploadSuccessListener {
        public void onUploadSuccess(ArrayList<MDImage> mdImageArrayList);
    }

    private static FileRestful mInstance = new FileRestful();

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.FILE;
    }

    @Override
    protected String getHost() {
        return Constants.FILE_HOST;
    }


    private FileRestful() {

    }

    public static FileRestful getInstance() {
        if (mInstance == null) {
            mInstance = new FileRestful();
        }
        return mInstance;
    }

    // 获取图片
    public ResponseData GetPhoto(int PhotoID) {
        JsonObject obj = new JsonObject();
        obj.addProperty("PhotoID", PhotoID);

        return synchronousPost("GetPhoto", obj);
    }

    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String dataStr = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        try {
            dataStr = URLEncoder.encode(dataStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dataStr;
    }

}


