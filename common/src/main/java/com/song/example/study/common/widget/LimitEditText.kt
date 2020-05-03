package com.song.example.study.common.widget

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet

/**
 *
 * @author Listensong
 */
class LimitEditText : androidx.appcompat.widget.AppCompatEditText {
    private var limitLengthFilter: LimitLengthFilter? = null

    interface OnTextExceedListener {
        fun onExceed()
    }

    fun setExceedListener(exceedListener: OnTextExceedListener?) {
        limitLengthFilter?.setExceedListener(exceedListener)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLengthAttr(attrs, context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initLengthAttr(attrs, context)
    }

    private fun initLengthAttr(set: AttributeSet, context: Context) {
        val maxLen = set.getAttributeIntValue(androidNameSpace, attrMaxLength, -1)
        if (maxLen > -1) {
            limitLengthFilter = LimitLengthFilter(maxLen)
            filters = arrayOf<InputFilter>(limitLengthFilter!!)
        } else {
            filters = NO_FILTERS
        }
    }

    companion object {
        const val androidNameSpace = "http://schemas.android.com/apk/res/android"
        const val attrMaxLength = "maxLength"
        private val NO_FILTERS = arrayOfNulls<InputFilter>(0)
    }
}