package com.song.example.wanandroid.common.network.retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: Listensong
 * Time: 19-10-24 下午1:59
 * Desc: com.song.example.wanandroid.common.network.retrofit.TestService
 */
interface TestService {
    @GET("/")
    fun testBaidu(): ILifecycleCall<ResponseBody>
}