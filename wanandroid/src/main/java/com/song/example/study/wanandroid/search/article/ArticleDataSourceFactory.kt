package com.song.example.study.wanandroid.search.article

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.song.example.study.wanandroid.main.home.article.ArticleVO
import com.song.example.study.wanandroid.network.WanService
import kotlinx.coroutines.CoroutineScope

/**
 * @package com.song.example.study.wanandroid.search.article
 * @fileName ArticleDataSourceFactory
 * @date on 6/6/2020 10:20 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class ArticleDataSourceFactory(
        private val searchApi: WanService,
        private val workScope: CoroutineScope,
        private var word: String
): DataSource.Factory<Int, ArticleVO>() {

    private val sourceLiveData: MutableLiveData<ArticlePageKeyedDataSource> = MutableLiveData<ArticlePageKeyedDataSource>()

    private fun getSourceDataSourceValue(): ArticlePageKeyedDataSource? {
        return sourceLiveData.value
    }

    fun invalidate() {
        getSourceDataSourceValue()?.invalidate()
    }

    fun updateDataSourceKeyword(keyword: String) {
        if (isKeywordNotChanged(keyword)) {
            return
        }

        getSourceDataSourceValue()?.let {
            it.invalidate()
            it.setKeyword(keyword)
        }
    }

    private fun isKeywordNotChanged(newWord: String): Boolean {
        if (word == newWord) {
            return true
        }
        return false
    }

    override fun create(): DataSource<Int, ArticleVO> {
        val source = ArticlePageKeyedDataSource(searchApi, workScope, word)
        sourceLiveData.postValue(source)
        return source
    }
}