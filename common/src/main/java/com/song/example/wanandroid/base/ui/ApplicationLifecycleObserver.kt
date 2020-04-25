package com.song.example.wanandroid.base.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.song.example.wanandroid.util.WanLog

/**
 *  * @author song
 * @time 19-8-22 下午3:36
 * @desc com.song.example.wanandroid.base.ui.ApplicationLifecycleObserver
 */
class ApplicationLifecycleObserver : LifecycleObserver {
    companion object {
        private const val TAG: String = "AppLifecycleObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        WanLog.e(TAG, "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {//Foreground
        WanLog.e(TAG, "ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {//Foreground
        WanLog.e(TAG, "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {//Background
        WanLog.e(TAG, "ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {//Background
        WanLog.e(TAG, "ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        WanLog.e(TAG, "ON_DESTROY")
    }
}