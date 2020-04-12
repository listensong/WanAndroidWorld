package com.song.example.wanandroid.app.network

import com.song.example.wanandroid.BuildConfig
import com.song.example.wanandroid.common.network.retrofit.HttpLoggingInterceptorConfiguration
import com.song.example.wanandroid.common.network.retrofit.LifecycleCallAdapterFactoryCreator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @package com.song.example.wanandroid.app.network
 * @fileName WanClientModule
 * @date on 4/11/2020 11:03 AM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
private const val APP_WAN_CLIENT_MODULE = "APP_WAN_CLIENT_MODULE"
private const val APP_WAN_HTTP_CLIENT_MODULE_LOG_INTERCEPTOR = "APP_WAN_HTTP_CLIENT_MODULE_LOG_INTERCEPTOR"

const val APP_WAN_BASE_URL = "https://www.wanandroid.com/"
//const val APP_WAN_SERVICE = "APP_WAN_SERVICE"

val wanAppHttpClientModule = Kodein.Module(APP_WAN_CLIENT_MODULE) {

    bind<WanService>() with singleton {
        //instance<Retrofit>().create(WanService::class.java)
        Retrofit.Builder()
                .client(instance())
                .baseUrl(APP_WAN_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(LifecycleCallAdapterFactoryCreator.create())
                .build()
                .create(WanService::class.java)
    }

    bind<Retrofit.Builder>() with provider { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with provider { OkHttpClient.Builder() }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(APP_WAN_BASE_URL)
                .client(instance())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(LifecycleCallAdapterFactoryCreator.create())
                .build()
    }

    bind<Interceptor>(APP_WAN_HTTP_CLIENT_MODULE_LOG_INTERCEPTOR) with singleton {
        HttpLoggingInterceptorConfiguration.createLoggingInterceptor(BuildConfig.DEBUG)
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .proxy(Proxy.NO_PROXY)
//                .addInterceptor(instance<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG))
                .addInterceptor(
                        instance<Interceptor>(APP_WAN_HTTP_CLIENT_MODULE_LOG_INTERCEPTOR)
                )
                .build()
    }
}