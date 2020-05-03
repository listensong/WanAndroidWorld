package com.song.example.study.common.network.retrofit

import com.song.example.study.BaseApplication

/**
 * @author Listensong
 * @package com.song.example.study.common.network.retrofit
 * @fileName LifecycleCallAdapterFactoryCreator
 * @date on 2/28/2020 9:22 PM
 * @desc: TODO
 * @email No
 */
object LifecycleCallAdapterFactoryCreator {
    fun create(): LifecycleCallAdapterFactory {
        return LifecycleCallAdapterFactory(BaseApplication.instance, true)
    }
}