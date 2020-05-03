package com.song.example.study.extension

import android.content.Context
import android.net.ConnectivityManager
import java.lang.Exception

/**
 * @author: Listensong
 * @time 19-11-21 下午8:22
 * @desc com.song.example.study.extension.ContextExtension
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