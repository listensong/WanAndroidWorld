package com.song.example.wanandroid.app.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEachIndexed
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
    private lateinit var pagerAdapter: HomePagerAdapter
    private lateinit var tabLayout: TabLayout

    private val homeFragment: HomeFragment by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initViewPager()
        initTabLayoutView()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.vp_home)
        pagerAdapter = HomePagerAdapter(supportFragmentManager)

        pagerAdapter.addFragment(homeFragment, "首页")

        viewPager.offscreenPageLimit = 2
        viewPager.adapter = pagerAdapter
    }

    private fun initTabLayoutView() {
        tabLayout = findViewById(R.id.tl_home_page)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.forEachIndexed { index, view ->
            if (view is TabLayout.Tab) {
                view.customView = pagerAdapter.getTabView(this, index)
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: 0
                viewPager.setCurrentItem(position, true)

                updateTabTitleStyle(tab, 20f, true)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_welcome, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "action_search", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
