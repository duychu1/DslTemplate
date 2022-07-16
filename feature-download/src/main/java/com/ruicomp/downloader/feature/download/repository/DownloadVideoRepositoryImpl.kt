package com.ruicomp.downloader.feature.download.repository

import android.content.Context
import com.ruicomp.downloader.download.video.Result
import com.ruicomp.downloader.download.video.data.VideoData

class DownloadVideoRepositoryImpl: DownloadVideoRepository {
    override suspend fun fetchData(inUrl: String): Result<VideoData> {
        TODO("Not yet implemented")
    }

    override suspend fun saveToInternal(
        inUrl: String,
        fileName: String,
        context: Context
    ): Result<String> {
        TODO("Not yet implemented")
    }
}