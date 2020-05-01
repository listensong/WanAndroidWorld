package com.song.example.wanandroid.common.web

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

/**
 * @package com.song.example.wanandroid.common.web
 * @fileName WebModule
 * @date on 5/1/2020 8:56 PM
 * @author Listensong
 * @desc
 * @email No
 */
@Suppress("ObjectPropertyName")
const val _COMMON_WEB_VIEW_MODULE = "_COMMON_WEB_VIEW_MODULE"

val commonWebViewModule = Kodein.Module(_COMMON_WEB_VIEW_MODULE) {

    bind<WanWebView>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        WanWebView.instance(context.requireActivity())
    }

    bind<WanWebView>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        WanWebView.instance(context)
    }
}