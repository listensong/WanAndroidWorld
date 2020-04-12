package com.song.example.wanandroid.app

import androidx.annotation.CallSuper
import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.app.data.appDbModule
import com.song.example.wanandroid.app.network.wanAppHttpClientModule
import org.kodein.di.Kodein

/**
 * @author song
 * Time: 19-8-22 上午10:23
 * Desc: com.song.example.wanandroid.app.BoreApplication
 */
class WanApplication : BaseApplication() {

    override val kodein = Kodein.lazy {
        import(wanAppHttpClientModule)
        import(appDbModule)
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
    }
}
