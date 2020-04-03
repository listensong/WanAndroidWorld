package com.song.example.wanandroid.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.song.example.wanandroid.BaseApplication
import com.song.example.wanandroid.app.main.home.BannerDAO
import com.song.example.wanandroid.app.main.home.BannerPO

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
            BannerPO::class
        ],
        version = 1,
        exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun homeBannersDao(): BannerDAO

    companion object {
        @Volatile private var instance: AppDataBase? = null

        private const val DB_NAME = "HelloWorld.db"

        fun getInstance(): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(BaseApplication.instance).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //val request = OneTimeWorkRequestBuilder<>()
                        }
                    })
                    .build()
        }
    }
}