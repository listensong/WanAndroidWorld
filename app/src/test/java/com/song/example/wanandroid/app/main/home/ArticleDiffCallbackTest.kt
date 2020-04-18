package com.song.example.wanandroid.app.main.home

import com.song.example.wanandroid.app.main.home.article.ArticleVO
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home
 * @fileName ArticleDiffCallbackTest
 * @date on 4/15/2020 10:45 PM
 * @desc TODO
 * @email No
 */
class ArticleDiffCallbackTest {

    private lateinit var articleDiffCallback: ArticleDiffCallback

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun getMaskVO(maskId: Int, maskTitle: String, maskLink: String): ArticleVO {
        return ArticleVO(
                id = maskId,
                curPage = 0,
                itemType = 0,
                apkLink = "",
                audit = 0,
                author = "",
                chapterName = "",
                desc = "",
                descMd = "",
                envelopePic = "",
                fresh = false,
                link = maskLink,
                projectLink = "",
                publishTime = 0L,
                shareDate = 0L,
                shareUser = "",
                superChapterId = 0,
                superChapterName = "",
                title = maskTitle,
                type = 0
        )
    }

    @Test
    fun areItemsTheSame_whenOldEmptyListAndNewEmptyListThenReturnFalse() {
        articleDiffCallback = ArticleDiffCallback(emptyList(), emptyList())
        assertFalse(articleDiffCallback.areItemsTheSame(0, 0))
    }

    @Test
    fun areItemsTheSame_whenOldListIdIsEqualsNewListIdThenReturnTrue() {
        articleDiffCallback = ArticleDiffCallback(
                listOf(getMaskVO(1, "title", "maskLink")),
                listOf(getMaskVO(1, "titleNew", "maskLinkNew"))
        )
        assertTrue(articleDiffCallback.areItemsTheSame(0, 0))
    }

    @Test
    fun areItemsTheSame_whenOldListIdIsNotEqualsNewListIdThenReturnFalse() {
        articleDiffCallback = ArticleDiffCallback(
                listOf(getMaskVO(1, "title", "maskLink")),
                listOf(getMaskVO(2, "titleNew", "maskLinkNew"))
        )
        assertFalse(articleDiffCallback.areItemsTheSame(0, 0))
    }

    @Test
    fun getOldListSize_whenOldEmptyListAndNewEmptyListThenReturn0() {
        articleDiffCallback = ArticleDiffCallback(emptyList(), emptyList())
        assertEquals(0, articleDiffCallback.oldListSize)
    }

    @Test
    fun getNewListSize_whenOldEmptyListAndNewEmptyListThenReturn0() {
        articleDiffCallback = ArticleDiffCallback(emptyList(), emptyList())
        assertEquals(0, articleDiffCallback.newListSize)
    }

    @Test
    fun areContentsTheSame_whenOldEmptyListAndNewEmptyListThenReturnFalse() {
        articleDiffCallback = ArticleDiffCallback(emptyList(), emptyList())
        assertFalse(articleDiffCallback.areContentsTheSame(0, 0))
    }

    @Test
    fun areContentsTheSame_whenOldListIsEqualNewListThenReturnTrue() {
        articleDiffCallback = ArticleDiffCallback(
                listOf(getMaskVO(1, "title", "maskLink")),
                listOf(getMaskVO(1, "title", "maskLink"))
        )
        assertTrue(articleDiffCallback.areContentsTheSame(0, 0))
    }

    @Test
    fun areContentsTheSame_whenOldListIsNotEqualNewListThenReturnFalse() {
        articleDiffCallback = ArticleDiffCallback(
                listOf(getMaskVO(1, "titleOld", "maskLink")),
                listOf(getMaskVO(1, "title", "maskLink"))
        )
        assertFalse(articleDiffCallback.areContentsTheSame(0, 0))

        articleDiffCallback = ArticleDiffCallback(
                listOf(getMaskVO(1, "title", "maskLinkOld")),
                listOf(getMaskVO(1, "title", "maskLink"))
        )
        assertFalse(articleDiffCallback.areContentsTheSame(0, 0))

        articleDiffCallback = ArticleDiffCallback(
                listOf(getMaskVO(10, "title", "maskLink")),
                listOf(getMaskVO(1, "title", "maskLink"))
        )
        assertFalse(articleDiffCallback.areContentsTheSame(0, 0))
    }
}