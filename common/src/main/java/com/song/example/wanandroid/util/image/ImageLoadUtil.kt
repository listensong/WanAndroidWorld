package com.song.example.wanandroid.util.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.EmptySignature
import java.lang.ref.WeakReference
import java.util.HashSet

/**
 * @author: Listensong
 * Time: 19-10-25 下午5:45
 * Desc: com.song.example.wanandroid.util.image.ImageLoadUtil
 */
class ImageLoadUtil {
    companion object {
        const val TAG: String = "ImageLoadUtil"

        fun <E : Context> load(loader: E, url: String, view: ImageView) {
            val image = WeakReference<ImageView>(view).get()
            if (image != null) {
                Glide.with(loader)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(image)
            }
        }

        fun <E : Fragment> load(loader: E,
                               url: String,
                               view: ImageView,
                               listener: ProgressListener? = null) {
            if (listener != null) {
                ProgressInterceptor.addListener(url, listener)
            }
            val image = WeakReference<ImageView>(view).get()
            if (image != null) {
                Glide.with(loader)
                        .load(url)
                        .transition(DrawableTransitionOptions.withCrossFade(1200))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(object : ProgressDrawableImageViewTarget(image, url) {})
            }
        }
    }
}