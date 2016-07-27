package com.mdground.hdenergy.glide;

import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.StreamLocalUriFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.mdground.hdenergy.models.MDImage;

import java.io.File;
import java.io.InputStream;

/**
 * Created by yoghourt on 3/21/16.
 */
public class MDGroundLoader implements ModelLoader<MDImage, InputStream> {

    private Context mContext;

    public MDGroundLoader(Context context) {
        this.mContext = context;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(MDImage model, int width, int height) {
        if (model.getImageLocalPath() != null
                && (model.getImageLocalPath().contains("storage") || model.getImageLocalPath().contains("sdcard"))) {
            return new StreamLocalUriFetcher(mContext, Uri.fromFile(new File(model.getImageLocalPath())));
        }

        return new MDGroundFetcher(model, mContext);
    }

    public static class Factory implements ModelLoaderFactory<MDImage, InputStream> {
        // 缓存
        private final ModelCache<MDImage, MDImage> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<MDImage, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new MDGroundLoader(context);
        }

        @Override
        public void teardown() {

        }
    }
}
