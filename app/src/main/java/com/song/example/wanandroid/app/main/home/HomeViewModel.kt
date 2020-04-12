package com.song.example.wanandroid.app.main.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.song.example.wanandroid.app.main.home.article.ArticleVO
import com.song.example.wanandroid.app.main.home.banner.BannerVO
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

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(fragment, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        fun instance(activity: AppCompatActivity, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(activity, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        @Suppress("UNCHECKED_CAST")
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

    val banners:  LiveData<List<BannerVO>>
            get() = homeRepository.getBanners()

    fun loadBanner(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            homeRepository.requestBanners()
        }
    }

    val articles = homeRepository.getArticles()

    val pagedArticles: LiveData<PagedList<ArticleVO>>
        get() = homeRepository.initArticlesPageList(boundaryCallback)

    fun loadArticle(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            homeRepository.requestArticles()
        }
    }

    private val boundaryCallback: PagedList.BoundaryCallback<ArticleVO> =
            object : PagedList.BoundaryCallback<ArticleVO>() {
                override fun onZeroItemsLoaded() {
                    viewModelScope.launch {
                        homeRepository.requestArticles(0)
                    }
                }

                override fun onItemAtEndLoaded(itemAtEnd: ArticleVO) {
                    viewModelScope.launch {
                        homeRepository.requestArticles(itemAtEnd.curPage + 1)
                    }
                }
            }
}