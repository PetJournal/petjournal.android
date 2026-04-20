package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.petjournal.ui.theme.ColorCustom
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    textTitleModifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Porte do seu pet",
    titleText: String = "Title",
    isError: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<PetSizeItemModel>? = null,
    onEvent: (String) -> Unit,
    textValue: String,
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(500),
                modifier = textTitleModifier.fillMaxWidth().padding(end = 24.sdp),
            )
        }
        Row {
            Box(
                modifier =
                    textInputModifier
                        .shadow(
                            elevation = 30.dp,
                            spotColor = ColorCustom.shadow_color,
                            ambientColor = ColorCustom.shadow_color,
                        )
                        .height(50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(size = 12.dp),
                        )
                        .fillMaxWidth()
                        .drawBehind {
                            val stroke =
                                Stroke(
                                    width = 2.dp.toPx(),
                                )
                            drawRoundRect(
                                color = if (isError) Color.Transparent else onSurfaceColor,
                                style = stroke,
                                cornerRadius = CornerRadius(12.dp.toPx()),
                            )
                        }
                        .clip(RoundedCornerShape(10.sdp))
                        .clickable { isDropdownExpanded = true },
            ) {
                Text(
                    modifier =
                        Modifier
                            .padding(start = 14.sdp)
                            .align(Alignment.CenterStart),
                    text = if (isError) "X" else textValue.ifEmpty { placeholderText },
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight(300),
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                )

                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier =
                        Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd),
                )

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier =
                        Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .width(IntrinsicSize.Max)
                            .padding(top = 5.dp)
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                RoundedCornerShape(10.dp),
                            ),
                ) {
                    dropdownItems?.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                isDropdownExpanded = false
                                onEvent(item.name)
                            },
                        ) {
                            Text(text = item.name, color = onSurfaceColor)
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
