package com.song.example.study.wanandroid.main.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.song.example.study.wanandroid.main.home.banner.BannerVO
import com.song.example.study.base.job.BaseViewModel
import com.song.example.study.common.network.RequestStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @package com.song.example.study.wanandroid.main.home
 * @fileName HomeViewModel
 * @date on 3/29/2020 3:40 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class HomeViewModel(
        private val repository: HomeRepository
) : BaseViewModel() {

    companion object {
        const val TAG = "HomeViewModel"

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(fragment, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        fun instance(activity: AppCompatActivity, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(activity, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        fun instance(activity: FragmentActivity, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(activity, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        @Suppress("UNCHECKED_CAST")
        class HomeViewModelFactory(
                private val repository: HomeRepository
        ): ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                    return HomeViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    val banners:  LiveData<List<BannerVO>>
            get() = repository.getBanners()

    fun loadBanner(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            repository.requestBanners()
        }
    }

    val articles = repository.getArticles()

    fun loadArticle(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            repository.requestTopArticles()
            repository.requestArticles(0)
        }
    }

    fun loadNextPage(workScope: CoroutineScope = viewModelScope,
                     currentPage: Int) {
        if (currentPage < 0) {
            return
        }
        workScope.launch {
            repository.requestArticles(currentPage + 1)
        }
    }

    val requestState: LiveData<RequestStatus> = repository.requestStatus
}