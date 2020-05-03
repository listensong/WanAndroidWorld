package com.song.example.study.util

import android.content.Context
import android.util.TypedValue

/**
 * @author song
 * Created by song on 2018/3/12.
 */

object UiUtil {
    fun dp2px(context: Context, dp: Float) : Float{
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics)
    }
}
