package com.song.example.wanandroid.app.main.home.article

import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * @package com.song.example.wanandroid.app.main.home.article
 * @fileName ArticleVO
 * @date on 4/5/2020 3:50 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
data class ArticleVO(
        var id: Int,
        val curPage: Int,
        var apkLink: String = "",
        var audit: Int,
        var author: String,
        var chapterName: String,
        var desc: String,
        var descMd: String,
        var envelopePic: String,
        var fresh: Boolean,
        var link: String,
        var projectLink: String,
        var publishTime: Long,
        var shareDate: Long,
        var shareUser: String,
        var superChapterId: Int,
        var superChapterName: String,
        var title: String,
        var type: Int
)