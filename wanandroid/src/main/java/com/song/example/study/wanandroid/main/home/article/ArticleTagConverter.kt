package com.song.example.study.wanandroid.main.home.article

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @package com.song.example.study.wanandroid.main.home.article
 * @fileName ArticleTagConverter
 * @date on 4/5/2020 4:05 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
class ArticleTagConverter {
    @TypeConverter
    fun stringToObject(values: String) : List<Tag> {
        return Gson().fromJson(values, object : TypeToken<List<Tag>>(){}.type)
    }

    @TypeConverter
    fun objectToString(values: List<Tag>) : String {
        return Gson().toJson(values)
    }
}
