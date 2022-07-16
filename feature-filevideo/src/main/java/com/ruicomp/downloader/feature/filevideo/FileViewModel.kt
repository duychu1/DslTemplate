package com.ruicomp.downloader.feature.filevideo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ruicomp.downloader.core.model.VideoInfo
import com.ruicomp.downloader.core.model.repository.VideoInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(
    private val videoInfoRepository: VideoInfoRepository,
) : ViewModel() {



    private val _uiState: MutableStateFlow<FileScreenUiState> = MutableStateFlow(FileScreenUiState.Loading)
    val uiState: StateFlow<FileScreenUiState> = _uiState

    val ioScrop = CoroutineScope(Dispatchers.IO)

    init {
        _uiState.value = FileScreenUiState.Loading
        dlog("init FileViewModel")
        getVideosInfo()
    }


    private fun getVideosInfo() {

        ioScrop.launch {
            videoInfoRepository.getVideosInfo().collect {
                _uiState.value = FileScreenUiState.Success(it)
                dlog("uiState getVideosInfo in thread: ${Thread.currentThread().name}")
            }
        }

    }

    fun deleteVideoInfo(videoInfo: VideoInfo) {
        dlog("on deleteVideoInfo")
        CoroutineScope(Dispatchers.IO).launch {
            videoInfoRepository.delete(videoInfo)
            try {
                File(videoInfo.uri).delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun dlog(msg: String) {

    }

    @Throws(Exception::class)
    fun shareVideo(uri:String, context: Context) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, Uri.parse(uri))
            type = "video/*"
        }
        context.startActivity(Intent.createChooser(shareIntent, null))
    }

//    fun getVideosInfo

//    companion object {
//    @Suppress("UNCHECKED_CAST")
//    fun provideFactory(
//        videoInfoRepository: VideoInfoRepository = VideoInfoRepository()
//    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return FileViewModel(videoInfoRepository) as T
//            }
//        }
//    }

}

sealed class FileScreenUiState {
    data class Success(val videosInfo: List<VideoInfo>) : FileScreenUiState()
    object Loading: FileScreenUiState()
    object Error: FileScreenUiState()
}

