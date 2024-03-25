package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.components.mask.formatDate

@Composable
fun DateInputText(
    modifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    titleText: String = "Title",
    textValue: String,
    isError: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    Column(modifier = modifier) {
        Row {
            Text(
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
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .height(50.dp)
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
                        BorderStroke(
                            width = 2.dp,
                            color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp)),
                value = textValue,
                onValueChange = { newValue ->
                    onEvent(newValue)
                },
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                placeholder = { Text(text = placeholderText) },
                maxLines = 1,
                visualTransformation = visualTransformation
            )
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
                    fontSize = 15.sp
                )
            }
        }
    }
}

