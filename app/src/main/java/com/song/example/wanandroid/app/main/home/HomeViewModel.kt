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
import com.song.example.wanandroid.common.network.RequestStatus
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
        private val repository: HomeRepository
) : BaseViewModel() {

    companion object {
        const val TAG = "HomeViewModel"

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(fragment, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        fun instance(activity: AppCompatActivity, repo: HomeRepository): HomeViewModel =
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

//    @Deprecated("use articles instead")
//    val pagedArticles: LiveData<PagedList<ArticleVO>>
//        get() = repository.initArticlesPageList(boundaryCallback)

    fun loadArticle(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            repository.requestArticles()
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

//    private val boundaryCallback: PagedList.BoundaryCallback<ArticleVO> =
//            object : PagedList.BoundaryCallback<ArticleVO>() {
//                override fun onZeroItemsLoaded() {
//                    viewModelScope.launch {
//                        repository.requestArticles(0)
//                    }
//                }
//
//                override fun onItemAtEndLoaded(itemAtEnd: ArticleVO) {
//                    viewModelScope.launch {
//                        repository.requestArticles(itemAtEnd.curPage + 1)
//                    }
//                }
//            }

    val requestState: LiveData<RequestStatus> = repository.requestStatus
}