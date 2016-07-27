package com.mdground.hdenergy.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.application.MDGroundApplication;
import com.mdground.hdenergy.models.MDImage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yoghourt on 5/18/16.
 */
public class GlideUtils {

    public static void loadImageByMDImage(ImageView imageView, MDImage mdImage, boolean showPlaceHolder) {

        if (mdImage != null) {
            Glide.with(MDGroundApplication.sInstance)
                    .load(mdImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(showPlaceHolder ? R.drawable.layerlist_image_placeholder : 0)
                    .error(R.drawable.layerlist_image_placeholder)
                    .dontAnimate()
                    .into(imageView);

        }
    }

    public static void loadImageByMDImageWithDialog(final ImageView imageView, MDImage mdImage) {
        ViewUtils.loading(imageView.getContext());
        Glide.with(imageView.getContext())
                .load(mdImage)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        imageView.setImageBitmap(bitmap);
                        ViewUtils.dismiss();
                    }
                });
    }

    public static void loadImageByPhotoSID(ImageView imageView, int photoSID, boolean showPlaceHolder) {
        MDImage mdImage = new MDImage();
        mdImage.setPhotoSID(photoSID);
        loadImageByMDImage(imageView, mdImage, showPlaceHolder);
    }

    public static void loadImageByPhotoSIDWithDialog(ImageView imageView, int photoSID) {
        MDImage mdImage = new MDImage();
        mdImage.setPhotoSID(photoSID);
        loadImageByMDImageWithDialog(imageView, mdImage);
    }

    public static void loadImageAsBitmap(MDImage mdImage, Target target) {
        Glide.with(MDGroundApplication.sInstance)
                .load(mdImage)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(target);
    }

    public static void loadImageAsBitmapRezie(MDImage mdImage, Target target) {
        Glide.with(MDGroundApplication.sInstance)
                .load(mdImage)
                .asBitmap()
                .override(300, 300)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(target);
    }

    public static long getFileSize(final File file) {
        if (file == null || !file.exists())
            return 0;
        if (!file.isDirectory())
            return file.length();
        final List<File> dirs = new LinkedList<File>();
        dirs.add(file);
        long result = 0;
        while (!dirs.isEmpty()) {
            final File dir = dirs.remove(0);
            if (!dir.exists())
                continue;
            final File[] listFiles = dir.listFiles();
            if (listFiles == null || listFiles.length == 0)
                continue;
            for (final File child : listFiles) {
                result += child.length();
                if (child.isDirectory())
                    dirs.add(child);
            }
        }
        return result;
    }
}
