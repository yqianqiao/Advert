package com.huimee.advertlibrary

import android.content.Intent
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class SplashActivity : AppCompatActivity() {

    private var isSkip = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, flag)

        setContentView(R.layout.activity_splash)
        val image_url = intent.getStringExtra("image_url")
        val url = intent.getStringExtra("url")
        val time = intent.getIntExtra("time", 3)
        Glide.with(this).load(image_url).into(iv_splash)

        Observable.timer(time.toLong(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (!isSkip) {
                    finish()
                }
            }

        iv_splash.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }


        tv_skip.setOnClickListener {
            isSkip = true
            finish()
        }


    }


}
