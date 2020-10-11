package com.song.example.study.wanandroid.search.word

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class HotWordDataDTO(

        @Json(name = "visible")
        val visible: Int? = null,

        @Json(name = "link")
        val link: String? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "order")
        val order: Int? = null
)