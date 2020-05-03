package com.song.example.study.wanandroid.main.home.article

import com.squareup.moshi.Json

/**
 * @package com.song.example.study.wanandroid.main.home.article
 * @fileName Tag
 * @date on 4/18/2020 6:28 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */

data class Tag(
        @Json(name="name")
        val name: String? = null,

        @Json(name="url")
        val url: String? = null
)