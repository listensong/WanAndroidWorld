package com.song.example.study.wanandroid.main.home.article

import com.song.example.study.extension.moshi
import com.song.example.study.wanandroid.WanAppTestUtils
import com.song.example.study.wanandroid.main.home.HomeTestConst
import org.junit.After
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

/**
 * @author Listensong
 * @package com.song.example.study.app.main.home.article
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

        private const val EXPECTED_CURRENT_PAGE = 2
        private const val EXPECTED_PAGE_COUNT = 412
        private const val EXPECTED_SIZE = 20
        private const val EXPECTED_TOTAL = 8221
        private const val EXPECTED_ERROR_CODE = 0
    }

    @After
    fun tearDown() {
    }


    @Test
    fun testArticleDTOWrapper() {
        val json = WanAppTestUtils.readLocalJsonFile(HomeTestConst.WAN_HOME_ARTICLE_FILE)
        val list = json.moshi(ArticleDataDTO::class.java)

        assertEquals(EXPECTED_CURRENT_PAGE, list?.data?.curPage)
        assertEquals(EXPECTED_PAGE_COUNT, list?.data?.pageCount)
        assertEquals(EXPECTED_SIZE, list?.data?.size)
        assertEquals(EXPECTED_TOTAL, list?.data?.total)
        assertEquals(EXPECTED_ERROR_CODE, list?.errorCode)
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
            val expectedDTO = validTestDTO.data?.datas?.get(index)
            assertEquals(expectedDTO?.apkLink, articleVO.apkLink)
            assertEquals(expectedDTO?.chapterName, articleVO.chapterName)
            assertEquals(expectedDTO?.title, articleVO.title)
            assertEquals(expectedDTO?.link, articleVO.link)
        }
    }

    @Test
    fun testToVO() {
        val originalDTO = getTestArticleDTO(1)
        val vo = originalDTO.toVO(0, false)
        assertEquals(0, vo.curPage)
        assertEquals(originalDTO.link, vo.link)
        assertEquals(originalDTO.title, vo.title)
        assertEquals(originalDTO.type, vo.type)
    }

    @Test
    fun testToPOList_whenDTOListIsEmptyThenReturnEmptyList() {
        assertEquals(0, emptyTestDTO.data?.datas?.size)
        val articlePOList = emptyTestDTO.toPOList<ArticlePO>(0, 0)
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
            val expectedDTO = validTestDTO.data?.datas?.get(index)
            assertEquals(expectedDTO?.apkLink, articlePO.apkLink)
            assertEquals(expectedDTO?.chapterName, articlePO.chapterName)
            assertEquals(expectedDTO?.title, articlePO.title)
            assertEquals(expectedDTO?.link, articlePO.link)
        }
    }

    @Test
    fun testToPO() {
        val originalDTO = getTestArticleDTO(1)
        val po = originalDTO.toPO<ArticlePO>(0, 0, false)
        assertEquals(0, po.curPage)
        assertEquals(originalDTO.link, po.link)
        assertEquals(originalDTO.title, po.title)
        assertEquals(originalDTO.type, po.type)
    }

}