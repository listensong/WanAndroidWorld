package com.song.example.wanandroid.app.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.song.example.wanandroid.app.main.home.article.ArticleDAO
import com.song.example.wanandroid.app.main.home.banner.BannerDAO
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @package com.song.example.wanandroid.app.data
 * @fileName appDbModule
 * @date on 4/11/2020 10:36 AM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
const val APP_DB_MODULE_DI_TAG = "APP_DB_Module_DI_Tag"
const val APP_DB_NAME = "HelloWorld.db"

val appDbModule = Kodein.Module(APP_DB_MODULE_DI_TAG) {

    bind<ArticleDAO>() with singleton {
        instance<AppDataBase>().homeArticleDao()
    }

    bind<BannerDAO>() with singleton {
        instance<AppDataBase>().homeBannersDao()
    }

    bind<AppDataBase>() with singleton {
        Room.databaseBuilder(instance(), AppDataBase::class.java, APP_DB_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //val request = OneTimeWorkRequestBuilder<>()
                    }
                })
                .build()
    }
}