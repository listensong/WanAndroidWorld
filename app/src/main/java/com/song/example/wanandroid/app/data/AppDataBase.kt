package com.song.example.wanandroid.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.app.main.home.article.ArticleDAO
import com.song.example.wanandroid.app.main.home.article.ArticlePO
import com.song.example.wanandroid.app.main.home.banner.BannerDAO
import com.song.example.wanandroid.app.main.home.banner.BannerPO

/**
 * @package com.song.example.wanandroid.app.data
 * @fileName AppDataBase
 * @date on 4/1/2020 9:27 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
@Database(
        entities = [
            BannerPO::class,
            ArticlePO::class
        ],
        version = 1,
        exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun homeBannersDao(): BannerDAO
    abstract fun homeArticleDao(): ArticleDAO
}