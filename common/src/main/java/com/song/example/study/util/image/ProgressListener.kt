package com.song.example.study.util.image

import android.graphics.drawable.Drawable

/**
 *
 * Created by song on 2017/11/23.
 */

interface ProgressListener {
    fun onProgress(progress: Int)
    fun onStart()
    fun onSourceReady(resource: Drawable?)
    fun onFail(errorDrawable: Drawable?)
}
