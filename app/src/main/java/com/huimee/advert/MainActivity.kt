package com.huimee.advert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.huimee.advertlibrary.DialogUtils
import com.huimee.advertlibrary.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //调用方法
        DialogUtils.showDialog(this,"11364",4,0.5f,0,img)
    }

    override fun onResume() {
        super.onResume()
//        LogUtils.showHoverButton(this,"11364",1,0)
    }

    fun text(view: View) {
        Toast.makeText(this,"aaaa",Toast.LENGTH_SHORT).show()
    }
}
