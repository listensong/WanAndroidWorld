package com.song.example.wanandroid.common.network.retrofit

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeoutException
import kotlin.coroutines.resume

suspend fun <T : Any> ILifecycleCall<T>.awaitResult(): HttpResult<T> {
    return suspendCancellableCoroutine { cancellableContinuation ->
        cancellableContinuation.invokeOnCancellation {
            if (cancellableContinuation.isCancelled) {
                cancel()
            }
        }

        enqueue(object : ILifecycleCallback<T> {
            override fun onFinish() {
            }

            override fun onSuccess(statusCode: Int, response: Response<T>) {
                cancellableContinuation.resumeWith(runCatching {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body == null) {
                            HttpResult.Error(
                                    NetworkError(response.code(), response.message(),
                                    NullPointerException("Response body is null"))
                            )
                        } else {
                            HttpResult.Okay(body, response.raw())
                        }
                    } else {
                        HttpResult.Error(
                                NetworkError(
                                        response.code(), response.message(), HttpException(response)
                                )
                        )
                    }
                })
            }

            override fun onFail(statusCode: Int, throwable: Throwable) {
                if (cancellableContinuation.isCancelled) {
                    return
                }
                cancellableContinuation.resume(
                        HttpResult.Error(
                                NetworkError(statusCode, throwable.message, Exception(throwable))
                        )
                )
            }
        })
    }
}

suspend fun <T : Any> ILifecycleCall<T>.awaitWithTimeout(
        timeout: Long = 0L
): HttpResult<T> {
    return if (timeout <= 100) {
        awaitResult()
    } else {
        withTimeoutOrNull(timeout) {
            awaitResult()
        } ?: HttpResult.Error(
                NetworkError(
                        0, "Timeout: no response within $timeout",
                        TimeoutException("Timeout: no response within $timeout")
                )
        )
    }
}