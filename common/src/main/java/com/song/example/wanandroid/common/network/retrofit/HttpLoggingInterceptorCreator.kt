package com.song.example.wanandroid.common.network.retrofit

import okhttp3.logging.HttpLoggingInterceptor

/**
 * @package com.song.example.wanandroid.common.network.retrofit
 * @fileName HttpLoggingInterceptorConfiguration
 * @date on 2/24/2020 6:15 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
object HttpLoggingInterceptorCreator {
    @JvmStatic
    fun create(debugConfig: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = if (debugConfig) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }
    }
}