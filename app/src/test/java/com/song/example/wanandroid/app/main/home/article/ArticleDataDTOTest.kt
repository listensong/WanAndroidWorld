package com.song.example.wanandroid.app.main.home.article

import com.song.example.wanandroid.app.network.BaseWanApiCallMock.Companion.BASE_PATH
import com.song.example.wanandroid.basetest.MockAssets
import com.song.example.wanandroid.extend.moshi
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.MockitoAnnotations

/**
 * @author Listensong
 * @package com.song.example.wanandroid.app.main.home.article
 * @fileName ArticleDataDTOTest
 * @date on 4/5/2020 4:08 PM
 * @desc: TODO
 * @email No
 */
class ArticleDataDTOTest {

    companion object {
        private fun getTestArticleDTO(index: Int): ArticleItemDTO {
            return ArticleItemDTO(
                    apkLink = "getTestArticleDTO_apkLink_$index",
                    audit = 0,
                    author = "getTestArticleDTO_author_$index",
                    canEdit = false,
                    chapterId = 0,
                    chapterName = "getTestArticleDTO_chapterName_$index",
                    collect = false,
                    courseId = 0,
                    desc = "getTestArticleDTO_desc_$index",
                    descMd = "getTestArticleDTO_descMd_$index",
                    envelopePic = "getTestArticleDTO_envelopePic_$index",
                    fresh = false,
                    id = 0,
                    link = "getTestArticleDTO_link_$index",
                    niceDate = "getTestArticleDTO_niceDate_$index",
                    niceShareDate = "getTestArticleDTO_niceShareDate_$index",
                    origin = "getTestArticleDTO_origin_$index",
                    prefix = "getTestArticleDTO_prefix_$index",
                    projectLink = "getTestArticleDTO_projectLink_$index",
                    publishTime = 0,
                    selfVisible = 0,
                    shareDate = 0,
                    shareUser = "getTestArticleDTO_shareUser_$index",
                    superChapterId = 0,
                    superChapterName = "getTestArticleDTO_superChapterName_$index",
                    tags = emptyList(),
                    title = "getTestArticleDTO_title_$index",
                    type = 0,
                    userId = 0,
                    visible = 0,
                    zan = 0
            )
        }

        private fun getTestArticleDTOList(): List<ArticleItemDTO> {
            val list = mutableListOf<ArticleItemDTO>()
            for (i in 0..20) {
                list.add(i, getTestArticleDTO(i))
            }
            return list
        }

        private fun getTestArticleDataDTOWrapper(emptyCase: Boolean): ArticleDataItemDTO {
            return ArticleDataItemDTO(
                    curPage = 0,
                    datas = if (emptyCase) {
                        emptyList()
                    } else {
                        getTestArticleDTOList()
                    },
                    offset = 0,
                    over = false,
                    pageCount = 0,
                    size = 0,
                    total = 0
            )
        }

        private fun getTestArticleDataDTO(emptyCase: Boolean): ArticleDataDTO {
            return ArticleDataDTO(
                data = getTestArticleDataDTOWrapper(emptyCase),
                errorCode = 110,
                errorMsg = "getTestArticleDataDTO"
            )
        }

        lateinit var validTestDTO: ArticleDataDTO
        lateinit var emptyTestDTO: ArticleDataDTO

        @JvmStatic
        @BeforeClass
        fun setUp() {
            validTestDTO = getTestArticleDataDTO(false)
            emptyTestDTO = getTestArticleDataDTO(true)
        }
    }

    @After
    fun tearDown() {
    }


    @Test
    fun testArticleDTOWrapper() {
        val json = MockAssets.readFile("$BASE_PATH/HomeArticleJson.json")
        val list = json.moshi(ArticleDataDTO::class.java)
        assertEquals(2, list?.data?.curPage)
        assertEquals(412, list?.data?.pageCount)
        assertEquals(20, list?.data?.size)
        assertEquals(8221, list?.data?.total)
        assertEquals(0, list?.errorCode)
    }

    @Test
    fun testToVOList_whenDTOListIsEmptyThenReturnEmptyList() {
        assertEquals(0, emptyTestDTO.data?.datas?.size)
        val articleVOList = emptyTestDTO.toVOList()
        assertEquals(0, articleVOList.size)
    }

    @Test
    fun testToVOList_whenDTOListIsNotEmptyThenReturnVOList() {
        val dtoSize = validTestDTO.data?.datas?.size
        assertNotNull(dtoSize)
        assertTrue(dtoSize != 0)
        val articleVOList = validTestDTO.toVOList()
        assertEquals(dtoSize, articleVOList.size)
        articleVOList.forEachIndexed { index, articleVO ->
            assertEquals(validTestDTO.data?.datas?.get(index)?.apkLink, articleVO.apkLink)
            assertEquals(validTestDTO.data?.datas?.get(index)?.chapterName, articleVO.chapterName)
            assertEquals(validTestDTO.data?.datas?.get(index)?.title, articleVO.title)
            assertEquals(validTestDTO.data?.datas?.get(index)?.link, articleVO.link)
        }
    }

    @Test
    fun testToVO() {
        val dto = getTestArticleDTO(1)
        val vo = dto.toVO(0)
        assertEquals(0, vo.curPage)
        assertEquals(dto.link, vo.link)
        assertEquals(dto.title, vo.title)
        assertEquals(dto.type, vo.type)
    }

    @Test
    fun testToPOList_whenDTOListIsEmptyThenReturnEmptyList() {
        assertEquals(0, emptyTestDTO.data?.datas?.size)
        val articlePOList = emptyTestDTO.toPOList()
        assertEquals(0, articlePOList.size)
    }

    @Test
    fun testToPOList_whenDTOListIsNotEmptyThenReturnVOList() {
        val dtoSize = validTestDTO.data?.datas?.size
        assertNotNull(dtoSize)
        assertTrue(dtoSize != 0)
        val articlePOList = validTestDTO.toVOList()
        assertEquals(dtoSize, articlePOList.size)
        articlePOList.forEachIndexed { index, articlePO ->
            assertEquals(validTestDTO.data?.datas?.get(index)?.apkLink, articlePO.apkLink)
            assertEquals(validTestDTO.data?.datas?.get(index)?.chapterName, articlePO.chapterName)
            assertEquals(validTestDTO.data?.datas?.get(index)?.title, articlePO.title)
            assertEquals(validTestDTO.data?.datas?.get(index)?.link, articlePO.link)
        }
    }

    @Test
    fun testToPO() {
        val dto = getTestArticleDTO(1)
        val po = dto.toPO(0)
        assertEquals(0, po.curPage)
        assertEquals(dto.link, po.link)
        assertEquals(dto.title, po.title)
        assertEquals(dto.type, po.type)
    }
}