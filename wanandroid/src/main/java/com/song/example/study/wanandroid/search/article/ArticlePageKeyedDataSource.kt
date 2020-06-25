package com.song.example.study.wanandroid.search.article

import androidx.paging.PageKeyedDataSource
import com.song.example.study.common.network.retrofit.*
import com.song.example.study.extension.moshi
import com.song.example.study.wanandroid.util.WanLog
import com.song.example.study.wanandroid.main.home.article.ArticleDataDTO
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.main.home.article.toHighLightVOList
import com.song.example.study.wanandroid.network.WanService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @package com.song.example.study.wanandroid.search.article
 * @fileName ArticlePageKeyedDataSource
 * @date on 6/6/2020 10:20 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class ArticlePageKeyedDataSource(
        private val searchApi: WanService,
        private val workScope: CoroutineScope,
        private var word: String
): PageKeyedDataSource<Int, ArticleVO>() {

    fun setKeyword(keyWord: String) {
        word = keyWord
    }

    companion object {
        const val TAG = "ArticlePageKeyedDataSource"
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ArticleVO>
    ) {
        workScope.launch {
            val searchResultList = getSearchResult(0, word)
            callback.onResult(searchResultList, 0, 1)
        }
    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ArticleVO>
    ) {
        workScope.launch {
            val searchResultList = getSearchResult(params.key, word)
            callback.onResult(searchResultList, params.key + 1)
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ArticleVO>
    ) {
    }

    private suspend fun getSearchResult(
            pageNum: Int,
            keyWord: String
    ): List<ArticleVO> {
        return searchApi.queryKeyWord(pageNum, keyWord)
                .suspendAwaitTimeout(10000)
                .onFailure {
                    WanLog.e(TAG, "requestSearchResult $it")
                }
                .onSuccess {
                    val jsonString = it.value.string()
                    val list = jsonString.moshi(ArticleDataDTO::class.java)
                    WanLog.e(TAG, "requestSearchResult $list")
                    HttpResult.Okay(list.toHighLightVOList(), it.response)
                }
                .followDo {
                    if (it is HttpResult.Okay) {
                        it.value
                    } else {
                        emptyList()
                    }
                }

    }
}