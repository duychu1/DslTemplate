package com.ruicomp.downloader.fbreels.ui

import android.content.ClipboardManager
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ruicomp.downloader.core.ui.components.TdDrawer
import com.ruicomp.downloader.core.ui.permissions.SinglePermission
import com.ruicomp.downloader.core.ui.theme.ReelSaveTheme
import com.ruicomp.downloader.fbreels.navigation.TdBottomBar
import com.ruicomp.downloader.fbreels.navigation.TdNavHost
import com.ruicomp.downloader.fbreels.navigation.dlog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppUi(
    clipboard: ClipboardManager,
    isWindowFocus: MutableState<Boolean>
) {
    ReelSaveTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppContent(clipboard = clipboard, isWindowFocus = isWindowFocus)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(
    clipboard: ClipboardManager,
    isWindowFocus: State<Boolean>,
    modifier: Modifier = Modifier,
) {
    dlog("checkRecompose: onMainScaffold")
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scopeDrawer = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            TdDrawer(
                clipboard = clipboard,
                onCloseDrawer = { openOrCloseDrawer(scopeDrawer, drawerState) }
            )
        },
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerShape = MaterialTheme.shapes.extraLarge.copy(
            topStart = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        )
    ) {
        Scaffold(
            bottomBar = {
                TdBottomBar(navController = navController)
            },
        ) {
            dlog("checkRecompose: onMainScaffold content")

        //        Box(modifier = modifier
        //            .fillMaxSize()
        //            .padding(it)
        //        ) {
                TdNavHost(
                    navController = navController,
                    isWindowFocus = isWindowFocus,
                    clipboard = clipboard,
                    openCloseDrawer = { openOrCloseDrawer(scopeDrawer, drawerState) },
                    modifier = Modifier.padding(it)
                )
        //        }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                SinglePermission()
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
private fun openOrCloseDrawer(
    scopeDrawer: CoroutineScope,
    drawerState: DrawerState
) {
    scopeDrawer.launch {
        drawerState.apply {
            if (isClosed) open() else close()
        }
    }
}