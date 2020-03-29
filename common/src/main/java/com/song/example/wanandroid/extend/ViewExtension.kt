package com.song.example.wanandroid.extend

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * @author song
 * Created by song on 2017/11/20.
 */

fun View.setHeight(height: Int) {
    val params = layoutParams
    params.height = height
    layoutParams = params
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

interface OnCircularRevealListener {
    fun onShowEnd()
    fun onHideEnd()
}

fun View.applyCircularReveal(triggerPointX: Int,//揭露效果触发点x
                             triggerPointY: Int,//揭露效果触发点
                             show: Boolean,//显示还是隐藏
                             startRadius: Float,//揭露效果开始时的半径
                             endRadius: Float,//揭露效果结束时的半径
                             listener: OnCircularRevealListener? = null) {
    this.visibility = View.VISIBLE
    ViewAnimationUtils
            .createCircularReveal(this, triggerPointX, triggerPointY, startRadius, endRadius)
            .apply {
                duration = 400
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        if (show) {
                            this@applyCircularReveal.visibility = View.VISIBLE
                            listener?.onShowEnd()
                        } else {
                            this@applyCircularReveal.visibility = View.GONE
                            listener?.onHideEnd()
                        }
                    }
                })
            }.start()
}

fun View.applyCircularReveal(triggerPointX: Int,//揭露效果触发点x
                             triggerPointY: Int,//揭露效果触发点
                             show: Boolean,//显示还是隐藏
                             listener: OnCircularRevealListener? = null) {
    //获取animEffectView的中心点坐标
    val animEffectViewLocation = IntArray(2)
    this.getLocationInWindow(animEffectViewLocation)
    val sourcePointX = animEffectViewLocation[0] + this.width / 2
    val sourcePointY = animEffectViewLocation[1] + this.height / 2

    val rippleW = if (triggerPointX < sourcePointX) this.width - triggerPointX else triggerPointX - animEffectViewLocation[0]
    val rippleH = if (triggerPointY < sourcePointY) this.height - triggerPointY else triggerPointY - animEffectViewLocation[1]

    val maxRadius = Math.sqrt((rippleW * rippleW + rippleH * rippleH).toDouble()).toFloat()
    val startRadius: Float
    val endRadius: Float
    if (show) {
        startRadius = 0f
        endRadius = maxRadius
    } else {
        startRadius = maxRadius
        endRadius = 0f
    }

    applyCircularReveal(triggerPointX, triggerPointY, show, startRadius, endRadius, listener)
}

fun View.applyCircularReveal(triggerView: View,
                             show: Boolean,//显示还是隐藏
                             listener: OnCircularRevealListener? = null) {
    val triggerViewLocation = IntArray(2)
    triggerView.getLocationInWindow(triggerViewLocation)
    val triggerPointX = triggerViewLocation[0] + triggerView.width / 2
    val triggerPointY = triggerViewLocation[1] + triggerView.height / 2

    applyCircularReveal(triggerPointX, triggerPointY, show, listener)
}


fun View.setLayoutHeight(height: Int) {
    val newLayoutParams = this.layoutParams as CoordinatorLayout.LayoutParams
    newLayoutParams.height = height
    this.layoutParams = newLayoutParams
}

fun View.setLinearLayoutHeight(height: Int) {
    val newLayoutParams = this.layoutParams as LinearLayout.LayoutParams
    newLayoutParams.height = height
    this.layoutParams = newLayoutParams
}

fun View.setLinearLayoutTopMargin(topMargin: Int) {
    val newLayoutParams = this.layoutParams as LinearLayout.LayoutParams
    newLayoutParams.topMargin = topMargin
    this.layoutParams = newLayoutParams
}

fun View.applyAppearAnim(offsetY1: Float, offsetY2: Float, down: Boolean = true,
                         dut: Long = 300, listener: ValueAnimator.AnimatorUpdateListener? = null) {
    val animator: ObjectAnimator
    if (down) {
        animator = ObjectAnimator.ofFloat(this, "translationY", -offsetY1, offsetY2)
        animator.interpolator = DecelerateInterpolator()
    } else {
        animator = ObjectAnimator.ofFloat(this, "translationY", offsetY2, -offsetY1)
        animator.interpolator = AccelerateInterpolator()
    }
    if (listener != null) {
        animator.addUpdateListener(listener)
    }
    animator.duration = dut
    animator.start()
}
