package com.song.example.wanandroid.common.network.retrofit

import com.song.example.wanandroid.Global

/**
 * @author Listensong
 * @package com.song.example.wanandroid.common.network.retrofit
 * @fileName LifecycleCallAdapterFactoryCreator
 * @date on 2/28/2020 9:22 PM
 * @desc: TODO
 * @email No
 */
object LifecycleCallAdapterFactoryCreator {
    fun create(): LifecycleCallAdapterFactory {
        return LifecycleCallAdapterFactory(Global.globalApplication, true)
    }
}