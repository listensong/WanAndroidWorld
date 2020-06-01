package com.song.example.study.wanandroid.main.home.article

import com.song.example.study.wanandroid.AppTestUtils
import com.song.example.study.wanandroid.main.home.HomeConst
import com.song.example.study.extension.moshi
import com.song.example.study.wanandroid.main.home.HomeTestConst
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Listensong
 * @package com.song.example.study.app.main.home.article
 * @fileName TopArticleDTOTest
 * @date on 4/18/2020 6:29 PM
 * @desc TODO
 * @email No
 */
class TopArticleDTOTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testTopArticleDTOStructure() {
        val json = AppTestUtils.readLocalJsonFile(HomeTestConst.WAN_HOME_TOP_ARTICLE_FILE)
        val dto = json.moshi(TopArticleDTO::class.java)
        assertNotNull(dto?.data)
        dto?.data?.let {
            assertEquals("扔物线", it[0]?.author)
            assertEquals("https://mp.weixin.qq.com/s/CFWznkSrq6JmW1fZdqdlOg", it[0]?.link)
            assertEquals( "【扔物线】消失了半年，这个 Android 界的第一骚货终于回来了", it[0]?.title)

            assertEquals("xiaoyang", it[2]?.author)
            assertEquals("https://study.com/wenda/show/12922", it[2]?.link)
            assertEquals(  "每日一问 | &ldquo;必须在UI线程才能更新控件/界面&rdquo;  这句人人皆知的话，100%正确吗？", it[2]?.title)

            //Tag
            assertEquals(  "本站发布", it[2]?.tags?.get(0)?.name)
            assertEquals(  "/article/list/0?cid=440", it[2]?.tags?.get(0)?.url)
        }
    }

    @Test
    fun testToPlaceTopPOList() {
        val json = AppTestUtils.readLocalJsonFile(HomeTestConst.WAN_HOME_TOP_ARTICLE_FILE)
        val dto = json.moshi(TopArticleDTO::class.java)
        val srcDTOListSize = dto?.data?.size
        val baseIndexTest = -100
        val currentPageTest = 10
        val placeTopPOList = dto.toPlaceTopPOList(
                baseIndexTest,
                currentPageTest
        )

        assertTrue(placeTopPOList.isNotEmpty())
        assertEquals(srcDTOListSize, placeTopPOList.size)
        placeTopPOList.forEachIndexed { index, articlePO ->
            assertEquals(currentPageTest, articlePO.curPage)
            assertEquals(baseIndexTest, articlePO._index - index)
            assertEquals(HomeConst.ITEM_TYPE_ARTICLE, articlePO.itemType)
        }
    }
}