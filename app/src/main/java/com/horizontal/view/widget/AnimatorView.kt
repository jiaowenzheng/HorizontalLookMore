package com.horizontal.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.RelativeLayout
import com.horizontal.view.R


class AnimatorView @JvmOverloads constructor(context: Context?,attrs: AttributeSet? = null,defStyleAttr: Int = 0) : RelativeLayout(context,attrs,defStyleAttr) {

    private var mPath: Path
    private var mBackPaint: Paint
    private var mTextPaint: TextPaint

    private var mMove: Float = 0f
    private var mHeight: Int = 0
    private var mTextSize: Float = 0f
    private var mText: String

    private var mMaxWidth: Int = 0

    init {
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10f,resources.displayMetrics)
        mPath = Path()

        mBackPaint = Paint()
        mBackPaint.isAntiAlias = true
        mBackPaint.isDither = true
        mBackPaint.style = Paint.Style.FILL
        mBackPaint.color = Color.BLACK

        mTextPaint = TextPaint()
        mTextPaint.color = Color.WHITE
        mTextPaint.textSize = mTextSize
        mTextPaint.strokeWidth = 0f
        mTextPaint.isAntiAlias = true

        mText = resources.getString(R.string.look_more)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        mHeight = height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath.reset()
        //右上角X坐标、右上角Y坐标
        mPath.moveTo(mMove,0f)
        //左边弧形x坐标、左边弧形y坐标、右下角x坐标、右下角y坐标
        mPath.quadTo(0f,mHeight / 2.0f,mMove,mHeight.toFloat())
        canvas.drawPath(mPath,mBackPaint)
        //绘制垂直文字
        drawText(canvas)
    }

    /**
     * 首先设置paint的文字大小
     */
    private fun drawText(canvas: Canvas){
        canvas?.run {
            save()
            restore()

            val staticLayout = StaticLayout(mText,mTextPaint,mTextSize.toInt(), Layout.Alignment.ALIGN_CENTER,
                    1f,0f,true)
            val height = staticLayout.height
            val value = (mHeight - height) / 2.0f
            translate(mMove / 2 + mTextSize,value)

            staticLayout.draw(this,null,null,0)
            save()
        }
    }


    fun setRefresh(width: Float){
        mMove += width
        if (mMove < 0){
            mMove = 0f
        }else if (mMove > mMaxWidth){
            mMove = mMaxWidth.toFloat()
        }

        setTextByX()
    }

    private fun setTextByX(){
        mText = if (mMove > mMaxWidth / 2){
            resources.getString(R.string.release_look)
        }else{
            resources.getString(R.string.look_more)
        }

        requestLayout()
    }

    fun setMaxWidth(width: Int){
        this.mMaxWidth = width
    }

    fun setRelease(){
        mMove = 0f
    }

}