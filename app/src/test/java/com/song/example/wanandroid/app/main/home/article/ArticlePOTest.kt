package com.song.example.wanandroid.app.main.home.article

import com.song.example.wanandroid.app.main.home.HomeConst
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home.article
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
        val po = createMaskArticlePO(0,
                HomeConst.ITEM_TYPE_BANNER, "BANNER_TITLE", "BANNER_LINK")
        assertEquals(0, po.curPage)
        assertEquals(HomeConst.ITEM_TYPE_BANNER, po.itemType)
        assertEquals("MASK_TITLE_BANNER_TITLE_BANNER_LINK_${HomeConst.ITEM_TYPE_BANNER}", po.title)
        assertEquals("MASK_LINK_BANNER_TITLE_BANNER_LINK_${HomeConst.ITEM_TYPE_BANNER}", po.link)
    }
}