package com.ruicomp.downloader.fbreels.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun TdBottomBar(
    navController: NavHostController = rememberNavController(),
) {
    val screens = listOf(BottomBarScreen.Download, BottomBarScreen.File)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary.copy(0.1f),
            thickness = 1.dp
        )

//        CompositionLocalProvider(LocalElevationOverlay provides null) {
            NavigationBar(
//                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                screens.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        },

                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconDrawable),
                                contentDescription = "Navigation Icon"
                            )
                        },
                        label = { Text(screen.title) }
                    )
                }
            }
//        }
    }

}
