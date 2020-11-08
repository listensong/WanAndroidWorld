package com.song.example.study.wanandroid.main.home.banner

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Listensong
 * @package com.song.example.study.app.main.home
 * @fileName BannerDataDTOTest
 * @date on 4/3/2020 8:55 PM
 * @desc: TODO
 * @email No
 */
class BannerDataDTOTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun getTestBannerDTO(): BannerDTO {
        return BannerDTO(
                desc = "BannerDTO_desc",
                id = 0,
                imagePath = "BannerDTO_imagePath",
                isVisible = 0,
                order = 0,
                title = "BannerDTO_title",
                type = 0,
                url = "BannerDTO_url"
        )
    }

    private fun getTestBannerDataDTO(emptyDTOList: Boolean): BannerDataDTO {
        return BannerDataDTO().apply {
            data = if (emptyDTOList) {
                emptyList()
            } else {
                listOf(getTestBannerDTO())
            }
            errorCode = 110
            errorMsg = "getTestBannerDataDTO"
        }
    }

    @Test
    fun testToVOList_whenDTOListIsEmptyThenReturnEmptyList() {
        val dto = getTestBannerDataDTO(true)
        val bannerVOList = dto.toVOList()
        assertEquals(0, bannerVOList.size)
    }

    @Test
    fun testToVOList_whenDTOListIsNotEmptyThenReturnVOList() {
        val originalDto = getTestBannerDataDTO(false)
        val bannerVOList = originalDto.toVOList()
        assertEquals(originalDto.data?.size, bannerVOList.size)
        bannerVOList.forEachIndexed { index, bannerVO ->
            val expectedDto = originalDto.data?.get(index)
            assertEquals(expectedDto?.title, bannerVO.title)
            assertEquals(expectedDto?.type, bannerVO.type)
            assertEquals(expectedDto?.imagePath, bannerVO.imagePath)
            assertEquals(expectedDto?.url, bannerVO.url)
        }
    }

    @Test
    fun testToVO() {
        val originalDto = getTestBannerDTO()
        val vo = originalDto.toVO()
        assertEquals(originalDto.title, vo.title)
        assertEquals(originalDto.type, vo.type)
        assertEquals(originalDto.imagePath, vo.imagePath)
        assertEquals(originalDto.url, vo.url)
    }

    @Test
    fun testToPOList_whenDTOListIsEmptyThenReturnEmptyList() {
        val dto = getTestBannerDataDTO(true)
        val bannerPOList = dto.toPOList()
        assertEquals(0, bannerPOList.size)
    }

    @Test
    fun testToPOList_whenDTOListIsNotEmptyThenReturnVOList() {
        val originalDto = getTestBannerDataDTO(false)
        val bannerPOList = originalDto.toPOList()
        assertEquals(originalDto.data?.size,bannerPOList.size)
        bannerPOList.forEachIndexed { index, bannerPO ->
            val expectedDto = originalDto.data?.get(index)
            assertEquals(expectedDto?.id, bannerPO.id)
            assertEquals(expectedDto?.desc, bannerPO.desc)
            assertEquals(expectedDto?.imagePath, bannerPO.imagePath)
            assertEquals(expectedDto?.isVisible, bannerPO.isVisible)
            assertEquals(expectedDto?.order, bannerPO.order)
            assertEquals(expectedDto?.title, bannerPO.title)
            assertEquals(expectedDto?.type, bannerPO.type)
            assertEquals(expectedDto?.url, bannerPO.url)
        }
    }

    @Test
    fun testToPO() {
        val originalDto = getTestBannerDTO()
        val po = originalDto.toPO()
        assertEquals(originalDto.id, po.id)
        assertEquals(originalDto.desc, po.desc)
        assertEquals(originalDto.imagePath, po.imagePath)
        assertEquals(originalDto.isVisible, po.isVisible)
        assertEquals(originalDto.order, po.order)
        assertEquals(originalDto.title, po.title)
        assertEquals(originalDto.type, po.type)
        assertEquals(originalDto.url, po.url)
    }
}