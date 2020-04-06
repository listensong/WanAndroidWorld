package com.song.example.wanandroid.app.main.home.banner

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.song.example.wanandroid.app.main.home.banner.WAN_HOME_BANNER_TABLE_NAME

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
