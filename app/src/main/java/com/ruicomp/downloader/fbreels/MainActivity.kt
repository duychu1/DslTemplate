package com.ruicomp.downloader.fbreels

import android.content.ClipboardManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.WindowCompat
import com.ruicomp.downloader.fbreels.ui.AppUi

class MainActivity : ComponentActivity() {

    private lateinit var clipboard: ClipboardManager
    private var isWindowFocus: MutableState<Boolean> = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        setContent { AppUi(clipboard, isWindowFocus) }
    }
}

