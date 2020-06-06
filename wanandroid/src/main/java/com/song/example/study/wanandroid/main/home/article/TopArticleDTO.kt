package com.song.example.study.wanandroid.main.home.article

import com.song.example.study.wanandroid.main.home.HomeConst
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @package com.song.example.study.wanandroid.main.home.article
 * @fileName TopArticleDTO
 * @date on 4/18/2020 9:18 PM
 * @author Listensong
 * @desc
 * @email No
 */

@JsonClass(generateAdapter = true)
data class TopArticleDTO(

        @Json(name="data")
        val data: List<ArticleItemDTO?>? = null,

        @Json(name="errorCode")
        val errorCode: Int? = null,

        @Json(name="errorMsg")
        val errorMsg: String? = null
)

fun TopArticleDTO?.toPlaceTopPOList(
        baseIndex: Int,
        currentPage: Int
): List<ArticlePO> {
    return this?.run {
        val poList = mutableListOf<ArticlePO>()
        this.data?.forEachIndexed { index, articleItemDTO ->
            if (articleItemDTO != null) {
                poList.add(
                        articleItemDTO.toPlaceTopPO(
                                baseIndex + index,
                                currentPage,
                                HomeConst.ITEM_TYPE_TOP_ARTICLE)
                )
            }
        }
        poList
    } ?: emptyList()
}

fun ArticleItemDTO.toPlaceTopPO(index: Int,
                                currentPage: Int,
                                itemType: Int) : ArticlePO {
    return ArticlePO(index, itemType, currentPage, false, this)
}