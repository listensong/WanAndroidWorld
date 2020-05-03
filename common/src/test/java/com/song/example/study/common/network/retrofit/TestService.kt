package com.song.example.study.common.network.retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * @author: Listensong
 * @time 19-10-24 下午1:59
 * @desc com.song.example.study.common.network.retrofit.TestService
 */
interface TestService {
    @GET("/")
    fun testBaidu(): ILifecycleCall<ResponseBody>
}