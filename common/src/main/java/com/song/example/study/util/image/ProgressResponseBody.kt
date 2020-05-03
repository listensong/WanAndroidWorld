package com.song.example.study.util.image

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 *
 * Created by song on 2017/11/23.
 */

class ProgressResponseBody(
        url: String,
        private val responseBody: ResponseBody
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null
    private var progressListener: ProgressListener? = null

    init {
        progressListener = ProgressInterceptor.getListener(url)
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = ProgressSource(responseBody.source()).buffer()
        }
        return bufferedSource!!
    }

    private inner class ProgressSource(
            delegate: Source
    ) : ForwardingSource(delegate) {
        private var totalBytesRead: Long = 0
        internal var currentProgress = 0

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            val fullLength = responseBody.contentLength()
            if (bytesRead < 0) {
                totalBytesRead = fullLength
            } else {
                totalBytesRead += bytesRead
            }
            val progress = (100f * totalBytesRead / fullLength).toInt()

            progressListener?.run {
                if (progress != currentProgress) {
                    onProgress(progress)
                }

                if (totalBytesRead == fullLength) {
                    progressListener = null
                }
            }

            currentProgress = progress
            return bytesRead
        }
    }

    companion object {
        private const val TAG = "ProgressResponseBody"
    }
}
