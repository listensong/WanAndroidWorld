package com.song.example.wanandroid.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.StringRes
import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.Global

/**
 * @author: Listensong
 * @time 19-11-13 下午1:15
 * @desc com.song.example.wanandroid.util.StringUtil
 */
object StringUtil {

    fun getString(@StringRes strRes: Int): String {
        return Global.globalContext.getString(strRes)
    }

    fun convert2Html(htmlContent: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(htmlContent)
        }
    }
}