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
     * @param height 调整高度(type:0 范围是0-1 百分比模式，type:1,2 自动强转为int，单位是dp)
     * @param time 时间，单位秒(12默认最少10秒，4默认最少3秒)
     * @param img 只有在 type = 3 时传入的ImageView
     */
    fun showDialog(
        activity: Activity,
        s: String,
        type: Int,
        height: Float = 0f,
        time: Int = 0,
        img: View? = null
    ) {
        LogUtils.showHoverButton(activity, s, type, height, time, img)
    }
}