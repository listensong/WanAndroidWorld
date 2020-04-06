package com.song.example.wanandroid.app.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import com.song.example.wanandroid.app.main.home.article.*
import com.song.example.wanandroid.app.main.home.banner.*
import com.song.example.wanandroid.app.main.home.banner.toPOList
import com.song.example.wanandroid.app.main.home.banner.toVOList
import com.song.example.wanandroid.app.network.WanApiCallImpl
import com.song.example.wanandroid.app.network.WanService
import com.song.example.wanandroid.base.job.BaseRepository
import com.song.example.wanandroid.common.network.retrofit.*
import com.song.example.wanandroid.extend.moshi
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
        private val wanApiCallImpl: WanApiCallImpl,
        private val bannerDataSource: BannerDAO,
        private val articleDataSource: ArticleDAO
) : BaseRepository() {

    companion object {
        const val TAG = "HomeRepository"
    }

    fun getBanners():  LiveData<List<BannerVO>> = bannerDataSource.getBanners()

    suspend fun requestBanners(): List<BannerVO> {
        return wanApiCallImpl.callWanApi(WanService::class.java)
                .getBannerList()
                .awaitWithTimeout(10000)
                .onFailure {
                    Log.e(TAG, "onFailure $it")
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

    fun getArticles():  LiveData<List<ArticleVO>> = articleDataSource.getArticles()

    suspend fun requestArticles(): List<ArticleVO> {
        return wanApiCallImpl.callWanApi(WanService::class.java)
                .getArticleList()
                .awaitWithTimeout(10000)
                .onFailure {
                    Log.e(TAG, "onFailure $it")
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