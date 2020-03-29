package com.song.example.wanandroid.util.image

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 *
 * Created by song on 2017/11/23.
 */

abstract class ProgressDrawableImageViewTarget : CustomViewTarget<ImageView, Drawable> {

    companion object {
        const val TAG = "ProgressViewTarget"
    }

    private var progressListener: ProgressListener? = null
    private lateinit var progressImageViewUrl: String

    constructor(view: ImageView) : super(view) {}

    constructor(view: ImageView, url: String) : super(view) {
        progressImageViewUrl = url
        progressListener = ProgressInterceptor.getListener(progressImageViewUrl)
    }

    override fun onResourceReady(resource: Drawable,
                                 transition: Transition<in Drawable>?) {
        progressListener?.onSourceReady(resource)
        Log.e(TAG, "onSourceReady: $transition")
        ProgressInterceptor.removeListener(progressImageViewUrl)
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        progressListener?.onFail(errorDrawable)
        Log.e(TAG, "onLoadFailed ")
        ProgressInterceptor.removeListener(progressImageViewUrl)
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop: ")
    }

    override fun onStart() {
        super.onStart()
        progressListener?.onStart()
        Log.e(TAG, "onStart: ")
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        Log.e(TAG, "onResourceCleared: ")
    }

    override fun onResourceLoading(placeholder: Drawable?) {
        super.onResourceLoading(placeholder)
        Log.e(TAG, "onResourceLoading: ")
    }
}
