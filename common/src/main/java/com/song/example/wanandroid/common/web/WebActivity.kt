package com.song.example.wanandroid.common.web


import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.view.ViewGroup
import android.webkit.*
import com.google.android.material.appbar.AppBarLayout
import com.song.example.wanandroid.R
import com.song.example.wanandroid.base.ui.BaseActivity
import com.song.example.wanandroid.extension.getAndroidAttrPX
import com.song.example.wanandroid.extension.getStatusBarHeight
import com.song.example.wanandroid.extension.setFullScreen
import com.song.example.wanandroid.extension.setLayoutHeight
import com.song.example.wanandroid.util.WanLog
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * @package com.song.example.wanandroid.common.web
 * @fileName WebActivity
 * @date on 5/1/2020 8:17 PM
 * @author Listensong
 * @desc
 * @email No
 */

class WebActivity : BaseActivity() {

    companion object {
        const val TAG = "WebActivity"
    }

    private val wanWebView: WanWebView by instance()

    private lateinit var containView: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setFullScreen()
        setContentView(R.layout.activity_toolbar_web)

        val actionLayoutHeight = getAndroidAttrPX(android.R.attr.actionBarSize) + getStatusBarHeight()
        val appBar = findViewById<AppBarLayout>(R.id.app_bar_layout)
        appBar.setLayoutHeight(actionLayoutHeight)
        containView = findViewById(R.id.fl_web_view_container)

        setActionBar()

        intent.extras?.let {
            val url = it.getString(WebConst.KEY_WEB_URL, "")
            if (url.isNotEmpty()) {
                wanWebView.apply {
                    webViewClient = selfWebViewClient
                    webChromeClient = selfWebChromeClient
                }
                containView.addView(wanWebView)
                wanWebView.loadUrl(url)
            }
        }
    }


    private val selfWebViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            wanWebView.setProgressVisible(false)
            wanWebView.requestFocus()
        }

        override fun shouldInterceptRequest(view: WebView?,
                                            request: WebResourceRequest?): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

        override fun onReceivedError(view: WebView?, errorCode: Int,
                                     description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            WanLog.e(WebFragment.TAG, "onReceivedError $description")
            if (errorCode == -2) {
                view?.run {
                    stopLoading()
                    if (canGoBack()) {
                        goBack()
                    }
                }
                //TODO show network info
            }
        }

        override fun onReceivedHttpError(view: WebView?,
                                         request: WebResourceRequest?,
                                         errorResponse: WebResourceResponse?) {
            WanLog.e(TAG, "onReceivedHttpError $errorResponse")
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            wanWebView.let {
                it.setProgressVisible(true)
                it.requestFocus()
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView?,
                                              request: WebResourceRequest?): Boolean {
            WanLog.e(TAG, "shouldOverrideUrlLoading $request")
            if (request != null) {
                view?.loadUrl(request.url.toString())
                return true
            }
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            WanLog.e(TAG, "onReceivedSslError $error")
            super.onReceivedSslError(view, handler, error)
        }
    }

    private val selfWebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            updateTitle(title)
        }

        override fun onJsPrompt(view: WebView?, url: String?, message: String?,
                                defaultValue: String?, result: JsPromptResult?): Boolean {
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }

        override fun onShowFileChooser(webView: WebView?,
                                       filePathCallback: ValueCallback<Array<Uri>>?,
                                       fileChooserParams: FileChooserParams?): Boolean {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            wanWebView.setProgressValue(newProgress)
        }

        override fun onCreateWindow(view: WebView?, isDialog: Boolean,
                                    isUserGesture: Boolean, resultMsg: Message?): Boolean {
            WanLog.e(TAG, "onCreateWindow $resultMsg")
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
        }
    }

    override fun onBackPressed() {
        if (wanWebView.canGoBack()) {
            wanWebView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        wanWebView.clear()
        super.onDestroy()
    }
}
