package com.song.example.study.wanandroid.search.word

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotWordDTO(

        @Json(name = "data")
        val data: List<HotWordDataDTO?>? = null,

        @Json(name = "errorCode")
        val errorCode: Int? = null,

        @Json(name = "errorMsg")
        val errorMsg: String? = null
)