package hdenergy.mdground.com.hdenergy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.socks.library.KLog;

import hdenergy.mdground.com.hdenergy.R;

/**
 * Created by PC on 2016-06-06.
 */
public class SlidingMenu extends HorizontalScrollView {
    public static final String TAG="SLIDINMENU";
    private int mScreenWidth;
    private int mScreenHight;
    private float right_margin;//
    LinearLayout wrapper;
    ViewGroup menu;
    ViewGroup home;
    private int menuWidth;
    private boolean once;
    private boolean isOpen;
    public SlidingMenu(Context context) {
        super(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager manager= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth=manager.getDefaultDisplay().getWidth();
        mScreenHight=manager.getDefaultDisplay().getHeight();
        KLog.e("mScreenWidth---->"+mScreenWidth);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        right_margin=typedArray.getDimensionPixelSize(R.styleable.SlidingMenu_rightMragin, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50f,
                getResources().getDisplayMetrics()));
        KLog.e("right_margin="+right_margin);
        typedArray.recycle();

    }

    //设置子View的大小和自己的 大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!once){
            wrapper= (LinearLayout) getChildAt(0);
            menu= (ViewGroup) wrapper.getChildAt(0);
            home= (ViewGroup) wrapper.getChildAt(1);
            KLog.e("right_margin="+right_margin);
            menuWidth= (int) (mScreenWidth-right_margin);
            KLog.e("屏幕宽度"+String.valueOf(mScreenWidth));
            KLog.e("左侧宽度"+String.valueOf(menuWidth));
            menu.getLayoutParams().width=menuWidth;
            home.getLayoutParams().width=mScreenWidth;
            home.getLayoutParams().height=mScreenHight;
        }
    }
    //定义它的布局，一开始Menu是隐藏的
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//          if(changed){
              KLog.e("有点击进来");
              this.scrollTo(menuWidth,0);
              once=true;
      //    }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int ScollX=getScrollX();
                //隐藏部分大于菜单的1/2时要回收回去
                if(ScollX>=menuWidth/2){
                   smoothScrollTo(menuWidth,0);
                    isOpen=false;
                }else{
                    smoothScrollTo(0,0);
                    isOpen=true;
                }
                return  true;

        }
        return super.onTouchEvent(ev);
    }
    //关闭menu
      public void close(){
          if(isOpen){
              smoothScrollTo(menuWidth,0);
              isOpen=false;
          }
      }
      //打开一个menu
     public void open(){
         if(!isOpen){
             smoothScrollTo(0,0);
             isOpen=true;
         }


     }
    //一个开关
    public void toggle(){
        if (isOpen) {
            close();
        }else{
            open();
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale=l*1.0f/menuWidth;  //1-0梯度变化
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.7f + scale * 0.3f;

        ViewHelper.setScaleX(menu, leftScale);
        ViewHelper.setScaleY(menu, leftScale);
        ViewHelper.setAlpha(menu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(menu, menuWidth * scale * 0.6f);

        ViewHelper.setPivotX(home, 0);
        ViewHelper.setPivotY(home, home.getHeight() / 2);
        ViewHelper.setScaleX(home, rightScale);
        ViewHelper.setScaleY(home, rightScale);



    }

    //把dp转换为px
    public int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

}
