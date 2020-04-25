package com.song.example.wanandroid.extension

import android.graphics.Color
import android.view.View
import android.view.Window

/**
 * @author song
 * @time 19-8-22 上午8:49
 * @desc com.song.example.wanandroid.extension.WindowExtension
 *             View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or


View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
View.SYSTEM_UI_FLAG_IMMERSIVE
 */
fun Window.setFullScreen() {
    setBackgroundDrawable(null)
    statusBarColor = Color.TRANSPARENT
    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}