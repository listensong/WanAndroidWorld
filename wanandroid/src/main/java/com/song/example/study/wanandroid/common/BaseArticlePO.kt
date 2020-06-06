package com.song.example.study.wanandroid.common

import androidx.room.PrimaryKey
import com.song.example.study.wanandroid.main.home.article.ArticleItemDTO
import com.song.example.study.wanandroid.main.home.article.Tag

/**
 * @package com.song.example.study.wanandroid.common
 * @fileName BaseArticlePO
 * @date on 6/1/2020 10:37 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
open class BaseArticlePO {
    @PrimaryKey
    var _index: Int = 0
    var id: Int = 0
    var curPage: Int = 0
    var itemType: Int = 0
    var apkLink: String = ""
    var audit: Int = 0
    var author: String = ""
    var canEdit: Boolean = false
    var chapterId: Int = 0
    var chapterName: String = ""
    var collect: Boolean = false
    var courseId: Int = 0
    var desc: String = ""
    var descMd: String = ""
    var envelopePic: String = ""
    var fresh: Boolean = false
    var link: String = ""
    var niceDate: String = ""
    var niceShareDate: String = ""
    var origin: String = ""
    var prefix: String = ""
    var projectLink: String = ""
    var publishTime: Long = 0L
    var selfVisible: Int = 0
    var shareDate: Long = 0L
    var shareUser: String = ""
    var superChapterId: Int = 0
    var superChapterName: String = ""
    var tags: List<Tag> = emptyList()
    var title: String = ""
    var type: Int = 0
    var userId: Int = 0
    var visible: Int = 0
    var zan: Int = 0
    var placeTop: Boolean = false
    var over: Boolean = true

    fun unpackDTO(dto: ArticleItemDTO, over: Boolean) {
        this.apkLink = dto.apkLink ?: ""
        this.audit = dto.audit ?:  0
        this.author = dto.author ?:  ""
        this.canEdit = dto.canEdit ?:  false
        this.chapterId =  dto.chapterId ?: 0
        this.chapterName = dto.chapterName ?:  ""
        this.collect = dto.collect ?:  false
        this.courseId = dto.courseId ?:  0
        this.desc = dto.desc ?: ""
        this.descMd = dto.descMd ?: ""
        this.envelopePic = dto.envelopePic ?:  ""
        this.fresh = dto.fresh ?:  false
        this.id = dto.id ?:  0
        this.link = dto.link ?: ""
        this.niceDate = dto.niceDate ?: ""
        this.niceShareDate = dto.niceShareDate ?: ""
        this.origin = dto.origin ?: ""
        this.prefix = dto.prefix ?: ""
        this.projectLink = dto.projectLink ?: ""
        this.publishTime = dto.publishTime ?: 0
        this.selfVisible = dto.selfVisible ?:  0
        this.shareDate = dto.shareDate ?:  0
        this.shareUser = dto.shareUser ?: ""
        this.superChapterId = dto.superChapterId ?: 0
        this.superChapterName = dto.superChapterName ?: ""
        this.tags = dto.tags ?: emptyList()
        this.title = dto.title ?: ""
        this.type = dto.type ?: 0
        this.userId = dto.userId ?: 0
        this.visible = dto.visible ?: 0
        this.zan = dto.zan ?:  0
        this.placeTop = false
        this.over = over
    }
}