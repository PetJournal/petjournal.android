package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.registerScreen.state.StatesRegister

@Composable
fun PrivacyPolicy() {
    val showPrivacyPolicy = StatesRegister.showPrivacyPolicy.current

    Dialog(
        onDismissRequest = { showPrivacyPolicy.value = false },
        content = { BoxWithPrivacyPolicyText() },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    )
}

@Composable
fun BoxWithPrivacyPolicyText() {
    val showPrivacyPolicy = StatesRegister.showPrivacyPolicy.current
    Column(
        modifier = Modifier
            .size(800.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = { showPrivacyPolicy.value = false }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Botão para fechar a caixa"
                )
            }
        }
        LazyColumn {
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_0_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_0_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_1_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_1_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_2_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_2_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_3_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_3_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_4_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_4_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_5_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_5_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_6_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_6_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_7_title),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.privacy_policy_item_7_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}