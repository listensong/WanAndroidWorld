package com.song.example.study.wanandroid.main.home.article

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.TypeConverters
import com.song.example.study.wanandroid.common.BaseArticlePO

/**
 * @package com.song.example.study.wanandroid.main.home.article
 * @fileName ArticlePO
 * @date on 4/5/2020 3:50 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Keep
@Entity(tableName = WAN_HOME_ARTICLE_TABLE_NAME)
@TypeConverters(ArticleTagConverter::class)
class ArticlePO() : BaseArticlePO() {
    constructor(
            index: Int,
            itemType: Int,
            currentPage: Int,
            over: Boolean,
            dto: ArticleItemDTO
    ) : this() {
        unpackDTO(dto, over)
        this._index = index
        this.itemType = itemType
        this.curPage = currentPage
    }

    constructor(
            index: Int,
            id: Int,
            itemType: Int,
            link: String,
            title: String
    ) : this() {
        //unpackDTO(dto)
        this._index = index
        this.id = id
        this.itemType = itemType
        this.link = link
        this.title = title
    }
}

fun createMaskArticlePO(index: Int,
                        currentPage: Int,
                        itemType: Int,
                        title: String,
                        link: String): ArticlePO {
    val maskId = -(itemType * 10 + currentPage) + index
    val maskTitle = "MASK_TITLE_${title}_${link}_${itemType}"
    val maskLink = "MASK_LINK_${title}_${link}_${itemType}"
    return ArticlePO(index, maskId, itemType, maskLink, maskTitle)
}