package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.soujunior.petjournal.R

@Composable
fun AutoComplete(
    modifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Raça do seu pet",
    titleText: String = "Raça: ",
    textValue: String,
    isError: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<String>? = null,
    onEvent: (String) -> Unit,
    onDropdownItemSelected: (String) -> Unit = {},
) {

    var race by remember { mutableStateOf("") }

    val heightTextFields by remember { mutableStateOf(55.dp) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier) {
        Row {
            androidx.compose.material3.Text(
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
                androidx.compose.material3.Text(
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
                        .height(40.dp)
                        .drawBehind {
                            val stroke = Stroke(
                                width = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx(), 0f)
                                )
                            )
                            drawRoundRect(
                                color = if (isError) Color.Transparent else Color.Black,
                                style = stroke,
                                cornerRadius = CornerRadius(10.dp.toPx())
                            )

                        }
                        .border(
                            2.dp,
                            if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { isDropdownExpanded = true }
                ) {
                    androidx.compose.material3.Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterStart),
                        text = textValue ?: placeholderText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown),
                        contentDescription = "Dropdown",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd)
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTextFields)
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        value = race,
                        onValueChange = {
                            race = it
                            isDropdownExpanded = true
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_dropdown),
                                contentDescription = "Dropdown",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .align(Alignment.CenterEnd)
                            )

                        }
                    )

                }
            }
        }
    }
}

@Composable
fun CategoryItems(
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
        Text(text = title, fontSize = 16.sp)
    }
}

@Composable
fun DropDownRacePetsPreview() {

    AutoComplete(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        titleText = "Title",
        textValue = "Selected Item",
        isError = false,
        onEvent = {},
        onDropdownItemSelected = {},
        dropdownItems = listOf(
            "Raça 1",
            "Raça 2",
            "Raça 3",
            "Raça 4",
            "Raça 5",
            "Raça 6",
            "Raça 7"
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun DropDownRacePetsPreviewWithCompositionLocalProvider() {
    CompositionLocalProvider(
        LocalSoftwareKeyboardController provides LocalSoftwareKeyboardController.current!!
    ) {
        DropDownRacePetsPreview()
    }
}

