package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PetIcon(
    imageRes: Painter? = null,
    isSelected: Boolean = false,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(if (isSelected) Color(0xFF9C4DCC) else Color.White)
            .border(
                width = 2.dp,
                color = if (isSelected) Color(0xFF9C4DCC) else Color(0xFFCCCCCC),
            ),
        contentAlignment = Alignment.Center
    ) {
        if (imageRes != null) {
            Image(
                painter = imageRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected || imageRes != null) Color.White else Color(0xFF9C4DCC),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun PetFilterItem(
    name: String,
    isSelected: Boolean,
    icon: ImageVector,
    imageRes: Painter? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        PetIcon(
            imageRes = imageRes,
            isSelected = isSelected,
            icon = icon
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun PetFilterListPreview() {
    val icon = Icons.Default.ArrowOutward
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            PetFilterItem(name = "Jujuba", isSelected = false, icon = icon, imageRes = null)
            PetFilterItem(
                name = "Jujuba",
                isSelected = true,
                icon = icon,
                imageRes = null
            )
        }
        Column {
            PetFilterItem(name = "Todos", isSelected = false, icon = icon)
            PetFilterItem(name = "Todos", isSelected = true, icon = icon)
        }
        Column {
            PetFilterItem(
                name = "Alfredo",
                isSelected = false,
                icon = icon,
                imageRes = null
            )
            PetFilterItem(
                name = "Alfredo",
                isSelected = true,
                icon = icon,
                imageRes = null
            )
        }
    }
}

