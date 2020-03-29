package com.song.example.wanandroid.app.main.home

import android.util.Log
import com.song.example.wanandroid.app.network.WanApiCallImpl
import com.song.example.wanandroid.app.network.WanService
import com.song.example.wanandroid.base.job.BaseRepository
import com.song.example.wanandroid.common.network.retrofit.*
import com.song.example.wanandroid.extend.moshi

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeRepository
 * @date on 3/29/2020 3:41 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class HomeRepository(
        private val wanApiCallImpl: WanApiCallImpl
) : BaseRepository() {

    companion object {
        const val TAG = "HomeRepository"
    }

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
}