package com.song.example.study.wanandroid.search

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.song.example.study.base.job.PageBaseViewModel
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.network.WanService
import com.song.example.study.wanandroid.search.article.ArticleDataSourceFactory
import com.song.example.study.wanandroid.search.word.HotWordVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @package com.song.example.study.wanandroid.search
 * @fileName SearchViewModel
 * @date on 6/7/2020 5:00 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class SearchViewModel(
        private val service: WanService,
        private val repository: SearchRepository
) : PageBaseViewModel() {
    companion object {
        //const val TAG = "SearchViewModel"

        fun instance(fragment: Fragment,
                     service: WanService,
                     repo: SearchRepository
        ): SearchViewModel =
                ViewModelProvider(fragment,
                        SearchViewModelFactory(service, repo)).get(SearchViewModel::class.java)

        fun instance(activity: AppCompatActivity,
                     service: WanService,
                     repo: SearchRepository
        ): SearchViewModel =
                ViewModelProvider(activity,
                        SearchViewModelFactory(service, repo)).get(SearchViewModel::class.java)

        fun instance(activity: FragmentActivity,
                     service: WanService,
                     repo: SearchRepository
        ): SearchViewModel =
                ViewModelProvider(activity,
                        SearchViewModelFactory(service, repo)).get(SearchViewModel::class.java)

        @Suppress("UNCHECKED_CAST")
        class SearchViewModelFactory(
                private val service: WanService,
                private val repository: SearchRepository
        ): ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                    return SearchViewModel(service, repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    init {
        initRepo()
    }

    private fun initRepo() {
        repository.setDataSourceFactory(
                ArticleDataSourceFactory(service, viewModelScope, "")
        )
    }

    fun getHotWord(): LiveData<List<HotWordVO>> {
        return repository.getHotWord()
    }

    fun refreshHotWord(workScope: CoroutineScope = viewModelScope) {
        workScope.launch {
            repository.requestHotWord()
        }
    }

    fun refreshSearchKeyword(keyword: String?) {
        repository.refreshDataSourceAndUpdateKeyword(keyword)
    }

    fun refreshSearchResult() {
        repository.refreshDataSource()
    }

    fun livePagedList(keyword: String): LiveData<PagedList<ArticleVO>> {
        return repository.searchResultPagedList(keyword)
    }
}