package com.huimee.advertlibrary

import java.io.Serializable

/**
 *   Created by YX on 2019/12/5 17:30.
 */
data class AdvertBean(
    val adsid: Int,
    val width: String,
    val height: String,
    val zoneid: String,
    val uid: String,
    val mediaid: Int,
    val imageurl: String,
    val tourl: String,
    val views: String,
    val ip: String,
    val count_url: String,
    val click_url: String,
    var time:Long
) : Serializable
