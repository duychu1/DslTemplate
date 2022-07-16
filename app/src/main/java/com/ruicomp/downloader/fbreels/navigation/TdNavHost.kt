package com.ruicomp.downloader.fbreels.navigation

import android.content.ClipboardManager
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruicomp.downloader.fbreels.R
import com.ruicomp.downloader.feature.download.DownloadScreen
import com.ruicomp.downloader.feature.filevideo.FileScreen

@Composable
fun TdNavHost(
    navController: NavHostController = rememberNavController(),
    isWindowFocus: State<Boolean>,
    clipboard: ClipboardManager,
    openCloseDrawer:() -> Unit,
    startDestination: String = BottomBarScreen.Download.route,
    modifier: Modifier = Modifier
) {
    dlog("checkRecompose: onNavHost (before Download Screen) ")
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = BottomBarScreen.Download.route) {
            DownloadScreen(
                isWindowFocus,
                clipboard,
                openCloseDrawer
            )
        }
        composable(route = BottomBarScreen.File.route) { NavBackStackEntry ->
            val currentDestination1 = NavBackStackEntry.destination
            FileScreen()
        }
    }

}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val iconDrawable: Int
) {
    object Download : BottomBarScreen(
        route = "download",
        title = "Download",
        iconDrawable = R.drawable.ic_download
    )

    object File : BottomBarScreen(
        route = "file",
        title = "File",
        iconDrawable = R.drawable.ic_folder
    )
}


fun dlog(msg: String) {

}


