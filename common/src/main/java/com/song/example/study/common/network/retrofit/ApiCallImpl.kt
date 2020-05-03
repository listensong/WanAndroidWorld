package com.song.example.study.common.network.retrofit

import com.song.example.study.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @author song
 */
internal class ApiCallImpl {

    val baseCallUrl = "https://www.baidu.com/"

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
                        HttpLoggingInterceptorCreator.create(BuildConfig.DEBUG)
                )
                .build()
    }


    fun <T> callApi(clazz: Class<T>) : T {
        return callApi(clazz, InterceptorModifyRequest())
    }

    fun <T> callApi(clazz: Class<T>,
                       interceptorModifyRequest: InterceptorModifyRequest
    ) : T {
        return callApi(interceptorModifyRequest).create(clazz)
    }

    fun callApi(
            interceptorModifyRequest: InterceptorModifyRequest = InterceptorModifyRequest()
    ) : Retrofit {
        return Retrofit
                .Builder()
                .client(okHttpClient.newBuilder().addInterceptor(interceptorModifyRequest).build())
                .baseUrl(baseCallUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LifecycleCallAdapterFactoryCreator.create())
                .build()
    }

    companion object {
        @JvmStatic
        fun getInstance(): ApiCallImpl = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = ApiCallImpl()
    }
}