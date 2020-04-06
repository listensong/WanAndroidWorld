package com.song.example.wanandroid.app.main.home.article

import androidx.lifecycle.LiveData
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
    fun updateArticles(articles: List<ArticlePO>) {
        clear()
        insertAll(articles)
    }

    @Query("SELECT * FROM $WAN_HOME_ARTICLE_TABLE_NAME")
    fun getArticles(): LiveData<List<ArticleVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<ArticlePO>)

    @Query("DELETE from $WAN_HOME_ARTICLE_TABLE_NAME")
    fun clear()
}