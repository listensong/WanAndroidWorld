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

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testCreateMaskArticlePO() {
        val maskId = -(HomeConst.ITEM_TYPE_BANNER * 10 + 0) + HomeConst.BASE_INDEX_BANNER
        val po = createMaskArticlePO(
                HomeConst.BASE_INDEX_BANNER, 0,
                HomeConst.ITEM_TYPE_BANNER, "BANNER_TITLE", "BANNER_LINK")

        assertEquals(maskId, po.id)
        assertEquals(0, po.curPage)
        assertEquals(HomeConst.ITEM_TYPE_BANNER, po.itemType)
        assertEquals("MASK_TITLE_BANNER_TITLE_BANNER_LINK_${HomeConst.ITEM_TYPE_BANNER}", po.title)
        assertEquals("MASK_LINK_BANNER_TITLE_BANNER_LINK_${HomeConst.ITEM_TYPE_BANNER}", po.link)
    }
}