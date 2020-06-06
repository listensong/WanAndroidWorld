package com.song.example.study.common.network.retrofit

import okhttp3.Response
import java.lang.Exception

/**
 * @package com.song.example.study.common.network.retrofit
 * @fileName HttpResult
 * @date on 3/29/2020 10:42 AM
 * @author Listensong
 * @desc
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

inline fun <T: Any> HttpResult<T>.onSuccess(
        action: (HttpResult.Okay<T>) -> Unit
): HttpResult<T>? {
    if (this is HttpResult.Okay) {
        action(this)
    }
    return this
}

inline fun <T: Any> HttpResult<T>?.followDo(
        action: (HttpResult<T>?) -> Unit
) : Any {
    return action(this)
}

