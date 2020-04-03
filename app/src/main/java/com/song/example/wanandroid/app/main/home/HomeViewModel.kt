package com.song.example.wanandroid.app.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.song.example.wanandroid.base.job.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeViewModel
 * @date on 3/29/2020 3:40 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class HomeViewModel(
        private val homeRepository: HomeRepository
) : BaseViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    val banners = homeRepository.getBanners()

    fun loadBanner(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            homeRepository.requestBanners()
        }
    }
}