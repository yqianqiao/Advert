package com.huimee.advert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.huimee.advertlibrary.DialogUtils
import com.huimee.advertlibrary.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val strings = arrayOf("悬浮", "上浮", "下浮", "固定", "X屏")
        val s = intArrayOf(11408, 11364, 11352, 11357, 11365)
        but_switch_mode.setOnClickListener {
            AlertDialog.Builder(this)
                .setItems(strings) { _, which ->
                    but_switch_mode.text = strings[which]
                    but_toMain.text = "广告id ： ${s[which]} "
                    DialogUtils.showDialog(
                        this,
                        s[which].toString(),
                        which,
                        if (which == 0) 0.2f else 0f,
                        0,
                        img
                    )
                }.show()

        }

//        LogUtils.setCalla(object :LogUtils.Calla{
//            override fun getMediaid(media: String) {
//                runOnUiThread {
//                    tv_text.text = media
//                }
//
//            }
//        })


        //调用方法
//        DialogUtils.showDialog(this,"11364",1,0.3f,0,img)

    }

    override fun onResume() {
        super.onResume()
//        LogUtils.showHoverButton(this,"11364",1,0)
    }

    fun text(view: View) {
        DialogUtils.showDialog(this,"11364",1,0.3f,0,img)
    }
}
