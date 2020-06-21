package com.song.example.study.wanandroid.search

import android.os.Bundle
import com.song.example.study.base.ui.BaseActivity
import com.song.example.study.wanandroid.R

class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wan_activity_search)
        if (savedInstanceState == null) {
            pushFragment(SearchFragment.newInstance())
        }
    }
}