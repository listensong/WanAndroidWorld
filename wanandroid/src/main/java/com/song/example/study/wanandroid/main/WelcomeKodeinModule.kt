package com.song.example.study.wanandroid.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.song.example.study.wanandroid.network.WAN_RETROFIT
import com.song.example.study.wanandroid.network.WanService
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

/**
 * @package com.song.example.study.wanandroid.main.home
 * @fileName WelcomeKodeinModule
 * @date on 5/1/2020 12:12 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
const val MAIN_WELCOME_MODULE_DI_TAG = "MAIN_WELCOME_MODULE_DI_TAG"

val wanMainWelcomeKodeinModule = Kodein.Module(MAIN_WELCOME_MODULE_DI_TAG) {

    bind<WelcomeViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        WelcomeViewModel.instance(activity = context)
    }

    bind<WelcomeViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        WelcomeViewModel.instance(activity = context)
    }

    bind<WelcomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        WelcomeViewModel.instance(activity = this.context.requireActivity())
    }
}