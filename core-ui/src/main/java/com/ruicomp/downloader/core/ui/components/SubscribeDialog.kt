package com.ruicomp.downloader.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun SubscribeDialog(
    isRemoveAds: MutableState<Boolean>,
    onSubscribe1y: () -> Unit,
    onSubscribe6m: () -> Unit,
) {
    if (!isRemoveAds.value) return
    Dialog(
        onDismissRequest = { isRemoveAds.value = false },
        content = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
//                    .background(bkgColor)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Remove Ads",
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    tonalElevation = 4.dp,
                    shape = Shapes.Full,
//                    color = surfaceColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(Shapes.Full)
                            .clickable {
                                onSubscribe1y()
                                isRemoveAds.value = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "1 year:      8.49 usd", fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    tonalElevation = 4.dp,
                    shape = Shapes.Full,
//                    color = surfaceColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(Shapes.Full)
                            .clickable {
                                onSubscribe6m()
                                isRemoveAds.value = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "6 month:      5.99 usd", fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { isRemoveAds.value = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Cancel")
                }
            }
        },
    )
}