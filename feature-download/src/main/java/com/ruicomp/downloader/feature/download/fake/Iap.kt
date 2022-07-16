package com.ruicomp.downloader.feature.download.fake

import android.app.Activity
import android.content.Context
import com.ruicomp.downloader.feature.download.dlog

class Iap {
    var vip = false
    private val subsList =
        listOf<String>("remove_ads", "remove_ads_6m")

    var iapConnector: IapConnector? = null

    class IapConnector {
        fun subscribe (activity: Activity, sku:String) {
            dlog("on subscribe: $sku")

        }
    }

    fun getIapConnector(context: Context) {
        dlog("on getIapConnector")
    }
}