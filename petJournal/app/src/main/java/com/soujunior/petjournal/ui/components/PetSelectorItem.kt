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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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
    onClick: () -> Unit = {},
) {
    val backgroundColor = if (isSelected) ColorCustom.color_background_pet_icon else Color.White
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier =
            modifier
                .then(
                    if (isLoading) {
                        Modifier
                            .size(55.dp)
                            .clip(shape)
                            .shimmerEffect()
                    } else {
                        Modifier
                            .shadow(
                                elevation = 13.1762.dp,
                                spotColor = ColorCustom.color_spot_pet_icon,
                                ambientColor = ColorCustom.color_spot_pet_icon,
                                shape = shape,
                            )
                            .size(55.dp)
                            .clip(shape)
                            .background(color = backgroundColor)
                            .border(width = 1.dp, color = ColorCustom.color_border_pet_icon, shape = shape)
                            .clickable { onClick() }
                            .padding(1.dp)
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
                        modifier = Modifier.size(32.dp).offset(y = 4.dp).testTag("SelectedIcon"),
                    )
                }
                imageRes != null -> {
                    Image(
                        painter = imageRes,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(shape),
                    )
                    if (isSelected) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_pet_selected),
                            contentDescription = null,
                            tint = ColorCustom.color_background_pet_icon,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }
                else -> {
                    if (isSelected) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_pet_selected),
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
    onSelect: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .padding(start = 0.dp, end = 16.dp)
                .testTag("PetItem_$name"),
    ) {
        PetIcon(
            imageRes =
                if (name == stringResource(R.string.label_all_pets)) {
                    painterResource(id = R.drawable.icon_pet_selected)
                } else {
                    imageRes
                },
            isSelected = isSelected,
            isLoading = isLoading,
            onClick = { onSelect() },
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight(500),
            color = if (isLoading) Color.Transparent else ColorCustom.color_title_pet_icon,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier =
                Modifier
                    .padding(top = 4.dp)
                    .then(if (isLoading) Modifier.clip(RoundedCornerShape(4.dp)).shimmerEffect() else Modifier),
        )
    }
}

@Composable
fun PetFilterList(
    listPet: List<Pets> = listOf(),
    isLoading: Boolean = false,
    selectedIds: List<String> = listOf(),
    onSelectionChanged: (List<String>) -> Unit = {},
) {
    val labelAll = stringResource(R.string.label_all_pets)
    val allPetIds = listPet.mapNotNull { it.id }
    val isAllSelected = allPetIds.isNotEmpty() && selectedIds.containsAll(allPetIds)

    Column(modifier = Modifier) {
        Text(
            text = stringResource(R.string.which_pets_need_this_task),
            color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight(500),
            style = MaterialTheme.typography.titleMedium,
            modifier = if (isLoading) Modifier.clip(RoundedCornerShape(4.dp)).shimmerEffect() else Modifier,
        )

        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(5) {
                    PetFilterItem(name = "", isSelected = false, isLoading = true, onSelect = {})
                }
            }
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    PetFilterItem(
                        name = labelAll,
                        isSelected = isAllSelected,
                        imageRes = null,
                        isLoading = false,
                        onSelect = {
                            if (isAllSelected) {
                                onSelectionChanged(emptyList())
                            } else {
                                onSelectionChanged(allPetIds)
                            }
                        },
                    )
                }

                items(
                    items = listPet,
                    key = { it.id ?: it.hashCode() },
                ) { pet ->
                    val petId = pet.id ?: ""
                    val isThisPetSelected = selectedIds.contains(petId)
                    val painter = rememberAsyncImagePainter(model = pet.imageRes)

                    PetFilterItem(
                        name = pet.name ?: "",
                        isSelected = isThisPetSelected,
                        imageRes = painter,
                        isLoading = false,
                        onSelect = {
                            val newList = selectedIds.toMutableList()
                            if (isThisPetSelected) {
                                newList.remove(petId)
                            } else {
                                newList.add(petId)
                            }
                            onSelectionChanged(newList)
                        },
                    )
                }
            }
        }
    }
}
