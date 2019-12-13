package com.huimee.advertlibrary

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
//            .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。SonicWebViewClient
            .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
            .setWebChromeClient(mWebChromeClient) //WebChromeClient
            //                 .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。

            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
//                .setAgentWebUIController(UIController(this)) //自定义UI  AgentWeb3.0.0 加入。
            .setMainFrameErrorView(
                R.layout.agentweb_error_page,
                -1
            ) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
//                .useMiddlewareWebChrome(MiddlewareChromeClient()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
            //                .useMiddlewareWebClient(mSonicImpl.createSonicClientMiddleWare()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
            //                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
            //                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
            //                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
            .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
            .createAgentWeb()//创建AgentWeb。
            .ready()//设置 WebSettings。
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
            Log.i("", "onProgressChanged:$newProgress  view:$view")
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            var titles = title
            super.onReceivedTitle(view, title)

        }
    }
    private var mWebViewClient: WebViewClient = object : WebViewClient() {

        private val timer = HashMap<String, Long>()

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

        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?
        ): WebResourceResponse? {

            return super.shouldInterceptRequest(view, request)
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
