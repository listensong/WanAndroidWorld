package com.song.example.wanandroid.extension

import com.song.example.wanandroid.util.WanLog
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * @package com.song.example.wanandroid.extension
 * @fileName StringExtend
 * @date on 1/9/2020 7:48 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
fun <T> String?.moshi(clz: Class<T>) : T?{
    return this?.let {
        return try {
            Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                    .adapter(clz)
                    .fromJson(it)
        } catch (e: Exception) {
            WanLog.e("moshi", "e:$e")
            println("moshi" + "e:$e")
            null
        }
    }
}