package com.song.example.wanandroid.app.main.home.article

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json

/**
 * @package com.song.example.wanandroid.app.main.home.article
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

data class Tag(
        @Json(name="name")
        val name: String? = null,

        @Json(name="url")
        val url: String? = null
)