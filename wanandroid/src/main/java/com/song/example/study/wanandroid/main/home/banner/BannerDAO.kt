package com.song.example.study.wanandroid.main.home.banner

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @package com.song.example.study.wanandroid.main.home
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
    fun clearAndInsert(banners: List<BannerPO>) {
        clear()
        insert(banners)
    }

    @Query("SELECT * FROM $WAN_HOME_BANNER_TABLE_NAME")
    fun getBanners(): Flow<List<BannerVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(banners: List<BannerPO>)

    @Query("DELETE from $WAN_HOME_BANNER_TABLE_NAME")
    fun clear()
}