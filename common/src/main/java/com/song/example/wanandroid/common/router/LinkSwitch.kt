package com.song.example.wanandroid.common.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.song.example.wanandroid.common.web.WebActivity
import com.song.example.wanandroid.common.web.WebConst

/**
 * @package com.song.example.wanandroid.common.router
 * @fileName LinkSwitch
 * @date on 5/1/2020 8:17 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
object LinkSwitch {

    @JvmStatic
    fun goWebView(context: Context, url: String?) {
        url ?: return
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra(WebConst.KEY_WEB_URL, url)
        context.startActivity(intent)
    }

    @JvmStatic
    fun goWebView(activity: Activity, url: String?) {
        url ?: return
        val intent = Intent(activity, WebActivity::class.java)
        intent.putExtra(WebConst.KEY_WEB_URL, url)
        activity.startActivity(intent)
    }
}