package com.song.example.study.common.network.retrofit

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @package com.song.example.study.common.network.retrofit
 * @fileName HttpResultCallback
 * @date on 6/6/2020 9:22 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */

internal class Callback<V: Any> {
    internal var dispatcher: CoroutineContext = Dispatchers.Main
    internal var callback: (V) -> Unit = { }
}

class HttpResultCallback<T: Any>(
        internal var request: () -> Unit = { }
) {
    internal var success: Callback<HttpResult.Okay<T>>? = null
    internal var fail: Callback<HttpResult.Error>? = null
    internal var complete: Callback<Boolean>? = null
    internal var delayMillis: Long = 0L

    fun onSuccess(
            dispatcher: CoroutineContext? = null,
            callCallback: (HttpResult.Okay<T>)-> Unit
    ): HttpResultCallback<T> {
        if (this.success == null) {
            this.success = Callback()
            if (dispatcher != null) {
                this.success!!.dispatcher = dispatcher
            }
            this.success!!.callback = callCallback
        } else {
            throw DuplicateAssignmentException("success is not NULL, should be assignment only once")
        }

        return this
    }

    fun onFailure(
            dispatcher: CoroutineContext? = null,
            callCallback: (HttpResult.Error)-> Unit
    ): HttpResultCallback<T> {
        if (this.fail == null) {
            this.fail = Callback()
            if (dispatcher != null) {
                this.fail!!.dispatcher = dispatcher
            }
            this.fail!!.callback = callCallback
        } else {
            throw DuplicateAssignmentException("fail is not NULL, should be assignment only once")
        }
        return this
    }

    fun onComplete(
            dispatcher: CoroutineContext? = null,
            callCallback: (Boolean)-> Unit
    ): HttpResultCallback<T> {
        if (this.complete == null) {
            this.complete = Callback()
            if (dispatcher != null) {
                this.complete!!.dispatcher = dispatcher
            }
            this.complete!!.callback = callCallback
        } else {
            throw DuplicateAssignmentException("complete is not NULL, should be assignment only once")
        }
        return this
    }

    internal fun clear() {
        this.success = null
        this.fail = null
        this.complete = null
    }

    fun start() {
        this.request.invoke()
    }

    fun delayStart(delay: Long) {
        delayMillis = delay
        this.request.invoke()
    }
}