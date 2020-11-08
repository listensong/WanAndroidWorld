package com.song.example.study.wanandroid.main.home.article

import com.song.example.study.extension.moshi
import com.song.example.study.wanandroid.WanAppTestUtils
import com.song.example.study.wanandroid.main.home.HomeConst
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

    companion object {
        private const val EXPECTED_0_AUTHOR = "扔物线"
        private const val EXPECTED_0_LINK = "https://mp.weixin.qq.com/s/CFWznkSrq6JmW1fZdqdlOg"
        private const val EXPECTED_0_TITLE = "【扔物线】消失了半年，这个 Android 界的第一骚货终于回来了"

        private const val EXPECTED_2_AUTHOR = "xiaoyang"
        private const val EXPECTED_2_LINK = "https://wanandroid.com/wenda/show/12922"
        private const val EXPECTED_2_TITLE = "每日一问 | &ldquo;必须在UI线程才能更新控件/界面&rdquo;  这句人人皆知的话，100%正确吗？"

        private const val EXPECTED_2_TAG_NAME = "本站发布"
        private const val EXPECTED_2_TAG_URL = "/article/list/0?cid=440"

        private const val EXPECTED_BASE_INDEX = -100
        private const val EXPECTED_CURRENT_PAGE = 10
        private const val EXPECTED_ITEM_TYPE = HomeConst.ITEM_TYPE_TOP_ARTICLE
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testTopArticleDTOStructure() {
        val json = WanAppTestUtils.readLocalJsonFile(HomeTestConst.WAN_HOME_TOP_ARTICLE_FILE)
        val dto = json.moshi(TopArticleDTO::class.java)
        assertNotNull(dto?.data)
        dto?.data?.let {
            assertEquals(EXPECTED_0_AUTHOR, it[0]?.author)
            assertEquals(EXPECTED_0_LINK, it[0]?.link)
            assertEquals(EXPECTED_0_TITLE, it[0]?.title)

            assertEquals(EXPECTED_2_AUTHOR, it[2]?.author)
            assertEquals(EXPECTED_2_LINK, it[2]?.link)
            assertEquals(EXPECTED_2_TITLE, it[2]?.title)

            //Tag
            assertEquals(EXPECTED_2_TAG_NAME, it[2]?.tags?.get(0)?.name)
            assertEquals(EXPECTED_2_TAG_URL, it[2]?.tags?.get(0)?.url)
        }
    }

    @Test
    fun testToPlaceTopPOList() {
        val json = WanAppTestUtils.readLocalJsonFile(HomeTestConst.WAN_HOME_TOP_ARTICLE_FILE)
        val dto = json.moshi(TopArticleDTO::class.java)
        val srcDTOListSize = dto?.data?.size
        val placeTopPOList = dto.toPlaceTopPOList(EXPECTED_BASE_INDEX, EXPECTED_CURRENT_PAGE)

        assertTrue(placeTopPOList.isNotEmpty())
        assertEquals(srcDTOListSize, placeTopPOList.size)
        placeTopPOList.forEachIndexed { index, articlePO ->
            assertEquals(EXPECTED_CURRENT_PAGE, articlePO.curPage)
            assertEquals(EXPECTED_BASE_INDEX, articlePO._index - index)
            assertEquals(EXPECTED_ITEM_TYPE, articlePO.itemType)
        }
    }
}