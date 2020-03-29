package com.song.example.wanandroid.app.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.song.example.wanandroid.app.R
import com.song.example.wanandroid.app.main.home.*
import com.song.example.wanandroid.app.network.WanApiCallImpl
import com.song.example.wanandroid.base.ui.BaseActivity
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator


/**
 * @author song
 */
class WelcomeActivity : BaseActivity() {

    private lateinit var banner: Banner<*, *>

    companion object {
        class HomeViewModelFactory(
                private val homeRepository: HomeRepository
        ): ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                    return HomeViewModel(homeRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val homeViewModelFactory: HomeViewModelFactory = HomeViewModelFactory(
            HomeRepository(WanApiCallImpl.getInstance())
    )
    private val viewModel: HomeViewModel by lazy(LazyThreadSafetyMode.NONE) {
        homeViewModelFactory.create(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val list = listOf(
                BannerVO(
                        title = "",
                        type = 0,
                        imageUrl = "https://img.zcool.cn/community/013c7d5e27a174a80121651816e521.jpg",
                        linkUrl = ""
                )
        )

        val bannerAdapter = HomeBannerAdapter(list)
        banner = findViewById<View>(R.id.banner) as Banner<*, *>
        banner.run {
            adapter = bannerAdapter
            setOrientation(Banner.HORIZONTAL)
            indicator = CircleIndicator(this@WelcomeActivity)
        }

        viewModel.banners.observe(this, Observer {
            bannerAdapter.setDatas(it)
        })

        lifecycleScope.launchWhenResumed {
            viewModel.loadBanner()
        }
    }

    override fun onStart() {
        super.onStart()
        banner.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        banner.stop()
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
