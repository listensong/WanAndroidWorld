package com.song.example.study.common.network.retrofit

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * @author song
 */
class ErrorHandleCallAdapter<R> internal constructor(
        private val responseType: Type,
        private val lifecycleRegistry: LifecycleRegistry? = null,
        private val enableCancel: Boolean
) : CallAdapter<R, ILifecycleCall<R>> {
    override fun adapt(call: Call<R>): ILifecycleCall<R> {
        return LifecycleCallImpl(call, enableCancel).also { lifecycleCallImpl ->
            if (lifecycleRegistry?.currentState == Lifecycle.State.DESTROYED) {
                lifecycleCallImpl.onDestroy()
            } else {
                lifecycleRegistry?.addObserver(lifecycleCallImpl)
            }
        }
    }

    override fun responseType(): Type {
        return responseType
    }
}