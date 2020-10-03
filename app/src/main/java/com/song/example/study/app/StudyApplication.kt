package com.song.example.study.app

import androidx.annotation.CallSuper
import com.song.example.study.BaseApplication
import com.song.example.study.wanandroid.WanInitialize
import org.kodein.di.DI

/**
 * @author song
 * @time 19-8-22 上午10:23
 * @desc com.song.example.study.app.BoreApplication
 */
class StudyApplication : BaseApplication() {

    override fun moduleDiConfig() = DI.Module("StudyApplication") {
        import(WanInitialize.getInstance().moduleDiConfig())
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        WanInitialize.getInstance().onCreate(this)
    }

    @CallSuper
    override fun onTerminate() {
        super.onTerminate()
        WanInitialize.getInstance().onTerminate()
    }
}
