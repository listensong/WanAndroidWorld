package com.song.example.wanandroid.app.network

import com.song.example.wanandroid.BuildConfig
import com.song.example.wanandroid.common.network.retrofit.HttpLoggingInterceptorConfiguration
import com.song.example.wanandroid.common.network.retrofit.InterceptorModifyRequest
import com.song.example.wanandroid.common.network.retrofit.LifecycleCallAdapterFactoryCreator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @author song
 */
class WanApiCallImpl {

    private var clientCacheDir: String  = ""
    val wanBaseUrl = "https://www.wanandroid.com/"

    private val okHttpClient by lazy {
        OkHttpClient
                .Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .proxy(Proxy.NO_PROXY)
                //.cache(Cache(BaseApplication.instance.cacheDir, 52428800))//50*1024*1024 = 52428800
                .addInterceptor(
                        HttpLoggingInterceptorConfiguration.createLoggingInterceptor(BuildConfig.DEBUG)
                )
                .build()
    }

    fun <T> callWanApi(clazz: Class<T>) : T {
        return callWanApi(clazz, InterceptorModifyRequest())
    }

    fun <T> callWanApi(clazz: Class<T>,
                       interceptorModifyRequest: InterceptorModifyRequest
    ) : T {
        return callWanApi(interceptorModifyRequest).create(clazz)
    }

    fun callWanApi(
            interceptorModifyRequest: InterceptorModifyRequest = InterceptorModifyRequest()
    ) : Retrofit {
        return Retrofit
                .Builder()
                .client(okHttpClient.newBuilder().addInterceptor(interceptorModifyRequest).build())
                .baseUrl(wanBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LifecycleCallAdapterFactoryCreator.create())
                .build()
    }

    companion object {
        @JvmStatic
        fun getInstance(): WanApiCallImpl = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = WanApiCallImpl()
    }
}