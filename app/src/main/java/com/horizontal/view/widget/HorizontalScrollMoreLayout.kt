package com.horizontal.view.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.recyclerview.widget.RecyclerView
import com.horizontal.view.R

class HorizontalScrollMoreLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr),
        NestedScrollingParent {

    companion object{
        const val DRAG: Int = 2
    }

    private val mParentHelper: NestedScrollingParentHelper
    private var mHeaderView: View
    private var mFooterView: AnimatorView
    private var mChildView: RecyclerView? = null

    //解决多点触控问题
    private var isRunAnim: Boolean = false
    private var mMaxWidth: Int = 0

    private var onStartListener: OnStartActivityListener? = null;

    interface OnStartActivityListener{
        fun onStartActivity()
    }

    fun setOnStartActivity(listener: OnStartActivityListener){
        this.onStartListener = listener
    }

    init {
        mMaxWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,120f,resources.displayMetrics).toInt()
        mParentHelper = NestedScrollingParentHelper(this)

        mHeaderView = View(context)
        mHeaderView.setBackgroundColor(0x00FFFFFF)

        mFooterView = AnimatorView(context)
        mFooterView.setBackgroundColor(resources.getColor(R.color.color_efefef))
        mFooterView.setMaxWidth(mMaxWidth)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        orientation = HORIZONTAL

        if (childCount == 0) return

        val view = getChildAt(0)
        if (view is RecyclerView){
            mChildView = view
            val layoutParams = LayoutParams(mMaxWidth,LayoutParams.MATCH_PARENT)

            addView(mHeaderView,0,layoutParams)
            addView(mFooterView,childCount,layoutParams)
            //左移
            scrollBy(mMaxWidth,0)

            mChildView?.setOnTouchListener { v, event ->
                //保证动画状态，子view 不能滑动
                isRunAnim
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mChildView?.let {
            val params = it.layoutParams
            params.width = measuredWidth
            it.layoutParams = params
        }
    }

    /**
     * 必须要复写 onStartNestedScroll后调用
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        mParentHelper.onNestedScrollAccepted(child,target,axes)
    }

    /**
     * 返回true代表处理本次事件
     * 在执行动画时间里不能处理本次事件
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
       //是否显示 footer view
        return target is RecyclerView && !isRunAnim
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        parent.requestDisallowInterceptTouchEvent(true)
        // dx>0 往左滑动 dx<0往右滑动
        val hiddenLeft = dx > 0 && scrollX < mMaxWidth && !target.canScrollHorizontally(-1)
        val showLeft = dx < 0 && !target.canScrollVertically(-1)
        val hiddenRight = dx < 0 && scrollX > mMaxWidth && !target.canScrollHorizontally(1)
        val showRight = dx > 0 && !target.canScrollHorizontally(1)

        if (hiddenLeft || showLeft || hiddenRight || showRight){
            scrollBy(dx / DRAG,0)
            consumed[0] = dx
        }

        if (hiddenRight || showRight){
            mFooterView.setRefresh((dx / DRAG).toFloat())
        }

        //限制错位问题
        if (dx > 0 && scrollX > mMaxWidth && target.canScrollHorizontally(-1).not()){
            scrollTo(mMaxWidth,0)
        }

        if (dx < 0 && scrollX < mMaxWidth && target.canScrollHorizontally(1).not()){
            scrollTo(mMaxWidth,0)
        }

    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {

    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return scrollX != mMaxWidth
    }

    override fun getNestedScrollAxes(): Int {
        return 0
    }

    override fun scrollTo(x: Int, y: Int) {
        var temX = x
        if (x < 0){
            temX = 0
        } else if (x > mMaxWidth * 2){
            temX  = mMaxWidth * 2
        }
        super.scrollTo(temX, y)
    }

    /**
     * 复位初始位置
     * scrollTo 移动到指定坐标
     * scrollBy 在原有坐标上面移动
     */
    override fun onStopNestedScroll(target: View) {
        mParentHelper.onStopNestedScroll(target)
        //如果不在RecyclerView 滑动范围内
        if (mMaxWidth != scrollX){
            startAnimation(ProgressAnimation())
        }

        if (scrollX > mMaxWidth + mMaxWidth / 2) {
            onStartListener?.onStartActivity()
        }
    }

    inner class ProgressAnimation : Animation() {
        init {
            isRunAnim = true
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            scrollBy(((mMaxWidth - scrollX) * interpolatedTime).toInt(),0)
            if (interpolatedTime.toInt() == 1){
                isRunAnim = false
                mFooterView.setRelease()
            }
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            duration = 300
            interpolator = AccelerateInterpolator()
        }
    }

}