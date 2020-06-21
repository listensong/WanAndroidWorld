package com.song.example.study.wanandroid.search.word

import com.song.example.study.extension.moshi
import com.song.example.study.wanandroid.WanAppTestUtils
import com.song.example.study.wanandroid.main.home.HomeTestConst
import com.song.example.study.wanandroid.main.home.article.TopArticleDTO
import com.song.example.study.wanandroid.search.SearchTestConst
import com.squareup.moshi.Json
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * @package com.song.example.study.wanandroid.search.word
 * @fileName HotWordPOJOTest
 * @date on 6/7/2020 5:42 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class HotWordPOJOTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testMoshiHotWordDTO() {
        val json = WanAppTestUtils.readLocalJsonFile(SearchTestConst.WAN_HOT_WORD_FILE)
        val dto = json.moshi(HotWordDTO::class.java)
        assertNotNull(dto?.data)
        assertEquals(9, dto?.data?.size)
        dto?.data?.get(5)?.let { dto ->
            assertEquals(1, dto.visible)
            assertEquals("", dto.link)
            assertEquals("gradle", dto.name)
            assertEquals(3, dto.id)
            assertEquals(5, dto.order)
        }
    }

    @Test
    fun testHotWordDTOToPOList() {
        val json = WanAppTestUtils.readLocalJsonFile(SearchTestConst.WAN_HOT_WORD_FILE)
        val hotWordDto = json.moshi(HotWordDTO::class.java)
        val poList = hotWordDto.toPOList()

        assertEquals(9, poList.size)
        poList[5].let { dto ->
            assertEquals(1, dto.visible)
            assertEquals("", dto.link)
            assertEquals("gradle", dto.name)
            assertEquals(3, dto.id)
            assertEquals(5, dto.order)
        }
    }

    @Test
    fun testHotWordDataDTOToPO() {
        val dto = createHotWordDataDTO()
        val po = dto.toPO()
        assertEquals(111, po.id)
        assertEquals(1, po.visible)
        assertEquals("link", po.link)
        assertEquals("helloName", po.name)
        assertEquals(10086, po.order)
    }

    private fun createHotWordDataDTO(): HotWordDataDTO {
        return HotWordDataDTO(
                1, "link", "helloName", 111, 10086
        )
    }

    @Test
    fun testDefaultValueHotWordDataDTOToPO() {
        val dto = createHotWordDataDTOWithNullValue()
        val po = dto.toPO()
        assertEquals(0, po.id)
        assertEquals(0, po.visible)
        assertEquals("", po.link)
        assertEquals("", po.name)
        assertEquals(0, po.order)
    }

    private fun createHotWordDataDTOWithNullValue(): HotWordDataDTO {
        return HotWordDataDTO(
                null, null, null, null, null
        )
    }
}