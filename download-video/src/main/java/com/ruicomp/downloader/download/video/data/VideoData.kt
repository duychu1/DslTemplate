package com.ruicomp.downloader.download.video.data

import org.json.JSONArray

data class VideoData(
    val lDownloadUrl: JSONArray,
    val title: String,
    val authorName: String,
    val aid: String,
    val duration: String
)