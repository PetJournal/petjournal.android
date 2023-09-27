package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCustom(
    titleTopBar: String = "",
    titleTopBarColor: Color = MaterialTheme.colorScheme.primary,
    titleTopBarAligh: Alignment = Center,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    showButtonToReturn: Boolean = false,
    showMenu: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
) {
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
                        actions = actions
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