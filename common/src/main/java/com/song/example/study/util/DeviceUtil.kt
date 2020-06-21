package com.song.example.study.util

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import java.text.SimpleDateFormat
import java.util.*

/**
 * @package com.song.example.study.util
 * @fileName DeviceUtil
 * @date on 4/30/2020 8:36 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
object DeviceUtil {

    @JvmStatic
    fun hideKeyboard(view: View) {
        try {
            val context = view.context
            if (context != null) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {
            WanLog.e("DeviceUtil", "hideKeyboard $e")
        }
    }

    @JvmStatic
    fun dp2PxFloat(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }

    @JvmStatic
    fun dp2PxInt(dp: Float): Int {
        return (dp2PxFloat(dp)).toInt()
    }

    @JvmStatic
    fun dimensionPixelSizeInt(context: Context, @DimenRes resId: Int): Int {
        return try {
            context.resources.getDimensionPixelSize(resId)
        } catch (e: Resources.NotFoundException) {
            0
        }
    }

    @JvmStatic
    fun dimensionPixelSizeFloat(context: Context, @DimenRes resId: Int): Float {
        return dimensionPixelSizeInt(context, resId).toFloat()
    }

    const val DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd"
    const val DATE_PATTERN_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss"
    const val DATE_PATTERN_YYYY_MM_DD_CN = "yyyy年MM月dd日"
    const val DATE_PATTERN_YYYY_MM_DD_HHMMSS_CN = "yyyy年MM月dd日 HH时mm分ss秒"

    @JvmStatic
    fun longMillisSec2DateString(millisSecond: Long): String {
        val date = Date(millisSecond);
        val format = SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD_CN)
        return format.format(date);
    }
}