package com.song.example.study.wanandroid.main.home.banner

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @package com.song.example.study.wanandroid.main.home
 * @fileName BannerPO
 * @date on 4/1/2020 9:28 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Keep
@Entity(tableName = WAN_HOME_BANNER_TABLE_NAME)
data class BannerPO(
        @PrimaryKey(autoGenerate = true)
        var _index: Int = 0,
        val id: Int,
        val desc: String,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
)
