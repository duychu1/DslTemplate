package com.ruicomp.downloader.feature.download

import android.app.Activity
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ruicomp.downloader.core.datastore.MyDatastore
import com.ruicomp.downloader.core.model.VideoInfo
import com.ruicomp.downloader.core.ui.components.toasttext
import com.ruicomp.downloader.download.video.Result
import com.ruicomp.downloader.feature.download.fake.Iap
import com.ruicomp.downloader.feature.download.fake.ManagerInterAds
import com.ruicomp.downloader.feature.download.repository.DownloadVideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val managerAds: ManagerInterAds,
    private val insertVideoInfoUseCaseDb: InsertVideoInfoUseCase,
    private val downloadVideoRepository: DownloadVideoRepository,
    val iap: Iap
) : ViewModel() {

    var appVip = true

    var receiveUrl: String? = null

    private val _statusState = mutableStateOf("Let start")
    val statusState: State<String> = _statusState

    private val _btnSwap = mutableStateOf(true)
    val btnSwap: State<Boolean> = _btnSwap

    private val _textField = mutableStateOf("")
    val textField: State<String> = _textField

    private var nRate = 1
    private var _isRate = false
    val isRate = mutableStateOf(false)

    val isShare = mutableStateOf(false)
//    val isShare: State<Boolean> = _isShare


    fun onReceiveUrl(inurl:String) {
        dlog("receive: $inurl")
    }

    fun subscribe1y(context: Context) {
        iap.iapConnector?.subscribe(context as Activity, "remove_ads")
    }

    fun subscribe6m(context: Context) {
        iap.iapConnector?.subscribe(context as Activity, "remove_ads_6m")
    }

    fun createInterAds(context: Context, isVip: Boolean = appVip) {
        if (isVip) return
        if (managerAds.admobInters.mInterstitialAd == null) {
            dlog("create admob inter")
            managerAds.admobInters.loadAd(context)
        }
        if (managerAds.applovinInters.maxInterstitialAd == null) {
            dlog("create applovin inter")
            managerAds.applovinInters.createAd(context as Activity)
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.windowFocus -> {
                CoroutineScope(Dispatchers.IO).launch {
                    dlog("downloadViewModel: on Event windowFocus")

                    getTextClipboard(event.clipboard)?.let { newUrl ->

                        if (handleUrl(newUrl)) return@launch

                        getDataAndDownload(event.context)
                    }

                }
            }

            is Event.topBtnClick -> {
                if (_btnSwap.value) {
                    dlog("topClick: download")

                    CoroutineScope(Dispatchers.IO).launch {
                        if (_textField.value.contains("tiktok.com")) {
                            getDataAndDownload(event.context)
                        } else {
                            _statusState.value = "Link not correct"
                        }
                    }

                } else {
                    dlog("topclick: Open")

                    try {
                        openTiktok()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }

            is Event.botBtnClick -> {
                if (!_btnSwap.value) {
                    dlog("botClick: download")

                    CoroutineScope(Dispatchers.IO).launch {
                        if (_textField.value.contains("tiktok.com")) {
                            getDataAndDownload(event.context)
                        } else {
                            _statusState.value = "Link not correct"
                        }
                    }

                } else {
                    dlog("botclick: Open")

                    try {
                        openTiktok()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            is Event.textFieldChanged -> {
                _textField.value = event.value
            }

            is Event.pasteBtnClick -> {
                dlog("click paste")

                getTextClipboard(event.clipboard)?.let { _textField.value = it }
            }

            is Event.btnSwapClick -> {
                dlog("click swap")
                _btnSwap.value = !_btnSwap.value
            }
        }
    }

    private fun dlog(msg: String) {

    }

    private fun handleUrl(newUrl: String): Boolean {
        if (newUrl == _textField.value) {
            return true
        } else {
            _textField.value = newUrl
        }

        if (!_textField.value.contains("tiktok.com")) {
            _statusState.value = "Link not correct"
            return true
        }

        _statusState.value = "Downloading..."
        dlog(newUrl)
        return false
    }

    fun windowFocusChange(clipboard: ClipboardManager) {
        dlog("window focus viewmodel")
    }

    private suspend fun getDataAndDownload(context: Context, isVip: Boolean = appVip) {
        if (!isVip) {
            withContext(Dispatchers.Main) {
                managerAds.showInters(context)
            }
        }

        rateSetup(context)
        vipCheck(context)

        toastCoroutine(context, "Downloading...")
        //first step for download
        val getData = downloadVideoRepository.fetchData(_textField.value)
        when (getData) {
            is Result.Success -> {
                val videoData = getData.data
                val fileName = videoData.authorName + "_" + videoData.aid.drop(3)
                val duration = videoData.duration
//                Log.d("TAG", "onEvent: lURL: ${videoData.lDownloadUrl}")
//                dlog("thread pasrdata: ${Thread.currentThread()}")

                val saveVideo = downloadVideoRepository.saveToInternal(
                    inUrl = videoData.lDownloadUrl.getString(0),
                    fileName = fileName,
                    context = context,
                )
                when (saveVideo) {
                    is Result.Success -> {
                        _statusState.value = "Done: ${fileName.take(18)}..."
                        toastCoroutine(context, "Download complete")
                        val videoInfo = VideoInfo(
                            id = 0,
                            title = fileName,
                            uri = saveVideo.data,
                            duration = duration
                        )
                        insertVideoInfoUseCaseDb(videoInfo)
                    }
                    is Result.Error -> {
                        _statusState.value = "Error save to storage"
                        toastCoroutine(context, "Error save to storage")
                    }
                }

            }
            is Result.Error -> {
                _statusState.value = getData.msg
                toastCoroutine(context, "Can't get data")
            }
        }
    }

    private fun vipCheck(context: Context) {
        if (appVip != iap.vip) {
            CoroutineScope(Dispatchers.IO).launch {
                appVip = iap.vip
                MyDatastore.saveDatastore(context, "isVip", iap.vip)
            }
        }
    }

    private fun rateSetup(context: Context) {
        CoroutineScope(Dispatchers.Default).launch {
            if (nRate == 1) {

                _isRate = MyDatastore.getDatastore(context, "isRate", false)
                dlog("is rate from data store: $_isRate")

            }

            if (nRate == 5 && !_isRate) {
                isRate.value = true
                _isRate = true
            }

            if (nRate < 6) {
                nRate++
            }
        }
    }

    private fun openTiktok() {
//        val intent: Intent? =
//            App.appcontext.packageManager.getLaunchIntentForPackage("com.ss.android.ugc.trill")
//
//        try {
//            App.appcontext.startActivity(intent)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }

    fun getTextClipboard(clipboard: ClipboardManager): String? {
        when {
            !clipboard.hasPrimaryClip() -> return null
            !(clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))!! -> return null
        }
        val item = clipboard.primaryClip?.getItemAt(0)

        return item?.text.toString()
    }

    private suspend fun toastCoroutine(context:Context, msg: String) = withContext(Dispatchers.Main) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }catch (e:Exception) {
            dlog("toast false")
        }
    }

//    companion object {
//        @Suppress("UNCHECKED_CAST")
//        fun provideFactory(
//            managerAds: ManagerInterAds = ManagerInterAds(),
//            insetVideoInfo: InsertVideoInfo = InsertVideoInfo(VideoInfoRepository()),
//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return DownloadViewModel(managerAds, insetVideoInfo) as T
//            }
//
//        }
//    }
}

const val c9 = "il/?aweme_id="