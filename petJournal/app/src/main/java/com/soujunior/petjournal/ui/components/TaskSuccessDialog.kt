package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom

@Composable
fun TaskSuccessDialog(
    onNewTaskClick: () -> Unit,
    onGoToHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Box(
            modifier = modifier
                .shadow(
                    elevation = 3.dp,
                    spotColor = ColorCustom.color_shadow_dialog,
                    ambientColor = ColorCustom.color_shadow_dialog
                )
                .border(2.dp, ColorCustom.color_border_dialog, RoundedCornerShape(16.dp))
                .width(330.dp)
                .height(338.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(R.string.label_task_added_successfully),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.scrim,
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(id = R.drawable.success_animals),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .width(71.dp)
                        .height(57.50.dp)
                )

                OutlinedButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    onClick = onNewTaskClick,
                    border = BorderStroke(1.dp, ColorCustom.color_background_button_dialog),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .width(24.dp)
                            .height(24.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = ColorCustom.color_background_button_dialog
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.label_new_task),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight(600),
                        color = ColorCustom.color_border_dialog
                    )
                }

                androidx.compose.material3.Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 6.dp),
                    onClick = onGoToHomeClick,
                    colors = ButtonDefaults.buttonColors(containerColor = ColorCustom.color_border_dialog),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.label_go_to_home),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskSuccessDialogPreview() {
    TaskSuccessDialog(onNewTaskClick = {}, onGoToHomeClick = {})
}
