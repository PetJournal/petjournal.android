package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.model.Pets
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.shimmerEffect

@Composable
fun PetIcon(
    modifier: Modifier = Modifier,
    imageRes: Painter? = null,
    isSelected: Boolean = false,
    isLoading: Boolean = false,
) {
    val backgroundColor = if (isSelected) ColorCustom.color_background_pet_icon else Color.White

    Box(
        modifier =
            modifier
                .then(
                    if (isLoading) {
                        Modifier
                            .size(55.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()
                    } else {
                        Modifier
                            .shadow(
                                elevation = 13.1762.dp,
                                spotColor = ColorCustom.color_spot_pet_icon,
                                ambientColor = ColorCustom.color_spot_pet_icon,
                            )
                            .padding(1.dp)
                            .size(55.dp)
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .border(
                                width = 1.dp,
                                color = ColorCustom.color_border_pet_icon,
                                shape = RoundedCornerShape(8.dp),
                            )
                    },
                ),
        contentAlignment = Alignment.Center,
    ) {
        if (!isLoading) {
            when {
                imageRes is VectorPainter -> {
                    Icon(
                        painter = imageRes,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else ColorCustom.color_background_pet_icon,
                        modifier =
                            Modifier
                                .size(32.dp)
                                .offset(y = 4.dp)
                                .testTag("SelectedIcon"),
                    )
                }

                imageRes != null -> {
                    Image(
                        painter = imageRes,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                    )

                    if (isSelected) {
                        val defaultPainter = painterResource(id = R.drawable.icon_pet_selected)
                        Icon(
                            painter = defaultPainter,
                            contentDescription = null,
                            tint = ColorCustom.color_background_pet_icon,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }

                else -> {
                    if (isSelected) {
                        val defaultPainter = painterResource(id = R.drawable.icon_pet_selected)
                        Icon(
                            painter = defaultPainter,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PetFilterItem(
    name: String,
    isSelected: Boolean,
    imageRes: Painter? = null,
    isLoading: Boolean = false,
    onSelect: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .then(
                    if (isLoading) {
                        Modifier
                    } else {
                        Modifier
                            .padding(start = 0.dp, end = 16.dp)
                            .clickable { onSelect(name) }
                    },
                )
                .testTag("PetItem_$name"),
    ) {
        PetIcon(
            imageRes = if (name == "Todos") painterResource(id = R.drawable.icon_pet_selected) else imageRes,
            isSelected = isSelected,
            isLoading = isLoading,
        )
        Text(
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight(500),
            color = if (isLoading) Color.Transparent else ColorCustom.color_title_pet_icon,
            textAlign = TextAlign.Center,
            text = name,
            maxLines = 1,
            modifier =
                if (isLoading) {
                    Modifier
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                } else {
                    Modifier
                },
        )
    }
}

@Composable
fun PetFilterList(
    listPet: List<Pets> = listOf(),
    isLoading: Boolean = false,
    onSelectedPet: (String) -> Unit = {},
) {
    var selectedPets by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier) {
        Text(
            text = stringResource(R.string.which_pets_need_this_task),
            color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight(500),
            style = MaterialTheme.typography.titleMedium,
            modifier =
                if (isLoading) {
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                } else {
                    Modifier
                },
        )

        if (isLoading) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(5) {
                    PetFilterItem(
                        name = "        ",
                        isSelected = false,
                        isLoading = true,
                        onSelect = {},
                    )
                }
            }
        } else {
            LazyRow(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    PetFilterItem(
                        name = stringResource(R.string.label_all_pets),
                        isSelected = selectedPets.contains(stringResource(R.string.label_all_pets)),
                        imageRes = null,
                        isLoading = false,
                        onSelect = { name ->
                            selectedPets =
                                if (selectedPets.contains(name)) {
                                    selectedPets - name
                                } else {
                                    selectedPets + name
                                }
                            onSelectedPet(if (selectedPets.contains(name)) name else "")
                        },
                    )
                }
                items(
                    items = listPet,
                    key = { it.id },
                ) { item ->
                    PetFilterItem(
                        name = item.name!!,
                        isSelected = selectedPets.contains(item.name),
                        imageRes = item.imageRes,
                        isLoading = false,
                        onSelect = { name ->
                            selectedPets =
                                if (selectedPets.contains(name)) {
                                    selectedPets - name
                                } else {
                                    selectedPets + name
                                }
                            onSelectedPet(if (selectedPets.contains(name)) name else "")
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun PetFilterListPreview() {
    val listPet =
        listOf(
            Pets(
                id = 1,
                imageRes = painterResource(R.drawable.image_jujuba),
                name = "Jujuba",
            ),
            Pets(
                id = 2,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 1423,
                imageRes = painterResource(R.drawable.image_jujuba),
                name = "Jujuba",
            ),
            Pets(
                id = 245,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 1455,
                imageRes = painterResource(R.drawable.image_jujuba),
                name = "Jujuba",
            ),
            Pets(
                id = 6452,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
        )

    PetFilterList(
        listPet,
        onSelectedPet = {},
    )
}
