package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.ActionItem
import com.soujunior.petjournal.ui.components.LogoutButton
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TrailBack
import com.soujunior.petjournal.ui.components.UserProfileHeader
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getTutorViewModelForPreview(): TutorViewModel {
    return if (LocalInspectionMode.current) {
        FakeTutorViewModel()
    } else {
        getViewModel<TutorViewModel>()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TutorScreen(navController: NavController) {
    val viewModel: TutorViewModel = getTutorViewModelForPreview()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ScaffoldCustom(
        modifier = Modifier.fillMaxSize(),
        navigationUp = navController,
        bottomNavigationBar = {
            NavigationBar(
                navController = navController,
                modifier = Modifier.navigationBarsPadding(),
            )
        },
        showBottomBarNavigation = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            LogoutButton(
                onLogoutClick = {
                    viewModel.logout()
                    navController.navigate("account_manager")
                },
                modifier = Modifier.padding(bottom = 10.dp),
            )
        },
        contentToUse = {
            TrailBack()
            Column {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = it,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        UserProfileHeader(
                            name =
                                state.nameUser.replaceFirstChar {
                                    it.uppercase()
                                },
                            email = state.email,
                            imageUrl = null,
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                        ActionItem(
                            title = stringResource(R.string.change_password),
                            onClick = {
                                navController.navigate("forgotPassword")
                            },
                        )
                        ActionItem(
                            title = "Configurações",
                            onClick = { navController.navigate("profile/notificationScreen") },
                        )
                        ActionItem(
                            title = stringResource(R.string.privacy_policy),
                            onClick = { navController.navigate("profile/privacyPolicyScreen") },
                        )
//                        ActionItem(
//                            title = stringResource(R.string.delete_account),
//                            onClick = {},
//                        )
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TutorScreenPreview() {
    PetJournalTheme {
        TutorScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun LogoutButtonPreview() {
    MaterialTheme {
        LogoutButton(onLogoutClick = {
        })
    }
}
