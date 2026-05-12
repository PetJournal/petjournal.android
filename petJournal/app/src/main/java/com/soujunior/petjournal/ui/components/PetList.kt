package com.soujunior.petjournal.ui.components

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp

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
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black.copy(alpha = 0.5f),
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
                    Text(
                        text = stringResource(R.string.reload),
                        style = MaterialTheme.typography.labelMedium,
                    )
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
                                val placeholderRes =
                                    if (pet.species?.lowercase()?.contains("gato") == true) {
                                        R.drawable.cat_profile
                                    } else {
                                        R.drawable.dog_profile
                                    }
                                Image(
                                    painter = painterResource(id = placeholderRes),
                                    contentDescription = "Placeholder do pet",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                )
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
                                        val errorPlaceholderRes =
                                            if (pet.species?.lowercase()?.contains("gato") == true) {
                                                R.drawable.cat_profile
                                            } else {
                                                R.drawable.dog_profile
                                            }
                                        Image(
                                            painter = painterResource(id = errorPlaceholderRes),
                                            contentDescription = "Erro ao carregar imagem",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop,
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(16.sdp))
                        .weight(1f)
                        .height(30.sdp)
                        .shimmerEffect(),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.sdp),
        ) {
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(16.sdp))
                        .size(108.sdp)
                        .shimmerEffect(),
            )
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(16.sdp))
                        .size(108.sdp)
                        .shimmerEffect(),
            )
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(16.sdp))
                        .size(108.sdp)
                        .shimmerEffect(),
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
