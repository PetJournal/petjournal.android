package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val items =
        remember {
            listOf(
                NavigationBarItems.Home,
                NavigationBarItems.Schedule,
                NavigationBarItems.Pets,
                NavigationBarItems.Profile,
            )
        }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(elevation = 20.dp, spotColor = Color(0x59000000), ambientColor = Color(0x59000000))
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        backgroundColor = Color(0xFFE8D3FF),
        contentColor = Color.Gray,
    ) {
        items.forEach { item ->
            val isSelected =
                currentDestination?.hierarchy?.any {
                    it.route == item.route || it.route?.startsWith(
                        item.group,
                    ) == true
                } == true
            val iconColor = if (isSelected) Color(0xFF8B4CC5) else Color(0xFF5E5E5E)
            val textColor = if (isSelected) Color(0xFF8B4CC5) else Color(0xFF5E5E5E)
            val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icons),
                        contentDescription = item.title,
                        tint = iconColor,
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = textColor,
                        fontWeight = fontWeight,
                    )
                },
                selectedContentColor = Color(0xFF7F33CF),
                unselectedContentColor = Color(0xFF5E5E5E),
                selected = isSelected,
                onClick = {
                    if (isSelected) {
                        navController.popBackStack(item.route, inclusive = false)
                    } else {
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
                },
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
