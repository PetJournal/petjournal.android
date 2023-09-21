package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCustom(
    titleTopBar: String = "",
    showTopBar: Boolean = false,
    showButtonToReturn: Boolean = false,
    showMenu: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    menuClick: () -> Unit = {},
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                Surface(shadowElevation = 4.dp)
                {
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
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
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
                                IconButton(onClick = { menuClick }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
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

@Composable
fun exemple(navController: NavController) {
    ScaffoldCustom(
        titleTopBar = "Titulo da PAGINA",
        showButtonToReturn = false, //opcional
        navigationUp = navController,
        showTopBar = true,
        showBottomBarNavigation = false, //opcional
        bottomNavigationBar = { NavigationBar(navController) },
        contentToUse = {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                /**O Scaffold tem um recurso chamado PaddingValues, onde ele é definido no padding
                 * do elemento, para que seja adicionado as bordas no elemento adicionado a ele.**/
                Text(
                    text = "valor para ser exibido",
                    Modifier
                        .padding(it)
                        .align(End)
                )
            }
        }
    )
}


