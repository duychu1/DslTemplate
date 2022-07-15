package com.ruicomp.downloader.core.ui.components

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.play.core.review.ReviewManagerFactory
import com.ruicomp.downloader.core.datastore.MyDatastore
import com.ruicomp.downloader.core.ui.R
import kotlinx.coroutines.launch



@Composable
fun TdDrawer(
    clipboard: ClipboardManager,
    onCloseDrawer: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.large.copy(
                topStart = CornerSize(0),
                bottomEnd = CornerSize(0))
            )
//            .statusBarsPadding()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {

        headerLogo(onCloseDrawer)

//        itemRemoveAds(iapConnector, context, isAds)

        itemDrawerTutorial(
            icon = Icons.Rounded.Settings,
            text = "How to use")
        {
            val link = context.resources.getString(R.string.link_tutorial)
            val intentTutorial = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            try {
                context.startActivity(intentTutorial)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        itemDrawerDirectory(
            icon = painterResource(id = R.drawable.ic_folder),
            text = "Directory") {

        }


        itemDrawerShare(
            icon = painterResource(id = R.drawable.ic_share),
            text = "Share") {
//                            val appLink = "https://play.google.com/store/apps/details?id=com.ruicomp.dousaved"
            val appLink = "https://play.google.com/store/apps/details?id=${context.packageName}"
            val clip: ClipData = ClipData.newPlainText("url", appLink)
            clipboard.setPrimaryClip(clip)

            toasttext(msg = "Link copied", context)
        }

        itemDrawerRate(
            icon = Icons.Rounded.Star,
            text = "Rate") { showRatting(context) }


    }
}

@Composable
private fun headerLogo(onCloseDrawer: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable(onClick = onCloseDrawer)
        )
        Row(
            Modifier
//                    .fillMaxHeight()
                .fillMaxSize(),
//                    .padding(start = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//                Box(modifier = Modifier.height(100.dp).width(100.dp)){}
            Image(
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = "App icon",
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
//                    .background(bkg_icon)
            )

            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = stringResource(id = R.string.header_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        }
    }
}

//@Composable
//private fun itemRemoveAds(
//    iapConnector: IapConnector,
//    context: Context,
//    isClick: MutableState<Boolean>
//) {
////    var isClick by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//                .clip(Shapes.Full)
////        .padding(start = 16.dp, end = 24.dp)
//                .clickable { isClick.value = !isClick.value },
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Spacer(modifier = Modifier.width(14.dp))
//            AdIcon2()
//            Spacer(modifier = Modifier.width(10.dp))
//            Text(text = "Remove Ads")
////        Spacer(modifier = Modifier.width(24.dp).align(Alignment.End)
//        }
//
//        if (isClick.value) {
//            AnimatedVisibility(
//                visible = isClick.value,
//                enter = expandVertically(),
//                exit = shrinkVertically()
//            ) {
//
//                adsItem(
//                    iapConnector,
//                    context,
//                    context.resources.getString(R.string.ads6m),
//                    text1 = "6 Month",
//                    text2 = stringResource(id = R.string._799usd)
//                )
//
//                adsItem(
//                    iapConnector,
//                    context,
//                    context.resources.getString(R.string.ads1y),
//                    text1 = "1 Year",
//                    text2 = stringResource(id = R.string._999usd)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun adsItem(iapConnector: IapConnector, context: Context, subId:String, text1:String, text2: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(42.dp)
//            .clip(Shapes.Full)
//            .clickable {
//                iapConnector.subscribe(
//                    context as Activity,
//                    subId
//                )
//            },
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Spacer(modifier = Modifier.width(54.dp))
//        textCustom("$text1:")
//        Text(
//            text = text2,
//            color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
//            modifier = Modifier.padding(horizontal = 15.dp),
//            style = MaterialTheme.typography.body2
//
//        )
//    }
//}

@Composable
fun itemDrawerTutorial(icon: ImageVector, text: String, onClick: () -> Unit ) {

    var isClick by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(Shapes.Full)
//        .padding(start = 16.dp, end = 24.dp)
                .clickable { isClick = !isClick },
            horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text)
//        Spacer(modifier = Modifier.width(24.dp).align(Alignment.End)
        }

//        if (isClick) {
            AnimatedVisibility(
                visible = isClick,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clip(Shapes.Full)
                        .clickable { onClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(54.dp))
                    val text = "Video tutorial"

                    textCustom(text)
                }
            }
//        }
    }
}

@Composable
fun itemDrawerDirectory(icon: Painter, text: String, onClick: () -> Unit ) {

    var isClick by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(Shapes.Full)
//        .padding(start = 16.dp, end = 24.dp)
                .clickable { isClick = !isClick },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(painter = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text)
//        Spacer(modifier = Modifier.width(24.dp).align(Alignment.End)
        }

        AnimatedVisibility(
            visible = isClick,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clip(Shapes.Full),
//                .clickable { iapConnector.subscribe(context as Activity, App.appcontext.resources.getString(R.string.ads6m)) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(54.dp))
                textCustom("/Download/${stringResource(id = R.string.app_name)}/")
            }
        }
    }


}

@Composable
fun itemDrawerShare(icon: Painter, text: String, onClick: () -> Unit ) {

    var isClick by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(Shapes.Full)
//        .padding(start = 16.dp, end = 24.dp)
                .clickable { isClick = !isClick },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(painter = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text)
//        Spacer(modifier = Modifier.width(24.dp).align(Alignment.End)
        }

//        if (isClick) {
            AnimatedVisibility(
                visible = isClick,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Shapes.Full),
                    horizontalAlignment = Alignment.CenterHorizontally
//                .clickable { iapConnector.subscribe(context as Activity, App.appcontext.resources.getString(R.string.ads6m)) },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_qrtikerdown),
                        contentDescription = "",
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth(0.5f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onClick() },
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = "Copy link", color = Color.White)
                    }

                }
            }
//        }
    }


}

@Composable
fun itemDrawerRate(icon: ImageVector, text: String, onClick: () -> Unit ) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(Shapes.Full)
//        .padding(start = 16.dp, end = 24.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text)
//        Spacer(modifier = Modifier.width(24.dp).align(Alignment.End)
    }
}

@Composable
private fun textCustom(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        modifier = Modifier.padding(horizontal = 20.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}

//fun showRatting(context: Context) {}

fun showRatting(context: Context) {
    try {
        val manager = ReviewManagerFactory.create(context)
//            val manager = FakeReviewManager(context)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(context as Activity, reviewInfo!!)
                flow.addOnCompleteListener { _ ->
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
//                    dlog("review complete")
                    MyDatastore.saveDatastore(context, "isRate", true)
                }
            } else {
                // There was some problem, log or handle the error code.
//                    @ReviewErrorCode val reviewErrorCode = (task.exception as TaskException).errorCode
                val appLink = "https://play.google.com/store/apps/details?id=${context.packageName}"
                val intentRate = Intent(Intent.ACTION_VIEW, Uri.parse(appLink))
                try {
                    context.startActivity(intentRate)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

const val y = "om/aweme/v1/aweme/deta"



