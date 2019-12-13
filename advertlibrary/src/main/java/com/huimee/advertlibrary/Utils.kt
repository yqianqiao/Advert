package com.huimee.advertlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import java.text.SimpleDateFormat
import java.util.*

/**
 *   Created by YX on 2019/12/6 15:37.
 */
object  Utils {


    public fun getUserAgent(context:Context): String {
        var userAgent :String?= ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context)
            } catch (e: Exception) {
                userAgent = System.getProperty("http.agent")
            }
        } else {
            userAgent = System.getProperty("http.agent")
        }
        userAgent = System.getProperty("http.agent")
        //调整编码，防止中文出错
        val sb = StringBuffer()
        var i = 0
        val length = userAgent!!.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }




}