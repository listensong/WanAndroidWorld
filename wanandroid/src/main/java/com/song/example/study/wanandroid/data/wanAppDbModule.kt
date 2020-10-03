package com.song.example.study.wanandroid.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.song.example.study.wanandroid.main.home.article.ArticleDAO
import com.song.example.study.wanandroid.main.home.banner.BannerDAO
import com.song.example.study.wanandroid.search.word.HotWordDAO
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

/**
 * @package com.song.example.study.wanandroid.data
 * @fileName appDbModule
 * @date on 4/11/2020 10:36 AM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
const val WAN_APP_DB_MODULE_DI_TAG = "WAN_APP_DB_Module_DI_Tag"
const val WAN_APP_DB_NAME = "wan_android_module.db"

val wanAppDbModule = DI.Module(WAN_APP_DB_MODULE_DI_TAG) {

    bind<ArticleDAO>() with singleton {
        instance<WanDataBase>().homeArticleDao()
    }

    bind<BannerDAO>() with singleton {
        instance<WanDataBase>().homeBannersDao()
    }

    bind<HotWordDAO>() with singleton {
        instance<WanDataBase>().searchHotWordDao()
    }

    bind<WanDataBase>() with singleton {
        Room.databaseBuilder(instance(), WanDataBase::class.java, WAN_APP_DB_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //val request = OneTimeWorkRequestBuilder<>()
                    }
                })
                .build()
    }
}