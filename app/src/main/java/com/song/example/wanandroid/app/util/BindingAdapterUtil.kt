package com.song.example.wanandroid.app.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.song.example.wanandroid.app.main.home.HomeConst
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import com.song.example.wanandroid.app.widget.ArticleViewItem
import com.song.example.wanandroid.util.StringUtil


/**
 * @author: Listensong
 * @time 19-10-25 下午5:44
 * @desc com.song.example.wanandroid.util.BindingAdapterUtil
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
    fun setArticleItemUrl(item: ArticleViewItem, vo: ArticleVO) {
        item.bindItemView(vo.title, vo.placeTop, vo.link)
    }
}