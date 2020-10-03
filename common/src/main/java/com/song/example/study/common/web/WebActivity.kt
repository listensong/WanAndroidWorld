package com.song.example.study.common.web


import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import com.google.android.material.appbar.AppBarLayout
import com.song.example.study.R
import com.song.example.study.base.ui.BaseActivity
import com.song.example.study.extension.getAndroidAttrPX
import com.song.example.study.extension.getStatusBarHeight
import com.song.example.study.extension.setFullScreen
import com.song.example.study.extension.setLayoutHeight
import org.kodein.di.instance

/**
 * @package com.song.example.study.common.web
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

    private val commWebView: CommWebView by instance()

    private lateinit var containView: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setFullScreen()
        setContentView(R.layout.activity_toolbar_web)

        val appBar = findViewById<AppBarLayout>(R.id.app_bar_layout)
        appBar.setLayoutHeight(calcAppbarLayoutHeight())
        containView = findViewById(R.id.fl_web_view_container)

        setActionBar()
        updateTitle(getString(R.string.app_name))

        intent.extras?.let {
            val url = it.getString(WebConst.KEY_WEB_URL, "")
            if (url.isNotEmpty()) {
                commWebView.apply {
                    webViewClient = selfWebViewClient
                    webChromeClient = selfWebChromeClient
                }
                containView.addView(commWebView)
                commWebView.loadUrl(url)
            }
        }
    }

    private fun calcAppbarLayoutHeight(): Int {
        return getAndroidAttrPX(android.R.attr.actionBarSize) + getStatusBarHeight()
    }


    private val selfWebViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            commWebView.setProgressVisible(false)
            commWebView.requestFocus()
        }

        override fun shouldInterceptRequest(view: WebView?,
                                            request: WebResourceRequest?): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

        override fun onReceivedError(view: WebView?, errorCode: Int,
                                     description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            Log.e(WebFragment.TAG, "onReceivedError $description")
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
            Log.e(TAG, "onReceivedHttpError $errorResponse")
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            commWebView.let {
                it.setProgressVisible(true)
                it.requestFocus()
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView?,
                                              request: WebResourceRequest?): Boolean {
            Log.e(TAG, "shouldOverrideUrlLoading $request")
            if (request != null) {
                view?.loadUrl(request.url.toString())
                return true
            }
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            Log.e(TAG, "onReceivedSslError $error")
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
            commWebView.setProgressValue(newProgress)
        }

        override fun onCreateWindow(view: WebView?, isDialog: Boolean,
                                    isUserGesture: Boolean, resultMsg: Message?): Boolean {
            Log.e(TAG, "onCreateWindow $resultMsg")
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
        }
    }

    override fun onBackPressed() {
        if (commWebView.canGoBack()) {
            commWebView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        commWebView.clear()
        super.onDestroy()
        containView.removeAllViews()
    }
}
