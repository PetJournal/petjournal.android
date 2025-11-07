package com.soujunior.petjournal.ui.components.data

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    label: String,
    value: Long?,
    onValueChange: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    roundedCornerShape:RoundedCornerShape = RoundedCornerShape(10.dp),
    textStyle: TextStyle = LocalTextStyle.current.copy(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        disabledTextColor = Color.DarkGray,
        disabledBorderColor = MaterialTheme.colorScheme.primary,
        disabledLabelColor = MaterialTheme.colorScheme.primary,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
) {
    val _00_00_0000 = stringResource(R.string._00_00_0000)
    val _dd_mm_yyyy = stringResource(R.string.dd_mm_yyyy)

    var showDatePicker by remember { mutableStateOf(false) }

    val formattedDate = remember(value) {
        value?.let {
            val instant = Instant.ofEpochMilli(it)
            val formatter = DateTimeFormatter.ofPattern(_dd_mm_yyyy).withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } ?: _00_00_0000
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            shape = roundedCornerShape,
            label = { Text(label) },
            textStyle = textStyle,
            colors = colors,
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color.LightGray.copy(alpha = 0.6f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.open_calendar),
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            enabled = false
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { showDatePicker = true }
                )
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = value)

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        onValueChange(datePickerState.selectedDateMillis)
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDatePicker = false },
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = SolidColor(MaterialTheme.colorScheme.primary)
                    )
                ) {
                    Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.primary)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Preview(showBackground = true, widthDp = 380)
@Composable
fun CustomDatePickerPreview() {
    var selectedTimestamp by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Exemplo com Borda Vermelha e Texto em Negrito", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        val customModifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f))

        val boldTextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        val redBorderColors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.DarkGray,
            disabledBorderColor = Color.Red,
            disabledLabelColor = Color.Red,
            disabledTrailingIconColor = Color.DarkGray
        )

        CustomDatePicker(
            label = "Data de Nascimento",
            value = selectedTimestamp,
            onValueChange = { selectedTimestamp = it },
            modifier = customModifier,
            textStyle = boldTextStyle,
            colors = redBorderColors
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text("Exemplo Padrão (Roxo)", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        CustomDatePicker(
            label = "Data",
            value = selectedTimestamp,
            onValueChange = { selectedTimestamp = it },
            modifier = Modifier.fillMaxWidth(0.6f)
        )
    }
}
