package com.song.example.wanandroid.common.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import com.song.example.wanandroid.util.BitmapUtil
import com.song.example.wanandroid.util.BitmapUtil.getRoundBitmapByShader
import com.song.example.wanandroid.util.BitmapUtil.getRoundBitmapByShaderWithRadius
import com.song.example.wanandroid.util.DeviceUtil

/**
 * @package com.song.example.wanandroid.common.widget
 * @fileName RoundImageView
 * @date on 4/30/2020 8:35 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */

class RoundImageView: androidx.appcompat.widget.AppCompatImageView {

    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var newBackgroundSet = false
    private var bitmapSrcRect = Rect(0, 0, 0, 0)
    private var bitmapDstRect = Rect(0, 0, 0, 0)

    private var radius: Int = 0
    private val radiusArray = floatArrayOf(
           0f, 0f, 0f, 0f,
           0f, 0f, 0f, 0f
    )

    constructor(context: Context): super(context) {
        initStyle(context, null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) {
        initStyle(context, attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initStyle(context, attributeSet, defStyleAttr)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun initStyle(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        background = null
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
        newBackgroundSet = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = if (w > h) {
            h / 2
        } else {
            w / 2
        }
        updateRadiusArray(radius.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null && drawable != null) {
            val bitmap = getRoundBitmapByShaderWithRadius(
                    BitmapUtil.getBitmapFromDrawable(drawable, width, height), width, height,
                    radius, 0, radiusArray)

            if (bitmap != null) {
                bitmapPaint.reset()
                bitmapSrcRect.right = bitmap.width
                bitmapSrcRect.bottom = bitmap.height

                bitmapDstRect.right = width
                bitmapDstRect.bottom = height

                canvas.drawBitmap(bitmap, bitmapSrcRect, bitmapDstRect, bitmapPaint)
            }
            //super.onDraw(canvas)
        } else {
            super.onDraw(canvas)
        }
        newBackgroundSet = false
    }

    private fun updateRadiusArray(radius: Float) {
        for (i in 0..7) {
            radiusArray[i] = radius
        }
    }
}