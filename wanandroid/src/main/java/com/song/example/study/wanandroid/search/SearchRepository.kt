package com.song.example.study.wanandroid.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.song.example.study.base.job.PageBaseRepository
import com.song.example.study.base.job.toPagedListLiveData
import com.song.example.study.common.network.retrofit.HttpResult
import com.song.example.study.common.network.retrofit.onFailure
import com.song.example.study.common.network.retrofit.onSuccess
import com.song.example.study.common.network.retrofit.suspendAwaitTimeout
import com.song.example.study.extension.moshi
import com.song.example.study.util.WanLog
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.network.WanService
import com.song.example.study.wanandroid.search.article.ArticleDataSourceFactory
import com.song.example.study.wanandroid.search.word.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

/**
 * @package com.song.example.study.wanandroid.search
 * @fileName SearchRepository
 * @date on 6/7/2020 5:00 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class SearchRepository(
        private val service: WanService,
        private val hotWordDAO: HotWordDAO
) : PageBaseRepository() {

    companion object {
        const val TAG = "SearchRepository"
        const val SEARCH_WORD_ARTICLE_PAGE_SIZE = 20
        const val SEARCH_WORD_ARTICLE_INIT_LOAD_SIZE = 20
    }

    fun getHotWord(): LiveData<List<HotWordVO>> {
        return hotWordDAO.getHotWord()
    }

    suspend fun requestHotWord() {
        service.getSearchHotKey()
                .suspendAwaitTimeout(10000)
                .onFailure {
                    WanLog.e("requestHotWord $it" )
                }
                .onSuccess {
                    val poList = parseHotWordResponseBody(it)
                    saveHotWordToDB(poList)
                    HttpResult.Okay(emptyList<HotWordVO>(), it.response)
                }
    }

    private fun parseHotWordResponseBody(
            result: HttpResult.Okay<ResponseBody>
    ): List<HotWordPO> {
        val json = result.value.string()
        val dto = json.moshi(HotWordDTO::class.java)
        return dto.toPOList()
    }

    private suspend fun saveHotWordToDB(poList: List<HotWordPO>) {
        if (poList.isEmpty()) {
            return
        }

        withContext(Dispatchers.IO) {
            hotWordDAO.clearAndInsert(poList)
        }
    }

    private var articleDataSourceFactory: ArticleDataSourceFactory? = null
    fun setDataSourceFactory(dataSourceFactory: ArticleDataSourceFactory) {
        articleDataSourceFactory = dataSourceFactory
    }

    fun refreshDataSourceAndUpdateKeyword(keyword: String?) {
        if (keyword == null || keyword.isEmpty()) {
            return
        }
        this.articleDataSourceFactory?.updateDataSourceKeyword(keyword)
    }

    fun refreshDataSource() {
        this.articleDataSourceFactory?.invalidate()
    }

    fun searchResultPagedList(
            keyword: String?
    ): LiveData<PagedList<ArticleVO>> {
        if (keyword == null || keyword.isEmpty()) {
            return MutableLiveData()
        }
        this.articleDataSourceFactory?.updateDataSourceKeyword(keyword)
        return this.articleDataSourceFactory
                ?.toPagedListLiveData(SEARCH_WORD_ARTICLE_PAGE_SIZE)
                ?: MutableLiveData()
    }
}