package com.soujunior.petjournal.ui.components.horizontalButtonList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.CardButton

@Composable
fun HorizontalButtonList(
    onItemClick: () -> Unit,
    menuItems: List<MenuOption>,
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
                image =
                    rememberVectorPainter(
                        Icons.Default.Home,
                    ),
                cardColor = item.color,
                submit = { onItemClick() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalButtonListPreview() {
    val menuItems =
        listOf(
            MenuOption(
                label = "Todos",
                icon = Icons.Default.Menu,
                color = MaterialTheme.colorScheme.primary,
            ),
            MenuOption(
                label = "Vacinas",
                icon = Icons.Default.Home,
                color = MaterialTheme.colorScheme.primary,
            ),
        )
    MaterialTheme {
        HorizontalButtonList(onItemClick = {}, menuItems = menuItems)
    }
}
