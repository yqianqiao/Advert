package com.huimee.advertlibrary

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


/**
 *   Created by YX on 2019/12/11 15:26.
 */
class AdvertImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {


    fun setImage(activity: Activity, s: String) {
        DialogUtils.showDialog(activity,s,3,-1,this)
    }


}