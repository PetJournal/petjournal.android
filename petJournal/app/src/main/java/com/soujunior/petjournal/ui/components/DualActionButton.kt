package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.ui.theme.Shapes
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DualActionButton(
    isLeftSelected: Boolean = false,
    isRightSelected: Boolean = false,
    rightButtonSubmit: () -> Unit,
    leftButtonSubmit: () -> Unit,
    titleText: String = "Title",
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    leftButtonText: String = "Button",
    rightButtonText: String = "Button",
    isLoading: Boolean = false,
) {
    val activeContainerColor = MaterialTheme.colorScheme.primary
    val inactiveContainerColor = MaterialTheme.colorScheme.surface

    val activeContentColor = MaterialTheme.colorScheme.onPrimary
    val inactiveContentColor = MaterialTheme.colorScheme.primary

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = buttonModifier.fillMaxWidth(),
        ) {
            androidx.compose.material3.Button(
                onClick = { leftButtonSubmit() },
                enabled = enableButton,
                modifier =
                    modifier
                        .width(115.sdp)
                        .height(32.sdp),
                border =
                    BorderStroke(
                        width = 1.sdp,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                shape = Shapes.medium,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (isLeftSelected) activeContainerColor else inactiveContainerColor,
                        contentColor = if (isLeftSelected) activeContentColor else inactiveContentColor,
                    ),
                contentPadding = PaddingValues(2.sdp),
            ) {
                if (!isLoading) {
                    Text(
                        text = leftButtonText,
                        fontWeight = FontWeight.W900,
                        fontSize = 12.ssp,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isLeftSelected) activeContentColor else inactiveContentColor,
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(17.sdp),
                        color = if (isLeftSelected) activeContentColor else inactiveContentColor,
                    )
                }
            }

            androidx.compose.material3.Button(
                onClick = { rightButtonSubmit() },
                enabled = enableButton,
                modifier =
                    modifier
                        .width(115.sdp)
                        .height(32.sdp),
                border =
                    BorderStroke(
                        width = 1.sdp,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                shape = Shapes.medium,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (isRightSelected) activeContainerColor else inactiveContainerColor,
                        contentColor = if (isRightSelected) activeContentColor else inactiveContentColor,
                    ),
                contentPadding = PaddingValues(2.sdp),
            ) {
                if (!isLoading) {
                    Text(
                        text = rightButtonText,
                        fontWeight = FontWeight.W900,
                        fontSize = 12.ssp,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isRightSelected) activeContentColor else inactiveContentColor,
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(17.sdp),
                        color = if (isRightSelected) activeContentColor else inactiveContentColor,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DualActionButtonPreview() {
    DualActionButton(
        rightButtonSubmit = {},
        leftButtonSubmit = {},
        enableButton = true,
        leftButtonText = "Macho",
        rightButtonText = "Fêmea",
    )
}
