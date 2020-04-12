package com.song.example.wanandroid.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.common.network.retrofit.commonNetworkModule
import org.kodein.di.Kodein
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @package com.song.example.wanandroid
 * @fileName ApplicationModule
 * @date on 4/12/2020 8:38 AM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
const val APP_WIDE_MODULE = "APP_WIDE_MODULE"
fun appWideModule(application: Application) = Kodein.Module(APP_WIDE_MODULE) {
    bind<Context>() with singleton { application }
    bind<Resources>() with singleton { instance<BaseApplication>().resources }
    import(androidCoreModule(application))
    import(androidXModule(application))
    import(commonNetworkModule)
}