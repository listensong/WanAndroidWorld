package com.song.example.wanandroid.util.image

import com.bumptech.glide.load.Key

import java.io.UnsupportedEncodingException
import java.security.MessageDigest

/**
 * 获取图片缓存的key
 * Created by song on 2017/11/29.
 */

class OriginalKey(private val id: String, private val signature: Key) : Key {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val that = o as OriginalKey

        return id == that.id && signature == that.signature
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + signature.hashCode()
        return result
    }

    @Throws(UnsupportedEncodingException::class)
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(id.toByteArray(charset(Key.STRING_CHARSET_NAME)))
        signature.updateDiskCacheKey(messageDigest)
    }
}
