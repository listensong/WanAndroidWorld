package com.song.example.wanandroid.common.network.retrofit

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import retrofit2.Response

/**
 * @author song
 */

interface ILifecycleStateObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}

interface ILifecycleCallback<T> {
    fun onSuccess(statusCode: Int, response: Response<T>)
    fun onFail(statusCode: Int, throwable: Throwable)
    fun onFinish()
}

interface ILifecycleCall<T> : ILifecycleStateObserver {

    fun cancel()
    fun enqueue(callback: ILifecycleCallback<T>)
    fun clone(): ILifecycleCall<T>
    fun isCanceled(): Boolean
    fun enableCancel(): Boolean

    override fun onDestroy() {
        if (enableCancel() && !isCanceled()) {
            cancel()
        }
    }
}