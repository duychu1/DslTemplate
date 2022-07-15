package com.ruicomp.downloader.core.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ruicomp.downloader.core.datastore.MyDatastore
import com.ruicomp.downloader.core.ui.R

@Composable
fun ReviewDialog(isReview : MutableState<Boolean>, showRate: () -> Unit) {
//    val isReview = remember { mutableStateOf(true) }
    val context = LocalContext.current

    AlertDialog(onDismissRequest = {  },
        title = { Text(text = "Rate ${stringResource(id = R.string.app_name)}") },
        text = { Text(text = stringResource(id = R.string.rate_description)) },
        confirmButton = {
            Button(
                onClick = {
                    showRate()
//                    dlog( "show rate")
                    isReview.value = false
                }
            ) {
                Text(text = "RATE NOW")
            }
        },

        dismissButton = {
            TextButton(
                onClick = {
//                    dlog("remove rate")
                    isReview.value = false
                    MyDatastore.saveDatastore(context,"isRate", true)
                }
            ) {
                Text(text = "No, Thanks")
            }

        },
    ) 
}