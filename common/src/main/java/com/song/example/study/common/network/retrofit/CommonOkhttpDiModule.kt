package com.song.example.study.common.network.retrofit

import com.song.example.study.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @package com.song.example.study.common.network.retrofit
 * @fileName CommonOkhttpDiModule
 * @date on 4/12/2020 4:39 PM
 * @author Listensong
 * @desc
 * @email No
 */
@Suppress("ObjectPropertyName")
const val _COMMON_NETWORK_CLIENT_MODULE = "_COMMON_NETWORK_CLIENT_MODULE"
@Suppress("ObjectPropertyName")
const val _HTTP_DEFAULT_LOG_INTERCEPTOR = "_HTTP_DEFAULT_LOG_INTERCEPTOR"
@Suppress("ObjectPropertyName")
const val _HTTP_DEFAULT_HEADER_INTERCEPTOR = "_HTTP_DEFAULT_HEADER_INTERCEPTOR"
@Suppress("ObjectPropertyName")
const val _HTTP_LIFECYCLE_CALL_ADAPTER = "_HTTP_LIFECYCLE_CALL_ADAPTER"


val commonNetworkModule = Kodein.Module(_COMMON_NETWORK_CLIENT_MODULE) {

    bind<Retrofit.Builder>() with provider { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with provider { OkHttpClient.Builder() }

    bind<Retrofit>(tag = "_template") with singleton {
        instance<Retrofit.Builder>()
                .baseUrl("")
                .client(instance())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(
                        instance(_HTTP_LIFECYCLE_CALL_ADAPTER)
                )
                .build()
    }

    bind<CallAdapter.Factory>(_HTTP_LIFECYCLE_CALL_ADAPTER) with singleton {
        CoroutineLifecycleCallAdapterFactory(true)
    }

    bind<Interceptor>(_HTTP_DEFAULT_LOG_INTERCEPTOR) with singleton {
        HttpLoggingInterceptor().also {
            it.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    bind<Interceptor>(_HTTP_DEFAULT_HEADER_INTERCEPTOR) with singleton {
        InterceptorModifyRequest()
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(
                        instance<Interceptor>(_HTTP_DEFAULT_LOG_INTERCEPTOR)
                )
                .build()
    }

    bind<OkHttpClient>(tag = "_has_default_header_interceptor") with singleton {
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