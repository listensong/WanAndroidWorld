package com.song.example.wanandroid.app.main.home

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName BannerPO
 * @date on 4/1/2020 9:28 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Entity(tableName = WAN_HOME_BANNER_TABLE_NAME)
data class BannerPO(
        @PrimaryKey
        val id: Int,
        var desc: String,
        var imagePath: String,
        var isVisible: Int,
        var order: Int,
        var title: String,
        var type: Int,
        var url: String
)
