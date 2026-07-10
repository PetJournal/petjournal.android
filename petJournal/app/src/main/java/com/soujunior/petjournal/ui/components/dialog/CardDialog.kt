package com.soujunior.petjournal.ui.components.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun CardDialog(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.label_task_added_successfully),
    textTopButton: String = stringResource(R.string.label_new_task),
    textCenterButton: String = stringResource(R.string.label_go_to_home),
    textFooterButton: String = stringResource(R.string.footer_button),
    subText: String? = null,
    onButtonTopClick: (() -> Unit)? = null,
    onButtonCenterClick: (() -> Unit)? = null,
    onButtonFooterClick: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false),
    ) {
        Box(
            modifier =
                modifier
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(16.dp),
                    )
                    .width(330.dp)
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Image(
                    painter = painterResource(id = R.drawable.success_animals),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .padding(vertical = 24.dp)
                            .size(width = 80.dp, height = 65.dp),
                )

                if (onButtonTopClick != null || onButtonCenterClick != null) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        onButtonTopClick?.let {
                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = it,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Text(
                                    text = textTopButton,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }

                        onButtonCenterClick?.let {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = it,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Text(
                                    text = textCenterButton,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.background,
                                )
                            }
                        }

                        onButtonFooterClick?.let {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = it,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Text(
                                    text = textFooterButton,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.background,
                                )
                            }
                        }

                        subText?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardDialogWithoutButtonsPreview() {
    CardDialog()
}

@Preview
@Composable
fun CardDialogWithoutTopButtonPreview() {
    CardDialog(onButtonCenterClick = {})
}

@Preview
@Composable
fun CardDialogSubPreview() {
    CardDialog(
        onButtonCenterClick = {},
        subText = "Subtitulo",
    )
}

@Preview
@Composable
fun CardDialogWithoutBottomButtonPreview() {
    CardDialog(
        onButtonTopClick = {},
        onButtonCenterClick = {},
        onButtonFooterClick = {},
    )
}

@Preview
@Composable
fun CardDialogPreview() {
    CardDialog(onButtonCenterClick = {}, onButtonTopClick = {})
}
