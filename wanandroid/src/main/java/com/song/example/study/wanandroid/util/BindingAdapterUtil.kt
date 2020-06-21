package com.song.example.study.wanandroid.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.widget.ArticleViewItem
import com.song.example.study.util.StringUtil
import com.song.example.study.wanandroid.widget.CommonArticleViewItem


/**
 * @author: Listensong
 * @time 19-10-25 下午5:44
 * @desc com.song.example.study.util.BindingAdapterUtil
 */
object BindingAdapterUtil {

    @BindingAdapter("highLightText")
    @JvmStatic
    fun setHighLightText(textView: TextView, highLightText: String?) {
        highLightText ?: return
        textView.text = StringUtil.convert2Html(highLightText)
    }

    @BindingAdapter("formatText")
    @JvmStatic
    fun setFormatText(textView: TextView, highLightText: String?) {
        highLightText ?: return
        textView.text = StringUtil.convert2Html(highLightText)
    }

    @BindingAdapter("data")
    @JvmStatic
    fun setArticleItemData(item: ArticleViewItem, vo: ArticleVO) {
        item.bindItemView(vo.title, vo.placeTop, vo.link)
    }

    @BindingAdapter("data")
    @JvmStatic
    fun setCommonArticleItemData(item: CommonArticleViewItem, vo: ArticleVO) {
        item.bindItemView(vo)
    }
}