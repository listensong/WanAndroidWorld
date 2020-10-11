package com.song.example.study.wanandroid.main.home.article

import androidx.annotation.Keep
import com.song.example.study.extension.new
import com.song.example.study.wanandroid.common.BaseArticlePO
import com.song.example.study.wanandroid.main.home.HomeConst
import com.song.example.study.wanandroid.util.HighLightUtils
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @package com.song.example.study.wanandroid.main.home.article
 * @fileName ArticleDTO
 * @date on 4/5/2020 3:50 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Keep
@JsonClass(generateAdapter = true)
data class ArticleDataDTO(
        @Json(name = "data")
        val data: ArticleDataItemDTO? = null,

        @Json(name = "errorCode")
        val errorCode: Int? = null,

        @Json(name = "errorMsg")
        val errorMsg: String? = null
)

fun ArticleDataDTO?.toVOList(): List<ArticleVO> {
    return this?.run {
        val currentPage = this.data?.curPage ?: 0
        val over = this.data?.over ?: true
        val voList = mutableListOf<ArticleVO>()
        this.data?.datas?.forEach {
            if (it != null) {
                voList.add(it.toVO(currentPage, over))
            }
        }
        voList
    } ?: emptyList()
}

fun ArticleDataDTO?.toHighLightVOList(): List<ArticleVO> {
    return this?.run {
        val currentPage = this.data?.curPage ?: 0
        val over = this.data?.over ?: true
        val voList = mutableListOf<ArticleVO>()
        this.data?.datas?.forEach {
            if (it != null) {
                voList.add(it.toHighLightVO(currentPage, over))
            }
        }
        voList
    } ?: emptyList()
}

fun ArticleItemDTO.toVO(currentPage: Int, lastPage: Boolean): ArticleVO {
    return ArticleVO(
            curPage = currentPage,
            itemType = HomeConst.ITEM_TYPE_ARTICLE,
            apkLink = this.apkLink ?: "",
            audit = this.audit ?: 0,
            author = this.author ?: "",
            chapterName = this.chapterName ?: "",
            desc = this.desc ?: "",
            descMd = this.descMd ?: "",
            envelopePic = this.envelopePic ?: "",
            fresh = this.fresh ?: false,
            id = this.id ?: 0,
            link = this.link ?: "",
            projectLink = this.projectLink ?: "",
            publishTime = this.publishTime ?: 0,
            shareDate = this.shareDate ?: 0,
            shareUser = this.shareUser ?: "",
            superChapterId = this.superChapterId ?: 0,
            superChapterName = this.superChapterName ?: "",
            title = this.title ?: "",
            type = this.type ?: 0,
            placeTop = false,
            over = lastPage
    )
}


fun ArticleItemDTO.toHighLightVO(currentPage: Int, lastPage: Boolean): ArticleVO {
    return ArticleVO(
            curPage = currentPage,
            itemType = HomeConst.ITEM_TYPE_ARTICLE,
            apkLink = this.apkLink ?: "",
            audit = this.audit ?: 0,
            author = this.author ?: "",
            chapterName = this.chapterName ?: "",
            desc = this.desc ?: "",
            descMd = this.descMd ?: "",
            envelopePic = this.envelopePic ?: "",
            fresh = this.fresh ?: false,
            id = this.id ?: 0,
            link = this.link ?: "",
            projectLink = this.projectLink ?: "",
            publishTime = this.publishTime ?: 0,
            shareDate = this.shareDate ?: 0,
            shareUser = this.shareUser ?: "",
            superChapterId = this.superChapterId ?: 0,
            superChapterName = this.superChapterName ?: "",
            title = HighLightUtils.replaceHighLight(this.title),
            type = this.type ?: 0,
            placeTop = false,
            over = lastPage
    )
}

inline fun <reified PO : BaseArticlePO> ArticleDataDTO?.toPOList(
        baseIndex: Int,
        pageNum: Int
): List<PO> {
    return this?.run {
        val currentPage = this.data?.curPage ?: 0
        val over = this.data?.over ?: true
        val poList = mutableListOf<PO>()
        val startIndex = baseIndex + (100 * pageNum)
        var index = 0
        this.data?.datas?.forEach {
            if (it != null) {
                index++
                poList.add(it.toPO(startIndex + index, currentPage, over))
            }
        }
        poList
    } ?: emptyList()
}

inline fun <reified PO : BaseArticlePO> ArticleItemDTO.toPO(
        index: Int,
        currentPage: Int,
        over: Boolean
): PO {
    //return ArticlePO(index, HomeConst.ITEM_TYPE_ARTICLE, currentPage, this)
    return new<PO>().apply {
        unpackDTO(this@toPO, over)
        _index = index
        itemType = HomeConst.ITEM_TYPE_ARTICLE
        curPage = currentPage
    }
}