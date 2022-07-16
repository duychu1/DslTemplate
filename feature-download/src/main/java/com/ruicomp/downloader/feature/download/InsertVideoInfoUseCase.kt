package com.ruicomp.downloader.feature.download

import com.ruicomp.downloader.core.model.VideoInfo
import com.ruicomp.downloader.core.model.repository.VideoInfoRepository
import javax.inject.Inject

class InsertVideoInfoUseCase @Inject constructor(
    private val videoInfoRepository: VideoInfoRepository
) {
    suspend operator fun invoke(videoInfo: VideoInfo) {
        videoInfoRepository.insert(videoInfo)
    }
}