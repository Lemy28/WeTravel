package com.app.wetravel

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
//滚动限制
//TODO 在个人信息处的布置。
class CustomScrollView : ScrollView {

    private var backgroundHeight: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setBackgroundHeight(height: Int) {
        backgroundHeight = height
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // 检查内容的高度是否超过背景图的高度
        if (computeVerticalScrollRange() > backgroundHeight) {
            return super.onTouchEvent(ev)
        }
        // 不允许拖动
        return false
    }

    override fun onRequestFocusInDescendants(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        // 禁止子视图获取焦点
        return true
    }
}
