package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun SelectableButton(
    titleButton: String,
    colorButton: Color,
    modifierSelectableButton: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelectionChange: (Boolean) -> Unit = {}
) {
    androidx.compose.material3.Button(
        modifier = modifierSelectableButton
            .width(115.dp)
            .height(70.dp)
            .padding(top = 15.dp, end = 15.dp, bottom = 15.dp)
            .then(
                if (isSelected) {
                    Modifier.shadow(
                        elevation = 10.dp,
                        spotColor = ColorCustom.shadow_color_selectable_button,
                        ambientColor = ColorCustom.shadow_color_selectable_button
                    )
                } else {
                    Modifier
                }
            ),
        onClick = { onSelectionChange(!isSelected) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorButton else MaterialTheme.colorScheme.onPrimary,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else colorButton
        ),
        shape = RoundedCornerShape(size = 16.dp),
        border = if (!isSelected) BorderStroke(
            1.dp,
            ColorCustom.border_color_selectable_button
        ) else null,
    ) {
        Text(
            text = titleButton,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontWeight = FontWeight(500),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else colorButton,
                textAlign = TextAlign.Center,
            )
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GroupSelectableButton(
    onSelection: (Boolean) -> Unit = {}
) {
    val buttons = listOf(
        SelectableButtonInfo(stringResource(R.string.label_selectable_button_vaccines), ColorCustom.color_selectable_button_1),
        SelectableButtonInfo(stringResource(R.string.label_selectable_button_consultations), ColorCustom.color_selectable_button_2),
        SelectableButtonInfo(stringResource(R.string.label_selectable_button_medicine), ColorCustom.color_selectable_button_3),
        SelectableButtonInfo(stringResource(R.string.label_selectable_button_bath), ColorCustom.color_selectable_button_4),
        SelectableButtonInfo(stringResource(R.string.label_selectable_button_food), ColorCustom.color_selectable_button_5),
        SelectableButtonInfo(stringResource(R.string.label_selectable_button_pet_walk), ColorCustom.color_selectable_button_6),
        SelectableButtonInfo("Nova Atividade", Color(0xFFFFC107)),
        SelectableButtonInfo("Outro Item", Color(0xFF4CAF50))
    )

    val selectionState = remember { mutableStateListOf(*Array(buttons.size) { false }) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.label_select_main_category),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight(500),
            lineHeight = 24.sp
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            buttons.forEachIndexed { index, buttonInfo ->
                SelectableButton(
                    titleButton = buttonInfo.title,
                    colorButton = buttonInfo.color,
                    isSelected = selectionState[index],
                    onSelectionChange = { newState ->
                        selectionState[index] = newState
                        onSelection(newState)
                    },
                    modifierSelectableButton = if (buttonInfo.title == stringResource(R.string.label_selectable_button_medicine))
                        Modifier.widthIn(min = 140.dp) else Modifier
                )
            }
        }
    }
}


data class SelectableButtonInfo(
    val title: String,
    val color: Color
)



@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun CustomSelectableButtonPreview() {
    GroupSelectableButton()
}