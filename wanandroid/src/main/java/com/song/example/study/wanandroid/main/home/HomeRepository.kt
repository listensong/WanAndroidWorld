package com.song.example.study.wanandroid.main.home

import androidx.lifecycle.LiveData
import com.song.example.study.wanandroid.main.home.article.*
import com.song.example.study.wanandroid.main.home.banner.*
import com.song.example.study.wanandroid.network.WanService
import com.song.example.study.base.job.BaseRepository
import com.song.example.study.common.network.RequestStatus
import com.song.example.study.common.network.retrofit.*
import com.song.example.study.extension.moshi
import com.song.example.study.util.WanLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @package com.song.example.study.wanandroid.main.home
 * @fileName HomeRepository
 * @date on 3/29/2020 3:41 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class HomeRepository(
        private val wanApiService: WanService,
        private val bannerDAO: BannerDAO,
        private val articleDAO: ArticleDAO
) : BaseRepository() {

    companion object {
        const val TAG = "HomeRepository"
    }

    fun getBanners():  LiveData<List<BannerVO>> = bannerDAO.getBanners()

    suspend fun requestBanners() {
        wanApiService
                .getBannerList()
                .suspendAwaitTimeout(10000)
                .onFailure {
                    WanLog.e(TAG, "requestBanners onFailure $it")
                    requestStatus.value = RequestStatus.Complete(it.error)
                }
                .onSuccess {
                    WanLog.d(TAG, "requestBanners onSuccess ")
                    requestStatus.value = RequestStatus.Complete()
                    val jsonString = it.value.string()
                    val list = jsonString.moshi(BannerDataDTO::class.java)
                    saveBanners(list.toPOList())
                    HttpResult.Okay(emptyList<BannerVO>(), it.response)
                }
    }

    private suspend fun saveBanners(banners: List<BannerPO>) {
        if (banners.isEmpty()) {
            return
        }
        withContext(Dispatchers.IO) {
            bannerDAO.clearAndInsert(banners)
        }
    }

    fun getArticles():  LiveData<List<ArticleVO>> = articleDAO.getArticles()

    suspend fun requestTopArticles() {
        wanApiService
                .getTopArticles()
                .suspendAwaitTimeout(10000)
                .onFailure {
                    WanLog.e(TAG, "requestTopArticles onFailure $it")
                    requestStatus.value = RequestStatus.Complete(it.error)
                }
                .onSuccess {
                    requestStatus.value = RequestStatus.Complete()
                    val jsonString = it.value.string()
                    val list = jsonString.moshi(TopArticleDTO::class.java)
                    WanLog.d(TAG, "requestTopArticles list size:${list?.data?.size}")
                    insertTopArticles(
                            list.toPlaceTopPOList(HomeConst.BASE_INDEX_TOP_ARTICLE, 0)
                    )
                    HttpResult.Okay(emptyList<ArticleVO>(), it.response)
                }
    }


    private suspend fun insertTopArticles(articles: List<ArticlePO>) {
        if (articles.isEmpty()) {
            return
        }
        withContext(Dispatchers.IO) {
            articleDAO.clearRangeAndInsert(
                    HomeConst.BASE_INDEX_TOP_ARTICLE,
                    HomeConst.BASE_INDEX_ARTICLE - 1,
                    prependBannerPlaceholderItem(articles))
        }
    }

    suspend fun requestArticles(pageNum: Int = 0) {
        wanApiService
                .getArticleList(pageNum)
                .suspendAwaitTimeout(10000)
                .onFailure {
                    WanLog.e(TAG, "requestArticles onFailure $it")
                    requestStatus.value = RequestStatus.Complete(it.error)
                }
                .onSuccess {
                    WanLog.d(TAG, "requestArticles onSuccess ")
                    requestStatus.value = RequestStatus.Complete()
                    val jsonString = it.value.string()
                    val list = jsonString.moshi(ArticleDataDTO::class.java)
                    saveArticles(pageNum, list.toPOList(HomeConst.BASE_INDEX_ARTICLE, pageNum))
                    HttpResult.Okay(emptyList<ArticleVO>(), it.response)
                }
    }

    private suspend fun saveArticles(pageNum: Int = 0,
                                     articles: List<ArticlePO>) {
        if (articles.isEmpty()) {
            return
        }
        withContext(Dispatchers.IO) {
            if (pageNum <= 0) {
                articleDAO.clearAboveAndInsert(
                        HomeConst.BASE_INDEX_ARTICLE, prependBannerPlaceholderItem(articles))
            } else {
                articleDAO.insert(articles)
            }
        }
    }

    private fun prependBannerPlaceholderItem(articles: List<ArticlePO>): List<ArticlePO>{
        val newList = mutableListOf(
                createMaskArticlePO(
                        HomeConst.BASE_INDEX_BANNER, 0,
                        HomeConst.ITEM_TYPE_BANNER, "BANNER_TITLE", "BANNER_LINK")
        )
        newList.addAll(articles)
        return newList
    }
}