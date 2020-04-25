package com.song.example.wanandroid.app.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.forEachIndexed
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.song.example.wanandroid.app.R
import com.song.example.wanandroid.app.main.home.HomeFragment
import com.song.example.wanandroid.app.main.home.homeKodeinModule
import com.song.example.wanandroid.base.ui.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


/**
 * @author song
 */
class WelcomeActivity : BaseActivity() {

    companion object {
        const val TAG = "WelcomeActivity"
    }

    override fun activityCustomDiModule() = Kodein.Module(TAG) {
        import(homeKodeinModule)
    }

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var drawer: DrawerLayout
    private var pagerAdapter: HomePagerAdapter? = null

    private val homeFragment: HomeFragment by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initStatusBarStyle()
        setActionBar()
        initViewPager()
        initTabLayoutView()
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        drawer = findViewById(R.id.dl_main)
        drawer.let {
            val toggle = ActionBarDrawerToggle(
                    this@WelcomeActivity,
                    it,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            it.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun initStatusBarStyle() {
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.vp_home)
        pagerAdapter = HomePagerAdapter(supportFragmentManager).apply {
            addFragment(homeFragment, "首页")
        }

        viewPager.offscreenPageLimit = 2
        viewPager.adapter = pagerAdapter
    }

    private fun initTabLayoutView() {
        tabLayout = findViewById(R.id.tl_home_page)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.forEachIndexed { index, view ->
            if (view is TabLayout.Tab) {
                view.customView = pagerAdapter?.getTabView(this, index)
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.run {
                    viewPager.setCurrentItem(position, true)
                    updateTabTitleStyle(this, 20f, true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabTitleStyle(tab, 16f, false)
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })

        viewPager.setCurrentItem(0, true)
        updateTabTitleStyle(tabLayout.getTabAt(0), 20f, true)
    }

    private fun updateTabTitleStyle(tab: TabLayout.Tab?, textSize: Float, isSelected: Boolean) {
        val textView: TextView = tab?.customView?.findViewById(R.id.tv_tab_title) ?: return
        textView.textSize = textSize
        if (isSelected) {
            textView.setTextColor(resources.getColor(R.color.colorWhite))
        } else {
            textView.setTextColor(resources.getColor(R.color.colorGray))
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pagerAdapter?.release()
        pagerAdapter = null
    }
}
