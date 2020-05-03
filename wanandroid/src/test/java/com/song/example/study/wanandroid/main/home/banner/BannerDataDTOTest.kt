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
        val dto = getTestBannerDataDTO(false)
        val bannerVOList = dto.toVOList()
        assertEquals(dto.data?.size, bannerVOList.size)
        bannerVOList.forEachIndexed { index, bannerVO ->
            assertEquals(dto.data?.get(index)?.title, bannerVO.title)
            assertEquals(dto.data?.get(index)?.type, bannerVO.type)
            assertEquals(dto.data?.get(index)?.imagePath, bannerVO.imagePath)
            assertEquals(dto.data?.get(index)?.url, bannerVO.url)
        }
    }

    @Test
    fun testToVO() {
        val dto = getTestBannerDTO()
        val vo = dto.toVO()
        assertEquals(dto.title, vo.title)
        assertEquals(dto.type, vo.type)
        assertEquals(dto.imagePath, vo.imagePath)
        assertEquals(dto.url, vo.url)
    }

    @Test
    fun testToPOList_whenDTOListIsEmptyThenReturnEmptyList() {
        val dto = getTestBannerDataDTO(true)
        val bannerPOList = dto.toPOList()
        assertEquals(0, bannerPOList.size)
    }

    @Test
    fun testToPOList_whenDTOListIsNotEmptyThenReturnVOList() {
        val dto = getTestBannerDataDTO(false)
        val bannerPOList = dto.toPOList()
        assertEquals(dto.data?.size,bannerPOList.size)
        bannerPOList.forEachIndexed { index, bannerPO ->
            assertEquals(dto.data?.get(index)?.id, bannerPO.id)
            assertEquals(dto.data?.get(index)?.desc, bannerPO.desc)
            assertEquals(dto.data?.get(index)?.imagePath, bannerPO.imagePath)
            assertEquals(dto.data?.get(index)?.isVisible, bannerPO.isVisible)
            assertEquals(dto.data?.get(index)?.order, bannerPO.order)
            assertEquals(dto.data?.get(index)?.title, bannerPO.title)
            assertEquals(dto.data?.get(index)?.type, bannerPO.type)
            assertEquals(dto.data?.get(index)?.url, bannerPO.url)
        }
    }

    @Test
    fun testToPO() {
        val dto = getTestBannerDTO()
        val po = dto.toPO()
        assertEquals(dto.id, po.id)
        assertEquals(dto.desc, po.desc)
        assertEquals(dto.imagePath, po.imagePath)
        assertEquals(dto.isVisible, po.isVisible)
        assertEquals(dto.order, po.order)
        assertEquals(dto.title, po.title)
        assertEquals(dto.type, po.type)
        assertEquals(dto.url, po.url)
    }
}