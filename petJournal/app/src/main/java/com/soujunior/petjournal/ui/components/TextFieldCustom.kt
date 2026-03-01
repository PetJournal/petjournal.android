package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.shimmerEffect

@Composable
fun TextFieldCustom(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight(500),
            style = MaterialTheme.typography.titleMedium,
            modifier =
                if (isLoading) {
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                } else {
                    Modifier
                },
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = !isLoading,
            textStyle =
                TextStyle(
                    color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
                ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = if (isLoading) Color.Transparent else ColorCustom.color_placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            modifier =
                Modifier
                    .then(
                        if (isLoading) {
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .shimmerEffect()
                        } else {
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .height(100.dp)
                        },
                    ),
            shape = RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ColorCustom.color_placeholder,
                    unfocusedBorderColor = ColorCustom.color_placeholder,
                    disabledBorderColor = if (isLoading) Color.Transparent else ColorCustom.color_placeholder,
                ),
        )
    }
}

@Preview
@Composable
fun DescriptionTextFieldPreview() {
    TextFieldCustom(
        title = "Título",
        placeholder = "Descrição",
        value = "",
        onValueChange = {},
    )
}
