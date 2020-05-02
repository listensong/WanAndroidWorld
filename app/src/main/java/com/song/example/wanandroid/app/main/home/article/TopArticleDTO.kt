package com.song.example.wanandroid.app.main.home.article

import com.song.example.wanandroid.app.main.home.HomeConst
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @package com.song.example.wanandroid.app.main.home.article
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
    return ArticlePO(
            _index = index,
            itemType = itemType,
            curPage = currentPage,
            apkLink = this.apkLink ?: "",
            audit = this.audit ?:  0,
            author = this.author ?:  "",
            canEdit = this.canEdit ?:  false,
            chapterId =  this.chapterId ?: 0,
            chapterName = this.chapterName ?:  "",
            collect = this.collect ?:  false,
            courseId = this.courseId ?:  0,
            desc = this.desc ?: "",
            descMd =  this.descMd ?: "",
            envelopePic = this.envelopePic ?:  "",
            fresh = this.fresh ?:  false,
            id = this.id ?:  0,
            link =  this.link ?: "",
            niceDate =  this.niceDate ?: "",
            niceShareDate =  this.niceShareDate ?: "",
            origin = this.origin ?: "",
            prefix =  this.prefix ?: "",
            projectLink =  this.projectLink ?: "",
            publishTime =  this.publishTime ?: 0,
            selfVisible = this.selfVisible ?:  0,
            shareDate = this.shareDate ?:  0,
            shareUser =  this.shareUser ?: "",
            superChapterId =  this.superChapterId ?: 0,
            superChapterName =  this.superChapterName ?: "",
            tags =  this.tags ?: emptyList(),
            title = this.title ?: "",
            type =  this.type ?: 0,
            userId =  this.userId ?: 0,
            visible =  this.visible ?: 0,
            zan = this.zan ?:  0,
            placeTop = true
    )
}