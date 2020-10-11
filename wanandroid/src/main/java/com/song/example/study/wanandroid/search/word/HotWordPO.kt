package com.song.example.study.wanandroid.search.word

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = WAN_HOT_WORD_TABLE_NAME)
data class HotWordPO(
        @PrimaryKey
        val name: String,

        var _index: Int = 0,

        val id: Int,
        val visible: Int,
        val link: String,
        val order: Int
)