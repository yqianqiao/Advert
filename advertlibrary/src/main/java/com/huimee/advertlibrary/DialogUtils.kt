package com.huimee.advertlibrary

import android.app.Activity
import android.view.View

/**
 *   Created by YX on 2019/12/13 17:05.
 */
object DialogUtils {
    /**
     * @param activity 界面
     * @param s 广告id
     * @param type  0：悬浮，1：上浮，2：下浮，3：插入页面，4：X屏
     * @param height 调整高度(仅123有效)
     * @param time 时间，单位秒(12默认最少10秒，4默认最少3秒)
     * @param img type = 3 时传入的ImageView
     */
    fun showDialog(
        activity: Activity,
        s: String,
        type: Int,
        height: Int = 0,
        time: Int = 0,
        img: View? = null
    ) {
        LogUtils.showHoverButton(activity, s, type, height, time, img)
    }
}