package com.song.example.study.wanandroid.main.home.article

import com.squareup.moshi.Json

data class ArticleDataItemDTO (

        @Json(name="over")
        val over: Boolean? = null,

        @Json(name="pageCount")
        val pageCount: Int? = null,

        @Json(name="total")
        val total: Int? = null,

        @Json(name="curPage")
        val curPage: Int? = null,

        @Json(name="offset")
        val offset: Int? = null,

        @Json(name="size")
        val size: Int? = null,

        @Json(name="datas")
        val datas: List<ArticleItemDTO?>? = null
)