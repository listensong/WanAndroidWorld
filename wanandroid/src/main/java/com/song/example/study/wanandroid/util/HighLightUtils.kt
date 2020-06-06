package com.song.example.study.wanandroid.util

/**
 * @package com.song.example.study.wanandroid.util
 * @fileName HighLightUtils
 * @date on 6/1/2020 10:47 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
object HighLightUtils {
    const val HIGH_LIGHT_TAG_START = "<em class='highlight'>"
    const val HIGH_LIGHT_TAG_END = "</em>"

    const val HIGH_LIGHT_COLOR_TAG_START = "<font color='red'>"
    const val HIGH_LIGHT_COLOR_TAG_END = "</font>"

    @JvmStatic
    fun replaceHighLight(source: String?): String {
        source ?: return ""
        return source.replace(HIGH_LIGHT_TAG_START, HIGH_LIGHT_COLOR_TAG_START)
                .replace(HIGH_LIGHT_TAG_END, HIGH_LIGHT_COLOR_TAG_END)
    }
}