package com.song.example.study.common.network.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author song
 */
class LifecycleCallImpl<T>(
        private val callImpl: Call<T>,
        private val enableCancel: Boolean) : ILifecycleCall<T> {

    override fun cancel() {
        callImpl.cancel()
    }

    override fun enqueue(callback: ILifecycleCallback<T>) {
        callImpl.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFail(0, t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onSuccess(response.code(), response)
            }
        })
    }

    override fun clone(): ILifecycleCall<T> {
        return LifecycleCallImpl<T>(callImpl.clone(), enableCancel)
    }

    override fun isCanceled(): Boolean {
        return callImpl.isCanceled
    }

    override fun enableCancel(): Boolean {
        return enableCancel
    }

}