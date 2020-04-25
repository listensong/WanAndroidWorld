package com.song.example.wanandroid.app

import androidx.annotation.CallSuper
import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.app.data.appDbModule
import com.song.example.wanandroid.app.network.wanAppHttpClientModule
import org.kodein.di.Kodein

/**
 * @author song
 * @time 19-8-22 上午10:23
 * @desc com.song.example.wanandroid.app.BoreApplication
 */
class WanApplication : BaseApplication() {

    override fun wideModuleConfig() = Kodein.Module("WelcomeActivity") {
        import(wanAppHttpClientModule)
        import(appDbModule)
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
    }
}
