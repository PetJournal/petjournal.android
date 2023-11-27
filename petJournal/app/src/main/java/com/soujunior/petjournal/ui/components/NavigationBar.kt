package com.soujunior.petjournal.ui.components


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(navController: NavController) {
    val darkTheme = isSystemInDarkTheme()
    val items = listOf(
        NavigationBarItems.Home,
        NavigationBarItems.Pets,
        NavigationBarItems.Tutor,
        )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = if (darkTheme) Color(0xFFFF4081) else Color(0xFFB90063)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            Log.e(TAG, "currentRoute: ${currentRoute}, startsWith: ${currentRoute?.startsWith(item.group)}, group: ${item.group}")

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icons),
                        contentDescription = null
                    )
                },
                selectedContentColor = Color(0xFF54C1E9),
                unselectedContentColor = Color.White,
                selected = currentRoute?.startsWith(item.group) == true,
                onClick = {
                    val isSameGroup = items.any { it.group == item.group && currentRoute?.startsWith(it.route) == true }

                    if (!isSameGroup) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}