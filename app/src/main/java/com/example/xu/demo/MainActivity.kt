package com.example.xu.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 简书1（Acc）
        findViewById<Button>(R.id.button1).setOnClickListener { openWebsite("https://www.jianshu.com/p/5b91f6e3ed71") }

        // 简书2（Butterknife解析）
        findViewById<Button>(R.id.button2).setOnClickListener { openWebsite("https://www.jianshu.com/p/b2f9840a4afd") }

        // 简书3（View和属性动画解析）
        findViewById<Button>(R.id.button3).setOnClickListener { openWebsite("https://www.jianshu.com/p/f6f34b6bb4f2") }

        // 简书4（ImageLoader加载库）
        findViewById<Button>(R.id.button4).setOnClickListener { openWebsite("https://www.jianshu.com/p/1030b3bcac8b") }

        // 简书5（Handler机制理解）
        findViewById<Button>(R.id.button5).setOnClickListener { openWebsite("https://www.jianshu.com/p/03f31f73c235") }

        // 掘金1（Xpopupwindow）
        findViewById<Button>(R.id.button6).setOnClickListener { openWebsite("https://juejin.im/post/5b67101e6fb9a04f9963c818") }

        // 掘金2（View和属性动画解析）
        findViewById<Button>(R.id.button7).setOnClickListener { openWebsite("https://juejin.im/post/5a029ff46fb9a045076f166e") }
    }

    private fun openWebsite(address: String) {
        var intent = Intent(this, WebActivity::class.java)
        intent.putExtra("address", address)
        startActivity(intent)
    }
}
