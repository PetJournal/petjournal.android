package com.soujunior.petjournal.ui.screensapp.screenTutor.config.notifyScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.switchComponent.SwitchOptionItem
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
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
                            title = "Tema escuro",
                            checked = uiState.isDarkMode,
                            onCheckedChange = { viewModel.toggleDarkMode(it) },
                        )
//                        SwitchOptionItem(
//                            title = "Quer receber as notificações por e-mail?",
//                            checked = false,
//                            onCheckedChange = {},
//                        )
//                        SwitchOptionItem(
//                            title = "Notificar pelo celular",
//                            checked = false,
//                            onCheckedChange = {},
//                        )
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TutorScreenPreview() {
    PetJournalTheme {
        SettingsScreen(navController = rememberNavController())
    }
}
