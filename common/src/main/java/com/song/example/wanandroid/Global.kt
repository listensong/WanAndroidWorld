package com.song.example.wanandroid

import android.app.Application
import android.content.Context

/**
 * @package com.song.example.wanandroid
 * @fileName GlobalContext
 * @date on 3/22/2020 4:13 PM
 * @author Listensong
 * @desc: TODO
 * @email No
 */
object Global {
    val globalApplication: Application = BaseApplication.instance
    val globalContext: Context = BaseApplication.instance
}