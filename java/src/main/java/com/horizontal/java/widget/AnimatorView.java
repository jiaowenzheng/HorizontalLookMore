package com.horizontal.java.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.horizontal.java.R;

/**
 * @author JiaoWenZheng
 */
public class AnimatorView extends RelativeLayout {

    private int mMove;
    private Path mPath;
    private Paint mBackPaint;
    private TextPaint mTextPaint;

    private int mHeight;
    private int mTextSize;
    private String mText;

    private int mMaxWidth;

    public AnimatorView(Context context) {
        this(context,null);
    }

    public AnimatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPath = new Path();
        mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        mBackPaint.setDither(true);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(Color.BLACK);

        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(0);
        mTextPaint.setAntiAlias(true);

        mText = getResources().getString(R.string.look_more);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getHeight();
    }

    public void setRefresh(int width){
        mMove += width;
        if(mMove < 0){
            mMove = 0;
        }else if(mMove > mMaxWidth){
            mMove = mMaxWidth;
        }

        if(mMove > mMaxWidth / 2){
            mText = getResources().getString(R.string.release_look);
        }else{
            mText = getResources().getString(R.string.look_more);
        }

        requestLayout();
    }

    public void setRelease(){
        mMove = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        // 右上角x坐标、右上角y坐标
        mPath.moveTo(mMove, 0);
        // 左边弧形x坐标、左边弧形y坐标、右下角x坐标、右下角y坐标
        mPath.quadTo(0,  mHeight / 2, mMove, mHeight);
        canvas.drawPath(mPath, mBackPaint);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        // 首先设置paint的文字大小
        canvas.save();
        canvas.restore();
        StaticLayout staticLayout = new StaticLayout(mText, mTextPaint, mTextSize,
                Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        int height  = staticLayout.getHeight();
        int value = (mHeight - height) / 2;

        canvas.translate(mMove / 2 + mTextSize ,value);
        staticLayout.draw(canvas,null,null,0);
        canvas.save();
    }

    public void setMaxWidth(int width){
        this.mMaxWidth = width;
    }
}
