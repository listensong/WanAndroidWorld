package com.song.example.study

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ProcessLifecycleOwner
import com.song.example.study.base.ui.ApplicationLifecycleObserver
import com.song.example.study.di.appWideModule
import com.song.example.study.util.WanLog
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import kotlin.properties.Delegates

/**
 * @author song
 * @time 19-8-22 上午10:27
 * @desc com.song.example.study.BaseApplication
 */
open class BaseApplication: Application(), KodeinAware {

    @VisibleForTesting
    var overrideBindings: Kodein.MainBuilder.() -> Unit = {}

    override val kodein = Kodein.lazy {
        import(appWideModule(this@BaseApplication))
        import(moduleDiConfig())
    }

    protected open fun moduleDiConfig() = Kodein.Module("baseApplicationModule") {

    }

    companion object {
        const val TAG = "BaseApplication"

        var instance: BaseApplication by Delegates.notNull()
            private set
    }

    private val handler = Handler(Looper.getMainLooper())
    private val applicationLifecycleObserver = ApplicationLifecycleObserver()

    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            if (activity != null) {
                WanLog.d(TAG, activity.javaClass.simpleName + " onCreated")
            }
        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity?) {
            if (activity != null) {
                WanLog.d(TAG, activity.javaClass.simpleName + " onDestroyed")
            }
        }
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ProcessLifecycleOwner.get().lifecycle.addObserver(applicationLifecycleObserver)
    }

    @CallSuper
    override fun onTerminate() {
        handler.removeCallbacksAndMessages(null)
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ProcessLifecycleOwner.get().lifecycle.removeObserver(applicationLifecycleObserver)
        super.onTerminate()
    }
}