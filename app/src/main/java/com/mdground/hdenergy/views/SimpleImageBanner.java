package com.mdground.hdenergy.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.bulletin.BulletinDetailActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.models.Bulletin;
import com.mdground.hdenergy.models.MDImage;


public class SimpleImageBanner extends BaseIndicatorBanner<Bulletin, SimpleImageBanner> {

    private ColorDrawable colorDrawable;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final Bulletin item = mDatas.get(position);
//        tv.setText(item.title);
    }

    @Override
    public View onCreateItemView(int position) {
        View view = View.inflate(mContext, R.layout.item_banner, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);

        final Bulletin bulletin = mDatas.get(position);
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth * 360 * 1.0f / 640);
//        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

        MDImage mdImage = new MDImage();

        mdImage.setPhotoID(bulletin.getPhotoID());
        mdImage.setPhotoSID(bulletin.getPhotoSID());

        if (bulletin != null) {
            Glide.with(mContext)
                    .load(mdImage)
//                    .override(itemWidth, itemHeight)
                    .centerCrop()
                    .placeholder(colorDrawable)
                    .into(iv);
        } else {
           iv.setImageDrawable(colorDrawable);
        }

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BulletinDetailActivity.class);
                intent.putExtra(Constants.KEY_BULLETIN, bulletin);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
