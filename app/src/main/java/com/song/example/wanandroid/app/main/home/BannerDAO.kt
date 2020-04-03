package com.song.example.wanandroid.app.main.home

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName BannerDAO
 * @date on 4/1/2020 9:28 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
const val WAN_HOME_BANNER_TABLE_NAME = "wan_world_banner"
@Dao
interface BannerDAO {

    @Transaction
    fun updateBanners(banners: List<BannerPO>) {
        clear()
        insertAll(banners)
    }

    @Query("SELECT * FROM $WAN_HOME_BANNER_TABLE_NAME")
    fun getBanners(): LiveData<List<BannerVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(banners: List<BannerPO>)

    @Query("DELETE from $WAN_HOME_BANNER_TABLE_NAME")
    fun clear()
}