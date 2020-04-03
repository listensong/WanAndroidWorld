package com.song.example.wanandroid.app.main.home

import com.squareup.moshi.JsonClass

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName BannerDTO
 * @date on 3/29/2020 3:42 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@JsonClass(generateAdapter = true)
class BannerDataDTO {
    var data: List<BannerDTO>? = null
    var errorCode: Int? = 0
    var errorMsg: String = ""
}

class BannerDTO(
        var desc: String? = "",
        var id: Int = 0,
        var imagePath: String? = "",
        var isVisible: Int? = 0,
        var order: Int? = 0,
        var title: String? = "",
        var type: Int? = 0,
        var url: String? = ""
)

fun BannerDataDTO?.toVOList(): List<BannerVO> {
    return this?.run {
        this.data?.map {
            it.toVO()
        } ?: emptyList()
    } ?: emptyList()
}

fun BannerDTO.toVO() : BannerVO {
    return  BannerVO(
            title = this.title ?: "",
            type = this.type ?: 0,
            imagePath = this.imagePath ?: "",
            url = this.url ?: ""
    )
}

fun BannerDataDTO?.toPOList(): List<BannerPO> {
    return this?.run {
        this.data?.map {
            it.toPO()
        } ?: emptyList()
    } ?: emptyList()
}

fun BannerDTO.toPO() : BannerPO {
    return  BannerPO(
            id = this.id,
            desc = this.desc ?: "",
            imagePath = this.imagePath ?: "",
            isVisible = this.isVisible ?: 0,
            order = this.order ?: 0,
            title = this.title ?: "",
            type = this.type ?: 0,
            url = this.url ?: ""
    )
}

