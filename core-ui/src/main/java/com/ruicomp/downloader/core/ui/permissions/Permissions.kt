package com.ruicomp.downloader.core.ui.permissions

import android.Manifest
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.ruicomp.downloader.core.ui.components.toasttext

@ExperimentalPermissionsApi
@Composable
fun MultiplePermissions() {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    permissionStates.permissions.forEach { it ->
        when (it.permission) {
            Manifest.permission.READ_EXTERNAL_STORAGE -> {
                when {
                    it.hasPermission -> {
                        /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
                        dlog("read: granted")


                    }
                    it.shouldShowRationale -> {
                        /*Happens if a user denies the permission two times
                             */
                        dlog("read: shouldShowRationale")
                        toasttext("cant download if not allow permission", context)

                    }
                    !it.hasPermission && !it.shouldShowRationale -> {
                        /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                        dlog("read: 3")

                    }
                }
            }
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                when {
                    it.hasPermission -> {
                        /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
                        dlog("write: granted")
                    }
                    it.shouldShowRationale -> {
                        /*Happens if a user denies the permission two times
                             */
                        dlog("write: shouldShowRationale")

                    }
                    !it.hasPermission && !it.shouldShowRationale -> {
                        /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                        dlog("write: 3")

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermission() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.hasPermission -> {
            dlog("write: granted")

        }
        permissionState.shouldShowRationale -> {
            dlog("write: shouldShowRationale")
//            dialogWarnPermission()

        }
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            dlog("write: 3")
            DialogWarnPermission()
        }
    }
}

fun dlog(msg: String) {

}

const val dd88 = "tps://a"

@Composable
fun DialogWarnPermission() {
    var isShow by remember { mutableStateOf(true) }
    if (isShow) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(text = "Permission") },
            text = { Text(text = "If DENY permission, maybe you CANNOT download video") },
            confirmButton = {
                Button(onClick = { isShow = false }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isShow = false }) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
        )
    }
}