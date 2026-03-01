package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.adaptiveWidthForTitle
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SelectableButton(
    titleButton: String,
    colorButton: Color,
    isSelected: Boolean,
    isLoading: Boolean = false,
    modifierSelectableButton: Modifier = Modifier,
    onSelectionChanged: (String, Boolean) -> Unit,
) {
    Button(
        modifier =
            modifierSelectableButton
                .height(40.dp)
                .then(
                    if (isLoading) {
                        Modifier
                            .clip(RoundedCornerShape(size = 16.dp))
                            .shimmerEffect()
                    } else if (isSelected) {
                        Modifier.shadow(
                            elevation = 10.dp,
                            spotColor = ColorCustom.shadow_color_selectable_button,
                            ambientColor = ColorCustom.shadow_color_selectable_button,
                        )
                    } else {
                        Modifier
                    },
                ),
        enabled = !isLoading,
        onClick = {
            onSelectionChanged(titleButton, !isSelected)
        },
        colors =
            ButtonDefaults.buttonColors(
                containerColor =
                    if (isLoading) {
                        Color.Transparent
                    } else if (isSelected) {
                        colorButton
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (isLoading) {
                        Color.Transparent
                    } else if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        colorButton
                    },
                disabledContainerColor = if (isLoading) Color.Transparent else Color.Unspecified,
                disabledContentColor = if (isLoading) Color.Transparent else Color.Unspecified,
            ),
        shape = RoundedCornerShape(size = 16.dp),
        border =
            if (!isSelected && !isLoading) {
                BorderStroke(
                    1.dp,
                    ColorCustom.border_color_selectable_button,
                )
            } else {
                null
            },
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = titleButton,
            style =
                TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color =
                        if (isLoading) {
                            Color.Transparent
                        } else if (isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            colorButton
                        },
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GroupSelectableButton(
    listOfTags: List<SelectableButtonInfo>,
    isLoading: Boolean = false,
    showButton: Boolean = false,
    onAddClick: () -> Unit = {},
    onSelection: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
) {
    val displayTasks =
        if (isLoading && listOfTags.isEmpty()) {
            List(6) { SelectableButtonInfo("", Color.Transparent) }
        } else {
            listOfTags
        }

    val selectionState =
        remember(displayTasks.size) {
            mutableStateListOf(*Array(displayTasks.size) { false })
        }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.label_select_main_category),
                style = MaterialTheme.typography.titleMedium,
                color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight(500),
                lineHeight = 24.sp,
                modifier =
                    if (isLoading) {
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    } else {
                        Modifier
                    },
            )

            if (showButton) {
                Surface(
                    modifier =
                        Modifier
                            .clip(CircleShape)
                            .size(24.sdp)
                            .clickable(onClick = onAddClick),
                    shape = CircleShape,
                    color = Color(0xFF8D4CD2),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.addpet),
                            tint = Color.White,
                            modifier = Modifier.size(16.sdp),
                        )
                    }
                }
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            maxItemsInEachRow = if (isLoading) 3 else maxItemsInEachRow,
        ) {
            displayTasks.forEachIndexed { index, buttonInfo ->
                SelectableButton(
                    titleButton = buttonInfo.title,
                    colorButton = buttonInfo.color,
                    isSelected = if (index < selectionState.size) selectionState[index] else false,
                    isLoading = isLoading,
                    onSelectionChanged = { title, selected ->
                        if (index < selectionState.size) {
                            selectionState[index] = selected
                        }
                        if (selected) {
                            onSelection(title)
                        } else {
                            onSelection("")
                        }
                    },
                    modifierSelectableButton =
                        if (isLoading) {
                            Modifier
                                .weight(1f)
                                .padding(bottom = 15.dp)
                        } else {
                            Modifier
                                .adaptiveWidthForTitle(buttonInfo.title)
                                .padding(bottom = 15.dp)
                        },
                )
            }
        }
    }
}

data class SelectableButtonInfo(
    val title: String,
    val color: Color,
)

@Preview()
@Composable
fun CustomSelectableButtonWithoutDevicePreview() {
    val listOfTasks =
        listOf(
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_vaccines),
                ColorCustom.color_selectable_button_1,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_consultations),
                ColorCustom.color_selectable_button_2,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_medicine),
                ColorCustom.color_selectable_button_3,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_bath),
                ColorCustom.color_selectable_button_4,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_food),
                ColorCustom.color_selectable_button_5,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_pet_walk),
                ColorCustom.color_selectable_button_6,
            ),
        )
    GroupSelectableButton(listOfTags = listOfTasks, showButton = true)
}
