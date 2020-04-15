package com.song.example.wanandroid.app.main.home

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.song.example.wanandroid.app.main.home.article.*
import com.song.example.wanandroid.app.main.home.banner.*
import com.song.example.wanandroid.app.network.WanService
import com.song.example.wanandroid.base.job.PageBaseRepository
import com.song.example.wanandroid.common.network.retrofit.*
import com.song.example.wanandroid.extend.moshi
import com.song.example.wanandroid.util.WanLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeRepository
 * @date on 3/29/2020 3:41 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class HomeRepository(
        private val wanApiService: WanService,
        private val bannerDataSource: BannerDAO,
        private val articleDataSource: ArticleDAO
) : PageBaseRepository() {

    companion object {
        const val TAG = "HomeRepository"
        const val HOME_ARTICLE_PAGE_SIZE = 20
        const val HOME_ARTICLE_INIT_LOAD_SIZE = 20
    }

    fun getBanners():  LiveData<List<BannerVO>> = bannerDataSource.getBanners()

    suspend fun requestBanners(): List<BannerVO> {
        return wanApiService
                .getBannerList()
                .awaitWithTimeout(10000)
                .onFailure {
                    WanLog.e(TAG, "onFailure $it")
                }
                .onSuccess {
                    val jsonString = it.value.string()
                    val list = jsonString.moshi(BannerDataDTO::class.java)
                    saveBanners(list.toPOList())
                    HttpResult.Okay(list.toVOList(), it.response)
                }
                .doFollow {
                    if (it is HttpResult.Okay) {
                        it.value
                    } else {
                        emptyList()
                    }
                }
    }

    private suspend fun saveBanners(banners: List<BannerPO>) {
        if (banners.isEmpty()) {
            return
        }
        withContext(Dispatchers.IO) {
            bannerDataSource.insertAll(banners)
        }
    }


    /********************************** Article **************************************/
//    fun initArticlesPageList(
//            pagedBoundaryCallback:  PagedList.BoundaryCallback<ArticleVO>
//    ): LiveData<PagedList<ArticleVO>> {
//        return queryPagedList(
//                dataSourceFactory = articleDataSource.getArticleVOPage(),
//                pageSize = HOME_ARTICLE_PAGE_SIZE,
//                initialLoadSize = HOME_ARTICLE_INIT_LOAD_SIZE,
//                boundaryCallback = pagedBoundaryCallback
//        )
//    }

    fun getArticles():  LiveData<List<ArticleVO>> = articleDataSource.getArticles()

    suspend fun requestArticles(pageNum: Int = 0): List<ArticleVO> {
        return wanApiService
                .getArticleList(pageNum)
                .awaitWithTimeout(10000)
                .onFailure {
                    WanLog.e(TAG, "onFailure $it")
                }
                .onSuccess {
                    val jsonString = it.value.string()
                    val list = jsonString.moshi(ArticleDataDTO::class.java)
                    saveArticles(list.toPOList())
                    HttpResult.Okay(list.toVOList(), it.response)
                }
                .doFollow {
                    if (it is HttpResult.Okay) {
                        it.value
                    } else {
                        emptyList()
                    }
                }
    }

    private suspend fun saveArticles(articles: List<ArticlePO>) {
        if (articles.isEmpty()) {
            return
        }
        withContext(Dispatchers.IO) {
            articleDataSource.insertAll(articles)
        }
    }
}