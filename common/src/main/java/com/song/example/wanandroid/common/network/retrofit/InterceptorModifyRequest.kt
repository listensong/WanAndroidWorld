package com.song.example.wanandroid.common.network.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author song
 */
class InterceptorModifyRequest(
        private vararg val modifyRequests: (original: Request, builder: Request.Builder) -> Boolean
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.let { originalChain ->
            val originalRequest = originalChain.request()
            val requestBuilder = originalRequest.newBuilder().apply {
                header(ApiCallConstant.CONTENT_TYPE   , ApiCallConstant.CONTENT_TYPE_APPLICATION_JSON)
                header(ApiCallConstant.ACCEPT_LANGUAGE, ApiCallConstant.AC_LAN_ZH_CN)
                header(ApiCallConstant.CACHE_CONTROL  , ApiCallConstant.CACHE_CONTROL_NO_CACHE)

                header(ApiCallConstant.AUTHORIZATION, getAuthorization())
            }

            modifyRequests.forEach {
                it(originalRequest, requestBuilder)
            }

            requestBuilder.build().let {
                originalChain.proceed(it)
            }
        }
    }

    private fun getAuthorization(): String {
        return ""
    }
}