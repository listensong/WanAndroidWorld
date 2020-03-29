package com.song.example.wanandroid.base.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.song.example.wanandroid.R
import java.lang.IllegalStateException
import java.lang.NullPointerException

/**
 *  @author song
 * Time: 19-8-21 下午9:14
 * Desc: com.song.example.wanandroid.app.base.BaseActivity
 */
abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var TAG : String
    protected var toolbar: Toolbar? = null
    protected val handler = Handler(Looper.getMainLooper())

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.javaClass.simpleName
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun setActionBar() {
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

    protected fun updateTitle(title: String) {
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
            Log.e(TAG, "popFragment: e:$e")
        } catch (e: NullPointerException) {
            Log.e(TAG, "popFragment: e:$e")
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