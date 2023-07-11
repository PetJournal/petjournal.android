package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.soujunior.petjournal.R

@Composable
fun NavigationBar() {
    val darkTheme = isSystemInDarkTheme()
    Row(modifier = Modifier.fillMaxHeight()) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                backgroundColor = if (darkTheme) Color(0xFFFF4081) else Color(0xFFB90063)
            ) {
                BottomNavigationItem(
                    icon = {
                        Image(
                            painterResource(id = R.drawable.home_button_bar),
                            contentDescription = null
                        )
                    },
                    selected = true,
                    selectedContentColor = Color(0xFF54C1E9),
                    unselectedContentColor = Color.White,
                    onClick = { }
                )

                BottomNavigationItem(
                    icon = {
                        Image(
                            painterResource(id = R.drawable.pets_button_bar),
                            contentDescription = null
                        )
                    },
                    selected = true,
                    onClick = { }
                )
                BottomNavigationItem(
                    icon = {
                        Image(
                            painterResource(id = R.drawable.tutor_button_bar),
                            contentDescription = null
                        )
                    },
                    selected = true,
                    onClick = { }
                )
            }
        }
    }
}