package com.ruicomp.downloader.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ruicomp.downloader.core.ui.R


@Composable
fun TdFileAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    backgroundColor: Color = Color.Transparent
) {

    SmallTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },

    )
//    Divider(
//        Modifier
//            .fillMaxWidth()
//            .height(1.dp)
//            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
//    )
}

@Composable
fun FileAppbar() {
    TdFileAppBar(
        titleRes = R.string.title_file,
        navigationIcon = Icons.Default.Menu
    )
}