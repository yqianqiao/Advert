package com.huimee.advertlibrary

import android.app.Activity
import android.content.Intent
import com.bumptech.glide.Glide
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.dialog_img.view.*

/**
 *   Created by YX on 2019/10/15 15:19.
 */
class SignInPopup(var activity: Activity, var imgUrl: String,var url:String) : CenterPopupView(activity) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_img
    }

    override fun initPopupContent() {
        super.initPopupContent()
        Glide.with(activity).load(imgUrl).into(iv_img)

        iv_img.setOnClickListener {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url",url)
            activity.startActivity(intent)
        }

        iv_Close.setOnClickListener {
            dismiss()
        }
    }
}