package com.song.example.wanandroid.app.network

import com.song.example.wanandroid.common.network.retrofit._HTTP_DEFAULT_HEADER_INTERCEPTOR
import com.song.example.wanandroid.common.network.retrofit._HTTP_DEFAULT_LOG_INTERCEPTOR
import com.song.example.wanandroid.common.network.retrofit._HTTP_LIFECYCLE_CALL_ADAPTER
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
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

const val APP_WAN_BASE_URL = "https://www.wanandroid.com/"
const val APP_WAN_CLIENT_WITH_DEFAULT_INTERCEPTOR = "APP_WAN_CLIENT_WITH_DEFAULT_INTERCEPTOR"
const val APP_WAN_RETROFIT = "APP_WAN_RETROFIT"

val wanAppHttpClientModule = Kodein.Module(APP_WAN_CLIENT_MODULE) {

    bind<WanService>() with singleton {
        instance<Retrofit>(APP_WAN_RETROFIT).create(WanService::class.java)
    }

    bind<Retrofit>(APP_WAN_RETROFIT) with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(APP_WAN_BASE_URL)
                .client(instance(APP_WAN_CLIENT_WITH_DEFAULT_INTERCEPTOR))
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(instance(_HTTP_LIFECYCLE_CALL_ADAPTER))
                .build()
    }

    bind<OkHttpClient>(tag = APP_WAN_CLIENT_WITH_DEFAULT_INTERCEPTOR) with singleton {
        instance<OkHttpClient.Builder>()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(
                        instance<Interceptor>(_HTTP_DEFAULT_HEADER_INTERCEPTOR)
                )
                .addInterceptor(
                        instance<Interceptor>(_HTTP_DEFAULT_LOG_INTERCEPTOR)
                )
                .build()
    }
}