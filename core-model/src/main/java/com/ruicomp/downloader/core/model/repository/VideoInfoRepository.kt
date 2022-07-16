package com.ruicomp.downloader.core.model.repository

import com.ruicomp.downloader.core.database.VideoInfoDao
import com.ruicomp.downloader.core.model.VideoInfo
import com.ruicomp.downloader.core.model.asModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoInfoRepository @Inject constructor(
    private val videoInfoDao: VideoInfoDao
) {
    fun getVideosInfo(): Flow<List<VideoInfo>> {
        return videoInfoDao.getAllVideosInfo().map { list ->
            list.map {
                it.asModel()
            }
        }
    }

    suspend fun insert(videoInfo: VideoInfo) {
        return videoInfoDao.insert(videoInfo.asEntity())
    }

    suspend fun delete(videoInfo: VideoInfo) {
        return videoInfoDao.delete(videoInfo.asEntity())
    }

    suspend fun update(videoInfo: VideoInfo) {
        return videoInfoDao.update(videoInfo.asEntity())
    }

}