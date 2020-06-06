package com.song.example.study.common.network.retrofit

import retrofit2.Response

/**
 * @author song
 */

interface CoroutineLifecycleCallback<T> {
    fun onSuccess(statusCode: Int, response: Response<T>)
    fun onFail(statusCode: Int, throwable: Throwable)
    fun onFinish()
}
