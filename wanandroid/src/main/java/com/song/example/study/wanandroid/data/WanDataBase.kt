package com.song.example.study.wanandroid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.song.example.study.wanandroid.main.home.article.ArticleDAO
import com.song.example.study.wanandroid.main.home.article.ArticlePO
import com.song.example.study.wanandroid.main.home.banner.BannerDAO
import com.song.example.study.wanandroid.main.home.banner.BannerPO
import com.song.example.study.wanandroid.search.word.HotWordDAO
import com.song.example.study.wanandroid.search.word.HotWordPO

/**
 * @package com.song.example.study.wanandroid.data
 * @fileName AppDataBase
 * @date on 4/1/2020 9:27 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Database(
        entities = [
            BannerPO::class,
            ArticlePO::class,
            HotWordPO::class
        ],
        version = 2,
        exportSchema = false
)
abstract class WanDataBase : RoomDatabase() {
    abstract fun homeBannersDao(): BannerDAO
    abstract fun homeArticleDao(): ArticleDAO

    abstract fun searchHotWordDao(): HotWordDAO
}