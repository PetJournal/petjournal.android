package com.soujunior.petjournal.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AutoCompleteDropDown(
    modifier: Modifier = Modifier,
    placeholderText: String = "Raça do seu pet",
    titleText: String = "Raça: ",
    textValue: String,
    isError: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<String>? = null,
    onEvent: (String) -> Unit,
    onDropdownItemSelected: (String) -> Unit = {},
) {

    var expanded by remember {
        mutableStateOf(false)
    }


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

        Column(modifier = modifier) {
            Row {
                Box(
                    modifier = Modifier.fillMaxWidth(),

                    ) {
                    TextField(
                        modifier = Modifier
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
                                    color = if (isError) Color.Transparent else if (expanded) Color.Transparent else Color.Black,
                                    style = stroke,
                                    cornerRadius = CornerRadius(10.dp.toPx())
                                )

                            }
                            .border(
                                2.dp,
                                if (isError) MaterialTheme.colorScheme.error
                                else if (expanded) MaterialTheme.colorScheme.primary
                                else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp)),


                        value = textValue,
                        onValueChange = {
                            onEvent(it)
                            expanded = true
                        },

                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),

                        textStyle = MaterialTheme.typography.bodyMedium,

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                expanded = !expanded
                            }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = "arrow",
                                    tint = Color.Black
                                )
                            }

                        },

                        placeholder = {
                            Text(
                                text = if (isError) "X" else placeholderText,
                                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 15.sp
                            )
                        },
                    )

                }
            }
            Row {
                if (textError != null) {
                    textError.forEach {
                        AlertText(textMessage = it, modifier = Modifier.padding(10.dp))
                    }
                } else {
                    androidx.compose.material3.Text(
                        "*Campo Obrigatório.",
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 15.sp
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .heightIn(max = 180.dp),
                    elevation = 15.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 180.dp)
                            .padding(5.dp),

                        ) {

                        if (textValue.isNotEmpty()) {
                            dropdownItems?.filter {
                                it.lowercase()
                                    .contains(textValue.lowercase()) || it.lowercase()
                                    .contains("Outro")
                            }?.let { itemList ->
                                items(
                                    itemList
                                        .sorted()
                                ) { itemFilter ->
                                    CategoryItems(true, title = itemFilter) {
                                        expanded = false
                                        onEvent(itemFilter)
                                        onDropdownItemSelected(itemFilter)
                                    }
                                }
                            }
                        } else {
                            if (dropdownItems != null) {
                                items(
                                    dropdownItems.sorted()
                                ) { itemFilter ->
                                    CategoryItems(false, title = itemFilter) {
                                        expanded = false
                                        onEvent(itemFilter)
                                        onDropdownItemSelected(itemFilter)
                                    }
                                }
                            }
                        }

                    }

                }
            }

        }

    }


}

@Composable
fun CategoryItems(
    styleSearch: Boolean?,
    title: String,
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = if (styleSearch == true) FontWeight.Bold else FontWeight.Normal
        )
    }

}
