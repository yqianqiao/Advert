package com.huimee.advert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.huimee.advertlibrary.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        LogUtils.showHoverButton(this,"11365",0,50)
    }

    override fun onResume() {
        super.onResume()
//        LogUtils.showHoverButton(this,"11364",1,0)
    }

    fun text(view: View) {
        Toast.makeText(this,"aaaa",Toast.LENGTH_SHORT).show()
    }
}
