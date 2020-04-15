package com.song.example.wanandroid.app.main.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.song.example.wanandroid.app.data.AppDataBase
import com.song.example.wanandroid.app.network.WanService
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

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

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(fragment = context, repo = instance())
    }

    bind<HomeRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRepository(
                instance(),
                instance<AppDataBase>().homeBannersDao(),
                instance<AppDataBase>().homeArticleDao()
        )
    }
}