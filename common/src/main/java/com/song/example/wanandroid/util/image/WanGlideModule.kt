package com.song.example.wanandroid.util.image

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @author: Listensong
 * Time: 19-10-26 下午2:43
 * Desc: com.song.example.wanandroid.util.image.WanGlideModule
 */
@GlideModule
class WanGlideModule : AppGlideModule() {

    override fun registerComponents(
            context: Context, glide: Glide, registry: Registry) {
        //super.registerComponents(context, glide, registry)
        val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(ProgressInterceptor())
                .build()

        registry.replace(
                GlideUrl::class.java,
                InputStream::class.java,
                OkHttpGlideUrlLoader.Factory(okHttpClient))
    }
}