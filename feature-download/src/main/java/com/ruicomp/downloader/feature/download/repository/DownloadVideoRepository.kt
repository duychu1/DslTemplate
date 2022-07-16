package com.ruicomp.downloader.feature.download.repository

import android.content.Context
import com.ruicomp.downloader.download.video.Result
import com.ruicomp.downloader.download.video.data.VideoData

interface DownloadVideoRepository {
    suspend fun fetchData(inUrl:String): Result<VideoData>

    suspend fun saveToInternal(inUrl: String, fileName: String, context: Context): Result<String>
}