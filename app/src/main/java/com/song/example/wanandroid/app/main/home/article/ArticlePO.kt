package com.song.example.wanandroid.app.main.home.article

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.song.example.wanandroid.app.main.home.banner.WAN_HOME_BANNER_TABLE_NAME

/**
 * @package com.song.example.wanandroid.app.main.home.article
 * @fileName ArticlePO
 * @date on 4/5/2020 3:50 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Entity(tableName = WAN_HOME_ARTICLE_TABLE_NAME)
@TypeConverters(ArticleTagConverter::class)
data class ArticlePO(
        @PrimaryKey(autoGenerate = true)
        var _index: Int = 0,
        val id: Int,
        val curPage: Int,
        val itemType: Int,
        val apkLink: String? = "",
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)


fun createMaskArticlePO(currentPage: Int,
                        itemType: Int,
                        title: String,
                        link: String): ArticlePO {
        val maskId = -(itemType * 10 + currentPage)
        val maskTitle = "MASK_TITLE_${title}_${link}_${itemType}"
        val maskLink = "MASK_LINK_${title}_${link}_${itemType}"
        return ArticlePO(
                itemType = itemType,
                curPage = currentPage,
                apkLink = "",
                audit =  0,
                author = "",
                canEdit = false,
                chapterId = 0,
                chapterName = "",
                collect = false,
                courseId = 0,
                desc = "",
                descMd = "",
                envelopePic =  "",
                fresh = false,
                id = maskId,
                link = maskLink,
                niceDate = "",
                niceShareDate = "",
                origin = "",
                prefix = "",
                projectLink = "",
                publishTime = 0,
                selfVisible = 0,
                shareDate = 0,
                shareUser =  "",
                superChapterId = 0,
                superChapterName = "",
                tags = emptyList(),
                title = maskTitle,
                type = 0,
                userId = 0,
                visible = 0,
                zan =0
        )
}