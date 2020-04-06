package com.song.example.wanandroid.app.main.home.article

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
class ArticleDataDTO {
    var data: ArticleDTOWrapper? = null
    var errorCode: Int? = 0
    var errorMsg: String? = ""
}

data class ArticleDTOWrapper(
        var curPage: Int? = 0,
        var datas: List<ArticleDTO>? = null,
        var offset: Int = 0,
        var over: Boolean? = false,
        var pageCount: Int? = 0,
        var size: Int? = 0,
        var total: Int? = 0
)

data class ArticleDTO(
        var apkLink: String? = "",
        var audit: Int? = 0,
        var author: String? = "",
        var canEdit: Boolean? = false,
        var chapterId: Int? = 0,
        var chapterName: String? = "",
        var collect: Boolean? = false,
        var courseId: Int? = 0,
        var desc: String? = "",
        var descMd: String? = "",
        var envelopePic: String? = "",
        var fresh: Boolean? = false,
        var id: Int? = 0,
        var link: String? = "",
        var niceDate: String? = "",
        var niceShareDate: String? = "",
        var origin: String? = "",
        var prefix: String? = "",
        var projectLink: String? = "",
        var publishTime: Long? = 0,
        var selfVisible: Int? = 0,
        var shareDate: Long? = 0,
        var shareUser: String? = "",
        var superChapterId: Int? = 0,
        var superChapterName: String? = "",
        var tags: List<Tag>? = emptyList(),
        var title: String? = "",
        var type: Int? = 0,
        var userId: Int? = 0,
        var visible: Int? = 0,
        var zan: Int? = 0
)

fun ArticleDataDTO?.toVOList(): List<ArticleVO> {
    return this?.run {
        val currentPage = this.data?.curPage ?: 0
        this.data?.datas?.map {
            it.toVO(currentPage)
        } ?: emptyList()
    } ?: emptyList()
}

fun ArticleDTO.toVO(currentPage: Int) : ArticleVO {
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
        this.data?.datas?.map {
            it.toPO(currentPage)
        } ?: emptyList()
    } ?: emptyList()
}

fun ArticleDTO.toPO(currentPage: Int) : ArticlePO {
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