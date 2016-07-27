package com.mdground.hdenergy.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.mdground.hdenergy.models.MDImage;
import com.mdground.hdenergy.utils.GlideUtils;

/**
 * Created by yoghourt on 5/12/16.
 */
public class DataBindingAdapter {

    @BindingAdapter("bind:loadImageByMDImage")
    public static void

    loadImageByMDImage(ImageView imageView, MDImage mdImage) {
        GlideUtils.loadImageByMDImage(imageView, mdImage, true);
    }

    @BindingAdapter("bind:loadImageByPhotoSID")
    public static void loadImageByPhotoSID(ImageView imageView, int photoSID) {
        GlideUtils.loadImageByPhotoSID(imageView, photoSID, true);
    }
}

