package com.ruicomp.downloader.feature.download

import android.content.ClipboardManager
import android.content.Context

sealed class Event {
    data class windowFocus(val context: Context, val clipboard:ClipboardManager): Event()
    data class textFieldChanged(val value:String) : Event()
    data class pasteBtnClick(val clipboard:ClipboardManager): Event()
    data class topBtnClick(val context: Context): Event()
    data class botBtnClick(val context: Context): Event()
    object btnSwapClick: Event()

}
