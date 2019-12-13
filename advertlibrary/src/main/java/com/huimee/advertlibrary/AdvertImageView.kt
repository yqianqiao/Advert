package com.huimee.advertlibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cecil.okhttp.OkHttpManage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.huimee.advertlibrary.Utils.getUserAgent
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat


/**
 *   Created by YX on 2019/12/11 15:26.
 */
class AdvertImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    private val ipList = mutableListOf<String>()
    private var url = "http://m3.shunyi365.net/s.json?s="
    private var views: String? = null
    private lateinit var bean: AdvertBean

    fun setImage(activity: Activity, s: String) {
        DialogUtils.showDialog(activity,s,3,-1,this)
    }


}