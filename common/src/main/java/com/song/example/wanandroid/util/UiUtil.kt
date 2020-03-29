package com.song.example.wanandroid.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import kotlin.math.sqrt

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
