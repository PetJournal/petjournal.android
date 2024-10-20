package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.ColorCustom
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AccountConfirmationDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = ColorCustom.link_200,
            tonalElevation = 8.sdp
        ) {

            Column(
                modifier = Modifier
                    .padding(16.sdp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.title_dialog_Email_Confirmation),
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.sdp)
                )

                Text(
                    text = stringResource(R.string.txt_your_petJournal_account_is_almost_ready_to_activate_it_please_confirm_your_email),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.sdp)

                )

                Text(
                    text = stringResource(R.string.txt_If_you_have_not_registered_with_petJournal_recently_please_ignore_this_email),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.sdp)
                )
                Text(
                    text = stringResource(R.string.txt_petJournal_team),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.sdp)
                )

                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text(text = stringResource(R.string.txt_btn_understood), fontSize = 10.ssp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSimpleDialog() {
    AccountConfirmationDialog(onDismiss = {})
}