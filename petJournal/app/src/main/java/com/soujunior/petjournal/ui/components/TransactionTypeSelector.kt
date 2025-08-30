package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.TransactionType

@Composable
fun TransactionTypeSelector(
    onSelectionChanged: (TransactionType?) -> Unit
) {
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }

    val selectedColor = ColorCustom.color_background_month_disabled
    val unselectedColor = MaterialTheme.colorScheme.background
    val borderColor = ColorCustom.color_border_button_transaction_type

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            ToggleButton(
                text = stringResource(R.string.label_recurrent),
                isSelected = selectedType == TransactionType.Recurrent,
                onClick = {
                    val newType = TransactionType.Recurrent
                    selectedType = newType
                    onSelectionChanged(newType)
                },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                borderColor = if (selectedType == TransactionType.Recurrent) selectedColor else borderColor
            )

            ToggleButton(
                text = stringResource(R.string.label_one_off),
                isSelected = selectedType == TransactionType.OneOff,
                onClick = {
                    val newType = TransactionType.OneOff
                    selectedType = newType
                    onSelectionChanged(newType)
                },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                borderColor = if (selectedType == TransactionType.OneOff) selectedColor else borderColor
            )
        }

        if (selectedType == TransactionType.Recurrent) {
            onSelectionChanged(selectedType)
        } else {
            onSelectionChanged(selectedType)
        }
    }
}

@Composable
fun ToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    unselectedColor: Color,
    borderColor: Color
) {
    val backgroundColor = if (isSelected) selectedColor else unselectedColor

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(130.dp).height(50.dp)
            .clip(RoundedCornerShape(50))
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            color = ColorCustom.color_text_button_transaction_type,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionTypeSelectorPreview() {
    TransactionTypeSelector(
        onSelectionChanged = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ToggleButtonPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        ToggleButton(
            text = stringResource(R.string.label_recurrent),
            isSelected = true,
            onClick = {},
            selectedColor = ColorCustom.color_background_month_disabled,
            unselectedColor = MaterialTheme.colorScheme.background,
            borderColor = ColorCustom.color_border_button_transaction_type
        )

        ToggleButton(
            text = stringResource(R.string.label_one_off),
            isSelected = false,
            onClick = {},
            selectedColor = ColorCustom.color_background_month_disabled,
            unselectedColor = MaterialTheme.colorScheme.background,
            borderColor = ColorCustom.color_border_button_transaction_type
        )
    }

}