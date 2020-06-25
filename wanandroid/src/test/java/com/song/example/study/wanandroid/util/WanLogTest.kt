package com.song.example.study.wanandroid.util

import android.util.Log
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

/**
 * @author Listensong
 * @package com.song.example.study.wanandroid.util
 * @fileName WanLogTest
 * @date on 6/25/2020 10:27 PM
 * @desc TODO
 * @email No
 */
class WanLogTest {

    companion object {
        const val TEST_TAG = "WanTag"
        const val TEST_MESSAGE = "WanMessage"
    }

    @Before
    fun setUp() {
        mockkStatic(Log::class)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun wanLogECalledThenPrintMethodInfoLog() {
        WanLog.e(TEST_MESSAGE)
        verify(exactly = 1) {
            Log.e("WanLogTest.kt",
                    getLogMessage("wanLogECalledThenPrintMethodInfoLog", 39, TEST_MESSAGE))
        }
    }

    private fun getLogMessage(methodName: String, lineNum: Int, msg: String): String {
        return "${WanLog.LOG_SPLIT}${methodName}(WanLogTest.kt:${lineNum})${WanLog.LOG_SPLIT}:${msg}"
    }

    @Test
    fun wanLogECalledThenAndroidLogECalled() {
        WanLog.e(TEST_TAG, TEST_MESSAGE)
        verify(exactly = 1) { Log.e(TEST_TAG, TEST_MESSAGE) }
    }

    @Test
    fun printStackTraceWithEmptyExceptionMsg() {
        WanLog.printStackTrace(TEST_TAG, Exception())
        verify(exactly = 1) { Log.e(TEST_TAG, "") }
    }

    @Test
    fun printStackTraceWithNormalException() {
        WanLog.printStackTrace(TEST_TAG, Exception(TEST_MESSAGE))
        verify(exactly = 1) { Log.e(TEST_TAG, TEST_MESSAGE) }
    }

    @Test
    fun wanLogICalledThenPrintMethodInfoLog() {
        WanLog.i(TEST_MESSAGE)
        verify(exactly = 1) {
            Log.i("WanLogTest.kt",
                    getLogMessage("wanLogICalledThenPrintMethodInfoLog", 70, TEST_MESSAGE))
        }
    }

    @Test
    fun wanLogICalledThenAndroidLogICalled() {
        WanLog.i(TEST_TAG, TEST_MESSAGE)
        verify(exactly = 1) { Log.i(TEST_TAG, TEST_MESSAGE) }
    }

    @Test
    fun wanLogDCalledThenPrintMethodInfoLog() {
        WanLog.d(TEST_MESSAGE)
        verify(exactly = 1) {
            Log.d("WanLogTest.kt",
                    getLogMessage("wanLogDCalledThenPrintMethodInfoLog", 85, TEST_MESSAGE))
        }
    }

    @Test
    fun wanLogDCalledThenAndroidLogDCalled() {
        WanLog.d(TEST_TAG, TEST_MESSAGE)
        verify(exactly = 1) { Log.d(TEST_TAG, TEST_MESSAGE) }
    }

    @Test
    fun wanLogVCalledThenPrintMethodInfoLog() {
        WanLog.v(TEST_MESSAGE)
        verify(exactly = 1) {
            Log.v("WanLogTest.kt",
                    getLogMessage("wanLogVCalledThenPrintMethodInfoLog", 100, TEST_MESSAGE))
        }
    }

    @Test
    fun wanLogVCalledThenAndroidLogVCalled() {
        WanLog.v(TEST_TAG, TEST_MESSAGE)
        verify(exactly = 1) { Log.v(TEST_TAG, TEST_MESSAGE) }
    }

    @Test
    fun wanLogWCalledThenPrintMethodInfoLog() {
        WanLog.w(TEST_MESSAGE)
        verify(exactly = 1) {
            Log.w("WanLogTest.kt",
                    getLogMessage("wanLogWCalledThenPrintMethodInfoLog", 115, TEST_MESSAGE))
        }
    }

    @Test
    fun wanLogWCalledThenAndroidLogWCalled() {
        WanLog.w(TEST_TAG, TEST_MESSAGE)
        verify(exactly = 1) { Log.w(TEST_TAG, TEST_MESSAGE) }
    }
}