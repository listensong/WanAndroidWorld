package com.song.example.study.base.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.song.example.study.BaseApplication
import com.song.example.study.base.job.CompositeJob
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Job
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

/**
 * @author: Listensong
 * @time 19-10-21 下午8:20
 * @desc com.song.example.study.base.ui.BaseFragment
 */
abstract class BaseFragment : Fragment(), DIAware {

    protected var TAG = "BaseFragment"
    protected val safeContext = BaseApplication.instance

    private val _parentDI by closestDI()

    override val di: DI = DI.lazy {
        extend(_parentDI)
        import(fragmentCustomDiModule())
    }

    protected open fun fragmentCustomDiModule() = DI.Module("fragmentModule") {
    }


    protected var title: String = ""
    protected val handler = Handler(Looper.getMainLooper())

    private val createDisposable = CompositeDisposable()
    private val startDisposable = CompositeDisposable()
    private val resumeDisposable = CompositeDisposable()

    private val createKtDisposable = CompositeJob()
    private val startKtDisposable = CompositeJob()
    private val resumeKtDisposable = CompositeJob()

    protected val safeActivity: Activity?
        get() {
            val ret = activity
            return if (ret != null && !ret.isFinishing && !ret.isDestroyed) {
                ret
            } else {
                null
            }
        }

    val isActivityAvailable: Boolean
        get() {
            return !(
                    activity == null
                            || requireActivity().isFinishing
                            || requireActivity().isDestroyed
                    )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.javaClass.simpleName
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        resumeDisposable.clear()
        resumeKtDisposable.clear()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        startDisposable.clear()
        startKtDisposable.clear()
    }

    protected fun updateActionBar() {
        (activity != null && !(activity as FragmentActivity).isFinishing) || return
        val actionBar = (activity as AppCompatActivity).supportActionBar ?: return
        actionBar.title = title
    }

    protected fun updateTitle(title: String?) {
        if (title.isNullOrEmpty()) {
            return
        }

        (activity != null && !(activity as FragmentActivity).isFinishing) || return
        val actionBar = (activity as AppCompatActivity).supportActionBar ?: return
        actionBar.title = title
    }

    override fun onDestroy() {
        super.onDestroy()

        createDisposable.dispose()
        startDisposable.dispose()
        resumeDisposable.dispose()

        createKtDisposable.dispose()
        startKtDisposable.dispose()
        resumeKtDisposable.dispose()
    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun finishActivity() {
        activity?.finish()
    }

    protected fun bindLifecycle(state: Lifecycle.State, disposable: Disposable) {
        when {
            state.isAtLeast(Lifecycle.State.RESUMED) -> {
                resumeDisposable.add(disposable)
            }
            state.isAtLeast(Lifecycle.State.STARTED) -> {
                startDisposable.add(disposable)
            }
            else -> {
                createDisposable.add(disposable)
            }
        }
    }

    protected fun bindLifecycle(state: Lifecycle.State, job: Job) {
        when {
            state.isAtLeast(Lifecycle.State.RESUMED) -> {
                resumeKtDisposable.add(job)
            }
            state.isAtLeast(Lifecycle.State.STARTED) -> {
                startKtDisposable.add(job)
            }
            else -> {
                createKtDisposable.add(job)
            }
        }
    }

    @CallSuper
    open fun onBackPressed(): Boolean {
        return false
    }
}

fun Job.runImmediately(): Job {
    this.start()
    return this
}