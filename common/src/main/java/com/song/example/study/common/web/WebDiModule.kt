package com.song.example.study.common.web

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.kodein.di.DI
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton

/**
 * @package com.song.example.study.common.web
 * @fileName WebModule
 * @date on 5/1/2020 8:56 PM
 * @author Listensong
 * @desc
 * @email No
 */
@Suppress("ObjectPropertyName")
const val _COMMON_WEB_VIEW_MODULE = "_COMMON_WEB_VIEW_MODULE"

val commonWebViewModule = DI.Module(_COMMON_WEB_VIEW_MODULE) {

    bind<CommWebView>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        CommWebView.instance(context.requireActivity())
    }

    bind<CommWebView>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        CommWebView.instance(context)
    }
}