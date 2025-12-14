package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

@Composable
fun SelectableButton(
    titleButton: String,
    colorButton: Color,
    isSelected: Boolean,
    modifierSelectableButton: Modifier = Modifier,
    onSelectionChanged: (String, Boolean) -> Unit,
) {
    Button(
        modifier =
            modifierSelectableButton
                .height(40.dp)
                .then(
                    if (isSelected) {
                        Modifier.shadow(
                            elevation = 10.dp,
                            spotColor = ColorCustom.shadow_color_selectable_button,
                            ambientColor = ColorCustom.shadow_color_selectable_button,
                        )
                    } else {
                        Modifier
                    },
                ),
        onClick = {
            onSelectionChanged(titleButton, !isSelected)
        },
        colors =
            ButtonDefaults.buttonColors(
                containerColor = if (isSelected) colorButton else MaterialTheme.colorScheme.onPrimary,
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else colorButton,
            ),
        shape = RoundedCornerShape(size = 16.dp),
        border =
            if (!isSelected) {
                BorderStroke(
                    1.dp,
                    ColorCustom.border_color_selectable_button,
                )
            } else {
                null
            },
    ) {
        Text(
            text = titleButton,
            style =
                TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else colorButton,
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GroupSelectableButton(
    listOfTasks: List<SelectableButtonInfo>,
    onSelection: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
) {
    val selectionState = remember { mutableStateListOf(*Array(listOfTasks.size) { false }) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.label_select_main_category),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight(500),
            lineHeight = 24.sp,
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            maxItemsInEachRow = maxItemsInEachRow,
        ) {
            listOfTasks.forEachIndexed { index, buttonInfo ->
                SelectableButton(
                    titleButton = buttonInfo.title,
                    colorButton = buttonInfo.color,
                    isSelected = selectionState[index],
                    onSelectionChanged = { title, selected ->
                        selectionState[index] = selected
                        if (selected) {
                            onSelection(title)
                        } else {
                            onSelection("")
                        }
                    },
                    modifierSelectableButton =
                        Modifier
                            .adaptiveWidthForTitle(buttonInfo.title)
                            .padding(bottom = 15.dp),
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
    GroupSelectableButton(listOfTasks)
}
