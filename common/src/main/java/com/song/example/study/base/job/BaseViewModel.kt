package com.song.example.study.base.job

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

/**
 * @author: Listensong
 * @time 19-10-8 下午3:58
 * @desc com.song.example.study.base.job.BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {

    @CallSuper
    override fun onCleared() {
        Log.i("BaseViewModel", "onCleared")
        super.onCleared()
    }
}