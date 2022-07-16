package com.ruicomp.downloader.feature.download.fake

import android.app.Activity
import android.content.Context
import com.ruicomp.downloader.feature.download.dlog
import java.util.*

class ManagerInterAds(
    val admobInters: AdmobInters = AdmobInters(),
    val applovinInters: ApplovinInters = ApplovinInters(),
    private var nAds: Int = 1,
    private var day: Int = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
) {
    class ApplovinInters {
        var maxInterstitialAd = null
        fun createAd(activity: Activity){

        }

    }

    class AdmobInters {
        var mInterstitialAd = null
        fun loadAd(context: Context) {

        }
    }

    fun showInters(aContext: Context) { }

//    fun showInters(aContext: Context) {
//        dlog("showInters: $nAds")
//
//    }
}