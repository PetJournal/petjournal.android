package com.soujunior.petjournal.ui.components

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.domain.model.response.PetResponse
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PetList(
    pets: List<PetResponse>,
    onAddNewPet: () -> Unit = { },
) {
    if (pets.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Surface(
                modifier =
                    Modifier
                        .size(108.sdp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                            onClick = onAddNewPet,
                        ),
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
                            fontSize = 15.ssp,
                            color = Color.Black.copy(alpha = 0.5f),
                            lineHeight = 14.ssp,
                        )
                    }
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
                    val imageUrl = pet.petImage
                    val isLoading = remember { mutableStateOf(false) }
                    val hasError = remember { mutableStateOf(false) }

                    LaunchedEffect(imageUrl) {
                        isLoading.value = true
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
                                        isLoading.value = false
                                        hasError.value = !success
                                    },
                                )

                                if (isLoading.value) {
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

                                if (hasError.value && !isLoading.value) {
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

@Preview(showBackground = true, name = "Lista de Pets")
@Composable
private fun PreviewPetList() {
    val mockPets =
        listOf(
            PetResponse("Baleia", "url"),
            PetResponse("Rex", "url"),
            PetResponse("Nome Muito Longo de Pet", "url"),
        )
    PetList(pets = mockPets)
}

@Preview(showBackground = true, name = "Lista de Pets vazia")
@Composable
private fun PreviewPetList2() {
    val mockPets = emptyList<PetResponse>()
    PetList(pets = mockPets)
}
