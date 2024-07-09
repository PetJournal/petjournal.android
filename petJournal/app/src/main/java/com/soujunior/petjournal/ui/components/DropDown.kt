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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.domain.model.request.PetSizeItemModel

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Porte do seu pet",
    titleText: String = "Title",
    isError: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<PetSizeItemModel>? = null,
    onEvent: (String) -> Unit,
    textValue: String
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }


    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 5.dp)
            )
        }

        Row {
            Box(
                modifier = textInputModifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(5.dp)
                    .height(50.dp)
                    .drawBehind {
                        val stroke = Stroke(
                            width = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx(), 0f)
                            )
                        )


                        drawRoundRect(
                            color = if (isError) Color.Transparent else if (isDropdownExpanded) Color.Transparent else Color.Black,
                            style = stroke,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )

                    }
                    .border(
                        2.dp,
                        if (isError) MaterialTheme.colorScheme.error
                        else if (isDropdownExpanded) MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { isDropdownExpanded = true }
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterStart),
                    text = if (isError) "X" else textValue.ifEmpty { placeholderText },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                )

                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.CenterEnd)
                )

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .width(IntrinsicSize.Max)
                        .padding(top = 5.dp)
                        .border(
                            BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            RoundedCornerShape(10.dp)
                        )


                ) {
                    dropdownItems?.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                isDropdownExpanded = false
                                onEvent(item.name)
                            }

                        ) {
                            Text(text = item.name)
                        }
                    }
                }
            }
        }

        Row {
            if (textError != null) {
                textError.forEach {
                    AlertText(textMessage = it, modifier = Modifier.padding(10.dp))
                }
            } else {
                Text(
                    "*Campo Obrigatório.",
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 15.sp
                )
            }
        }
    }

}
