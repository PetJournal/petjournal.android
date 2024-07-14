package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.state.StatesRegister

@Composable
fun PrivacyPolicyCheckbox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    valueChecked: Boolean,
    onEvent: (Boolean) -> Unit,
) {
    var showPrivacyPolicy by StatesRegister.showPrivacyPolicy.current
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.eu_concordo_com_os))
        withStyle(style = SpanStyle(
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface,
            textDecoration = TextDecoration.Underline
        )
        ) {
            append(" termos de política e privacidade")
        }
    }
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(22.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(
                            1.2.dp,
                            if (valueChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Checkbox(
                        checked = valueChecked,
                        onCheckedChange = { onEvent(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Transparent,
                            uncheckedColor = Color.Transparent,
                            checkmarkColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            Column {
                Text(
                    text = annotatedText,
                    modifier = Modifier.clickable(onClick = { showPrivacyPolicy = true }),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
            }
        }
    }
    if (showPrivacyPolicy) {
        PrivacyPolicy()
    }
}