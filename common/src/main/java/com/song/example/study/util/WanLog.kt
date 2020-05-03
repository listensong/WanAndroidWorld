package com.song.example.study.util

import android.util.Log
import com.song.example.study.BuildConfig

/**
 * @package com.song.example.study.util
 * @fileName WanLog
 * @date on 4/12/2020 5:55 PM
 * @author Listensong
 * @desc TODO 将来要改成Timber或者是XLog
 * @email No
 */
object WanLog {
    private var className: String? = null
    private var methodName: String? = null
    private var lineNumber = 0
    private var debuggable = BuildConfig.DEBUG

    private fun generateLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append("================")
        buffer.append(methodName)
        buffer.append("(").append(className).append(":").append(lineNumber).append(")================:")
        buffer.append(log)
        return buffer.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (!debuggable) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.e(className, generateLog(message))
    }

    fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    fun i(message: String) {
        if (!debuggable) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.i(className, generateLog(message))
    }

    fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun d(message: String) {
        if (!debuggable) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.d(className, generateLog(message))
    }

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun v(message: String) {
        if (!debuggable) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.v(className, generateLog(message))
    }

    fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    fun w(message: String) {
        if (!debuggable) {
            return
        }
        getMethodNames(Throwable().stackTrace)
        Log.w(className, generateLog(message))
    }

    fun w(tag: String, message: String) {
        Log.w(tag, message)
    }
}