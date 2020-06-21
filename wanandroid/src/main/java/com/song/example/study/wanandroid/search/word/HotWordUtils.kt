package com.song.example.study.wanandroid.search.word

/**
 * @package com.song.example.study.wanandroid.search.word
 * @fileName HotWordUtils
 * @date on 6/7/2020 4:57 PM
 * @author Listensong
 * @desc
 * @email No
 */
fun HotWordDataDTO.toPO(): HotWordPO {
    return HotWordPO(
            id = this.id ?: 0,
            visible = this.visible ?: 0,
            link = this.link ?: "",
            name = this.name ?: "",
            order = this.order ?: 0
    )
}

fun HotWordDTO?.toPOList(): List<HotWordPO> {
    val poList = mutableListOf<HotWordPO>()
    this?.data?.forEach {
        if (it != null && !it.name.isNullOrEmpty()) {
            poList.add(it.toPO())
        }
    }
    return poList
}