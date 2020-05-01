package com.song.example.wanandroid.util

import android.content.res.Resources

/**
 * @package com.song.example.wanandroid.util
 * @fileName DeviceUtil
 * @date on 4/30/2020 8:36 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
object DeviceUtil {

    fun dp2PxFloat(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }

    fun dp2PxInt(dp: Float): Int {
        return (dp2PxFloat(dp)).toInt()
    }
}