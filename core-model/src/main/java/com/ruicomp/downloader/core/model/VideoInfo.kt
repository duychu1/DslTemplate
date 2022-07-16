package com.ruicomp.downloader.core.model

import android.graphics.Bitmap
import com.ruicomp.downloader.core.database.VideoInfoEntity

data class VideoInfo(
    val id:Int,
    val title:String,
    val uri:String,
    val duration:String,
    val thumbnail: Bitmap? = null,
) {
    fun asEntity(): VideoInfoEntity {
        return VideoInfoEntity(id, title, uri, duration)
    }
}

fun VideoInfoEntity.asModel(): VideoInfo {
    return VideoInfo(
        id, title, uri, duration
    )
}
