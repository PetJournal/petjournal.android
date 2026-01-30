package com.soujunior.petjournal.ui.components.switchComponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@Composable
fun SwitchCustom(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val checkedTrackColor = MaterialTheme.colorScheme.primary
    val uncheckedTrackColor = Color.Transparent
    val uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant

    val checkedThumbColor = Color.White
    val uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant

    val trackColor by animateColorAsState(
        targetValue = if (checked) checkedTrackColor else uncheckedTrackColor,
        animationSpec = tween(300),
        label = "trackColor",
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) checkedTrackColor else uncheckedBorderColor,
        animationSpec = tween(300),
        label = "borderColor",
    )

    val thumbColor by animateColorAsState(
        targetValue = if (checked) checkedThumbColor else uncheckedThumbColor,
        animationSpec = tween(300),
        label = "thumbColor",
    )

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 28.dp else 7.dp,
        animationSpec = tween(300),
        label = "thumbOffset",
    )

    Box(
        modifier =
            modifier
                .width(50.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(50))
                .background(trackColor)
                .border(
                    width = 3.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(50),
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    onCheckedChange(!checked)
                },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier =
                Modifier
                    .offset(x = thumbOffset)
                    .size(if (checked) 20.dp else 15.dp)
                    .background(color = thumbColor, shape = CircleShape),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SwitchCustomUncheckedPreview() {
    val (checked, setChecked) = remember { mutableStateOf(false) }
    PetJournalTheme {
        Box(
            modifier =
                Modifier
                    .padding(20.dp)
                    .size(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            SwitchCustom(
                checked = checked,
                onCheckedChange = setChecked,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwitchCustomCheckedPreview() {
    val (checked, setChecked) = remember { mutableStateOf(true) }
    PetJournalTheme {
        Box(
            modifier =
                Modifier
                    .padding(20.dp)
                    .size(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            SwitchCustom(
                checked = checked,
                onCheckedChange = setChecked,
            )
        }
    }
}
