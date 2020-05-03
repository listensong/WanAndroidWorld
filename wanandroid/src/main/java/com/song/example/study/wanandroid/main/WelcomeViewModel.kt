package com.song.example.study.wanandroid.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.song.example.study.base.job.BaseViewModel

/**
 * @package com.song.example.study.wanandroid.main.home
 * @fileName WelcomeViewModel
 * @date on 5/1/2020 12:11 PM
 * @author Listensong
 * @desc
 * @email No
 */
class WelcomeViewModel : BaseViewModel() {

    companion object {
        const val TAG = "WelcomeViewModel"

        fun instance(activity: AppCompatActivity): WelcomeViewModel =
                ViewModelProvider(activity,WelcomeViewModelFactory()).get(WelcomeViewModel::class.java)

        fun instance(activity: FragmentActivity): WelcomeViewModel =
                ViewModelProvider(activity, WelcomeViewModelFactory()).get(WelcomeViewModel::class.java)

        @Suppress("UNCHECKED_CAST")
        class WelcomeViewModelFactory: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
                    return WelcomeViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private var isDrawerOpen = MutableLiveData(false)
    fun isDrawerOpen(): LiveData<Boolean> {
        return isDrawerOpen
    }

    fun setDrawerOpenState(open: Boolean) {
        isDrawerOpen.value = open
    }
}