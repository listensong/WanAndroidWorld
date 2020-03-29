package com.song.example.wanandroid.extend

import android.content.Context
import android.net.ConnectivityManager
import java.lang.Exception

/**
 * @author: Listensong
 * Time: 19-11-21 下午8:22
 * Desc: com.song.example.wanandroid.extend.ContextExtent
 */
fun Context?.isNetworkAvailable(): Boolean {
    this ?: return false
    try {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}