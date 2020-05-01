package com.song.example.wanandroid.common.web


import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.song.example.wanandroid.R
import com.song.example.wanandroid.base.ui.BaseFragment
import com.song.example.wanandroid.util.WanLog

/**
 * @package com.song.example.wanandroid.common.web
 * @fileName WebFragment
 * @date on 5/1/2020 8:12 PM
 * @author Listensong
 * @desc
 * @email No
 */

class WebFragment : BaseFragment() {

    companion object {
        const val TAG = "WanWebView"

        @JvmStatic
        fun newInstance() = WebFragment()

        @JvmStatic
        fun newInstance(bundle: Bundle?) : WebFragment {
            return WebFragment().apply {
                arguments = bundle
            }
        }
    }

    private lateinit var rootView: ViewGroup
    private lateinit var containView: ViewGroup
    private var wanWebView: WanWebView? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        rootView = view.findViewById(R.id.root_container)
        containView = rootView.findViewById(R.id.fl_web_view_container)

        title = getString(R.string.app_name)
        updateActionBar()

        arguments?.let {
            val url = it.getString(WebConst.KEY_WEB_URL, "")
            if (url.isNotEmpty()) {
                wanWebView = WanWebView.instance(requireActivity()).apply {
                    webViewClient = selfWebViewClient
                    webChromeClient = selfWebChromeClient
                }
                containView.addView(wanWebView)
                wanWebView?.loadUrl(url)
            }
        }

        return view
    }

    private val selfWebViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            wanWebView?.let {
                it.setProgressVisible(false)
                it.requestFocus()
            }
        }

        override fun shouldInterceptRequest(view: WebView?,
                                            request: WebResourceRequest?): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

        override fun onReceivedError(view: WebView?, errorCode: Int,
                                     description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            WanLog.e(TAG, "onReceivedError $description")
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
            wanWebView?.setProgressVisible(true)
            wanWebView?.requestFocus()
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

        override fun onReceivedSslError(view: WebView?,
                                        handler: SslErrorHandler?, error: SslError?) {
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
            wanWebView?.setProgressValue(newProgress)
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

    override fun onBackPressed(): Boolean {
        return if (wanWebView?.canGoBack() == true) {
            wanWebView?.goBack()
            true
        } else {
            super.onBackPressed()
        }
    }

}
