package com.song.example.study.wanandroid.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.forEachIndexed
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.song.example.study.wanandroid.R
import com.song.example.study.wanandroid.main.home.HomeFragment
import com.song.example.study.wanandroid.main.home.wanHomeKodeinModule
import com.song.example.study.base.ui.BaseActivity
import com.song.example.study.wanandroid.search.SearchActivity
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
        import(wanHomeKodeinModule)
        import(wanMainWelcomeKodeinModule)
    }

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var drawer: DrawerLayout
    private var pagerAdapter: HomePagerAdapter? = null

    private val homeFragment: HomeFragment by instance()
    private val viewModel: WelcomeViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wan_activity_welcome)
        initStatusBarStyle()
        setActionBar()
        initViewPager()
        initTabLayoutView()
        initDrawerLayout()
        initToolbar()
    }

    private fun initToolbar() {
        val searchIcon = findViewById<ImageView>(R.id.iv_search)
        searchIcon.setOnClickListener {
            gotoSearchPage()
        }
    }

    private fun gotoSearchPage() {
        val searchIntent = Intent(this, SearchActivity::class.java)
        postDelayed(100) {
            startActivity(searchIntent)
        }
    }

    private fun initDrawerLayout() {
        drawer = findViewById(R.id.dl_main)
        drawer.let {
            val toggle = ActionBarDrawerToggle(
                    this@WelcomeActivity,
                    it,
                    toolbar,
                    R.string.wan_navigation_drawer_open,
                    R.string.wan_navigation_drawer_close)
            it.addDrawerListener(toggle)
            toggle.syncState()
            it.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) {

                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                }

                override fun onDrawerClosed(drawerView: View) {
                    viewModel.setDrawerOpenState(false)
                }

                override fun onDrawerOpened(drawerView: View) {
                    viewModel.setDrawerOpenState(true)
                }
            })
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
            textView.setTextColor(resources.getColor(R.color.wanColorWhite))
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
