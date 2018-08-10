package com.example.xu.demo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.test.uiautomator.UiDevice
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import android.widget.Button

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v4.os.HandlerCompat.postDelayed


/**
 * Created by xu on 2018/8/10.
 */
class WebActivity : AppCompatActivity() {

    var webview: WebView? = null
    var webSettings: WebSettings? = null
    @Volatile var count: Int = 50
    var handler: Handler? = null
    var runnable1: Runnable? = null
    var runnable2: Runnable? = null
    var address: String = ""
    var isJuejin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        address = intent.getStringExtra("address")

        webview = findViewById(R.id.webview)

        webSettings = webview?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.javaScriptCanOpenWindowsAutomatically = true
        webSettings?.setAppCacheEnabled(true)
        webSettings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webview?.webViewClient = CustomWebViewClient()
        webview?.webChromeClient = WebChromeClient()
        handler = Handler()

        if (address.contains("juejin")) {
            var intent = Intent()
            intent.action = Intent.ACTION_VIEW
            var content = Uri.parse(address)
            intent.data = content
            startActivity(intent)
            isJuejin = true
        } else {
            webview?.loadUrl(address)
            runnable1 = object : Runnable {
                override fun run() {
                    if (count != 0) {
                        webview?.loadUrl("https://www.baidu.com")
                        Thread.sleep(1500)
                        webview?.loadUrl(address)
                        count--
                        Log.i("test", "refresh, count=$count")
                        handler?.postDelayed(this, 10000)
                    }
                }
            }
            handler?.postDelayed(runnable1, 10000)
        }



    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        if (isJuejin) {
            runnable2 = object : Runnable {
                override fun run() {
                    if (count != 0) {
                        var intent = Intent()
                        intent.action = "com.tencent.QQBrowser.action.VIEW_IN_CURRENT"
                        var content = Uri.parse(address)
                        intent.data = content
                        startActivity(intent)
                        count--
                        Log.i("test", "refresh, count=$count")
                        handler?.postDelayed(this, 10000)
                    }
                }
            }
            handler?.postDelayed(runnable2, 10000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (runnable1 != null) {
            handler?.removeCallbacks(runnable1)
        }
        if (runnable2 != null) {
            handler?.removeCallbacks(runnable2)
        }
        webview?.destroy()
        webview = null
    }

}

class CustomWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        // shouldOverrideUrlLoading方法控制超连接是否在当前WebView中打开
        // 返回false表示当前WebView会处理此超链接，返回true则表示宿主应用程序处理此超链接。
        return false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d("test", "started")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d("test", "finished")
    }
}