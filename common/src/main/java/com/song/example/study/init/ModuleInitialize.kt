package com.song.example.study.init

import android.app.Application
import org.kodein.di.Kodein

/**
 * @package com.song.example.study.init
 * @fileName Initialize
 * @date on 5/3/2020 5:43 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
interface ModuleInitialize {

    fun moduleDiConfig() = Kodein.Module("BaseModuleInitialize") {
    }

    fun onCreate(application: Application)

    fun onTerminate()
}