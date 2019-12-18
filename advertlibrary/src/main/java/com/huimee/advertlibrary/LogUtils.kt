package com.huimee.advertlibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.contains
import com.bumptech.glide.Glide
import com.cecil.okhttp.OkHttpManage
import com.google.gson.Gson
import com.huimee.advertlibrary.Utils.getUserAgent
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*
import com.google.gson.reflect.TypeToken
import com.lxj.xpopup.XPopup
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


/**
 *   Created by YX on 2019/12/4 16:15.
 */

object LogUtils {
    private var url = "http://m3.shunyi365.net/s.json?s="
    private var imgUrl = "https://ae01.alicdn.com/kf/Hc78db477a97a470797b41825b5d08cd6P.gif"
    private var views: String? = null
    private var imgList: ArrayList<String>? = null
    private lateinit var bean: AdvertBean
    private val ipList = mutableListOf<String>()
    private var type = -1
    private var height = 0f
    private var time = 0
    private var isSkip = false

    fun showHoverButton(
        activity: Activity,
        s: String,
        type: Int,
        height: Float = 0f,
        time: Int = 0,
        img: View? = null
    ) {
//        Log.e("height == ", "height = $height")
        this.type = type
        this.height = height
        this.time = time
        val ipstring = ACache.get(activity).getAsString("ipList")
        if (ipstring != null) {
            val ipList1 = Gson().fromJson(
                ipstring,
                object : TypeToken<List<String>>() {}.type
            ) as List<String>
            if (ipList1.isNotEmpty()) {
                val day = ipList1[0].toLong().isDay()
                if (day) ipList.plus(ipList1)
            }
        }
        getUrl(activity, s, img)

    }

    private fun showImage(activity: Activity) {
        if (type == 0) {
            if (bean.imageurl.contains("|")) {
                imgList = bean.imageurl.trim().split("|") as ArrayList
                if (imgList!!.size == 1) {
                    imgList!!.add(0, imgUrl)
                }
            } else {
                imgList = arrayListOf()
                imgList!!.add(imgUrl)
                imgList!!.add(bean.imageurl)
            }
        }

        activity.runOnUiThread {
            //            val decorView = activity.window.decorView as FrameLayout
            val content = activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)

            val view = when (type) {
                0 -> {
                    activity.layoutInflater.inflate(R.layout.suspension, content, false)
                }
                1 -> {
                    activity.layoutInflater.inflate(R.layout.top, content, false)
                }
                2 -> {
                    activity.layoutInflater.inflate(R.layout.bottom, content, false)
                }
                else -> {
                    throw IllegalArgumentException("tpty = $type  img is Null?")
                }
            }

            if (type ==0){
                val layout = view.findViewById<ConstraintLayout>(R.id.item)
                val set = ConstraintSet()
                set.clone(layout)
                set.setVerticalBias(R.id.image, height)
                layout.setConstraintSet(set)
            }else {
//                val contentParent = decorView.findViewById<FrameLayout>(android.R.id.content)
                val headParams =
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
//                if (height < 0) {
//                    height =
//                        activity.window.decorView.height - activity.resources.displayMetrics.heightPixels
//                }
                if (type == 1) {
                    headParams.topMargin = height.toInt()
                } else {
                    headParams.gravity = Gravity.BOTTOM
                    headParams.bottomMargin = height.toInt()
                }
                view.layoutParams = headParams
                if (time < 10) time = 10
                Observable.timer(time.toLong(), TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (!isSkip) {
                            if (content.contains(view)) {
                                content.removeView(view)
                            }
                        }
                    }
            }
            val imageView = view.findViewById<ImageView>(R.id.image)

            Glide.with(activity).load(if (type == 0) imgList?.get(0) else bean.imageurl.trim())
                .into(imageView)

            view.findViewById<ImageView>(R.id.iv_Close).setOnClickListener {
                content.removeView(view)
                isSkip = true
            }
            imageView.setOnClickListener {
                if (type == 0) {
                    OkHttpManage.sendGetRequest(bean.click_url,
                        getUserAgent(activity), object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                            }

                            override fun onResponse(call: Call, response: Response) {
                            }
                        })

                    XPopup.Builder(activity)
                        .asCustom(SignInPopup(activity, imgList!![1], bean.tourl))
                        .show()
                } else {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("url", bean.tourl)
                    activity.startActivity(intent)
                }

            }
            content.addView(view)
        }
    }


    private fun getUrl(activity: Activity, s: String, img: View? = null) {
        OkHttpManage.sendGetRequest(
            "$url$s&views=$views",
            getUserAgent(activity),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    Log.e("aaa", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val shops = Gson().fromJson(
                        json,
                        object : TypeToken<List<AdvertBean>>() {}.type
                    ) as List<AdvertBean>
                    bean = shops[0]
                    views = bean.views
                    if (ipList.size == 0) {
                        ipList.add(System.currentTimeMillis().toString())
                    } else {
                        ipList[0] = System.currentTimeMillis().toString()
                    }
                    if (ipList.contains("${bean.ip}_${bean.adsid}_${bean.zoneid}")) {
                        getpv(activity, false)

                    } else {
                        ipList.add("${bean.ip}_${bean.adsid}_${bean.zoneid}")
                        getpv(activity, true)
                    }
//                    Log.e("ipList_aaa", ipList.toString())
                    ACache.get(activity).put("ipList", Gson().toJson(ipList))

//                    Log.e("aaa", bean.toString())

                    if (type == 4) {
                        val intent = Intent(activity, SplashActivity::class.java)
                        intent.putExtra("url", bean.tourl)
                        intent.putExtra("image_url", bean.imageurl)
                        intent.putExtra("time", if (time < 3) 3 else time)
                        activity.startActivity(intent)
                    } else {
                        if (img != null && type == 3) {
                            img.setOnClickListener {
                                val intent = Intent(activity, WebViewActivity::class.java)
                                intent.putExtra("url", bean.tourl)
                                activity.startActivity(intent)
                            }
                            activity.runOnUiThread {
                                Glide.with(activity).load(bean.imageurl.trim())
                                    .into(img as ImageView)
                            }
                        } else {
                            showImage(activity)

                        }
                    }

                }
            })
    }


    private fun getpv(activity: Activity, isAdd: Boolean) {
//        Log.e("aaa_url", if (isAdd) bean.count_url else bean.count_url + "&p=1")
        OkHttpManage.sendGetRequest(
            if (isAdd) bean.count_url + "&ref=" else bean.count_url + "&ref=&p=1",
            getUserAgent(activity),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                }
            })
    }


    @SuppressLint("SimpleDateFormat")
    fun Long.isDay(): Boolean {
        val dft = SimpleDateFormat("yyyy-MM-dd")
        val millis = System.currentTimeMillis()
        val sysTime = dft.format(millis)
        val newTime = dft.format(this@isDay)
        return sysTime == newTime
    }
}