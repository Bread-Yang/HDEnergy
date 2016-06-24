package hdenergy.mdground.com.hdenergy.restfuls;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.enumobject.restfuls.BusinessType;
import hdenergy.mdground.com.hdenergy.models.MDImage;

/**
 * Created by yoghourt on 5/6/16.
 */
public class FileRestful extends BaseRestful {

    public interface OnUploadSuccessListener {
        public void onUploadSuccess(ArrayList<MDImage> mdImageArrayList);
    }

    private static FileRestful mIntance = new FileRestful();

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
        if (mIntance == null) {
            mIntance = new FileRestful();
        }
        return mIntance;
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


