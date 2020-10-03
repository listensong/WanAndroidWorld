package com.song.example.study.wanandroid

import android.app.Application
import com.song.example.study.BaseApplication
import com.song.example.study.init.ModuleInitialize
import com.song.example.study.wanandroid.data.wanAppDbModule
import com.song.example.study.wanandroid.network.wanAppHttpClientModule
import org.kodein.di.DI

/**
 * @author song
 * @time 19-8-22 上午10:23
 * @desc com.song.example.study.wanandroid.WanApplication
 */
class WanInitialize private constructor() : ModuleInitialize {

    private object Holder {
        val INSTANCE: ModuleInitialize = WanInitialize()
    }

    companion object {
        fun getInstance(): ModuleInitialize {
            return Holder.INSTANCE
        }

        fun getApplication(): Application {
            return BaseApplication.instance
        }
    }

    override fun onCreate(application: Application) {
    }

    override fun onTerminate() {
    }

    override fun moduleDiConfig() = DI.Module("WanInitialize") {
        import(wanAppHttpClientModule)
        import(wanAppDbModule)
    }
}
