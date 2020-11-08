package com.song.example.study.wanandroid.main.home.article

import com.song.example.study.wanandroid.main.home.HomeConst
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Listensong
 * @package com.song.example.study.app.main.home.article
 * @fileName ArticlePOTest
 * @date on 4/18/2020 11:19 AM
 * @desc TODO
 * @email No
 */
class ArticlePOTest {

    companion object {
        private const val EXPECTED_ID =  -(HomeConst.ITEM_TYPE_BANNER * 10 + 0) + HomeConst.BASE_INDEX_BANNER
        private const val EXPECTED_CURRENT_PAGE =  0
        private const val EXPECTED_ITEM_TYPE = HomeConst.ITEM_TYPE_BANNER
        private const val EXPECTED_TITLE = "MASK_TITLE_BANNER_TITLE_BANNER_LINK_${HomeConst.ITEM_TYPE_BANNER}"
        private const val EXPECTED_LINK = "MASK_LINK_BANNER_TITLE_BANNER_LINK_${HomeConst.ITEM_TYPE_BANNER}"
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testCreateMaskArticlePO() {
        val po = createMaskArticlePO(
                HomeConst.BASE_INDEX_BANNER, 0,
                HomeConst.ITEM_TYPE_BANNER, "BANNER_TITLE", "BANNER_LINK"
        )

        assertEquals(EXPECTED_ID, po.id)
        assertEquals(EXPECTED_CURRENT_PAGE, po.curPage)
        assertEquals(EXPECTED_ITEM_TYPE, po.itemType)
        assertEquals(EXPECTED_TITLE, po.title)
        assertEquals(EXPECTED_LINK, po.link)
    }
}