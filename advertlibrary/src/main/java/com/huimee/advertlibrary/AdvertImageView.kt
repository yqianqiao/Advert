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
        getUrl(activity, s)


        setOnClickListener {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", bean.tourl)
            activity.startActivity(intent)
        }

    }


    private fun getUrl(activity: Activity, s: String) {
        OkHttpManage.sendGetRequest(
            "$url$s&views=$views",
            getUserAgent(activity),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("aaa", e.toString())
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
                    Log.e("ipList_aaa", ipList.toString())
                    ACache.get(activity).put("ipList", Gson().toJson(ipList))

                    Log.e("aaa", bean.toString())

//                    showImage(activity)
                    activity.runOnUiThread {
                        Glide.with(activity).load(bean.imageurl.trim()).into(this@AdvertImageView)
                    }

//                Log.e("aaa",response.body()?.string())


                }
            })
    }

    private fun getpv(activity: Activity, isAdd: Boolean) {
        Log.e("aaa_url", if (isAdd) bean.count_url else bean.count_url + "&p=1")

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