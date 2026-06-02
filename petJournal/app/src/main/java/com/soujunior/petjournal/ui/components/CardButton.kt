package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    image: Painter,
    imageColorFilter: ColorFilter? = null,
    cardColor: Color,
    text: String? = null,
    textColor: Color = Color.Unspecified,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    submit: () -> Unit,
) {
    val hasText = !text.isNullOrBlank()
    Surface(
        color = cardColor,
        shape = shape,
        modifier =
            modifier.clickable(
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() },
                onClick = submit,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
            Image(
                painter = image,
                contentDescription = null,
                colorFilter = imageColorFilter,
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .fillMaxWidth(if (hasText) 0.5f else 0.6f)
                        .aspectRatio(1f),
            )
            if (hasText) {
                Text(
                    text = text!!,
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardButtonPreview() {
    val image = rememberVectorPainter(Icons.Default.Home)
    MaterialTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CardButton(
                text = "Home",
                image = image,
                cardColor = MaterialTheme.colorScheme.primary,
                textColor = Color.White,
                imageColorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(150.dp).padding(10.dp),
                submit = {},
            )
            CardButton(
                image = image,
                cardColor = MaterialTheme.colorScheme.secondary,
                imageColorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(100.dp).padding(10.dp),
                submit = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "Tamanho 150dp")
@Composable
fun CardButtonLargePreview() {
    MaterialTheme {
        CardButton(
            text = "Home",
            imageColorFilter = ColorFilter.tint(Color.White),
            textColor = Color.White,
            modifier =
                Modifier
                    .size(150.dp)
                    .padding(10.dp),
            image =
                rememberVectorPainter(
                    Icons.Default.Home,
                ),
            cardColor = MaterialTheme.colorScheme.primary,
            submit = {},
        )
    }
}

@Preview(showBackground = true, name = "Tamanho 100dp")
@Composable
fun CardButtonSmallPreview() {
    MaterialTheme {
        CardButton(
            text = "Pequeno",
            imageColorFilter = ColorFilter.tint(Color.White),
            textColor = Color.White,
            modifier =
                Modifier
                    .size(80.dp)
                    .padding(bottom = 5.dp),
            image =
                rememberVectorPainter(
                    Icons.Default.Home,
                ),
            cardColor = MaterialTheme.colorScheme.secondary,
            submit = {},
        )
    }
}

@Preview(showBackground = true, name = "Sem Texto")
@Composable
fun CardButtonNoTextPreview() {
    MaterialTheme {
        CardButton(
            modifier =
                Modifier
                    .size(120.dp)
                    .padding(10.dp),
            image =
                rememberVectorPainter(
                    Icons.Default.Home,
                ),
            imageColorFilter = ColorFilter.tint(Color.White),
            cardColor = MaterialTheme.colorScheme.tertiary,
            submit = {},
        )
    }
}
