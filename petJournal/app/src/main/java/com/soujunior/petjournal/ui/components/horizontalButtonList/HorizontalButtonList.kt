package com.soujunior.petjournal.ui.components.horizontalButtonList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Apps
import androidx.compose.material.icons.sharp.Pets
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.CardButton
import com.soujunior.petjournal.ui.model.TagOption
import com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen.shimmerEffect

@Composable
fun GridButtonList(
    menuItems: List<TagOption>,
    quantityColumn: Int,
    onItemClick: (TagOption) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(quantityColumn),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(menuItems) { item ->
            CardButton(
                text = item.label,
                imageColorFilter = ColorFilter.tint(Color.White),
                textColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(99.dp)
                        .padding(bottom = 5.dp),
                image = rememberVectorPainter(image = item.icon),
                cardColor = item.color,
                submit = { onItemClick(item) },
            )
        }
    }
}

@Composable
fun HorizontalButtonList(
    onItemClick: (String) -> Unit,
    menuItems: List<TagOption>,
    isLoading: Boolean,
) {
    LazyRow(
        contentPadding =
            PaddingValues(
                start = 0.dp,
                end = 16.dp,
                top = 0.dp,
                bottom = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (isLoading) {
            items(4) {
                Box(
                    modifier =
                        Modifier
                            .size(99.dp)
                            .padding(bottom = 5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .shimmerEffect(),
                )
            }
        } else {
            items(menuItems) { item ->
                CardButton(
                    text = item.label,
                    imageColorFilter = ColorFilter.tint(Color.White),
                    textColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    modifier =
                        Modifier
                            .size(99.dp)
                            .padding(bottom = 5.dp),
                    image = rememberVectorPainter(image = item.icon),
                    cardColor = item.color,
                    submit = { onItemClick(item.id) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalButtonListPreview() {
    val menuItems =
        listOf(
            TagOption(
                id = "2",
                label = "Todos",
                icon = Icons.Sharp.Apps,
                color = MaterialTheme.colorScheme.primary,
            ),
            TagOption(
                id = "2",
                label = "Vacinas",
                icon = Icons.Sharp.Apps,
                color = MaterialTheme.colorScheme.primary,
            ),
        )
    MaterialTheme {
        HorizontalButtonList(
            onItemClick = {},
            menuItems = menuItems,
            isLoading = false,
        )
    }
}

@Preview(showBackground = true, name = "Grid 2 Colunas")
@Composable
private fun GridButtonListPreview() {
    val menuItems =
        listOf(
            TagOption(id = "1", label = "Todos", icon = Icons.Sharp.Apps, color = MaterialTheme.colorScheme.primary),
            TagOption(id = "2", label = "Vacinas", icon = Icons.Sharp.Pets, color = MaterialTheme.colorScheme.secondary),
            TagOption(id = "3", label = "Banho", icon = Icons.Sharp.Apps, color = MaterialTheme.colorScheme.tertiary),
            TagOption(id = "4", label = "Consulta", icon = Icons.Sharp.Pets, color = MaterialTheme.colorScheme.error),
            TagOption(id = "5", label = "Exames", icon = Icons.Sharp.Apps, color = MaterialTheme.colorScheme.primary),
            TagOption(id = "6", label = "Hotel", icon = Icons.Sharp.Pets, color = MaterialTheme.colorScheme.secondary),
        )

    MaterialTheme {
        GridButtonList(
            menuItems = menuItems,
            quantityColumn = 3,
            onItemClick = {},
        )
    }
}
