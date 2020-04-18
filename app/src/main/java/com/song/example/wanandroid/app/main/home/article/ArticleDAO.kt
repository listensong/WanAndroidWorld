package com.song.example.wanandroid.app.main.home.article

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

/**
 * @package com.song.example.wanandroid.app.main.home.article
 * @fileName ArticleDAO
 * @date on 4/5/2020 3:50 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
const val WAN_HOME_ARTICLE_TABLE_NAME = "wan_world_home_article"
@Dao
interface ArticleDAO {

    @Transaction
    fun clearAndInsert(articles: List<ArticlePO>) {
        clear()
        insert(articles)
    }

    @Transaction
    fun clearAboveAndInsert(aboveIndex: Int, articles: List<ArticlePO>) {
        clearAbove(aboveIndex)
        insert(articles)
    }

    @Transaction
    fun clearRangeAndInsert(startIndex: Int, endIndex: Int, articles: List<ArticlePO>) {
        clearRange(startIndex, endIndex)
        insert(articles)
    }

    @Query("SELECT * FROM $WAN_HOME_ARTICLE_TABLE_NAME")
    fun getArticles(): LiveData<List<ArticleVO>>


    @Query("SELECT * FROM $WAN_HOME_ARTICLE_TABLE_NAME order by _index")
    fun getArticleVOPage(): DataSource.Factory<Int, ArticleVO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<ArticlePO>)

    @Query("DELETE from $WAN_HOME_ARTICLE_TABLE_NAME")
    fun clear()

    @Query("DELETE from $WAN_HOME_ARTICLE_TABLE_NAME where (_index >= :aboveIndex)")
    fun clearAbove(aboveIndex: Int)

    @Query("DELETE from $WAN_HOME_ARTICLE_TABLE_NAME where _index between :startIndex and :endIndex")
    fun clearRange(startIndex: Int, endIndex: Int)
}