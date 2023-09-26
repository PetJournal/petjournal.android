package com.soujunior.petjournal.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCustom(
    titleTopBar: String = "",
    titleTopBarColor: Color = MaterialTheme.colorScheme.primary,
    titleTopBarAligh: Alignment = Center,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    showButtonToReturn: Boolean = false,
    showMenu: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
) {
    val showDropdownMenu = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (showTopBar) {
                Surface(shadowElevation = shadowBelowTopBar) {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        title = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = titleTopBar,
                                    color = titleTopBarColor,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .align(titleTopBarAligh),
                                    fontSize = 22.sp
                                )
                            }
                        },
                        navigationIcon = {
                            if (showButtonToReturn) {
                                IconButton(onClick = {
                                    navigationUp.navigateUp()
                                }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                                }
                            }
                        },
                        actions = {
                            if (showMenu) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menu),
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(end = 20.dp)
                                        .clickable {
                                            Log.e(TAG, "menu foi clicado")
                                            showDropdownMenu.value = true
                                        }
                                )
                                if (showDropdownMenu.value) {
                                    DropdownMenu(
                                        expanded = showDropdownMenu.value,
                                        onDismissRequest = { showDropdownMenu.value = false },
                                        modifier = Modifier.padding(end = 20.dp)
                                    ) {
                                        DropdownMenuItem(
                                            onClick = {
                                                showDropdownMenu.value = false
                                                Log.e(TAG, "Logout")
                                            },
                                            text = {
                                                Text(text = "Logout arrumar", fontSize = 18.sp)
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Logout,
                                                    contentDescription = "Logout"
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            if (showBottomBarNavigation) {
                bottomNavigationBar()
            }
        },
        content = { it ->
            contentToUse(it)
        },
        modifier = Modifier.shadow(4.dp)
    )
}