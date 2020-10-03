package com.song.example.study.base.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.song.example.study.R
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.retainedDI

/**
 * @author song
 * @time 19-8-21 下午9:14
 * @desc
 */
abstract class BaseActivity : AppCompatActivity(), DIAware {
    protected var toolbar: Toolbar? = null
    protected val handler = Handler(Looper.getMainLooper())

    protected val parentDI by closestDI()
    // override val diContext = diContext<AppCompatActivity>("", this)
    override val di: DI by retainedDI {
        extend(parentDI, copy = Copy.All)
        import(activityCustomDiModule())
    }
    protected open fun activityCustomDiModule() = DI.Module("activityModule") {
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    protected open fun setActionBar() {
        toolbar = findViewById(R.id.toolbar)

        toolbar?.also {
            it.setContentInsetsAbsolute(0, 0)
            it.contentInsetStartWithNavigation = 0
            setSupportActionBar(it)
        }

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.btn_back)
            setHomeActionContentDescription(resources.getString(R.string.action_bar_back_button_navi_up))
            title = ""
        }
    }

    protected fun updateTitle(title: String?) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun postDelayed(delay: Long = 1000, action: () -> Unit) {
        handler.postDelayed({action()}, delay)
    }

    protected fun pushFragment(fragment: Fragment?) {
        fragment ?: return
        supportFragmentManager.beginTransaction().run {
            replace(R.id.container,
                    fragment,
                    fragment.javaClass.simpleName + "_" + fragment.hashCode()
            )
            commit()
        }
        supportFragmentManager.executePendingTransactions()
    }

    protected fun popFragment() {
        try {
            supportFragmentManager.popBackStackImmediate()
        } catch (e: IllegalStateException) {
            Log.e("", "popFragment: e:$e")
        } catch (e: NullPointerException) {
            Log.e("", "popFragment: e:$e")
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BaseFragment) {
                if (it.onBackPressed()) {
                    return
                }
            }
        }
        super.onBackPressed()
    }
}