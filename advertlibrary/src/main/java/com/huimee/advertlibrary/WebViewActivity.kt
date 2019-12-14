package com.huimee.advertlibrary

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {
    private var agentWeb: AgentWeb? = null
    private var shareUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initView()
        initListener()
    }

    private fun initView() {
        shareUrl = intent.getStringExtra("url")

        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                fl_webView,
                -1,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .closeIndicator()
            .setWebViewClient(mWebViewClient)
            .setWebChromeClient(mWebChromeClient)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setMainFrameErrorView(
                R.layout.agentweb_error_page,
                -1
            )
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .interceptUnkownUrl()
            .createAgentWeb()
            .ready()
            .go(shareUrl)
        val webSettings = agentWeb?.agentWebSettings?.webSettings
        webSettings?.userAgentString = "${webSettings?.userAgentString}_android_app"
        agentWeb?.webCreator?.webView?.let {
            it.settings.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

     fun initListener() {

    }

    private var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //  super.onProgressChanged(view, newProgress);
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)

        }
    }
    private var mWebViewClient: WebViewClient = object : WebViewClient() {


        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
//            super.onReceivedError(view, request, error)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url.toString()
            if (!url.startsWith("http:") && !url.startsWith("https:")) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(intent)
                return true
            }
            return shouldOverrideUrlLoading(view, url + "")
        }


        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

            val urls = url.toString()
            if (!urls.startsWith("http:") && !urls.startsWith("https:")) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(intent)
                return true
            }
            return false
        }

        override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

        }
    }

    override fun onBackPressed() {
        agentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
//        super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
//            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}
