package com.song.example.study.common.network.retrofit

/**
 * @package com.song.example.study.common.network.retrofit
 * @fileName CoroutineLifecycleCall
 * @date on 6/6/2020 9:19 PM
 * @author Listensong
 * @desc
 * @email No
 */
interface CoroutineLifecycleCall<T> {
    fun cancel()
    fun enqueue(callback: CoroutineLifecycleCallback<T>)
    fun clone(): CoroutineLifecycleCall<T>
    fun isCanceled(): Boolean
    fun enableCancel(): Boolean
}