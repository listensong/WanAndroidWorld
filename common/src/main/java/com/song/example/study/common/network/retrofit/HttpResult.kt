package com.song.example.study.common.network.retrofit

import okhttp3.Response
import java.lang.Exception

/**
 * @package com.song.example.study.common.network.retrofit
 * @fileName HttpResult
 * @date on 3/29/2020 10:42 AM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
data class NetworkError(
        val errorCode: Int,
        val errorMessage: String?,
        val exception: Exception
)

sealed class HttpResult<out T: Any> {
    class Okay<out T: Any> (
        val value: T,
        val response: Response
    ): HttpResult<T>() {
        override fun toString(): String {
            return "HttpResult.Okay{value=$value, response=$response}"
        }
    }

    class Error (
            val error: NetworkError
    ): HttpResult<Nothing>() {
        override fun toString(): String {
            return "HttpResult.Error{error=$error}"
        }
    }
}

inline fun <T: Any> HttpResult<T>.onFailure(
        action: (HttpResult.Error) -> Unit
): HttpResult<T> {
    return if (this is HttpResult.Error) {
        action(this)
        this
    } else {
        this
    }
}

inline fun <A: Any, V: Any> HttpResult<A>.onSuccess(
        action: (HttpResult.Okay<A>) -> HttpResult<V>?
): HttpResult<V>? {
    return if (this is HttpResult.Okay) {
        action(this)
    } else {
        this as HttpResult<V>?
    }
}

inline fun <A: Any, V: Any> HttpResult<A>.onOkay(
        action: (HttpResult.Okay<A>) -> HttpResult<V>?
): HttpResult<V>? {
    return if (this is HttpResult.Okay) {
        action(this)
    } else {
        this as HttpResult<V>?
    }
}

inline fun <T: Any> HttpResult<T>?.doFollow(
        action: (HttpResult<T>?) -> T
) : T {
    return  action(this)
}

inline fun <S: Any, T: Any> HttpResult<S>?.doSwitchFollow(
        action: (HttpResult<S>?) -> T
) : T {
    return  action(this)
}


