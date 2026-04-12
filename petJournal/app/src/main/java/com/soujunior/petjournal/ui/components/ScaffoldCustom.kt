package com.soujunior.petjournal.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCustom(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    titleTopBar: String = "",
    actions: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit = {},
    showActions: Boolean = false,
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    showButtonToReturn: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    containerColor: Color = MaterialTheme.colorScheme.onPrimary,
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        modifier = modifier.shadow(shadowBelowTopBar),
        containerColor = containerColor,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
        floatingActionButtonPosition = floatingActionButtonPosition,
        topBar = {
            if (showTopBar) {
                if (isLoading) {
                    TopAppBar(
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        title = {
                            val shimmerColors =
                                listOf(
                                    Color.LightGray.copy(alpha = 0.6f),
                                    Color.LightGray.copy(alpha = 0.2f),
                                    Color.LightGray.copy(alpha = 0.6f),
                                )

                            val transition = rememberInfiniteTransition(label = "shimmer")
                            val translateAnim by transition.animateFloat(
                                initialValue = 0f,
                                targetValue = 1000f,
                                animationSpec =
                                    infiniteRepeatable(
                                        animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
                                        repeatMode = RepeatMode.Restart,
                                    ),
                                label = "shimmerTranslate",
                            )

                            val brush =
                                Brush.linearGradient(
                                    colors = shimmerColors,
                                    start = Offset.Zero,
                                    end = Offset(x = translateAnim, y = translateAnim),
                                )

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(0.6f)
                                        .height(22.sdp)
                                        .background(brush, shape = RoundedCornerShape(4.sdp)),
                            )
                        },
                        navigationIcon = {},
                        actions = {},
                    )
                } else {
                    TopAppBar(
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        title = {
                            Text(
                                text = titleTopBar,
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center,
                            )
                        },
                        navigationIcon = {
                            if (showButtonToReturn) {
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
    )
}

@Preview(showBackground = true)
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
        bottomNavigationBar = { },
    )
}
