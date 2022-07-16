package com.ruicomp.downloader.feature.filevideo

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ruicomp.downloader.core.model.VideoInfo
import com.ruicomp.downloader.core.ui.components.TdFileAppBar
import com.ruicomp.downloader.core.ui.components.TdVideoPlayer
import com.ruicomp.downloader.feature.filevideo.model.VideoInfoUi
import com.ruicomp.downloader.feature.filevideo.model.asUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

const val itemPading = 4

@Composable
fun FileScreen(
    modifier: Modifier = Modifier,
    viewModel: FileViewModel = hiltViewModel()
) {

    val dialogVideo = remember { mutableStateOf(false) }
    var uri by remember { mutableStateOf("") }

    val onImageClick: (String) -> Unit = {
        dialogVideo.value = true
        uri = it
    }

    DialogPlayVideo(dialogVideo, uri)

    val fileScreenUiState by viewModel.uiState.collectAsState()

    FileScreenContent(
        modifier,
        fileScreenUiState,
        onImageClick,
        viewModel::deleteVideoInfo,
        viewModel::shareVideo
    )
}

@Composable
private fun FileScreenContent(
    modifier: Modifier,
    fileScreenUiState: FileScreenUiState,
    onImageClick: (String) -> Unit,
    onDeleteVideoInfo: (VideoInfo) -> Unit,
    onShareVideo: (String, Context) -> Unit
) {
    Column(modifier = modifier) {
        TdFileAppBar(
            titleRes = R.string.title_file,
            navigationIcon = Icons.Default.Menu,
        )

//        BannerAds()

        dlog("on FileScreen content ")
        when (fileScreenUiState) {
            FileScreenUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                )
            }

            FileScreenUiState.Error -> {
                Text("Error")
            }

            is FileScreenUiState.Success -> {

                FileScrollContent(
                    fileScreenUiState.videosInfo,
                    onPlayVideo = onImageClick,
                    onDeleteItem = onDeleteVideoInfo,
                    onShareVideo = onShareVideo
                )
            }

        }
    }
}

@Composable
private fun FileScrollContent(
    videosInfoIn: List<VideoInfo>,
    onPlayVideo: (String) -> Unit = {},
    onDeleteItem: (VideoInfo) -> Unit = {},
    onShareVideo: (String, Context) -> Unit,
) {
    dlog("on FileScreen content, in FileScrollContent")
    val context = LocalContext.current

    val videosInfo: SnapshotStateList<VideoInfoUi> = remember {
        videosInfoIn.map {
            it.asUi()
        }.toMutableStateList()

//        val tmp = videosInfoIn.map {
//            it.toVideoInfo(it)
//        }
//
//        dlog(toJson(tmp))
//        tmp.toMutableStateList()
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = itemPading.dp),
    ) {
        itemsIndexed(videosInfo) { index, item ->

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = (itemPading * 2).dp, horizontal = itemPading.dp)
            ) {
                CardVideoItem(
                    item.title,
                    item.uri,
                    item.duration,
                    item.thumbnail,
                    R.drawable.ic_share,
                    onImageClick = { onPlayVideo(item.uri) },
                    onShareIconClick = { onShareVideo(item.uri, context) },
                    onDeleteIconClick = {
                        onDeleteItem(item.asModel())
                        videosInfo.removeAt(index)
                    },
                    onLoadImage =
                        LaunchedEffect(key1 = Unit) {
                            withContext(Dispatchers.IO) {
                                item.thumbnail.value = getThumbnailBitmap(item.uri, context)
                            }
                        }
                )
            }
        }
    }


}

@Composable
private fun CardVideoItem(
    title: String,
    uri: String,
    duration: String,
    imageBitmap: MutableState<Bitmap?>,
    icon: Int,
    onImageClick: () -> Unit = {},
    onShareIconClick: () -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
    onLoadImage: Unit = Unit,
    modifier: Modifier = Modifier
) {
    MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraSmall)
            .background(MaterialTheme.colorScheme.primary)
            .padding(1.dp)


    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            ThumbnailContainer(imageBitmap, onImageClick, onLoadImage)

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .clickable(onClick = onDeleteIconClick)
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Text(
                text = duration,
                modifier = Modifier
                    .padding(top = 2.dp, end = 5.dp)
                    .align(Alignment.TopEnd),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        //description
        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 3.dp),
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterStart),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(vertical = 3.dp)
                    .clickable(onClick = onShareIconClick)
            )
//            }
        }
    }
}

@Composable
private fun ThumbnailContainer(
    imageBitmap: MutableState<Bitmap?>,
    onImageClick: () -> Unit,
    onLoadImage: Unit,
) {
    if (imageBitmap.value != null) {
        Image(
            bitmap = imageBitmap.value!!.asImageBitmap(),
            contentDescription = "pic",
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(onClick = onImageClick),
            contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            modifier = Modifier.fillMaxHeight(0.4f),
            imageVector = Icons.Rounded.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )

        onLoadImage

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DialogPlayVideo(dialogVideo: MutableState<Boolean>, uri: String) {
    if (dialogVideo.value) {
        Dialog(
            onDismissRequest = { dialogVideo.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                dlog("uri: $uri")
                TdVideoPlayer(uri = uri)
            }
        }

    }
}

fun dlog(msg: String) {

}

fun getThumbnailBitmap(
    path: String,
    context: Context,
): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)
        else context.contentResolver.loadThumbnail(
            Uri.parse(path),
            Size(300, 300),
            CancellationSignal()
        )
    } catch (e: Exception) {
        null
    }
}

fun getVideoDuration(
    path: String,
    context: Context,
): String? {
    return try {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(path)
        } catch (e: Exception) {
            retriever.setDataSource(context, Uri.parse(path))
        }

        var duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toInt()

        duration /= 1000

        val formatter = DecimalFormat("00")

        "${formatter.format(duration/60)}:${formatter.format(duration%60)}"

    } catch (e:Exception) {
        null
    }
}



@Composable
private fun CardVideoItemCoil(
    title: String,
    uri: String,
    duration: String,
    icon: Int,
    onImageClick: () -> Unit = {},
    onShareIconClick: () -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraSmall)
            .background(MaterialTheme.colorScheme.primary)
            .padding(1.dp)


    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Color.DarkGray)
                .clickable(onClick = onImageClick)
            ,
            contentAlignment = Alignment.Center
        ) {

//            testCoil(uri)

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .clickable(onClick = onDeleteIconClick)
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Text(
                text = duration,
                modifier = Modifier
                    .padding(top = 2.dp, end = 5.dp)
                    .align(Alignment.TopEnd),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

        }

        //description
        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 3.dp),
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterStart),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(vertical = 3.dp)
                    .clickable(onClick = onShareIconClick)
            )
//            }
        }
    }
}


@Preview
@Composable
fun TestCard() {
//    CardVideoItem(
//        "1234567890",
//        "content://media/external/file/1703",
//        "00:19",
//        null,
//        R.drawable.ic_share,
//        modifier = Modifier.width(128.dp)
//    )
}

//@Composable
//fun   testCoil(uri: String) {
//
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(uri)
//            .decoderFactory(VideoFrameDecoder.Factory())
//            .videoFrameMillis(2000)
//            .size(300,300)
//            .crossfade(true)
//            .fetcherDispatcher(Dispatchers.IO)
//            .build(),
//    )
//    Image(
//        modifier = Modifier.fillMaxSize(),
//        painter = painter,
//        contentDescription = null,
//        contentScale = ContentScale.Crop
//    )
//
//}