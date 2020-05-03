package com.song.example.study.base.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper

/**
 * @author: Listensong
 * @time 19-10-21 下午8:20
 * @desc com.song.example.study.base.ui.BaseFragment
 */
abstract class BaseViewPagerFragment : BaseFragment() {

    private var isViewCreated = false
    private var isUserVisible = false

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        isViewCreated = false
    }

    protected fun userVisibility(): Boolean {
        return isViewCreated && isUserVisible && userVisibleHint
    }
}