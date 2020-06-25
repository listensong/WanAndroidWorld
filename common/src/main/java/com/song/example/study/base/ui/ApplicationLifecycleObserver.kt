package com.song.example.study.base.ui

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *  * @author song
 * @time 19-8-22 下午3:36
 * @desc com.song.example.study.base.ui.ApplicationLifecycleObserver
 */
class ApplicationLifecycleObserver : LifecycleObserver {
    companion object {
        private const val TAG: String = "AppLifecycleObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e(TAG, "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {//Foreground
        Log.e(TAG, "ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {//Foreground
        Log.e(TAG, "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {//Background
        Log.e(TAG, "ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {//Background
        Log.e(TAG, "ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        Log.e(TAG, "ON_DESTROY")
    }
}