package com.soujunior.petjournal.ui.appArea.homeScreen.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.soujunior.petjournal.R
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun Carousel() {
    val pagerState = rememberPagerState(
        pageCount = natural.size,
        initialOffscreenLimit = 1
    )
    LaunchedEffect(pagerState) {
        while (true) {
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount,
                animationSpec = tween(4000)
            )
        }
    }

    Column {
        HorizontalPager(
            state = pagerState
        ) { page ->
            Card(
                modifier = Modifier
                    .aspectRatio(2.6f)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.86f,
                            stop = 0.9f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
                    .fillMaxWidth(),
                RoundedCornerShape(8.dp)
            ) {
                val natural = natural[page]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(
                            id = when (page) {
                                1 -> R.drawable.banner2
                                2 -> R.drawable.banner3
                                else -> R.drawable.banner1
                            }
                        ),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

