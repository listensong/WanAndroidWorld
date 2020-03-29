package com.song.example.wanandroid.common.widget

import android.text.InputFilter
import android.text.Spanned
import com.song.example.wanandroid.common.widget.LimitEditText.OnTextExceedListener

/**
 *
 * @author Listensong
 */
class LimitLengthFilter : InputFilter {
    private val maxLimit: Int

    private var exceedListener: OnTextExceedListener? = null

    constructor(max: Int) {
        this.maxLimit = max
    }

    @Suppress("unused")
    constructor(max: Int, listener: OnTextExceedListener?) {
        this.maxLimit = max
        exceedListener = listener
    }

    fun setExceedListener(listener: OnTextExceedListener?) {
        exceedListener = listener
    }

    override fun filter(source: CharSequence, start: Int, end: Int,
                        dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        var keep = maxLimit - (dest.length - (dend - dstart))
        return when {
            keep <= 0 -> {
                if (exceedListener != null) {
                    exceedListener!!.onExceed()
                }
                ""
            }
            keep >= end - start -> {
                null
            }
            else -> {
                if (exceedListener != null) {
                    exceedListener!!.onExceed()
                }
                keep += start
                if (Character.isHighSurrogate(source[keep - 1])) {
                    --keep
                    if (keep == start) {
                        return ""
                    }
                }
                source.subSequence(start, keep)
            }
        }
    }
}