package com.soujunior.petjournal.ui.screensapp.screenTutor.config.notifyScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.switchComponent.SwitchOptionItem
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showNotificationDialog by remember { mutableStateOf(false) }
    var showAlarmDialog by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            viewModel.updatePermissionsStatus(context)
        }

    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.updatePermissionsStatus(context)
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.updatePermissionsStatus(context)
    }

    if (showNotificationDialog) {
        PermissionExplanationDialog(
            title = stringResource(R.string.settings_permission_dialog_title),
            description = stringResource(R.string.settings_notification_permission_description),
            onConfirm = {
                showNotificationDialog = false
                val intent =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        }
                    } else {
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    }
                context.startActivity(intent)
            },
            onDismiss = { showNotificationDialog = false },
        )
    }

    if (showAlarmDialog) {
        PermissionExplanationDialog(
            title = stringResource(R.string.settings_permission_dialog_title),
            description = stringResource(R.string.settings_exact_alarm_permission_description),
            onConfirm = {
                showAlarmDialog = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val intent =
                        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    context.startActivity(intent)
                }
            },
            onDismiss = { showAlarmDialog = false },
        )
    }

    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary)) {
        ScaffoldCustom(
            modifier =
                Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding(),
            navigationUp = navController,
            showTopBar = true,
            titleTopBar = stringResource(R.string.config),
            bottomNavigationBar = {
                NavigationBar(
                    navController = navController,
                    modifier =
                        Modifier
                            .navigationBarsPadding()
                            .statusBarsPadding(),
                )
            },
            showButtonToReturn = true,
            showBottomBarNavigation = true,
            contentToUse = {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = it,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        SwitchOptionItem(
                            title = stringResource(R.string.settings_dark_mode),
                            checked = uiState.isDarkMode,
                            onCheckedChange = { viewModel.toggleDarkMode(it) },
                        )
                        SwitchOptionItem(
                            title = stringResource(R.string.settings_notifications),
                            checked = uiState.isNotificationEnabled,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    showNotificationDialog = true
                                } else {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data = Uri.fromParts("package", context.packageName, null)
                                        }
                                    context.startActivity(intent)
                                }
                            },
                        )
                        /*SwitchOptionItem(
                            title = stringResource(R.string.settings_exact_alarm),
                            checked = uiState.isExactAlarmEnabled,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    showAlarmDialog = true
                                } else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                                            data = Uri.fromParts("package", context.packageName, null)
                                        }
                                        context.startActivity(intent)
                                    }
                                }
                            },
                        )*/
                    }
                }
            },
        )
    }
}

@Composable
fun PermissionExplanationDialog(
    title: String,
    description: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = description) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.settings_go_to_settings))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TutorScreenPreview() {
    PetJournalTheme {
        SettingsScreen(navController = rememberNavController())
    }
}
