package com.ruicomp.downloader.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun TdVideoPlayer(uri: String) {
    VideoPlayer(uri = uri)
}

@Composable
fun VideoPlayer(uri:String) {
    val context = LocalContext.current
    var playWhenReady by remember { mutableStateOf(true) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = ExoPlayer.REPEAT_MODE_OFF
            playWhenReady = playWhenReady
            prepare()
            play()
        }
    }
    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
//                    FrameLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                    )
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun VideoPlayer(){
    val sampleVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val context = LocalContext.current
    val player = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(sampleVideo)
    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    player.setMediaItem(mediaItem)
    playerView.player = player
    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady

    }
    AndroidView(factory = {
        playerView
    })
}

//@Composable
//fun Dialog(content: @Composable () -> Unit) {
//    Popup(popupPositionProvider = object : PopupPositionProvider {
//        override fun calculatePosition(
//            anchorBounds: IntRect,
//            windowSize: IntSize,
//            layoutDirection: LayoutDirection,
//            popupContentSize: IntSize
//        ): IntOffset {
//            return IntOffset.Zero
//        }
//
//    }) {
//        Box(
//            modifier = Modifier.fillMaxSize().background(Color.Green),
//            contentAlignment = Alignment.Center,
//            content = {
//                Text("AAA")
//            }
//        )
//    }
//}