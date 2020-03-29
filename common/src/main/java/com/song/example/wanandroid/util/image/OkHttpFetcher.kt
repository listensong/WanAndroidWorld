package com.song.example.wanandroid.util.image

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream

/**
 *
 * Created by song on 2017/11/23.
 */

class OkHttpFetcher(
        private val okHttpClient: OkHttpClient,
        private val glideUrl: GlideUrl
) : DataFetcher<InputStream> {

    private var inputStream: InputStream? = null
    private var responseBody: ResponseBody? = null

    @Volatile
    private var isCancelled: Boolean = false

    override fun loadData(priority: Priority,
                          callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder().url(glideUrl.toStringUrl())
        for ((key, value) in glideUrl.headers) {
            requestBuilder.addHeader(key, value)
        }

        val request = requestBuilder.build()
        if (isCancelled) {
            callback.onDataReady(null)
        }

        val response = okHttpClient.newCall(request).execute()
        responseBody = response.body
        if (!response.isSuccessful || responseBody == null) {
            throw IOException("Request failed with code: " + response.code)
        }

        responseBody?.run {
            inputStream = ContentLengthInputStream.obtain(
                    byteStream(),
                    contentLength()
            )
            callback.onDataReady(inputStream)
        }
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

    override fun cleanup() {
        try {
            inputStream?.close()
            responseBody?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun cancel() {
        isCancelled = true
    }
}
