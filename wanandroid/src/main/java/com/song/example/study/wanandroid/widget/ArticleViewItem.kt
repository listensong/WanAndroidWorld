package com.song.example.study.wanandroid.widget

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.song.example.study.wanandroid.R
import com.song.example.study.extension.setGone
import com.song.example.study.extension.setVisible

/**
 * @package com.song.example.study.common.widget
 * @fileName PartialViewItem
 * @date on 4/30/2020 8:34 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class ArticleViewItem: ConstraintLayout {

    private var titleTextView: TextView? = null
    private var pinnedImageView: ImageView? = null

    private var partialWidthMeasureSpec = 0
    private var partialHeightMeasureSpec = 0
    private var shouldPartialRefresh = false

    private var partialLeft = 0
    private var partialTop = 0
    private var partialRight = 0
    private var partialBottom = 0

    constructor(context: Context): super(context) {
        initStyle(context, null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) {
        initStyle(context, attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int):
            super(context, attributeSet, defStyleAttr) {
        initStyle(context, attributeSet, defStyleAttr)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun initStyle(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        background = null
        setWillNotDraw(false)
        layoutTransition = LayoutTransition().apply { enableTransitionType(LayoutTransition.CHANGE_APPEARING) }
        applyItemLayoutFromTemplateXml("", "")
    }

    private fun applyItemLayoutFromTemplateXml(title: String,
                                               url: String?
    ): View {
        val view = View.inflate(context, R.layout.wan_layout_round_article_item, null)
        if (view is ConstraintLayout) {
            val titleView = view.findViewById<TextView>(R.id.tv_title)
            titleView.text = title

            val imageView = view.findViewById<ImageView>(R.id.iv_pinned)

            view.removeView(imageView)
            this.addView(imageView)
            this.pinnedImageView = imageView

            view.removeView(titleView)
            this.addView(titleView)
            this.titleTextView = titleView
        }
        return view
    }

    override fun onLayout(changed: Boolean,
                          left: Int, top: Int, right: Int, bottom: Int) {
        partialLeft = left
        partialTop = top
        partialRight = right
        partialBottom = bottom

        super.onLayout(changed, left, top, right, bottom)
    }

    //适用于某个ViewGroup宽高已定位置已定，且其子view想改变宽高等场景。
    override fun requestLayout() {
        super.requestLayout()
        if (shouldPartialRefresh) {
            partialRequestLayout()
        } else {
            super.requestLayout()
        }
    }

    @SuppressLint("WrongCall")
    private fun partialRequestLayout() {
        onMeasure(partialWidthMeasureSpec, partialHeightMeasureSpec)
        onLayout(true, partialLeft, partialTop, partialRight, partialBottom)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        partialWidthMeasureSpec = widthMeasureSpec
        partialHeightMeasureSpec = heightMeasureSpec
    }

    fun setPinnedVisible(placeTop: Boolean?) {
    }

    @Suppress("UNUSED_PARAMETER")
    fun bindItemView(title: String?,
                     placeTop: Boolean,
                     url: String? = null) {

        if (placeTop) {
            this.pinnedImageView?.setVisible()
        } else {
            this.pinnedImageView?.setGone()
        }

        shouldPartialRefresh = false
        this.titleTextView?.text = title
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        shouldPartialRefresh = false
    }
}