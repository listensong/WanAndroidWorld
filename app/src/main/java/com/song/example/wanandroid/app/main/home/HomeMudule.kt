package com.song.example.wanandroid.app.main.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.song.example.wanandroid.app.data.AppDataBase
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.*

/**
 * @package com.song.example.wanandroid.app.main.home
 * @fileName HomeMudule
 * @date on 4/11/2020 10:22 AM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
const val HOME_MODULE_DI_TAG = "HOME_MODULE_DI_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_DI_TAG) {

    bind<HomeFragment>() with scoped(AndroidLifecycleScope).singleton {
        HomeFragment.newInstance()
    }

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(fragment = context, repo = instance())
    }

    bind<HomeViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(activity = context, repo = instance())
    }

    bind<HomeViewModel>(tag = "WelcomeActivityProvider") with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(activity = this.context.requireActivity(), repo = instance())
    }

    bind<HomeRepository>() with provider {
        HomeRepository(
                instance(),
                instance(),
                instance()
        )
    }

    bind<HomeRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRepository(
                instance(),
                instance(),
                instance()
        )
    }

    bind<HomeRepository>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        HomeRepository(
                instance(),
                instance(),
                instance()
        )
    }
}