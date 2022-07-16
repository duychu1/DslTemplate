package com.ruicomp.downloader.feature.filevideo.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ruicomp.downloader.core.model.VideoInfo

data class VideoInfoUi(
    val id:Int,
    val title:String,
    val uri:String,
    val duration:String,
    val thumbnail: MutableState<Bitmap?> = mutableStateOf(null),
) {
    fun asModel(): VideoInfo {
        return VideoInfo(
            id, title, uri, duration
        )
    }
}

fun VideoInfo.asUi():VideoInfoUi {
    return VideoInfoUi(
        id, title, uri, duration
    )
}
