package hdenergy.mdground.com.hdenergy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hdenergy.mdground.com.hdenergy.R;

import static hdenergy.mdground.com.hdenergy.utils.Tools.dip2px;

/**
 * Created by PC on 2016-06-27.
 */

public class CustomImageButton extends RelativeLayout{
    private ImageView mImageView;
    private LayoutParams mImageLayoutParams;
    private LayoutParams mTextLayoutParams;
    private TextView mTextView;
    private int mImageRightMargin=22;
    private int mImageHight=30;
    private int mImageWidth=30;
    private int mTextSize=16;
    private int mTextColor;
    private String mTextString;
    private int mSrc;
    public CustomImageButton(Context context) {
        super(context,null);
    }

    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CustomImageButton);
        mTextString=typedArray.getString(R.styleable.CustomImageButton_titleText);
        mTextColor=typedArray.getColor(R.styleable.CustomImageButton_titleColor,getResources().getColor(R.color.color_white));
        mTextSize=typedArray.getDimensionPixelSize(R.styleable.CustomImageButton_titleSize,mTextSize);
        mImageHight=typedArray.getDimensionPixelSize(R.styleable.CustomImageButton_ImgDrawHeight,(int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mImageHight,
                getResources().getDisplayMetrics()));
        mImageWidth=typedArray.getDimensionPixelSize(R.styleable.CustomImageButton_ImgDrawWidth,(int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mImageHight,
                getResources().getDisplayMetrics()));
        mImageRightMargin=typedArray.getDimensionPixelSize(R.styleable.CustomImageButton_rightMragin,dip2px(context,mImageRightMargin));
        mSrc=typedArray.getResourceId(R.styleable.CustomImageButton_Src,0);

        typedArray.recycle();

        mImageView=new ImageView(context);
        mImageLayoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mImageLayoutParams.setMargins(0,mImageRightMargin,mImageRightMargin,0);
        mImageLayoutParams.addRule(ALIGN_PARENT_LEFT|CENTER_VERTICAL);
        mImageView.setImageResource(mSrc);
        addView(mImageView,mImageLayoutParams);

        mTextView=new TextView(context);
        mTextLayoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            mTextLayoutParams.setMargins(mImageRightMargin,0,0,0);
        mTextLayoutParams.addRule(ALIGN_PARENT_RIGHT|CENTER_VERTICAL);
        mTextView.setText(mTextString);
        mTextView.setTextSize(mTextSize);
        mTextView.setTextColor(mTextColor);
        mTextView.setLines(1);
        addView(mTextView,mTextLayoutParams);


    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


}
