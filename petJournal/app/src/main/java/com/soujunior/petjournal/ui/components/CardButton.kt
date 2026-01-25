package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    image: Painter,
    imageColorFilter: ColorFilter? = null,
    cardColor: Color,
    text: String? = null,
    textColor: Color = Color.Unspecified,
    submit: () -> Unit,
) {
    Surface(
        color = cardColor,
        shape = RoundedCornerShape(8.dp),
        modifier =
            modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = submit,
            ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
            ) {
                Image(
                    painter = image,
                    colorFilter = imageColorFilter,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .then(
                                if (text.isNullOrBlank()) {
                                    Modifier
                                        .padding(16.dp)
                                        .fillMaxSize(1f)
                                } else {
                                    Modifier
                                        .fillMaxWidth(0.5f)
                                        .size(80.dp)
                                        .padding(top = 16.dp)
                                },
                            ),
                )
                text?.let {
                    Text(
                        text = text,
                        fontSize = 12.ssp,
                        color = textColor,
                        modifier =
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .then(
                                    if (text.isBlank()) {
                                        Modifier
                                    } else {
                                        Modifier
                                    },
                                )
                                .padding(bottom = 16.sdp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardButtonWithTextCardColorCorrectPreview() {
    MaterialTheme {
        CardButton(
            text = "Home",
            imageColorFilter = ColorFilter.tint(Color.White),
            textColor = Color.White,
            modifier =
                Modifier
                    .size(150.dp)
                    .padding(16.dp),
            image =
                androidx.compose.ui.graphics.vector.rememberVectorPainter(
                    androidx.compose.material.icons.Icons.Default.Home,
                ),
            cardColor = MaterialTheme.colorScheme.primary,
            submit = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardButtonWithTextPreview() {
    MaterialTheme {
        CardButton(
            text = "Home",
            imageColorFilter = ColorFilter.tint(Color.White),
            textColor = Color.White,
            modifier =
                Modifier
                    .size(150.dp)
                    .padding(16.dp),
            image =
                androidx.compose.ui.graphics.vector.rememberVectorPainter(
                    androidx.compose.material.icons.Icons.Default.Home,
                ),
            cardColor = MaterialTheme.colorScheme.primaryContainer,
            submit = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardButtonPreview() {
    MaterialTheme {
        CardButton(
            modifier =
                Modifier
                    .size(150.dp)
                    .padding(16.dp),
            image =
                androidx.compose.ui.graphics.vector.rememberVectorPainter(
                    androidx.compose.material.icons.Icons.Default.Home,
                ),
            cardColor = MaterialTheme.colorScheme.primaryContainer,
            submit = {},
        )
    }
}
