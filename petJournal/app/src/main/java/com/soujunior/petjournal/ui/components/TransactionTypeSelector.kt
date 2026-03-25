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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.soujunior.petjournal.ui.util.shimmerEffect

@Composable
fun TransactionTypeSelector(
    isLoading: Boolean = false,
    onSelectionChanged: (TransactionType?) -> Unit,
    transactionTypeSelected: TransactionType = TransactionType.Recurrent,
    isLocked: Boolean = false,
    onLockedClick: () -> Unit = {},
) {
    val selectedColor = ColorCustom.color_background_month_disabled
    val unselectedColor = MaterialTheme.colorScheme.background
    val borderColor = ColorCustom.color_border_button_transaction_type

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ToggleButton(
                text = stringResource(R.string.label_recurrent),
                isSelected = transactionTypeSelected == TransactionType.Recurrent,
                isLoading = isLoading,
                isDisabled = isLocked,
                onAction = onLockedClick,
                modifier = Modifier.weight(1f),
                onClick = {
                    onSelectionChanged(TransactionType.Recurrent)
                },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                borderColor = if (transactionTypeSelected == TransactionType.Recurrent) selectedColor else borderColor,
            )

            ToggleButton(
                text = stringResource(R.string.label_one_off),
                isSelected = transactionTypeSelected == TransactionType.OneOff,
                isLoading = isLoading,
                isDisabled = true,
                onAction = onLockedClick,
                modifier = Modifier.weight(1f),
                onClick = {
                    onSelectionChanged(TransactionType.OneOff)
                },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                borderColor = if (transactionTypeSelected == TransactionType.OneOff) selectedColor else borderColor,
            )
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
    borderColor: Color,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isDisabled: Boolean = false,
    onAction: () -> Unit = {},
) {
    val backgroundColor = if (isSelected) selectedColor else unselectedColor

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .height(50.dp)
                .clip(RoundedCornerShape(50))
                .then(
                    if (isLoading) {
                        Modifier.shimmerEffect()
                    } else {
                        Modifier
                            .border(1.dp, borderColor, RoundedCornerShape(50))
                            .background(backgroundColor)
                            .clickable {
                                if (isDisabled) {
                                    onAction() // Executa a ação de bloqueio
                                } else {
                                    onClick() // Executa a seleção normal
                                }
                            }
                    },
                )
                .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Text(
            text = text,
            // Opcional: Adicionar uma opacidade menor se estiver desabilitado para feedback visual
            color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.primary.copy(alpha = if (isDisabled) 0.5f else 1f),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionTypeSelectorPreview() {
    TransactionTypeSelector(
        onSelectionChanged = {},
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
            borderColor = ColorCustom.color_border_button_transaction_type,
        )

        ToggleButton(
            text = stringResource(R.string.label_one_off),
            isSelected = false,
            onClick = {},
            selectedColor = ColorCustom.color_background_month_disabled,
            unselectedColor = MaterialTheme.colorScheme.background,
            borderColor = ColorCustom.color_border_button_transaction_type,
        )
    }
}
