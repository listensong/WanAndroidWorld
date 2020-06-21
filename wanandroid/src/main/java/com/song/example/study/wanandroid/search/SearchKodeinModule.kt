package com.song.example.study.wanandroid.search

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.*

/**
 * @package com.song.example.study.wanandroid.search
 * @fileName SearchKodeinModule
 * @date on 6/7/2020 5:00 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
const val WAN_SEARCH_MODULE_DI_TAG = "WAN_SEARCH_MODULE_DI_TAG"

val wanSearchKodeinModule = Kodein.Module(WAN_SEARCH_MODULE_DI_TAG) {

    bind<SearchFragment>() with scoped(AndroidLifecycleScope).singleton {
        SearchFragment.newInstance()
    }

    bind<SearchViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        SearchViewModel.instance(fragment = context, service = instance(), repo = instance())
    }

    bind<SearchRepository>() with provider {
        SearchRepository(instance(), instance())
    }

    bind<SearchRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        SearchRepository(instance(), instance())
    }
}