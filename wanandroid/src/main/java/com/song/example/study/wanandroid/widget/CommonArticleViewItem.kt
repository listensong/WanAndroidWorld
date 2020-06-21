package com.song.example.study.wanandroid.widget

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.song.example.study.extension.setGone
import com.song.example.study.extension.setVisible
import com.song.example.study.util.DeviceUtil
import com.song.example.study.util.StringUtil
import com.song.example.study.wanandroid.R
import com.song.example.study.wanandroid.main.home.article.ArticleVO

/**
 * @package com.song.example.study.wanandroid.widget
 * @fileName CommonArticleViewItem
 * @date on 6/7/2020 5:27 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class CommonArticleViewItem: ConstraintLayout {

    private var titleTextView: TextView? = null
    private var timeTextView: TextView? = null
    private var sourceTextView: TextView? = null
    private var authorTextView: TextView? = null
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
        setWillNotDraw(false)
        layoutTransition = LayoutTransition().apply { enableTransitionType(LayoutTransition.CHANGE_APPEARING) }
        applyItemLayoutFromTemplateXml("")
    }

    private fun applyItemLayoutFromTemplateXml(title: String): View {
        val view = View.inflate(context, R.layout.wan_layout_round_article_item, null)
        if (view is ConstraintLayout) {
            val imageView = view.findViewById<ImageView>(R.id.iv_pinned)
            view.removeView(imageView)
            this.addView(imageView)
            this.pinnedImageView = imageView

            val titleView = view.findViewById<TextView>(R.id.tv_title)
            titleView.text = title
            view.removeView(titleView)
            this.addView(titleView)
            this.titleTextView = titleView

            val timeTextView = view.findViewById<TextView>(R.id.tv_timestamp)
            timeTextView.text = title
            view.removeView(timeTextView)
            this.addView(timeTextView)
            this.timeTextView = timeTextView

            val sourceTextView = view.findViewById<TextView>(R.id.tv_source)
            sourceTextView.text = title
            view.removeView(sourceTextView)
            this.addView(sourceTextView)
            this.sourceTextView = sourceTextView

            val authorTextView = view.findViewById<TextView>(R.id.tv_author)
            authorTextView.text = title
            view.removeView(authorTextView)
            this.addView(authorTextView)
            this.authorTextView = authorTextView
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
    fun bindItemView(data: ArticleVO) {

        if (data.placeTop) {
            this.pinnedImageView?.setVisible()
        } else {
            this.pinnedImageView?.setGone()
        }

        shouldPartialRefresh = false
        this.titleTextView?.text = StringUtil.convert2Html(data.title)

        this.timeTextView?.text = DeviceUtil.longMillisSec2DateString(data.publishTime)
        this.sourceTextView?.text = data.superChapterName
        this.authorTextView?.text = data.author
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        shouldPartialRefresh = false
    }
}