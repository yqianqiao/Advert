package com.huimee.advertlibrary

import android.app.Activity
import android.view.View

/**
 *   Created by YX on 2019/12/13 17:05.
 */
object DialogUtils {

    fun showDialog(
        activity: Activity,
        s: String,
        type: Int,
        height: Int = -1,
        img: View? = null
    ) {

        LogUtils.showHoverButton(activity, s, type, height, img)
    }

}