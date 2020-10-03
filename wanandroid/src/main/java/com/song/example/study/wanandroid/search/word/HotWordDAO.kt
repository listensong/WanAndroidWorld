package com.song.example.study.wanandroid.search.word

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @package com.song.example.study.wanandroid.search.word
 * @fileName HotWordDAO
 * @date on 6/7/2020 4:57 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
const val WAN_HOT_WORD_TABLE_NAME = "wan_search_hot_word"

@Dao
interface HotWordDAO {

    @Query("SELECT * FROM $WAN_HOT_WORD_TABLE_NAME order by id")
    fun getHotWord(): LiveData<List<HotWordVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hotKey: List<HotWordPO>)

    @Transaction
    fun clearAndInsert(hotKey: List<HotWordPO>) {
        clear()
        insert(hotKey)
    }

    @Query("DELETE from $WAN_HOT_WORD_TABLE_NAME")
    fun clear()
}