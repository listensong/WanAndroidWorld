package com.song.example.study.util.image

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 *
 * Created by song on 2017/11/23.
 */

class ProgressInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val url = request.url
        val body = response.body
        return response.newBuilder().body(
                ProgressResponseBody(url.toString(), body!!)
        ).build()
    }

    companion object {

        private val progressCallbackMap = HashMap<String, ProgressListener>()

        fun getListener(url: String): ProgressListener? {
            return progressCallbackMap[url]
        }

        fun addListener(url: String, listener: ProgressListener?) {
            if (listener == null) {
                progressCallbackMap.remove(url)
            } else {
                progressCallbackMap[url] = listener
            }
        }

        fun removeListener(url: String) {
            progressCallbackMap.remove(url)
        }
    }
}
