package com.song.example.wanandroid.util

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * @package com.song.example.wanandroid.util
 * @fileName BitmapUtil
 * @date on 4/30/2020 8:36 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
object BitmapUtil {

    private val defaultRadiusArray = floatArrayOf(
            DeviceUtil.dp2PxFloat(10f), DeviceUtil.dp2PxFloat(10f), DeviceUtil.dp2PxFloat(10f), DeviceUtil.dp2PxFloat(10f),
            DeviceUtil.dp2PxFloat(10f), DeviceUtil.dp2PxFloat(10f), DeviceUtil.dp2PxFloat(10f), DeviceUtil.dp2PxFloat(10f)
    )

    @JvmStatic
    fun getRoundBitmapByShader(bitmap: Bitmap?,
                               outWidth: Int, outHeight: Int, radius: Int, boarder: Int): Bitmap? {
        return getRoundBitmapByShaderWithRadius(bitmap,
                outWidth, outHeight, radius, boarder, defaultRadiusArray)
    }

    @JvmStatic
    fun getRoundBitmapByShaderWithRadius(
            bitmap: Bitmap?, outWidth: Int, outHeight: Int,
            radius: Int, boarder: Int, radiusArray: FloatArray): Bitmap? {
        if (bitmap == null) {
            return null
        }
        val width = bitmap.width
        val height = bitmap.height
        val widthScale = outWidth * 1f / width
        val heightScale = outHeight * 1f / height

        val matrix = Matrix()
        matrix.setScale(widthScale, heightScale)
        //创建输出的bitmap
        val desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888)
        //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
        val canvas = Canvas(desBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        //创建着色器
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        //给着色器配置matrix
        bitmapShader.setLocalMatrix(matrix)
        paint.shader = bitmapShader
        //创建矩形区域并且预留出border
        val rect = RectF(boarder.toFloat(), boarder.toFloat(), (outWidth - boarder).toFloat(), (outHeight - boarder).toFloat())
        //把传入的bitmap绘制到圆角矩形区域内
        val path = Path()
        path.addRoundRect(rect, radiusArray, Path.Direction.CW)
        canvas.drawPath(path, paint)
        //canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)

        if (boarder > 0) {
            //绘制boarder
            val boarderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            boarderPaint.color = Color.GREEN
            boarderPaint.style = Paint.Style.STROKE
            boarderPaint.strokeWidth = boarder.toFloat()
            canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), boarderPaint)
        }
        return desBitmap
    }

    @JvmStatic
    fun getBitmapFromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
                width,
                height,
                if (drawable.opacity != PixelFormat.OPAQUE) {
                    Bitmap.Config.ARGB_8888
                } else {
                    Bitmap.Config.RGB_565
                }
        )
        drawable.draw(Canvas(bitmap))
        return bitmap
    }
}