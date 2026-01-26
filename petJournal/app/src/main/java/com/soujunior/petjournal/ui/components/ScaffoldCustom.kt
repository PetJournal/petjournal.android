package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.RobotoRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCustom(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    titleTopBar: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    showActions: Boolean = false,
    showButtonToReturn: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TopAppBar(
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        title = {
                            Text(
                                text = titleTopBar,
                                fontSize = 22.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(RobotoRegular),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center,
                            )
                        },
                        navigationIcon = {
                            if (showButtonToReturn)
                                {
                                    IconButton(onClick = {
                                        navigationUp.navigateUp()
                                    }) {
                                        Image(
                                            painter = painterResource(id = R.drawable.navigate_before),
                                            contentDescription = stringResource(R.string.navigate_to_previous_screen),
                                            contentScale = ContentScale.None,
                                        )
                                    }
                                }
                        },
                        actions = {
                            if (showActions) {
                                actions()
                            } else {
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                            }
                        },
                    )
                }
            }
        },
        bottomBar = {
            if (showBottomBarNavigation) {
                bottomNavigationBar()
            }
        },
        floatingActionButton = floatingActionButton,
        content = { paddingValues ->
            contentToUse(paddingValues)
        },
        modifier =
            modifier
                .shadow(4.dp)
                .windowInsetsPadding(WindowInsets.systemBars),
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
        bottomNavigationBar = { NavigationBar(nav) },
    )
}
