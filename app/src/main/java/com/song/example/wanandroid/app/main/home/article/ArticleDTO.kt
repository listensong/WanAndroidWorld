package com.song.example.wanandroid.app.main.home.article

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @package com.song.example.wanandroid.app.main.home.article
 * @fileName ArticleDTO
 * @date on 4/5/2020 3:50 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@JsonClass(generateAdapter = true)
data class ArticleDataDTO(
    @Json(name="data")
    val data: ArticleDataItemDTO? = null,

    @Json(name="errorCode")
    val errorCode: Int? = null,

    @Json(name="errorMsg")
    val errorMsg: String? = null
)

fun ArticleDataDTO?.toVOList(): List<ArticleVO> {
    return this?.run {
        val currentPage = this.data?.curPage ?: 0
        val voList = mutableListOf<ArticleVO>()
        this.data?.datas?.forEach {
            if (it != null) {
                voList.add(it.toVO(currentPage))
            }
        }
        voList
    } ?: emptyList()
}

fun ArticleItemDTO.toVO(currentPage: Int) : ArticleVO {
    return ArticleVO(
            curPage = currentPage,
            apkLink = this.apkLink ?: "",
            audit = this.audit ?:  0,
            author = this.author ?:  "",
            chapterName = this.chapterName ?:  "",
            desc = this.desc ?: "",
            descMd =  this.descMd ?: "",
            envelopePic = this.envelopePic ?:  "",
            fresh = this.fresh ?:  false,
            id = this.id ?:  0,
            link =  this.link ?: "",
            projectLink =  this.projectLink ?: "",
            publishTime =  this.publishTime ?: 0,
            shareDate = this.shareDate ?:  0,
            shareUser =  this.shareUser ?: "",
            superChapterId =  this.superChapterId ?: 0,
            superChapterName =  this.superChapterName ?: "",
            title =  this.title ?: "",
            type =  this.type ?: 0
    )
}

fun ArticleDataDTO?.toPOList(): List<ArticlePO> {
    return this?.run {
        val currentPage = this.data?.curPage ?: 0
        val poList = mutableListOf<ArticlePO>()
        this.data?.datas?.forEach {
            if (it != null) {
                poList.add(it.toPO(currentPage))
            }
        }
        poList
    } ?: emptyList()
}

fun ArticleItemDTO.toPO(currentPage: Int) : ArticlePO {
    return ArticlePO(
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
            title =  this.title ?: "",
            type =  this.type ?: 0,
            userId =  this.userId ?: 0,
            visible =  this.visible ?: 0,
            zan = this.zan ?:  0
    )
}