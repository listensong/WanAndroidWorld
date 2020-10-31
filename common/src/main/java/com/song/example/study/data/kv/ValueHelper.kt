package com.song.example.study.data.kv

import android.app.Application
import android.util.Log
import com.tencent.mmkv.MMKV

/**
 * @author song
 * @time 20-10-13 下午10:10
 * @desc wrapper for MMKV
 */
@Suppress("unused")
class ValueHelper {

    private object Holder {
        val INSTANCE: ValueHelper = ValueHelper()
    }

    companion object {
        const val TAG = "ValueHelper"

        @JvmStatic
        fun getInstance() = Holder.INSTANCE


        @JvmStatic
        fun init(application: Application) {
            val dir = MMKV.initialize(application)
            Log.v(TAG, "dir $dir")
        }

        @JvmStatic
        fun save(key: String, value: String): Boolean {
            return MMKV.defaultMMKV().encode(key, value)
        }

        @JvmStatic
        fun save(key: String, value: Int): Boolean {
            return MMKV.defaultMMKV().encode(key, value)
        }

        @JvmStatic
        fun save(key: String, value: Float): Boolean {
            return MMKV.defaultMMKV().encode(key, value)
        }

        @JvmStatic
        fun save(key: String, value: Double): Boolean {
            return MMKV.defaultMMKV().encode(key, value)
        }

        @JvmStatic
        fun save(key: String, value: Long): Boolean {
            return MMKV.defaultMMKV().encode(key, value)
        }

        @JvmStatic
        fun readBool(key: String): Boolean {
            return MMKV.defaultMMKV().decodeBool(key)
        }

        @JvmStatic
        fun readInt(key: String): Int {
            return MMKV.defaultMMKV().decodeInt(key)
        }

        @JvmStatic
        fun readFloat(key: String): Float {
            return MMKV.defaultMMKV().decodeFloat(key)
        }

        @JvmStatic
        fun readDouble(key: String): Double {
            return MMKV.defaultMMKV().decodeDouble(key)
        }

        @JvmStatic
        fun readLong(key: String): Long {
            return MMKV.defaultMMKV().decodeLong(key)
        }

        @JvmStatic
        fun read(key: String): String {
            return MMKV.defaultMMKV().decodeString(key)
        }

        @JvmStatic
        fun readString(key: String): String {
            return MMKV.defaultMMKV().decodeString(key)
        }

        @JvmStatic
        fun readStringOrEmpty(key: String): String {
            return MMKV.defaultMMKV().decodeString(key, "")
        }

        @JvmStatic
        fun removeValue(key: String) {
            MMKV.defaultMMKV().removeValueForKey(key)
        }

        @JvmStatic
        fun removeValues(keys: Array<String>) {
            MMKV.defaultMMKV().removeValuesForKeys(keys)
        }

        @JvmStatic
        fun contains(key: String): Boolean {
            return MMKV.defaultMMKV().containsKey(key)
        }
    }
}