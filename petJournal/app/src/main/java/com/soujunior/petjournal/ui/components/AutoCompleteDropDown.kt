package com.soujunior.petjournal.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.domain.model.request.PetRaceItemModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AutoCompleteDropDown(
    modifier: Modifier = Modifier,
    placeholderText: String = "Raça do seu pet",
    titleText: String = "Raça: ",
    textValue: String,
    isError: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<PetRaceItemModel>? = null,
    onEvent: (String) -> Unit,
    onDropdownItemSelected: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    val colorBorder = MaterialTheme.colorScheme.outline
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
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 5.dp),
            )
        }

        Column(modifier = modifier) {
            Row {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextField(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(50.dp)
                                .onFocusChanged { focusState ->
                                    if (focusState.isFocused) {
                                        onFocusChange(true)
                                    } else {
                                        onFocusChange(false)
                                    }
                                }
                                .drawBehind {
                                    val stroke =
                                        Stroke(
                                            width = 1.dp.toPx(),
                                            pathEffect =
                                                PathEffect.dashPathEffect(
                                                    intervals = floatArrayOf(12.dp.toPx(), 12.dp.toPx(), 0f),
                                                ),
                                        )
                                    drawRoundRect(
                                        color = if (isError) Color.Transparent else colorBorder,
                                        style = stroke,
                                        cornerRadius = CornerRadius(10.dp.toPx()),
                                    )
                                }
                                .border(
                                    2.dp,
                                    if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                                    shape = RoundedCornerShape(10.sdp),
                                )
                                .clip(RoundedCornerShape(10.sdp)),
                        value = textValue,
                        onValueChange = {
                            onEvent(it)
                            expanded = true
                        },
                        colors =
                            TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                            ),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
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
                                    tint = MaterialTheme.colorScheme.outline,
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = if (isError) "X" else placeholderText,
                                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium,
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
                    Text(
                        "*Campo Obrigatório.",
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier =
                        Modifier
                            .padding(horizontal = 5.dp)
                            .heightIn(max = 180.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                ) {
                    LazyColumn(
                        modifier =
                            Modifier
                                .heightIn(max = 180.dp)
                                .padding(5.dp),
                    ) {
                        if (textValue.isNotEmpty()) {
                            dropdownItems?.filter {
                                it.name.lowercase()
                                    .contains(textValue.lowercase()) ||
                                    it.name.lowercase()
                                        .contains("Outro")
                            }?.let { itemList ->
                                items(
                                    itemList,
                                ) { itemFilter ->
                                    CategoryItems(true, title = itemFilter.name) {
                                        expanded = false
                                        onEvent(itemFilter.name)
                                        onDropdownItemSelected(itemFilter.name)
                                    }
                                }
                            }
                        } else {
                            if (dropdownItems != null) {
                                items(
                                    dropdownItems,
                                ) { itemFilter ->
                                    CategoryItems(false, title = itemFilter.name) {
                                        expanded = false
                                        onEvent(itemFilter.name)
                                        onDropdownItemSelected(itemFilter.name)
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
    onSelect: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onSelect(title)
                }
                .padding(10.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (styleSearch == true) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AutoCompleteDropDownPreview() {
    var textValue by remember { mutableStateOf("") }

    val sampleItems =
        listOf(
            PetRaceItemModel(id = "1", name = "Golden Retriever", specieId = "dog"),
            PetRaceItemModel(id = "2", name = "Husky Siberiano", specieId = "dog"),
            PetRaceItemModel(id = "3", name = "Poodle", specieId = "dog"),
            PetRaceItemModel(id = "4", name = "Bulldog", specieId = "dog"),
            PetRaceItemModel(id = "5", name = "Vira-lata (SRD)", specieId = "dog"),
            PetRaceItemModel(id = "6", name = "Outro", specieId = "dog"),
        )

    MaterialTheme {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            AutoCompleteDropDown(
                textValue = textValue,
                dropdownItems = sampleItems,
                onEvent = { newValue -> textValue = newValue },
                onDropdownItemSelected = { selectedItem -> textValue = selectedItem },
                placeholderText = "Selecione uma raça",
                titleText = "Raça",
                isError = false,
            )
        }
    }
}
