package com.song.example.wanandroid.base.job

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.song.example.wanandroid.util.WanLog

/**
 * @author: Listensong
 * @time 19-10-8 下午3:58
 * @desc com.song.example.wanandroid.base.job.BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {

    @CallSuper
    override fun onCleared() {
        WanLog.i("BaseViewModel", "onCleared")
        super.onCleared()
    }
}