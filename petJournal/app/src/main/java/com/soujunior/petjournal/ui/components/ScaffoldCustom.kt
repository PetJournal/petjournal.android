package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ScaffoldCustom(
//    titleTopBar: String = "",
//    isLoading: Boolean = false,
//    titleTopBarColor: Color = MaterialTheme.colorScheme.primary,
//    titleTopBarAligh: Alignment = Center,
//    shadowBelowTopBar: Dp = 4.dp,
//    showTopBar: Boolean = false,
//    actions: @Composable RowScope.() -> Unit = {},
//    showActions: Boolean = false,
//    showButtonToReturn: Boolean = false,
//    showBottomBarNavigation: Boolean = false,
//    navigationUp: NavController,
//    bottomNavigationBar: @Composable () -> Unit = {},
//    contentToUse: @Composable (PaddingValues) -> Unit = {},
//    modifier: Modifier = Modifier
//) {
//    Scaffold(
//        /*
//        topBar = {
//            if (showTopBar) {
//                Surface(shadowElevation = shadowBelowTopBar) {
//                    TopAppBar(
//                        colors = TopAppBarDefaults.topAppBarColors(
//                            containerColor = MaterialTheme.colorScheme.onPrimary,
//                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                        ),
//                        title = {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(16.dp),
//                                    contentAlignment = Center
//                                ) {
//                                    LoadingText(
//                                        titleTopBar = titleTopBar,
//                                        titleTopBarColor = titleTopBarColor,
//                                        modifierShimemr = Modifier.align(titleTopBarAligh),
//                                        modifierText = Modifier.align(titleTopBarAligh),
//                                        isLoading = isLoading,
//                                    )
//                                }
//                        },
//                        navigationIcon = {
//                                if (showButtonToReturn) {
//                                    IconButton(onClick = {
//                                        navigationUp.navigateUp()
//                                    }) {
//                                        Icon(
//                                            Icons.Filled.ArrowBack,
//                                            contentDescription = "Voltar"
//                                        )
//                                    }
//                                } else {
//                                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
//                                }
//                        },
//                        actions = {
//                            if(showActions) actions()
//                            else Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
//                        }
//                    )
//
//                }
//            }
//        },
//
//         */
//        bottomBar = {
//            if (showBottomBarNavigation) {
//                bottomNavigationBar()
//            }
//        },
//        content = { it ->
//            contentToUse(it)
//        },
//        modifier = modifier.shadow(4.dp)
//    )
//
//}

//This scaffold is customized to Design 2.0?
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCustom(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    titleTopBar: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    showActions: Boolean = false,
    showButtonToReturn: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Text(
                            text = titleTopBar,
                            fontSize = 24.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigationUp.navigateUp()
                        }) {
                            Icon(
                                Icons.Filled.ArrowBackIos,
                                contentDescription = "Voltar",
                            )
                        }
                    },
                    actions = {
                        if (showActions) actions()
                        else Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                    }
                )


            }
        },
        bottomBar = {
            if (showBottomBarNavigation) {
                bottomNavigationBar()
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                contentToUse(paddingValues)
            }
        },
        modifier = modifier.shadow(4.dp)
    )
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun ScaffoldCustomPreview() {
    val nav = rememberNavController()
    ScaffoldCustom(
        modifier = Modifier,
        navigationUp = nav,
        showActions = true,
        showTopBar = true,
        titleTopBar = stringResource(R.string.edit_pet_data),
        showBottomBarNavigation = true,
        bottomNavigationBar = { NavigationBar(nav) }
    )
}