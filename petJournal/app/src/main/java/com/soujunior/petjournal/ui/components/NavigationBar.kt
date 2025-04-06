package com.soujunior.petjournal.ui.components


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationBar(navController: NavController) {
    val darkTheme = isSystemInDarkTheme()
    val items = listOf(
        NavigationBarItems.Home,
        NavigationBarItems.Schedule,
        NavigationBarItems.Pets,
        NavigationBarItems.Profile,
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(elevation = 20.dp, spotColor = Color(0x59000000), ambientColor = Color(0x59000000))
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        backgroundColor = Color(0xFFE8D3FF),
        contentColor = Color.Gray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icons),
                        contentDescription = item.title,
                        tint = if (currentRoute == item.route) Color(0xFF8B4CC5) else Color(0xFF5E5E5E)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute == item.route) Color(0xFF8B4CC5) else Color(0xFF5E5E5E),
                        fontWeight = if (currentRoute == item.route) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selectedContentColor = Color(0xFF7F33CF),
                unselectedContentColor = Color(0xFF5E5E5E),
                selected = currentRoute?.startsWith(item.group) == true,
                onClick = {
                    val isSameGroup =
                        items.any { it.group == item.group && currentRoute?.startsWith(it.route) == true }

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

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun NavigationBarPreview() {
    val nav = rememberNavController()
    NavigationBar(nav)
}