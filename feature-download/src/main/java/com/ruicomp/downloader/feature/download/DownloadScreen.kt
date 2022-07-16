package com.ruicomp.downloader.feature.download

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ruicomp.downloader.core.ui.components.*

var currentFocus = false


@Composable
fun DownloadScreen(
    isWindowFocus: State<Boolean>,
    clipboard: ClipboardManager,
    openCloseDrawer:() -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DownloadViewModel = hiltViewModel()
) {

//
    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current
//    val activity = context as MainActivity
//    val intent = activity.intent

    dlog("checkRecompose: on DownloadScreen")

    val windowChange by isWindowFocus
    val status by viewModel.statusState
    val textField by viewModel.textField
    val btnSwap by viewModel.btnSwap
    val isVip = viewModel.iap.vip
//    val livedata by liveDataWindowFocus

    LaunchedEffect(Unit) {
        dlog("load ads here")
        viewModel.createInterAds(context)
    }

    LaunchedEffect(key1 = windowChange) {
        if(windowChange == !currentFocus) {
            dlog("window focus: $windowChange")
            currentFocus = windowChange
            viewModel.onEvent(Event.windowFocus(context,clipboard))
        }
    }

    DownloadScreenContent(
        modifier = modifier,
        clipboard = clipboard,
        isWindowFocus = windowChange,
        status = status,
        textField = textField,
        btnSwap = btnSwap,
        isVip = isVip,
        onEvent = viewModel::onEvent,
        onSubscribe1y = viewModel::subscribe1y,
        onSubscribe6m = viewModel::subscribe6m,
        openCloseDrawer = openCloseDrawer,

    )

    if (viewModel.isRate.value) {
        ReviewDialog(viewModel.isRate) {
            showRatting(context)
        }
    }

}


@Composable
private fun DownloadScreenContent(
    modifier: Modifier,
    clipboard: ClipboardManager,
    isWindowFocus: Boolean,
    status: String,
    textField: String,
    btnSwap: Boolean,
    isVip:Boolean,
    onEvent: (Event) -> Unit,
    onSubscribe1y: (Context) -> Unit,
    onSubscribe6m: (Context) -> Unit,
    openCloseDrawer:() -> Unit
) {
    val context = LocalContext.current

    val isRemoveAds = remember { mutableStateOf(false) }
    val isShareApp = remember { mutableStateOf(false) }

//    WindowFocusListener(isWindowFocus = isWindowFocus)
    SubscribeDialog(
        isRemoveAds,
        onSubscribe1y = { onSubscribe1y(context) },
        onSubscribe6m = { onSubscribe6m(context) }
    )

    ShareAppDialog(isShare = isShareApp, clipboard = clipboard)

    dlog("checkRecompose: on DownloadScreenContent")

    Column(modifier = modifier) {
        TdDownloadAppBar(
            titleRes = R.string.app_name,
            navigationIcon = Icons.Default.Menu,
            actionIcon = R.drawable.ic_share,
            onNavigationClick = openCloseDrawer,
            onActionLeftClick = { isRemoveAds.value = true },
            onActionRightClick = { isShareApp.value = true }
        )

        dlog("checkRecompose: on DownloadScreenContent, in Column")


        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp)
        ) {
            val (topAd, centerTutorial, botFunction) = createRefs()
            dlog("checkRecompose: on DownloadScreenContent, in Column, in Constraint")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(324.dp)
//                    .height(450.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(Color.Transparent)
                    .constrainAs(topAd) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.TopCenter
            ) {
//                NativeAds(context)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(260.dp)
//                    .background(Color.Red)
//                            .wrapContentSize(align = Alignment.BottomCenter)
                    .constrainAs(botFunction) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                DownloadFunctionContent(
                    status = status,
                    btnSwap = btnSwap,
                    onEvent = onEvent,
                    context = context,
                    textField = textField,
                    clipboard = clipboard
                )

            }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(centerTutorial) {
//                    top.linkTo(topAd.bottom)
//                    bottom.linkTo(botFunction.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    height = Dimension.fillToConstraints
//                }
////                .background(Color.DarkGray)
//                .padding(top = 10.dp)
//        ) {
//            TutorialOld()
//        }


        }

