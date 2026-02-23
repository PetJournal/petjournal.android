package com.soujunior.petjournal.ui.components

import android.widget.ImageView
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PetList(
    pets: List<PetModel>,
    isLoading: Boolean = false,
    showReloadButton: Boolean = false,
    onReload: () -> Unit = { },
    onAddNewPet: () -> Unit = { },
) {
    if (isLoading) {
        PetListShimmer()
    } else if (pets.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (!showReloadButton) {
                Surface(
                    onClick = onAddNewPet,
                    modifier = Modifier.size(108.sdp),
                    shape = RoundedCornerShape(16.sdp),
                    color = Color(0xFFD9D9D9),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Ainda sem pet.",
                                textAlign = TextAlign.Center,
                                fontSize = 18.ssp,
                                color = Color.Black.copy(alpha = 0.5f),
                                lineHeight = 14.ssp,
                            )
                        }
                    }
                }
            }

            if (showReloadButton) {
                Button(onClick = onReload, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(16.sdp),
                    )
                    Spacer(modifier = Modifier.size(8.sdp))
                    Text(text = stringResource(R.string.reload), fontSize = 12.ssp)
                }
            }
        }
    } else {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.sdp),
            contentPadding = PaddingValues(horizontal = 0.sdp),
        ) {
            items(pets) { pet ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    val imageUrl = pet.image
                    val isImageLoading = remember { mutableStateOf(false) }
                    val hasError = remember { mutableStateOf(false) }

                    LaunchedEffect(imageUrl) {
                        isImageLoading.value = true
                        hasError.value = false
                    }

                    Surface(
                        modifier = Modifier.size(108.sdp),
                        shape = RoundedCornerShape(16.sdp),
                        color = Color(0xFFD9D9D9),
                    ) {
                        if (imageUrl.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = rememberVectorPainter(image = Icons.Default.BrokenImage),
                                        contentDescription = "Pet ausente",
                                        modifier = Modifier.size(40.sdp),
                                        contentScale = ContentScale.Fit,
                                        alpha = 0.6f,
                                    )
                                }
                            }
                        } else {
                            Box(modifier = Modifier.fillMaxSize()) {
                                GlideImage(
                                    modifier = Modifier.fillMaxSize(),
                                    context = LocalContext.current,
                                    url = imageUrl,
                                    scaleType = ImageView.ScaleType.CENTER_CROP,
                                    onLoadingFinished = { success ->
                                        isImageLoading.value = false
                                        hasError.value = !success
                                    },
                                )

                                if (isImageLoading.value) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .fillMaxSize()
                                                .background(Color(0xFFDED1D1)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.sdp),
                                            color = MaterialTheme.colorScheme.primary,
                                            strokeWidth = 2.sdp,
                                        )
                                    }
                                }

                                if (hasError.value && !isImageLoading.value) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Image(
                                            painter = rememberVectorPainter(image = Icons.Default.BrokenImage),
                                            contentDescription = "Erro ao carregar imagem",
                                            modifier = Modifier.size(48.sdp),
                                            contentScale = ContentScale.Fit,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PetListShimmer() {
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

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.sdp),
        contentPadding = PaddingValues(horizontal = 0.sdp),
        userScrollEnabled = false,
    ) {
        items(1) {
            Box(
                modifier =
                    Modifier
                        .size(108.sdp)
                        .background(brush, shape = RoundedCornerShape(16.sdp)),
            )
        }
    }
}

@Preview(showBackground = true, name = "Lista de Pets em Loading")
@Composable
private fun PreviewPetListLoading() {
    PetList(
        pets = emptyList(),
        isLoading = true,
    )
}

@Preview(showBackground = true, name = "Lista de Pets vazia com Recarga")
@Composable
private fun PreviewPetListReload() {
    PetList(
        pets = emptyList(),
        showReloadButton = true,
        onReload = { },
    )
}
