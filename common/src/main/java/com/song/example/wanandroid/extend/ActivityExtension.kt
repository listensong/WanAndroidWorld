package com.song.example.wanandroid.extend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat

/**
 * @author song
 * Time: 19-8-22 下午12:18
 * Desc: com.song.example.wanandroid.extend.ActivityExtend
 */

fun Activity.getStatusBarHeight(): Int {
    val statusBarHeight: Int
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    statusBarHeight = if (resId > 0) {
        resources.getDimensionPixelSize(resId)
    } else {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        rect.top
    }
    return statusBarHeight
}

fun Activity.setStatusBarColor(colorResId: Int) {
    window?.run {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = ContextCompat.getColor(context, colorResId)
    }
}

fun Activity?.isActivityAvailable(): Boolean {
    this ?: return false
    if (isFinishing || isDestroyed) {
        return false
    }
    return true
}

fun Activity.setStatusBarVisibility(visible: Boolean) {
    window?.let {
        if (visible) {
            it.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            it.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}

fun Activity?.startActivitySafely(intent: Intent?, explain: String? = null) {
    this ?: return
    intent ?: return
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        if (explain != null && explain.isNotEmpty()) {
            Toast.makeText(this, explain, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context?.startActivitySafely(intent: Intent?, explain: String? = null) {
    this ?: return
    intent ?: return
    if (intent.resolveActivity(packageManager) != null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } else {
        if (explain != null && explain.isNotEmpty()) {
            Toast.makeText(this, explain, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Activity?.startActivitySafely(clz: Class<*>) {
    startActivitySafely(Intent(this, clz))
}

fun Context?.startActivitySafely(clz: Class<*>) {
    startActivitySafely(Intent(this, clz))
}

fun Activity?.callActivitySafelyDelayed(clz: Class<*>, handler: Handler, delay: Long = 200) {
    handler.postDelayed({
        startActivitySafely(Intent(this, clz))
    }, delay)
}

fun Activity?.callActivitySafelyDelayed(clzName: String, handler: Handler, delay: Long = 200) {
    this ?: return
    handler.postDelayed({
        startActivitySafely(Intent().setClassName(this.applicationContext, clzName))
    }, delay)
}

fun Activity?.callActivitySafelyDelayed(targetIntent: Intent?, handler: Handler, delay: Long = 200) {
    this ?: return
    targetIntent ?: return
    handler.postDelayed({
        startActivitySafely(targetIntent)
    }, delay)
}

fun Activity.setFullScreen() {
    window?.setFullScreen()
}

fun Activity.getWindowMetrics(): DisplayMetrics {
    val wm: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm
}


fun Context.getAndroidAttrPX(@AttrRes attrResId: Int): Int {
    val value = TypedValue()
    var actionBarHeight = 0
    var actionBarHeight2 = 0
    if (theme.resolveAttribute(attrResId, value, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(value.data, resources.displayMetrics)
        actionBarHeight2 = TypedValue.complexToDimensionPixelOffset(value.data, resources.displayMetrics)
    }
    Log.v("getAndroidAttrPX", "value " + value.toString() + " " + value.data + " " + actionBarHeight + " " + actionBarHeight2)
    return actionBarHeight
}


