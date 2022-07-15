package com.ruicomp.downloader.core.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ruicomp.downloader.core.ui.R

@Composable
fun ShareAppDialog(
    isShare: MutableState<Boolean>,
    clipboard: ClipboardManager
) {
    if (!isShare.value) return
    Dialog(
        onDismissRequest = { isShare.value = false },
        content = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium) //0,4,8,12,16,28,full
                    .background(MaterialTheme.colorScheme.background)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Share ${stringResource(id = R.string.app_name)}",
                    modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_qrtikerdown),
                    contentDescription = "",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(0.9f)

                )

                Spacer(modifier = Modifier.height(16.dp))

                val context = LocalContext.current

                Button(
                    onClick = {
                        isShare.value = false

//                            val appLink = "https://play.google.com/store/apps/details?id=com.ruicomp.dousaved"
                        val appLink = "https://play.google.com/store/apps/details?id=${context.packageName}"
                        val clip: ClipData = ClipData.newPlainText("url", appLink)
                        clipboard.setPrimaryClip(clip)

                        toasttext(msg ="Link copied", context)
                    },
                ) {
                    Text(text = "Copy link")
                }
            }
        },
    )
}

fun toasttext(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}




