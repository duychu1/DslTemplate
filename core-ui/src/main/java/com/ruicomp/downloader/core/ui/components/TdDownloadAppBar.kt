package com.ruicomp.downloader.core.ui.components

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ruicomp.downloader.core.ui.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun TdDownloadAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String? = null,
    @DrawableRes actionIcon: Int,
    actionIconContentDescription: String? = null,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
    onActionLeftClick: () -> Unit = {},
    onActionRightClick: () -> Unit = {},
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
        actions = {

            IconButton(onClick = onActionLeftClick) {
                AdIcon2()
            }

            IconButton(onClick = onActionRightClick ) {
                Icon(
                    painter = painterResource(id = actionIcon),
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        modifier = modifier,

    )
//    Divider(Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colors.primary.copy(alpha = 0.1f)))
}


@Composable
fun FakeTdTopAppBar() {
    TdDownloadAppBar(
        titleRes = R.string.app_name,
        navigationIcon = Icons.Default.Menu,
        actionIcon = R.drawable.ic_share
    )
}

private fun Modifier.bottomElevation(bottom: Dp): Modifier = this.then(Modifier.drawWithContent {
    val paddingPx = bottom.toPx()
    clipRect(
        left = 0f,
        top = 0f,
        right = 0f,
        bottom = size.height + paddingPx
    ) {
        this@drawWithContent.drawContent()
    }
})

