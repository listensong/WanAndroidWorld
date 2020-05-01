package com.song.example.wanandroid.common.web

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import com.song.example.wanandroid.extension.setGone
import com.song.example.wanandroid.extension.setVisible
import com.song.example.wanandroid.util.UiUtil


/**
 * @package com.song.example.wanandroid.common.web
 * @fileName WanWebView
 * @date on 5/1/2020 8:09 PM
 * @author Listensong
 * @desc TODO
 * @email No
 */
class WanWebView : WebView {

    constructor(context: Context): super(context) {
        callWebViewInit()
    }
    constructor(context: Context,
                attributeSet: AttributeSet): super(context, attributeSet) {
        callWebViewInit()
    }
    constructor(context: Context, attributeSet: AttributeSet,
                defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        callWebViewInit()
    }

    companion object {

        const val TAG: String = "WanWebView"

        @JvmStatic
        fun instance(activity: Activity): WanWebView {
            return WanWebView(activity)
        }

        @JvmStatic
        fun instance(activity: FragmentActivity): WanWebView {
            return WanWebView(activity)
        }
    }

    private fun callWebViewInit() {
        initConfigSettings()
        initProgressBar()
    }

    private fun initConfigSettings() {
        settings.run {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            textZoom = 100
            allowFileAccess = false
            domStorageEnabled = true
            defaultTextEncodingName = "UTF-8"
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            useWideViewPort = true
            loadWithOverviewMode = true
            loadsImagesAutomatically = true
            blockNetworkImage = false
            blockNetworkLoads = false
            displayZoomControls = false
            builtInZoomControls = Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            cacheMode = WebSettings.LOAD_NO_CACHE
            databaseEnabled = true
            setAppCacheEnabled(true)
            setSupportMultipleWindows(true)
        }

        /*
        * JNI CallObjectMethod called with pending exception java.lang.IllegalStateException:
        * Unable to create layer for WanWebView, size 1080x33319 exceeds max size 16384'
        * */
        setLayerType(View.LAYER_TYPE_NONE, null)

        isVerticalScrollBarEnabled = false
        webViewClient = selfWebViewClient
        webChromeClient = selfWebChromeClient
        layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    private lateinit var progressBar: ProgressBar
    private fun initProgressBar() {
        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
            isIndeterminate = false
            max = 100
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    UiUtil.dp2px(context, 4f).toInt())
        }

        addView(progressBar)
    }

    fun setProgressValue(value: Int) {
        progressBar.progress = value
    }

    fun setProgressVisible(visible: Boolean) {
        progressBar.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private val selfWebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.setGone()
            requestFocus()
        }

        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.setVisible()
            requestFocus()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            super.onReceivedSslError(view, handler, error)
        }
    }

    private val selfWebChromeClient = object : WebChromeClient() {
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
            progressBar.progress = newProgress
        }

        override fun onCreateWindow(view: WebView?, isDialog: Boolean,
                                    isUserGesture: Boolean, resultMsg: Message?): Boolean {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
        }
    }

    fun clear() {
        (parent as ViewGroup?)?.removeView(this)
        removeAllViews()
        clearHistory()
        //clearCache(true)
        destroy()
    }

}

