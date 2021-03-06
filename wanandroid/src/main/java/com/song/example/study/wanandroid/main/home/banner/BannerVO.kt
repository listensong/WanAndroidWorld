package com.song.example.study.wanandroid.main.home.banner

import androidx.annotation.Keep

/**
 * @package com.song.example.study.wanandroid.main.home
 * @fileName BannerVO
 * @date on 3/29/2020 3:38 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Keep
data class BannerVO(
        val title: String,
        val type: Int,
        val imagePath: String,
        val url: String
)