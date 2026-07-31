package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    textTitleModifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Porte do seu pet",
    titleText: String = "Title",
    isError: Boolean = false,
    isLoading: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<String>? = null,
    onEvent: (String) -> Unit,
    textValue: String,
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(500),
                modifier =
                    textTitleModifier
                        .fillMaxWidth()
                        .padding(end = 24.sdp),
            )
        }
        Row {
            Box(
                modifier =
                    textInputModifier.then(
                        if (isLoading) {
                            Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .shimmerEffect()
                        } else {
                            Modifier
                                .shadow(
                                    elevation = 30.dp,
                                    spotColor = ColorCustom.shadow_color,
                                    ambientColor = ColorCustom.shadow_color,
                                )
                                .height(50.dp)
                                .fillMaxWidth()
                                .background(
                                    color = if (isSystemInDarkTheme()) Color.Transparent else MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(size = 12.dp),
                                )
                                .border(
                                    width = 1.dp,
                                    color =
                                        if (isError) {
                                            ColorCustom.error_color
                                        } else {
                                            MaterialTheme.colorScheme.onSurface.copy(
                                                alpha = 0.3f,
                                            )
                                        },
                                    shape = RoundedCornerShape(size = 12.dp),
                                )
                                .clip(RoundedCornerShape(size = 12.dp))
                                .clickable { isDropdownExpanded = true }
                                .testTag("inputField_test")
                        },
                    ),
            ) {
                if (isLoading) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .shimmerEffect(),
                    )
                } else {
                    Text(
                        modifier =
                            Modifier
                                .padding(start = 14.sdp)
                                .align(Alignment.CenterStart),
                        text = if (isError) "X" else textValue.ifEmpty { placeholderText },
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            if (isError) {
                                MaterialTheme.colorScheme.error
                            } else if (textValue.isEmpty()) {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                    )

                    androidx.compose.material3.Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier =
                            Modifier
                                .padding(end = 10.dp)
                                .align(Alignment.CenterEnd),
                    )

                    androidx.compose.material3.DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier =
                            Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .widthIn(min = 150.dp, max = 280.dp)
                                .padding(top = 5.dp)
                                .border(
                                    BorderStroke(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                ),
                    ) {
                        dropdownItems?.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = item)
                                },
                                onClick = {
                                    isDropdownExpanded = false
                                    onEvent(item)
                                },
                                enabled = true,
                                colors =
                                    MenuItemColors(
                                        textColor = MaterialTheme.colorScheme.onSurface,
                                        leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                        trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                                    ),
                                contentPadding = PaddingValues(horizontal = 10.sdp),
                            )
                        }
                    }
                }
            }
        }

        Row {
            textError?.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun DropDownPreview() {
    DropDown(textValue = "", onEvent = {}, dropdownItems = null, textError = null)
}
