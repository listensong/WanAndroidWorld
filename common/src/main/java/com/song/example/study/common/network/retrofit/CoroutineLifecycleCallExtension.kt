package com.song.example.study.common.network.retrofit

import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeoutException
import kotlin.coroutines.resume


const val CoroutineLifecycleCallExtensionKtPath =
        "com.song.example.study.common.network.retrofit.CoroutineLifecycleCallExtensionKt"

internal const val CUSTOM_TIMEOUT_BRIEF_MSG = "Timeout: no response within "
internal const val CUSTOM_TIMEOUT_ERROR_CODE = 0


internal suspend fun <T : Any> CoroutineLifecycleCall<T>.awaitResult(): HttpResult<T> {
    return suspendCancellableCoroutine { cancellableContinuation ->
        cancellableContinuation.invokeOnCancellation {
            if (cancellableContinuation.isCancelled) {
                cancel()
            }
        }

        enqueue(object : CoroutineLifecycleCallback<T> {
            override fun onFinish() {}

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
                                NetworkError(response.code(), response.message(), HttpException(response))
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

suspend fun <T : Any> CoroutineLifecycleCall<T>.suspendAwaitTimeout(
        delayMillis: Long = 0L,
        timeoutMillis: Long = 0L
): HttpResult<T> {
    return withContext(Dispatchers.IO) {
        if (delayMillis > 0) {
            delay(delayMillis)
        }
        if (timeoutMillis <= 100) {
            awaitResult()
        } else {
            withTimeoutOrNull(timeoutMillis) {
                awaitResult()
            } ?: kotlin.run {
                this@suspendAwaitTimeout.cancel()
                HttpResult.Error(
                        NetworkError(
                                CUSTOM_TIMEOUT_ERROR_CODE,
                                "$CUSTOM_TIMEOUT_BRIEF_MSG $timeoutMillis millis",
                                TimeoutException("$CUSTOM_TIMEOUT_BRIEF_MSG $timeoutMillis")
                        )
                )
            }
        }
    }
}

fun <T : Any> CoroutineLifecycleCall<T>.awaitTimeout(
        scope: CoroutineScope,
        timeoutMillis: Long = 0L
) : HttpResultCallback<T> {
    val httpResultCallback = HttpResultCallback<T>()
    val job = scope.launch(start = CoroutineStart.LAZY) {
        if (httpResultCallback.delayMillis > 0) {
            delay(httpResultCallback.delayMillis)
        }
        suspendAwaitTimeout(timeoutMillis).let { result ->
            dispatchHttpResult(httpResultCallback, result)
        }
    }
    job.invokeOnCompletion { throwable ->
        httpResultCallback.complete?.let { complete ->
            scope.launch(context = complete.dispatcher) {
                val isCompleteNormal = (throwable == null)
                complete.callback(isCompleteNormal)
                httpResultCallback.clear()
            }
        }
    }

    httpResultCallback.request = { job.start() }
    return httpResultCallback
}

private suspend fun <T : Any> dispatchHttpResult(
        callback: HttpResultCallback<T>,
        result: HttpResult<T>
) {
    when (result) {
        is HttpResult.Okay -> {
            callback.success?.let { okay ->
                withContext(okay.dispatcher) {
                    okay.callback(result)
                }
            }
        }
        is HttpResult.Error -> {
            callback.fail?.let { fail ->
                withContext(fail.dispatcher) {
                    fail.callback(result)
                }
            }
        }
    }
}