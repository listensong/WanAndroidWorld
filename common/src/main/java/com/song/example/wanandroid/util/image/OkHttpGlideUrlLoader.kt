package com.song.example.wanandroid.util.image

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 *
 * Created by song on 2017/11/23.
 */

class OkHttpGlideUrlLoader(
        private val okHttpClient: OkHttpClient
) : ModelLoader<GlideUrl, InputStream> {

    override fun buildLoadData(glideUrl: GlideUrl,
                               width: Int,
                               height: Int,
                               options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(
                glideUrl, OkHttpFetcher(okHttpClient, glideUrl))
    }

    override fun handles(model: GlideUrl): Boolean {
        return true
    }

    class Factory(
            client: OkHttpClient
    ) : ModelLoaderFactory<GlideUrl, InputStream> {

        override fun build(
                multiFactory: MultiModelLoaderFactory
        ): ModelLoader<GlideUrl, InputStream> {
            return OkHttpGlideUrlLoader(okHttpClient)
        }

        private var mClient: OkHttpClient? = client

        private val okHttpClient: OkHttpClient
            @Synchronized get() {
                if (mClient == null) {
                    mClient = OkHttpClient()
                }
                return mClient!!
            }

        override fun teardown() {
        }
    }
}