//        DialogShareApp(clipboard, viewModel.isShare)


    }


}

fun dlog(mag: String) {

}

@Composable
private fun ColumnScope.DownloadFunctionContent(
    status: String,
    btnSwap: Boolean,
    onEvent: (Event) -> Unit,
    context: Context,
    textField: String,
    clipboard: ClipboardManager
) {
    //260dp duoi len
    Spacer(modifier = Modifier.height(17.dp))
//                Box(modifier = Modifier.fillMaxWidth().height(17.dp).background(Color.Cyan))
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(15.dp)
    ) {
        Text(
            text = "Tutorial",
            modifier = Modifier
                .height(15.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .align(Alignment.CenterStart)
                .clickable {
                    val link = context.resources.getString(R.string.link_tutorial)
                    val intentTutorial = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    try {
                        context.startActivity(intentTutorial)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
        )

        Text(
            text = status,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier
                .height(15.dp)
                .align(Alignment.Center)
        )
    }

//    Text(
//        text = status,
//        style = MaterialTheme.typography.bodySmall,
//        color = Color.Gray,
//        modifier = Modifier
//            .height(15.dp)
//            .align(Alignment.CenterHorizontally)
//    )

    Spacer(modifier = Modifier.height(10.dp))

    //218dp duoi len
    ButtonDownAndOpen(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.Blue,
        shape = MaterialTheme.shapes.extraLarge.copy(
            bottomEnd = CornerSize(6.dp),
            bottomStart = CornerSize(6.dp)
        ),
        name = if (btnSwap) {
            stringResource(id = R.string.Download)
        } else stringResource(id = R.string.Open_Tiktok),
        onSwapClick = { onEvent(Event.btnSwapClick) },
        onclick = { onEvent(Event.topBtnClick(context)) }
//                onclick = {}
    )

    Spacer(modifier = Modifier.height(6.dp))

    //52dp
    TextPaste(
        textField = textField,
        onTextFieldChanged = { onEvent(Event.textFieldChanged(it)) },
        onPasteClick = { onEvent(Event.pasteBtnClick(clipboard)) }
    )

    Spacer(modifier = Modifier.height(6.dp))

    ButtonDownAndOpen(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.Green,
        shape = MaterialTheme.shapes.extraLarge.copy(
            topStart = CornerSize(6.dp),
            topEnd = CornerSize(6.dp)
        ),
        name = if (!btnSwap) {
            stringResource(id = R.string.Download)
        } else stringResource(id = R.string.Open_Tiktok),
        isSwap = true,
        onSwapClick = { onEvent(Event.btnSwapClick) },
        onclick = { onEvent(Event.botBtnClick(context)) }
//                onclick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = "Directory: /Download/${stringResource(id = R.string.app_name)}/",
        modifier = Modifier
//            .fillMaxWidth()
//            .height(24.dp),
            .align(Alignment.CenterHorizontally),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = Color.DarkGray,
    )

    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun WindowFocusListener(isWindowFocus: Int) {
//    val windowChange = isWindowFocus
    dlog(isWindowFocus.toString())
    isWindowFocus

}



@Composable
fun ButtonDownAndOpen(
    modifier: Modifier,
    color: Color,
    shape: Shape,
    name: String,
    isSwap: Boolean = false,
    onSwapClick: () -> Unit,
    onclick: () -> Unit
) {
    Button(
        onClick = onclick,
        modifier = modifier.height(50.dp),
//        colors = ButtonDefaults.buttonColors(backgroundColor = pri, contentColor = Color.White),
        shape = shape
    )
    {
        Box(Modifier.fillMaxSize()) {
//            IconButton(
//                onClick = { /*TODO*/ },
//                modifier = Modifier.align(Alignment.CenterStart)
//
//            ) {
//                Icon(painter = painterResource(id = R.drawable.ic_swap_vert), contentDescription = "swap")
//            }
            if (isSwap) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_swap_vert),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable(onClick = onSwapClick),
                    contentDescription = "swap",
                    tint = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
                )
            }

            Text(
                name,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun TextPaste(textField: String, onTextFieldChanged: (String) -> Unit, onPasteClick: () -> Unit) {

    Box(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = textField,
            onValueChange = onTextFieldChanged,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 52.dp)
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(6.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
//            readOnly = true,
            singleLine = true,
            //sample link: https://vt.tiktok.com/ZSdyYxFdd/
            placeholder = { Text(text = "Ex: https://vt.tiktok.com/tIKeRDowN/", color = Color.DarkGray)}
        )
        Button(
            onClick = onPasteClick,
            modifier = Modifier
                .height(50.dp)
                .clip(RoundedCornerShape(6.dp))
                .align(Alignment.CenterEnd))
        {
            Text(text = "Paste", fontSize = 16.sp)
        }

    }
}


@Composable
fun TextAnnotation(inString: String, color: Color, textSize: TextUnit,) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {

        val str:String    = inString
        val textAnno = "video tutorial"
        val webStartIndex = str.indexOf(textAnno)
        val webEndIndex   = webStartIndex + textAnno.length
        val textAnno2 = "Navigation gestures"
        val fbStartIndex  = str.indexOf(textAnno2)
        val fbEndIndex  = fbStartIndex + textAnno2.length
        append(str)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = textSize,
                textDecoration = TextDecoration.Underline
            ),
            start = webStartIndex,
            end = webEndIndex
        )

        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.youtube.com/watch?v=PTF65LB-0q0",
            start = webStartIndex,
            end = webEndIndex
        )

        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = textSize,
                textDecoration = TextDecoration.Underline
            ),
            start = fbStartIndex,
            end = fbEndIndex
        )

        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.youtube.com/shorts/dfTY4SxTnSg",
            start = fbStartIndex,
            end = fbEndIndex
        )


    }

// UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

// 🔥 Clickable text returns position of text that is clicked in onClick callback
    ClickableText(
        text = annotatedLinkString,
        style = TextStyle(
            fontSize = textSize,
            color = color
        ),
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}

const val h1 = "ktokv.c"

@Preview
@Composable
fun btnPrev() {
    ButtonDownAndOpen(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.Blue,
        shape = MaterialTheme.shapes.extraLarge.copy(
            bottomEnd = CornerSize(6.dp),
            bottomStart = CornerSize(6.dp)
        ),
        name = if(true) {stringResource(id = R.string.Download)} else stringResource(id = R.string.Open_Tiktok),
        onSwapClick = {  },
        onclick = {  }
    )
}

//@Composable
//private fun TutorialOld() {
//    val textColor = tutorial_color
//    val fontSize = 14.sp
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        item {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(MaterialTheme.shapes.extraSmall)
//                    .background(Brush.verticalGradient(listOf(bkg_ad, nearBlack)))
//                    .padding(start = 6.dp, bottom = 2.dp, end = 6.dp),
//            ) {
//
//                Box(modifier = Modifier.fillMaxWidth()) {
//                    Text(
//                        color = Color.LightGray,
//                        fontSize = fontSize,
//                        text = "HOW TO USE",
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//                Text(
//                    color = textColor,
//                    fontSize = 15.sp,
//                    fontStyle = FontStyle.Italic,
//                    text = "Method 1 (recommend):",
//                    modifier = Modifier.padding(start = 15.dp)
//                )
////                    Text(color = textColor, fontSize = fontSize, text = "Click Share -> Copy link -> Open TikerDown, your video automatic download " +
////                            "(watch video tutorial, switch between 2 app by 'quick gestures')")
//                TextAnnotation(
//                    inString = stringResource(id = R.string.string_anno),
//                    color = textColor,
//                    textSize = fontSize
//                )
//
//                Text(
//                    color = textColor,
//                    fontSize = 15.sp,
//                    fontStyle = FontStyle.Italic,
//                    text = "Method 2:",
//                    modifier = Modifier.padding(start = 15.dp)
//                )
//                Text(
//                    color = textColor,
//                    fontSize = fontSize,
//                    text = "Click Share -> Other -> Chose TikerDown and your video automatic download"
//                )
//            }
//        }
//
//    }
//}

